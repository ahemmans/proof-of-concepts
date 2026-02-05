package demo.ai.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import demo.ai.db.model.TransmissionError;
import demo.ai.db.model.TransmissionStatus;
import demo.ai.db.model.dto.TransmissionErrorDto;
import demo.ai.db.model.dto.TransmissionStatusDto;
import demo.ai.repo.TransmissionErrorRepo;
import demo.ai.repo.TransmissionStatusRepo;

@Service
public class TransmissionService {

	@Autowired private TransmissionStatusRepo transmissionStatusRepo;
	@Autowired private TransmissionErrorRepo transmissionErrorRepo;
	
	public List<TransmissionStatus> getAllTransmissions() {
		return transmissionStatusRepo.findAll();
	}
	
	public List<TransmissionStatusDto> getAllTransmissionsView() {
		return transmissionStatusRepo.getAllTransmissionsView();
	}
	
	public Optional<TransmissionStatus> getTransmissionByReceiptId(String receiptId) {
		return transmissionStatusRepo.findByReceiptId(receiptId);
	}
	
	public List<TransmissionError> getAllTransmissionErrors() {
		return transmissionErrorRepo.findAll();
	}
	
	public List<TransmissionErrorDto> getAllTransmissionErrorsView() {
		return transmissionErrorRepo.getAllErrorsView();
	} 
	public Optional<List<TransmissionError>> getTransmissionErrorsByReceiptId(String receiptId) {
		return transmissionErrorRepo.findByReceiptId(receiptId);
	}
	
	public Optional<List<TransmissionErrorDto>> getTransmissionErrorsViewByReceiptId(String receiptId) {
		return transmissionErrorRepo.getByReceiptId(receiptId);
	}
}

