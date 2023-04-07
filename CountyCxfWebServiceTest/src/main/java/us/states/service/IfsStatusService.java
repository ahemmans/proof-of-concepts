package us.states.service;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Transactional
@Service
public class IfsStatusService {

	@Autowired EntityManager em;
	
	private StringBuilder insQry;
	
	private String getInsQry() {
		insQry = new StringBuilder();
		insQry.append("INSERT INTO IFS_STATUS (gso_id, submission_id, name, subtype, status, create_dt, update_dt) ");
		insQry.append("VALUES (:gsoId, 1, :testFile, :formType, :status, CURRENT_DATE, CURRENT_DATE)");
		return insQry.toString();
	}
	
	public void insertStatus(String _gsoId, String _testFile, String _formType, String _status) {		
		Query qry = em.createNativeQuery(getInsQry());
		qry.setParameter("gsoId", _gsoId);
		qry.setParameter("testFile", _testFile);
		qry.setParameter("formType", _formType);
		qry.setParameter("status", _status);
		qry.executeUpdate();		
	}
	
}
