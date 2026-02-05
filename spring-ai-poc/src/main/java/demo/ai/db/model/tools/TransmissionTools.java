package demo.ai.db.model.tools;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import demo.ai.repo.TransmissionErrorRepo;
import demo.ai.repo.TransmissionStatusRepo;
import demo.ai.service.dto.Answer;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class TransmissionTools {

	@Autowired private TransmissionStatusRepo transmissionStatusRepo;
	@Autowired private TransmissionErrorRepo transmissionErrorRepo;
	
	@Tool(name = "getStatusCountsByFormType", description = "Get transmission status count by form type")
	public Answer getStatusCountsByFormType(@ToolParam(description="The form type of the submission") String formType) {
	    log.info("==== getStatusCountsByFormType() invoked ==== : {}", formType);
	    var counts = transmissionStatusRepo.getStatusCountByFormType(formType);

	    if (counts.isEmpty()) {
	        return new Answer(formType, "No status counts found for form type: " + formType);
	    }

	    // Format all counts as a readable string
	    StringBuilder result = new StringBuilder();
	    for (var count : counts) {
	        result.append(count.toString()).append("; ");
	    }

	    log.info("Retrieved {} counts for form type: {}", counts.size(), formType);
	    return new Answer(formType, result.toString());
	}

	@Tool(name = "getStatusCountsByFormTypeByStatus", description = "Get transmission status count by form type by status")
	public Answer getStatusCountsByFormTypeByStatus(
			@ToolParam(description="The form type of the submission") String formType,
			@ToolParam(description="The status of the submission") String status) {
	    log.info("==== getStatusCountsByFormTypeByStatus() invoked ==== : {} : {}", formType, status);
	    var counts = transmissionStatusRepo.getStatusCountByFormTypeByStatus(formType, status);

	    if (counts.isEmpty()) {
	        return new Answer(formType, "No status counts found for form type: " + formType);
	    }

	    // Format all counts as a readable string
	    StringBuilder result = new StringBuilder();
	    for (var count : counts) {
	        result.append(count.toString()).append("; ");
	    }

	    log.info("Retrieved {} counts for form type: {}", counts.size(), formType);
	    return new Answer(formType, result.toString());
	}
	
	@Tool(name = "getStatusCountsByFormTypeByYear", description = "Get transmission status count by form type by year")
	public Answer getStatusCountsByFormTypeByYear(
			@ToolParam(description="The form type of the submission") String formType, 
			@ToolParam(description="The year of the submission") double year) {
	    log.info("==== getStatusCountsByFormTypeByYear() invoked ==== : {} : {}", formType, year);
	    var counts = transmissionStatusRepo.getStatusCountByFormTypeByYear(formType, year);

	    if (counts.isEmpty()) {
	        return new Answer(formType, "No status counts found for form type: " + formType);
	    }

	    // Format all counts as a readable string
	    StringBuilder result = new StringBuilder();
	    for (var count : counts) {
	        result.append(count.toString()).append("; ");
	    }

	    log.info("Retrieved {} counts for form type: {}", counts.size(), formType);
	    return new Answer(formType, result.toString());
	}
	
	@Tool(name = "getStatusCountsByFormTypeByYearAndMonth", description = "Get transmission status count by form type by year and month")
	public Answer getStatusCountsByFormTypeByYearAndMonth(
			@ToolParam(description="The form type of the submission") String formType, 
			@ToolParam(description="The year of the submission") double year, 
			@ToolParam(description="The month of the submission") String month) {
	    log.info("==== getStatusCountsByFormTypeByYearAndMonth() invoked ==== : {} : {} : {}", formType, year, month);
	    var counts = transmissionStatusRepo.getStatusCountByFormTypeByYearAndMonth(formType, year, month);

	    if (counts.isEmpty()) {
	        return new Answer(formType, "No status counts found for form type: " + formType);
	    }

	    // Format all counts as a readable string
	    StringBuilder result = new StringBuilder();
	    for (var count : counts) {
	        result.append(count.toString()).append("; ");
	    }

	    log.info("Retrieved {} counts for form type: {}", counts.size(), formType);
	    return new Answer(formType, result.toString());
	}
	
	@Tool(name = "getErrorCountsByFormType", description = "Get transmission error count by form type")
	public Answer getErrorCountsByFormType(@ToolParam(description="The form type of the submission") String formType) {
		log.info("==== getErrorCountsByFormType() invoked ==== : {}", formType);
		String formType_param = airFormTypeForQry(formType);
	    var counts = transmissionErrorRepo.getErrorCountByFormType(formType_param);
	    
	    if (counts.isEmpty()) {
	        return new Answer(formType, "No error counts found for form type: " + formType);
	    }

	    // Format all counts as a readable string
	    StringBuilder result = new StringBuilder();
	    for (var count : counts) {
	        result.append(count.toString()).append("; ");
	    }

	    log.info("Retrieved {} errors for form type: {}", counts.size(), formType);
	    return new Answer(formType, result.toString());
	}	
	
	@Tool(name = "getErrorCountsByFormTypeByYear", description = "Get transmission error count by form type by year")
	public Answer getErrorCountsByFormTypeByYear(
			@ToolParam(description="The form type of the submission") String formType, 
			@ToolParam(description="The year of the submission") double year) {
	    log.info("==== getErrorCountsByFormTypeByYear() invoked ==== : {} : {}", formType, year);
	    String formType_param = airFormTypeForQry(formType);
	    var counts = transmissionErrorRepo.getErrorCountByFormTypeByYear(formType_param, year);

	    if (counts.isEmpty()) {
	        return new Answer(formType, "No error counts found for form type: " + formType);
	    }

	    // Format all counts as a readable string
	    StringBuilder result = new StringBuilder();
	    for (var count : counts) {
	        result.append(count.toString()).append("; ");
	    }

	    log.info("Retrieved {} error for form type: {}", counts.size(), formType);
	    return new Answer(formType, result.toString());
	}
	
	@Tool(name = "getErrorCountsByFormTypeByYearAndMonth", description = "Get transmission error count by form type by year and month")
	public Answer getErrorCountsByFormTypeByYearAndMonth(
			@ToolParam(description="The form type of the submission") String formType, 
			@ToolParam(description="The year of the submission") double year,
			@ToolParam(description="The month of the submission") String month) {
		log.info("==== getErrorCountsByFormTypeByYearAndMonth() invoked ==== : {} : {} : {}", formType, year, month);
	    String formType_param = airFormTypeForQry(formType);
	    var counts = transmissionErrorRepo.getErrorCountByFormTypeByYearAndMonth(formType_param, year, month);

	    if (counts.isEmpty()) {
	        return new Answer(formType, "No error counts found for form type: " + formType);
	    }

	    // Format all counts as a readable string
	    StringBuilder result = new StringBuilder();
	    for (var count : counts) {
	        result.append(count.toString()).append("; ");
	    }

	    log.info("Retrieved {} error for form type: {}", counts.size(), formType);
	    return new Answer(formType, result.toString());
	}
	
	private String airFormTypeForQry(String formType) {	
		return switch (formType) {
			case "FORM1095A", "FORM1095B", "FORM1095C", "FORM1094B", "FORM1094C" -> formType.substring(4);
			default -> formType;
		};
	}
}
