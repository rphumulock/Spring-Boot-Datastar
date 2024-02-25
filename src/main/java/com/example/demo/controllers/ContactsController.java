package com.example.demo.controllers;

import com.example.demo.services.TemplateService;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/contact")
public class ContactsController {
    Logger logger = LoggerFactory.getLogger(ContactsController.class);

    @Autowired
    private TemplateService templateService; // This service will render Thymeleaf templates

    @GetMapping("/{id}")
    public void streamEvents(HttpServletResponse response) {
        response.setContentType("text/event-stream");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Connection", "keep-alive");

        Map<String, Object> model = new HashMap<>();
        model.put("emitterId", "YOYO");
        String htmlContent = templateService.renderTemplate("test1", model);

        try (PrintWriter writer = response.getWriter()) {
            // Data format for SSE
            writer.write("event: datastar-fragment" + "\n");
            writer.write("id: " + UUID.randomUUID() + "\n");
            writer.write("data: selector " + "\n");
            writer.write("data: merge morph_element" + "\n");
            writer.write("data: settle 0" + "\n");
            writer.write("data: fragment " + htmlContent);
            writer.flush(); // Flush the writer to send data immediately
        } catch (IOException e) {
            // Handle IO exception
            logger.info("Error: " + e.getMessage());
        }
    }

    @PostMapping("/{id}/dispatch")
    public void dispatch(HttpServletResponse response) {
        response.setContentType("text/event-stream");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Connection", "keep-alive");

        Map<String, Object> model = new HashMap<>();
        model.put("emitterId", "YOYO");
        String htmlContent = templateService.renderTemplate("test", model);

        try (PrintWriter writer = response.getWriter()) {
            // Data format for SSE
            writer.write("event: datastar-fragment" + "\n");
            writer.write("id: " + UUID.randomUUID() + "\n");
            writer.write("data: selector " + "\n");
            writer.write("data: merge morph_element" + "\n");
            writer.write("data: settle 0" + "\n");
            writer.write("data: fragment " + htmlContent);
            writer.flush();
        } catch (IOException e) {
            // Handle IO exception
        }
    }

}

