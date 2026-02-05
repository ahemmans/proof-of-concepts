package demo.ai.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import demo.ai.db.model.TransmissionError;
import demo.ai.db.model.dto.TransmissionCountByYear;
import demo.ai.db.model.dto.TransmissionCountByYearAndMonth;
import demo.ai.db.model.dto.TransmissionCountDto;
import demo.ai.db.model.dto.TransmissionErrorDto;

@Repository
public interface TransmissionErrorRepo extends JpaRepository<TransmissionError, Integer> {
	public Optional<List<TransmissionError>> findByReceiptId(String receiptId);
	
	public Optional<List<TransmissionErrorDto>> getByReceiptId(String receiptId);
	
	@Query("SELECT receiptId, errorCd FROM TransmissionError ORDER BY receiptId")
	public List<TransmissionErrorDto> getAllErrorsView();
	
	@Query(name="TransmissionError.getAllErrorCounts", nativeQuery=true)	
	public List<TransmissionCountDto> getAllErrorCounts();
	
	@Query(name="TransmissionError.getErrorCountByFormType", nativeQuery=true)	
	public List<TransmissionCountDto> getErrorCountByFormType(String formType);
	
	@Query(name="TransmissionError.getErrorCountByFormTypeByYear", nativeQuery=true)	
	public List<TransmissionCountByYear> getErrorCountByFormTypeByYear(String formType, double year);
	
	@Query(name="TransmissionError.getErrorCountByFormTypeByYearAndMonth", nativeQuery=true)	
	public List<TransmissionCountByYearAndMonth> getErrorCountByFormTypeByYearAndMonth(String formType, double year, String month);
	
}
