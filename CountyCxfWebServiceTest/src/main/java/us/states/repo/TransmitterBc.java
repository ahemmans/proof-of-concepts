package us.states.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import us.states.domain.TRANSMITTER_BC;
import us.states.domain.query.SubmissionStatus_1095A;

@Transactional
@Repository
public interface TransmitterBc extends JpaRepository<TRANSMITTER_BC, Integer> {

	@Modifying
	@Query(nativeQuery = true, 
			value = "INSERT INTO TRANSMITTER_BC (transmissionid_num, tax_yr, unique_trans_id, gso_id, form_type, tcc, doc_system_file_name_txt, inserted_by_txt, inserted_dt) " + 
					"VALUES (1,'2023','1', :gsoId, :formType,'tcc001', :testFile ,'int_test', CURRENT_DATE)"
		)
	public void insertTransmitter(@Param("gsoId") String gsoId, @Param("formType") String formType, @Param("testFile") String _testFile);
	
	@Query(nativeQuery = true, value = "SELECT MAX(transmitter_bc_id) from TRANSMITTER_BC")
	public int getId();
	
	//@Query(nativeQuery = true)
	//public List<SubmissionStatus_1095A> submissionStatus1095A(@Param("gsoId") String gsoId);
	
	@Query(nativeQuery = true,
			value = "SELECT " + 
					"	 A.UNIQUE_TRANS_ID, A.GSO_ID, A.FORM_TYPE " + 
					"    ,B.STATUS, to_char(B.CREATE_DT, 'yyyy-mm-dd HH:mi:ss') as CREATE_DT " + 
					"    ,D.submission_status_txt " +
					"FROM TRANSMITTER_BC A " +
					"    FULL OUTER JOIN IFS_STATUS B ON A.GSO_ID = B.GSO_ID " +
					"    FULL OUTER JOIN SUBMISSION_BC D ON B.GSO_ID = D.GSO_ID " +
					"WHERE A.GSO_ID = :gsoId")
	public List<Object[]> submissionStatus1095A(@Param("gsoId") String gsoId);
		
}
