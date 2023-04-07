package us.states.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;
import us.states.domain.query.SubmissionStatus_1095A;

@Slf4j
@Transactional
@Service
public class SubmissionBcService {

	@Autowired EntityManager em;
	
	private StringBuilder insQry;
	
	private String getInsQry() {
		insQry = new StringBuilder();
		insQry.append("INSERT INTO SUBMISSION_BC (transmitter_bc_id, tax_yr, transmit_mode_ind, unique_submission_id, gso_id, form_type, submission_status_txt, ein, inserted_by_txt, inserted_dt) ");
		insQry.append("VALUES (1, '2023', '1', '1', :gsoId, :formType, :status, '123456789','int_test', CURRENT_DATE)");
		return insQry.toString();
	}
	
	public void insertSubmission(String _gsoId, String _formType, String _status) {
		Query qry = em.createNativeQuery(getInsQry());
		qry.setParameter("gsoId", _gsoId);
		qry.setParameter("formType", _formType);
		qry.setParameter("status", _status);
		qry.executeUpdate();
	}

}
