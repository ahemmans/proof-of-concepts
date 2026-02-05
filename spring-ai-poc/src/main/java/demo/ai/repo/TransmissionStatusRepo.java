package demo.ai.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import demo.ai.db.model.TransmissionStatus;
import demo.ai.db.model.dto.TransmissionCountByStatusDto;
import demo.ai.db.model.dto.TransmissionCountByYear;
import demo.ai.db.model.dto.TransmissionCountByYearAndMonth;
import demo.ai.db.model.dto.TransmissionCountDto;
import demo.ai.db.model.dto.TransmissionStatusDto;

@Repository
public interface TransmissionStatusRepo extends JpaRepository<TransmissionStatus, Integer> {
	public Optional<TransmissionStatus> findByReceiptId(String receiptId);
	public List<TransmissionStatus> findByStatus(String status);
	public List<TransmissionStatus> findByFormType(String formType);
	
	@Query("SELECT receiptId, status FROM TransmissionStatus ORDER BY receiptId")
	public List<TransmissionStatusDto> getAllTransmissionsView();
	
	@Query(name="TransmissionStatus.getAllStatusCounts", nativeQuery=true)	
	public List<TransmissionCountDto> getAllStatusCounts();
	
	@Query(name="TransmissionStatus.getStatusCountByFormType", nativeQuery=true)	
	public List<TransmissionCountDto> getStatusCountByFormType(String formType);

	@Query(name="TransmissionStatus.getStatusCountByFormTypeByStatus", nativeQuery=true)	
	public List<TransmissionCountByStatusDto> getStatusCountByFormTypeByStatus(String formType, String status);
	
	@Query(name="TransmissionStatus.getStatusCountByFormTypeByYear", nativeQuery=true)	
	public List<TransmissionCountByYear> getStatusCountByFormTypeByYear(String formType, double year);
	
	@Query(name="TransmissionStatus.getStatusCountByFormTypeByYearAndMonth", nativeQuery=true)	
	public List<TransmissionCountByYearAndMonth> getStatusCountByFormTypeByYearAndMonth(String formType, double year, String month);
	
}
