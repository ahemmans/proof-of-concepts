package demo.ai.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.ai.vectorstore.filter.FilterExpressionBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import demo.ai.db.model.TransmissionError;
import demo.ai.db.model.TransmissionStatus;
import demo.ai.util.ApplicationDeterminator;
import demo.ai.util.ApplicationNames;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class VectorStoreService {

	private final VectorStore vectorStore;	
	
	@Value("classpath:/promptTemplates/systemPromptTemplate.st")
	Resource promptTemplate;
	
	@Autowired private TransmissionService transmissionService;
	@Autowired private ApplicationDeterminator appDetService;
	
	public VectorStoreService(VectorStore vectorStore) {
		super();
		this.vectorStore = vectorStore;
	}
	
	/*
	List<Document> documents = List.of(
			new Document("1095B-25-00013579 is not fine", Map.of("appName", "air", "receiptId", "1095B-25-00013579", "status", "REJECTED", "formType", "FORM1095B", "submissionDate", "2025-12-15")),
			new Document("1094B-26-00012345 is alive and kicking", Map.of("appName", "air", "receiptId", "1094B-26-00012345", "status", "ACCEPTED", "formType", "FORM1094B", "submissionDate", "2025-12-15")),
			new Document("1094C-26-00023456 is in the mix", Map.of("appName", "air", "receiptId", "1094C-26-00023456", "status", "ACCEPTED_WITH_ERRORS", "formType", "FORM1094C", "submissionDate", "2026-01-15"))
	);
	*/
		 	
	public void repopulateVectorStore() {
		deleteAllVectors();
		createVectorsFromSubmissionData();
	}
	
	private void deleteAllVectors() {
		var appFilter = new FilterExpressionBuilder();
		FilterExpressionBuilder.Op airApp =  appFilter.eq("appName", ApplicationNames.AIR.toString());
		FilterExpressionBuilder.Op pbbaApp = appFilter.eq("appName", ApplicationNames.PBBA.toString());
	
		vectorStore.delete(
				new FilterExpressionBuilder()
					.or(airApp, pbbaApp)
					.build()
				);
	}
	
	public void createVectorsFromSubmissionData() {
		log.info("==== Creating Vectors from Submission Data ====");
		
		List<TransmissionStatus> transmissionStatusList = transmissionService.getAllTransmissions();		
		transmissionStatusList.stream()		
			.forEach(ts -> {			
				List<TransmissionError> transmissionErrors = transmissionService.getTransmissionErrorsByReceiptId(ts.getReceiptId()).get();
				if (!transmissionErrors.isEmpty() && transmissionErrors!= null) {					
					createVectorDoc(ts, transmissionErrors);						
				} else {
					createVectorDoc(ts, new ArrayList<TransmissionError>());
				}
			});	
	}
	
	private void createVectorDoc(TransmissionStatus ts, List<TransmissionError> transmissionErrors) {
		var doc = new Document(String.format("Submission %s has a status of %s", ts.getReceiptId(), ts.getStatus()),
				Map.of(	"appName", appDetService.getApplicationNameFromFormType(ts.getFormType()), 
						"receiptId", ts.getReceiptId(), 
						"status", ts.getStatus(), 
						"formType", ts.getFormType(), 
						"submissionDate", ts.getInsertedDt().toString(),
						"submissionErrors", transmissionErrors
						)
			);
		vectorStore.add(List.of(doc));
	}
	
	
	public List<Document> findSimilarDocs(SearchRequest searchRequest) {
		
		return this.vectorStore.similaritySearch(searchRequest)
				.stream()
				.collect(Collectors.toList());				
	}
	
	public List<Document> getAllDocs() {
		
		return this.vectorStore.similaritySearch("*")
			.stream()
			.collect(Collectors.toList());			
	}
	

}	
