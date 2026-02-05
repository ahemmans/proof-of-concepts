package demo.ai.web.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import demo.ai.service.ACAService;
import demo.ai.service.dto.Answer;
import demo.ai.service.dto.Question;
import jakarta.validation.Valid;
import reactor.core.publisher.Flux;

@RestController
@CrossOrigin
public class AskController {

	private final ACAService acaService;

	public AskController(ACAService acaService) {
		super();
		this.acaService = acaService;
	}
	
	@PostMapping(path="/ask", produces = "application/json")
	public Answer ask(	@RequestHeader(name="X_AI_CONVERSATION_ID", defaultValue="default") String conversationId,
						@RequestBody @Valid Question question) {
		return acaService.askQuestion(question);
	}

	@PostMapping(path="/ask-anything", produces = "application/json")
	public Answer ask_anything(@RequestBody Question question) {
		return acaService.askQuestion(question);
	}
	
	@PostMapping(path="/ask-str", produces = "application/json")
	public String ask_str(@RequestBody @Valid Question question) {
		return acaService.askQuestionStr(question);
	}
	
	@GetMapping(path="/stream", produces = "application/json")
	public Flux<String> askStream(	@RequestHeader(name="X_AI_CONVERSATION_ID", defaultValue="default") String conversationId,
									@RequestParam String message, @RequestParam("app") String appName) {
	    Question question = new Question(appName, message);
		return acaService.askStreamQuestion(question);
	}
	
}
