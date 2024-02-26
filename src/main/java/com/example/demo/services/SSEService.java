package com.example.demo.services;

import com.example.demo.controllers.ContactsController;
import com.example.demo.utilities.RenderFragmentOptions;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.UUID;

@Service

public class SSEService {

    Logger logger = LoggerFactory.getLogger(ContactsController.class);

    @Autowired
    TemplateService templateService;

    public HttpServletResponse NewSSE(HttpServletResponse response) {
        response.setContentType("text/event-stream");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Connection", "keep-alive");
        return response;
    }

   public void RenderFragment(HttpServletResponse response, RenderFragmentOptions options, Map<String, Object> model, String template) {
        String htmlContent = templateService.renderTemplate(template, model);

        try (PrintWriter writer = response.getWriter()) {
            // Data format for SSE
            writer.write("event: " + options.getSseEventType().getValue() + "\n");
            writer.write("id: " + UUID.randomUUID() + "\n");
            writer.write("data: selector " + options.getQuerySelector().getValue() + "\n");
            writer.write("data: merge " + options.getMerge().getValue() + "\n");
            writer.write("data: settle 0" + "\n");
            writer.write("data: fragment " + htmlContent);
            writer.flush(); // Flush the writer to send data immediately
        } catch (IOException e) {
            // Handle IO exception
            logger.info("Error: " + e.getMessage());
        }
    }
}
