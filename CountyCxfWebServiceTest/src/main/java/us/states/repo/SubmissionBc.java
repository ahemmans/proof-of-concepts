package us.states.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import us.states.domain.SUBMISSION_BC;

@Transactional
@Repository
public interface SubmissionBc extends JpaRepository<SUBMISSION_BC, Integer> {

	@Modifying
	@Query(nativeQuery = true, 
			value = "INSERT INTO SUBMISSION_BC (transmitter_bc_id, tax_yr, transmit_mode_ind, unique_submission_id, gso_id, form_type, submission_status_txt, ein, inserted_by_txt, inserted_dt) " + 
					"VALUES (1, '2023', '1', '1', :gsoId, :formType, :status, '123456789','int_test', CURRENT_DATE)"
		)
	public void insertSubmission(@Param("gsoId") String gsoId, @Param("formType") String formType, @Param("status") String status);
	
	@Query(nativeQuery = true, value = "SELECT MAX(submission_bc_id) from SUBMISSION_BC")
	public int getId();
}
