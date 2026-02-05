package demo.ai.service;

import java.util.List;
import java.util.stream.Collectors;

import demo.ai.service.dto.Question;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.filter.FilterExpressionBuilder;

@Slf4j
@Service
public class ACARAGSubmissionSearchService {

	private final VectorStoreService vectorStoreService;
	
	public ACARAGSubmissionSearchService(VectorStoreService vectorStoreService) {
		super();
		this.vectorStoreService = vectorStoreService;
	}
	  
	public List<Document> getRAGSubmissionDocListFor(Question question) {
	
		var searchRequest = buildSearchRequest(question);
		log.info("{searchRequest: {}}", searchRequest.toString());
		
		var similarDoc = this.vectorStoreService.findSimilarDocs(searchRequest);
		
		return similarDoc;		
					
	}
	  
	public List<String> getRAGSubmissionStringListFor(Question question) {
		
		var searchRequest = buildSearchRequest(question);
		log.info("{searchRequest: {}}", searchRequest.toString());
		
		var similarDoc = this.vectorStoreService.findSimilarDocs(searchRequest)
			.stream()
			.map(Document::getText)
			.collect(Collectors.toList());	
		
		return similarDoc;		
					
	}
	  
	private SearchRequest buildSearchRequest(Question question) {
		return SearchRequest.builder()
				.query(question.question())
				.filterExpression(
					new FilterExpressionBuilder()
						.eq("appName", question.appName())
						.build()
					)
				.topK(5)
				.build();
	}
	
}
