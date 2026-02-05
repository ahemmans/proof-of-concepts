package demo.ai.service;


import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.evaluation.FactCheckingEvaluator;
import org.springframework.ai.chat.evaluation.RelevancyEvaluator;
import org.springframework.ai.evaluation.EvaluationRequest;
import org.springframework.ai.evaluation.EvaluationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import demo.ai.service.dto.Answer;
import demo.ai.service.dto.Question;

@Disabled
@SpringBootTest
public class SpringAIACAServiceTest {

	@Autowired
	private SpringAIACAService springAIACAService;
	
	@Autowired
	private ChatClient.Builder chatClientBuilder;
	
	private RelevancyEvaluator relevancyEvaluator;
	private FactCheckingEvaluator factCheckingEvaluator;
	
	String userText = "Why is the sky blue?";
	
	@BeforeEach
	public void setup() {		
		this.relevancyEvaluator = new RelevancyEvaluator(chatClientBuilder);
		//this.factCheckingEvaluator = new FactCheckingEvaluator(chatClientBuilder); 
	}
	
	@Test
	  public void evaluateRelevancy() {
		String appName = "AIR";
	    String userText = "Why is the sky blue?";
	    Question question = new Question(appName, userText);
	    Answer answer = springAIACAService.askQuestion(question);

	    EvaluationRequest evaluationRequest = new EvaluationRequest(userText, answer.answer());
	    EvaluationResponse response = relevancyEvaluator.evaluate(evaluationRequest);

	    Assertions.assertThat(response.isPass())
	        .withFailMessage("""
	          ========================================
	          The answer "%s"
	          is not considered relevant to the question
	          "%s".
	          ========================================
	          """, answer.answer(), userText)
	        .isTrue();
	  }
	
	@Disabled
	@Test
	  public void evaluateFactualAccuracy() {
		String appName = "AIR";
	    var userText = "Why is the sky blue?";
	    var question = new Question(appName, userText);
	    var answer = springAIACAService.askQuestion(question);

	    var evaluationRequest = new EvaluationRequest(userText, answer.answer());
	    var response = factCheckingEvaluator.evaluate(evaluationRequest);

	    Assertions.assertThat(response.isPass())
	        .withFailMessage("""
	          ========================================
	          The answer "%s"
	          is not considered correct for the question
	          "%s".
	          ========================================
	          """, answer.answer(), userText)
	        .isTrue();
	  }
	
}
