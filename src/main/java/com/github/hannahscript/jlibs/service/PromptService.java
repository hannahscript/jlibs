package com.github.hannahscript.jlibs.service;

import com.github.hannahscript.jlibs.message.user.PromptMessage;
import com.github.hannahscript.jlibs.model.PromptRequest;
import com.github.hannahscript.jlibs.model.RenderedStory;
import com.github.hannahscript.jlibs.model.Template;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@Log4j2
public class PromptService {
    private final UserService userService;
    private final TemplateService templateService;
    private final MessageSender messageSender;

    // User -> (Prompt id -> answer)
    private final Map<String, Map<Integer, String>> allAnswers = new HashMap<>();
    private final Map<String, PromptMessage> currentPromptAnswers = new HashMap<>();

    private int currentPromptId = 0;
    private Template template;

    public PromptService(UserService userService, TemplateService templateService, MessageSender messageSender) {
        this.userService = userService;
        this.templateService = templateService;
        this.messageSender = messageSender;
    }

    // called when game starts
    public void setup(Template template) {
        this.template = template;

        for (String user : this.userService.getUsers()) {
            this.allAnswers.put(user, new HashMap<>());
        }
    }

    public void enterPrompt(String enteringUser, PromptMessage promptMessage) {
        if (this.currentPromptAnswers.containsKey(enteringUser)) {
            log.error("User entered second answer");
            //  todo complain, changing answers is not allowed
        }

        if (promptMessage.getPromptId() != this.currentPromptId) {
            log.error("Received outdated answer message");
            // todo complain, outdated answer
        }

        log.info("Saving answer for prompt {} and user {}", this.currentPromptId, enteringUser);
        this.currentPromptAnswers.put(enteringUser, promptMessage);
        this.messageSender.sendUserEnteredPromptMessage(enteringUser);

        if (this.currentPromptAnswers.size() == this.userService.getTotalUsers()) {
            log.info("All users have entered an answer for prompt {}, proceeding...", this.currentPromptId);
            for (Map.Entry<String, PromptMessage> entry : this.currentPromptAnswers.entrySet()) {
                String username = entry.getKey();
                PromptMessage currentAnswer = entry.getValue();

                this.allAnswers.get(username).put(currentAnswer.getPromptId(), currentAnswer.getAnswer());
            }

            this.currentPromptAnswers.clear();
            this.currentPromptId++;

            if (this.currentPromptId < this.template.getPromptDescriptions().size()) {
                sendNewPrompt();
            } else {
                renderAll();
            }
        }
    }

    public void renderAll() {
        log.info("Rendering all stories");
        Map<String, RenderedStory> stories = new HashMap<>();
        for (Map.Entry<String, Map<Integer, String>> entry : this.allAnswers.entrySet()) {
            String username = entry.getKey();
            Map<Integer, String> answers = entry.getValue();

            RenderedStory story = this.templateService.renderTemplate(this.template, answers, username);
            stories.put(username, story);
        }

        this.messageSender.broadcastStories(stories);
    }

    public void sendNewPrompt() {
        log.info("Sending next prompt");
        PromptRequest promptRequest = new PromptRequest(this.currentPromptId, this.template.getPromptDescriptions().get(this.currentPromptId));
        this.messageSender.broadcastNewPrompt(promptRequest);
    }
}
