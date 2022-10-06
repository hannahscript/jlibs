package com.github.hannahscript.jlibs.service;

import com.github.hannahscript.jlibs.model.RenderedStory;
import com.github.hannahscript.jlibs.model.Template;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class TemplateService {
    private final Pattern promptRgx = Pattern.compile("\\{\\{(.+?)\\}\\}");

    private final List<Template> templates = new ArrayList<>();

    public TemplateService() {
        saveTemplate("the {{adjective}} {{adjective}} {{noun animal}} {{verb}} over the {{adjective}} {{noun animal}}");
    }

    public Template saveTemplate(String rawTemplate) {
        Template template = new Template();

        StringBuilder sb = new StringBuilder();
        Matcher m = this.promptRgx.matcher(rawTemplate);
        int n = 0;
        while (m.find()) {
            m.appendReplacement(sb, "{{"+ (n++) +"}}");
            template.getPromptDescriptions().add(m.group(1));
        }
        m.appendTail(sb);

        template.setRawTemplate(sb.toString());

        template.setId(this.templates.size());
        this.templates.add(template);

        return template;
    }

    public RenderedStory renderTemplate(Template template, Map<Integer, String> prompts, String username) {
        String renderedTemplate = template.getRawTemplate();
        for (Map.Entry<Integer, String> entry : prompts.entrySet()) {
            int promptId = entry.getKey();
            String answer = entry.getValue();

            renderedTemplate = renderedTemplate.replaceFirst("\\{\\{" + promptId + "\\}\\}", answer);
        }

        return new RenderedStory(username, renderedTemplate);
    }

    public Template getRandomTemplate() {
        try {
            return this.templates.get(SecureRandom.getInstanceStrong().nextInt(this.templates.size()));
        } catch (NoSuchAlgorithmException e) {
            System.out.println("too fucking bad");
            System.exit(1);
            return null;
        }
    }
}
