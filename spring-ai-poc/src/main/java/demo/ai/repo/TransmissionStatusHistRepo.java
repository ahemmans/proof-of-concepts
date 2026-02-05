package demo.ai.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import demo.ai.db.model.TransmissionStatusHist;

@Repository
public interface TransmissionStatusHistRepo extends JpaRepository<TransmissionStatusHist, Integer> {
	public List<TransmissionStatusHist> findByReceiptIdOrderByInsertedDtDesc(String receiptId);
	
}
