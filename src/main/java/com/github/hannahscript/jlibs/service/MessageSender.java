package com.github.hannahscript.jlibs.service;

import com.github.hannahscript.jlibs.message.server.*;
import com.github.hannahscript.jlibs.model.PromptRequest;
import com.github.hannahscript.jlibs.model.RenderedStory;
import lombok.extern.log4j.Log4j2;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Set;

@Service
@Log4j2
public class MessageSender {
    private final SimpMessagingTemplate template;

    public MessageSender(SimpMessagingTemplate template) {
        this.template = template;
    }

    public void sendError(String user, ErrorMessage.ErrorId errorId, String errorMessage) {
        ErrorMessage msg = new ErrorMessage(errorId, errorMessage);

        this.template.convertAndSendToUser(user, "/queue/error", msg);
    }

    public void broadcastNewPrompt(PromptRequest promptRequest) {
        log.info("Sending new prompt {}", promptRequest);
        this.template.convertAndSend("/topic/prompt", promptRequest);
    }

    public void broadcastFinishedStory(RenderedStory story) {
        this.template.convertAndSend("/topic/story", story);
    }

    public void broadcastNewUserConnected(String username) {
        this.template.convertAndSend("/topic/user", new NewUserConnectedMessage(username));
    }

    public void sendWelcomeMessage(String username, Set<String> currentUsers) {
        this.template.convertAndSendToUser(username, "/queue/welcome", new WelcomeMessage(currentUsers));
    }

    public void sendUserEnteredPromptMessage(String username) {
        this.template.convertAndSend("/topic/entered", new UserEnteredPromptMessage(username));
    }

    public void broadcastStories(Map<String, RenderedStory> stories) {
        log.info("Sending finished stories {}", stories);
        this.template.convertAndSend("/topic/stories", new PresentationMessage(stories));
    }
}
