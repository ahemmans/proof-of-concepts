package us.states.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;
import us.states.domain.query.SubmissionStatus_1095A;

@Slf4j
@Transactional
@Service
public class TransmitterBcService {

	//@Autowired EntityManager em;
	@PersistenceContext EntityManager em;
	
	private StringBuilder insQry, statusQry;
	
	private String getInsQry() {
		insQry = new StringBuilder();
		insQry.append("INSERT INTO TRANSMITTER_BC (transmissionid_num, tax_yr, unique_trans_id, gso_id, form_type, tcc, doc_system_file_name_txt, inserted_by_txt, inserted_dt) ");
		insQry.append("VALUES (1,'2023','1', :gsoId, :formType,'tcc001', :testFile ,'int_test', CURRENT_DATE)");
		return insQry.toString();
	}
	
	private String getStatusQry() {
		statusQry = new StringBuilder();
		statusQry.append("SELECT A.UNIQUE_TRANS_ID, A.GSO_ID, A.FORM_TYPE "); 
		statusQry.append("    ,B.STATUS, to_char(B.CREATE_DT, 'yyyy-mm-dd HH:mi:ss') as CREATE_DT "); 
		statusQry.append("    ,D.submission_status_txt "); 
		statusQry.append("FROM TRANSMITTER_BC A "); 
		statusQry.append("    FULL OUTER JOIN IFS_STATUS B ON A.GSO_ID = B.GSO_ID "); 
		statusQry.append("    FULL OUTER JOIN SUBMISSION_BC D ON B.GSO_ID = D.GSO_ID "); 
		statusQry.append("WHERE A.GSO_ID = :gsoId");
		return statusQry.toString();
	}
	
	public void insertTransmitter(String _gsoId, String _formType, String _testFile) {
		Query qry = em.createNativeQuery(getInsQry());
		qry.setParameter("gsoId", _gsoId);
		qry.setParameter("formType", _formType);
		qry.setParameter("testFile", _testFile);
		qry.executeUpdate();
	}
	
	public void getStatus1095A(String _gsoId) {
		//log.info("getStatus1095A() : gsoId : {}", _gsoId);
		//em.flush();
		
		Query qry = em.createNativeQuery(getStatusQry());
		//Query qry = em.createNativeQuery(getStatusQry(),"StatusResult1095A");
		//Query qry = em.createNamedQuery("SubmissionStatus1095A");
		//Query qry = em.createNativeQuery("SubmissionStatus1095A");
		
		qry.setParameter("gsoId", _gsoId);
		//qry.setParameter(1, _gsoId);
		
		List<Object[]> results = qry.getResultList();
		log.info("Resultset Size : ", results.size());
	}
}
