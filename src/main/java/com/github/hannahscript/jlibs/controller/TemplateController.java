package com.github.hannahscript.jlibs.controller;

import com.github.hannahscript.jlibs.model.Template;
import com.github.hannahscript.jlibs.service.TemplateService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TemplateController {
    private final TemplateService templateService;

    public TemplateController(TemplateService templateService) {
        this.templateService = templateService;
    }

    @PostMapping("/template")
    public Template saveTemplate(@RequestBody String template) {
        return this.templateService.saveTemplate(template);
    }
}
