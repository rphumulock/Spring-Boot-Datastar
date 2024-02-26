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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/games")
public class GameController {

	private static final Logger logger = LogManager.getLogger(GameController.class);

	@Autowired
	private GameService gameService;

	@Autowired
	private SSEService sseService;

	@Autowired
	private TemplateService templateService;
	public GameController(GameService gameService) {}

	@GetMapping("/showFormForAdd")
	public String showFormForAdd(Model theModel) {

		// create model attribute to bind form data
		Game game = new Game();

		theModel.addAttribute("employee", game);

		return "games/employee-form";
	}

	@PostMapping("/showFormForUpdate")
	public String showFormForUpdate(@RequestParam("employeeId") int theId,
									Model theModel) {

		// get the employee from the service
		Game game = gameService.findById(theId);

		// set employee as a model attribute to pre-populate the form
		theModel.addAttribute("employee", game);

		// send over to our form
		return "employees/employee-form";
	}

	@GetMapping("/{id}")
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
		sseService.RenderFragment(sse, options, model, "test1");
	}

//	@GetMapping("/battle")
//	public void streamEvents(HttpServletResponse response) {
//		response.setContentType("text/event-stream");
//		response.setCharacterEncoding("UTF-8");
//		response.setHeader("Cache-Control", "no-cache");
//		response.setHeader("Connection", "keep-alive");
//
//		List<Game> games = gameService.findBattle();
//		Map<String, Object> model = new HashMap<>();
//		model.put("games", games);
//		String htmlContent = templateService.renderTemplate("test1", model);
//
//		try (PrintWriter writer = response.getWriter()) {
//			// Data format for SSE
//			writer.write("event: datastar-fragment" + "\n");
//			writer.write("id: " + UUID.randomUUID() + "\n");
//			writer.write("data: selector " + "\n");
//			writer.write("data: merge morph_element" + "\n");
//			writer.write("data: settle 0" + "\n");
//			writer.write("data: fragment " + htmlContent);
//			writer.flush(); // Flush the writer to send data immediately
//		} catch (IOException e) {
//			// Handle IO exception
//			logger.info("Error: " + e.getMessage());
//		}
//	}

//	@GetMapping("/battle")
//	public String getBattle(Model theModel) {
//		List<Game> games = gameService.findBattle();
//		theModel.addAttribute("games", games);
//		return "fragments/battle";
//	}

	@PostMapping("/save")
	public String saveEmployee(@ModelAttribute("game") Game game) {

		// save the employee
		gameService.save(game);

		// use a redirect to prevent duplicate submissions
		return "redirect:/employees/list";
	}

	@PostMapping("/delete")
	public String delete(@RequestParam("employeeId") int theId) {

		// delete the employee
		gameService.deleteById(theId);

		// redirect to /employees/list
		return "redirect:/employees/list";

	}
}