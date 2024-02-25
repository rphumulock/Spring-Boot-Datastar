package com.example.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.util.Map;

@Service
public class TemplateService {

    private final SpringTemplateEngine templateEngine;

    @Autowired
    public TemplateService(SpringTemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    public String renderTemplate(String templateName, Map<String, Object> model) {
        Context context = new Context();
        model.forEach(context::setVariable);
        String renderedHtml = templateEngine.process(templateName, context);

        // Replace newlines if necessary. This is a simplistic approach; consider more robust HTML minification if needed.
        return renderedHtml.replace("\n", "");
    }


}
