package com.github.hannahscript.jlibs.wscontroller;

import com.github.hannahscript.jlibs.message.server.WelcomeMessage;
import com.github.hannahscript.jlibs.message.user.PromptMessage;
import com.github.hannahscript.jlibs.message.user.ReadyForInteractionMessage;
import com.github.hannahscript.jlibs.message.user.StartGameMessage;
import com.github.hannahscript.jlibs.model.GameManager;
import com.github.hannahscript.jlibs.service.MessageSender;
import com.github.hannahscript.jlibs.service.PromptService;
import com.github.hannahscript.jlibs.service.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;

import java.security.Principal;

@Controller
@Log4j2
public class MessageController {
    private final SimpMessagingTemplate template;
    private final GameManager gameManager;
    private PromptService promptService;
    private final MessageSender messageSender;
    private final UserService userService;

    public MessageController(SimpMessagingTemplate template, GameManager gameManager, PromptService promptService, MessageSender messageSender, UserService userService) {
        this.template = template;
        this.gameManager = gameManager;
        this.promptService = promptService;
        this.messageSender = messageSender;
        this.userService = userService;
    }

    @MessageMapping("/prompt")
    //@SendTo("/topic/welcome")
    public void enterPrompt(PromptMessage msg, Principal principal) {
        log.info("PromptMessage " + msg);
        this.promptService.enterPrompt(principal.getName(), msg);
    }

    @MessageMapping("/ready")
    public void readyForInteraction(ReadyForInteractionMessage message, Principal user) {
        log.info("User connection is ready: " + user.getName());
        this.messageSender.broadcastNewUserConnected(user.getName());
        this.messageSender.sendWelcomeMessage(user.getName(), this.userService.getUsers());
    }

    @MessageMapping("/start")
    public void startGame(StartGameMessage message) {
        this.gameManager.startGame();
    }

    /*@SubscribeMapping("/topic/stories")
    @SendToUser("/queue/welcome")
    public WelcomeMessage foo() {
        log.info("Subscribemapping /topic/stories");
        return new WelcomeMessage(this.userService.getUsers());
    }*/

    @SubscribeMapping("/queue/welcome")
    //@SendToUser("/queue/welcome")
    public WelcomeMessage broadcastUsers(Principal principal) {
        log.info("Subscribemapping /queue/welcome");
        return new WelcomeMessage(this.userService.getUsers());
    }
}
