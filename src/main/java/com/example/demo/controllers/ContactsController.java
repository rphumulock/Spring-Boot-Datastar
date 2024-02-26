package com.example.demo.controllers;

import com.example.demo.entity.Game;
import com.example.demo.services.GameService;
import com.example.demo.services.SSEService;
import com.example.demo.services.TemplateService;
import com.example.demo.utilities.FragmentMergeType;
import com.example.demo.utilities.QuerySelector;
import com.example.demo.utilities.RenderFragmentOptions;
import com.example.demo.utilities.SseEventType;
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
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/contact")
public class ContactsController {
    Logger logger = LoggerFactory.getLogger(ContactsController.class);

    @Autowired
    SSEService sseService;

    @Autowired
    GameService gameService;

    @Autowired
    private TemplateService templateService; // This service will render Thymeleaf templates

    @GetMapping("/battle")
    public void streamEvents(HttpServletResponse response) {
        HttpServletResponse sse = sseService.NewSSE(response);

        RenderFragmentOptions options = new RenderFragmentOptions(
                SseEventType.SSE_EVENT_TYPE_FRAGMENT,
                QuerySelector.FRAGMENT_SELECTOR_USE_ID,
                FragmentMergeType.MORPH_ELEMENT,
                Duration.ZERO
        );

        List<Game> games = gameService.findBattle();
        Map<String, Object> model = new HashMap<>();
        model.put("games", games);
        sseService.RenderFragment(sse, options, model, "test");
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

