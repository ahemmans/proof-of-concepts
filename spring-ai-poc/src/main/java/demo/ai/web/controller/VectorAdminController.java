package demo.ai.web.controller;

import java.util.List;

import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.filter.FilterExpressionBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import demo.ai.service.VectorStoreService;
import demo.ai.service.dto.SubmissionVectorSearch;

@RestController
@RequestMapping(value="/vector")
public class VectorAdminController {

	@Autowired private VectorStoreService vectorStoreService;
	
	@PostMapping(path="/doc", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> 
	getVectorDoc(@RequestBody SubmissionVectorSearch vectorSearch) {
	
		var searchRequest = SearchRequest.builder()
			.query(vectorSearch.receiptId())
			.filterExpression(
				new FilterExpressionBuilder()
					.eq("appName", vectorSearch.appName())
					.build()
				)
			.topK(1)
			.build();

		List<Document> vectorDocs = vectorStoreService.findSimilarDocs(searchRequest);
		
		return ResponseEntity.ok(vectorDocs);
	}
	
	@PostMapping(path="/repopulate", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> 
	repopulatVectorStore(@RequestBody SubmissionVectorSearch vectorSearch) {
	
		try {
			if (vectorSearch.receiptId().equalsIgnoreCase("repopulate")) {
				vectorStoreService.repopulateVectorStore();		
				return ResponseEntity.ok("SUCCESS");
			} else {
				return ResponseEntity.noContent().build();
			}
		} catch (Exception ex) {
			return ResponseEntity.badRequest().build();
		}		
	}
	
}
