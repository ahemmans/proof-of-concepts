package demo.ai.web.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import demo.ai.db.model.TransmissionError;
import demo.ai.db.model.TransmissionStatus;
import demo.ai.db.model.dto.TransmissionErrorDto;
import demo.ai.service.TransmissionService;

@RestController
@RequestMapping(value="/api/transmission")
public class TransmissionController {

	@Autowired private TransmissionService transmissionService;

	private List<TransmissionStatus> tranmissionStatusList;
	private List<TransmissionError> tranmissionErrorList;
	private Optional<TransmissionStatus> transmissionStatus;
	private Optional<List<TransmissionError>> transmissionErrors;
	
	
	@GetMapping(value="/status", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> 
	getAllTransmissions() {
		tranmissionStatusList = transmissionService.getAllTransmissions();
		
		return ResponseEntity.ok(tranmissionStatusList);
	}

	@GetMapping(value="/statusView", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> 
	getAllTransmissionsView() {
		
		return ResponseEntity.ok(transmissionService.getAllTransmissionsView());
	}
		
	@GetMapping(value="/status/{receiptId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> 
	getTransmissionByReceiptId(@PathVariable String receiptId) {
		transmissionStatus = transmissionService.getTransmissionByReceiptId(receiptId);
		
		if (transmissionStatus.isPresent()) {
			return ResponseEntity.ok(transmissionStatus);
		} else {
			return new ResponseEntity<>("{\"receiptId\": \"" + receiptId + "\", \"status\": \"Not Found\"}", HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping(value="/error", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> 
	getAllTransmissionErrors() {
		tranmissionErrorList = transmissionService.getAllTransmissionErrors();
		
		return ResponseEntity.ok(tranmissionErrorList);
	}
	
	@GetMapping(value="/errorView", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> 
	getAllTransmissionErrorsView() {
		
		return ResponseEntity.ok(transmissionService.getAllTransmissionErrorsView());
	}
	
	@GetMapping(value="/error/{receiptId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> 
	getTransmissionErrorsByReceiptId(@PathVariable String receiptId) {
		transmissionErrors = transmissionService.getTransmissionErrorsByReceiptId(receiptId);
		
		if (transmissionErrors.isPresent()) {
			return ResponseEntity.ok(transmissionErrors);
		} else {
			return new ResponseEntity<>("{\"receiptId\": \"" + receiptId + "\", \"status\": \"Not Found\"}", HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping(value="/errorView/{receiptId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> 
	getTransmissionErrorsViewByReceiptId(@PathVariable String receiptId) {
		Optional<List<TransmissionErrorDto>> transmissionErrorsView = transmissionService.getTransmissionErrorsViewByReceiptId(receiptId);
		
		if (transmissionErrorsView.isPresent()) {
			return ResponseEntity.ok(transmissionErrorsView);
		} else {
			return new ResponseEntity<>("{\"receiptId\": \"" + receiptId + "\", \"status\": \"Not Found\"}", HttpStatus.NOT_FOUND);
		}
	}
			
}
