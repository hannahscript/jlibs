package com.github.hannahscript.jlibs.config;

import com.github.hannahscript.jlibs.exception.AuthenticationException;
import com.github.hannahscript.jlibs.service.MessageSender;
import com.github.hannahscript.jlibs.service.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.stereotype.Component;

import java.security.Principal;

@Component
@Log4j2
public class AuthenticationChannelInterceptor implements ChannelInterceptor {
    private static final String USERNAME_HEADER = "username";
    private static final String PASSWORD_HEADER = "password";

    private final WebSocketAuthenticator webSocketAuthenticator;
    @Autowired
    private MessageSender messageSender;
    private final UserService userService;

    public AuthenticationChannelInterceptor(WebSocketAuthenticator webSocketAuthenticator /*,MessageSender messageSender*/, UserService userService) {
        this.webSocketAuthenticator = webSocketAuthenticator;
        //this.messageSender = messageSender;
        this.userService = userService;
    }

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

        if (accessor.getCommand() == StompCommand.CONNECT) {
            String username = accessor.getFirstNativeHeader(USERNAME_HEADER);
            String password = accessor.getFirstNativeHeader(PASSWORD_HEADER);

            if (this.userService.hasUser(username)) {
                throw new AuthenticationException("Username already exists: " + username);
            }

            accessor.setUser(this.webSocketAuthenticator.authenticate(username, password));
            this.userService.addUser(username);
        }

        return message;
    }

    @Override
    public void postSend(Message<?> message, MessageChannel channel, boolean sent) {
        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);


        Principal user = accessor.getUser();
        log.info("postSend " + accessor.getCommand() + " " + user);
        if (accessor.getCommand() != StompCommand.CONNECT || user == null) return;

        //this.messageSender.broadcastNewUserConnected(user.getName());
        //this.messageSender.sendWelcomeMessage(user.getName(), this.userService.getUsers());
    }
}
