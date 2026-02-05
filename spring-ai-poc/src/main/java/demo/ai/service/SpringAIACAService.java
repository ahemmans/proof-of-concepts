package demo.ai.service;

import java.util.List;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.document.Document;
import org.springframework.ai.chat.metadata.Usage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import demo.ai.db.model.tools.FillableFormTool;
import demo.ai.db.model.tools.TransmissionTools;
import demo.ai.service.dto.Answer;
import demo.ai.service.dto.Question;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

@Slf4j
@Service
public class SpringAIACAService implements ACAService {

	private final ChatClient chatClient;
	private final ACARAGSubmissionSearchService acaRagSubmissionSearchService;
	private final ACARulesService acaRulesService;
	private final FillableFormTool fillableFormTool;
	private final TransmissionTools transmissionTools;
	
	private List<Document> submissionDocs;
	private String appRules;
	
	@Value("classpath:/promptTemplates/systemPromptTemplate.st")
	Resource promptTemplate;
	
	public SpringAIACAService(ChatClient.Builder chatClientBuilder, 
			ACARAGSubmissionSearchService acaRagSubmissionSearchService, 
			ACARulesService acaRulesService, 
			FillableFormTool fillableFormTool, 
			TransmissionTools transmissionTools
			) {
			
		super();
		this.chatClient = chatClientBuilder.build();
		this.acaRagSubmissionSearchService = acaRagSubmissionSearchService;
		this.acaRulesService = acaRulesService;
		this.fillableFormTool = fillableFormTool;
		this.transmissionTools = transmissionTools;
	}
	
	@Override
	public Answer askQuestion(Question question) {
	
		getRAGDocs_and_appRules(question);
		
		var response = chatClient.prompt()
			.system(systemSpec -> systemSpec
				.text(promptTemplate)
				.param("appName", question.appName())
				.param("question",question.question())
				.param("submissionDocs", submissionDocs)
				.param("rules", appRules)
				)
			.user(question.question())
			.call()
			.content();
		
		return new Answer(question.appName(), response);		
	}

	@Override
	public String askAnything(Question question) {	
		
		var response = chatClient.prompt()
			.system(systemSpec -> systemSpec
				.param("question",question.question())
				)
			.user(question.question())
			.call()
			.content();
		
		return response;		
	}
	
	@Override    
	public String askQuestionStr(Question question) {
	
		getRAGDocs_and_appRules(question);
		
		var response = chatClient.prompt()
			.system(systemSpec -> systemSpec
					.text(promptTemplate)
					.param("appName", question.appName())
					.param("question",question.question())
					.param("submissionDocs", submissionDocs)
					.param("rules", appRules)
					)
			.user(question.question())			
			.call()
			.content();
		
		return response;		
	}
	
	@Override
	public Flux<String> askStreamQuestion(Question question) {
	
		getRAGDocs_and_appRules(question);
		
		return chatClient.prompt()
			.system(systemSpec -> systemSpec
				.text(promptTemplate)
				.param("appName", question.appName())
				.param("question",question.question())
				.param("submissionDocs", submissionDocs)
				.param("rules", appRules)
				)
			.tools(fillableFormTool)
			.tools(transmissionTools)
			.user(question.question())
		    .stream()
		    .content();
	}
	
	private void getRAGDocs_and_appRules(Question question) {
		submissionDocs = acaRagSubmissionSearchService.getRAGSubmissionDocListFor(question);
		appRules = acaRulesService.getRulesFor(question.appName());
	}
	
	private void logUsage(Usage usage) {
	    log.info("Token usage: prompt={}, generation={}, total={}",
	        usage.getPromptTokens(),
	        usage.getCompletionTokens(),
	        usage.getTotalTokens());
	  }
	
}
