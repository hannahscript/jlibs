package com.github.hannahscript.jlibs.model;

import com.github.hannahscript.jlibs.service.PromptService;
import com.github.hannahscript.jlibs.service.TemplateService;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class GameManager {
    private final TemplateService templateService;
    private final PromptService promptService;

    public GameManager(TemplateService templateService, PromptService promptService) {
        this.templateService = templateService;
        this.promptService = promptService;
    }

    public void startGame() {
        log.info("Starting game");
        Template template = this.templateService.getRandomTemplate();
        log.info("Selected template #{}", template.getId());
        this.promptService.setup(template);
        this.promptService.sendNewPrompt();
    }
}
