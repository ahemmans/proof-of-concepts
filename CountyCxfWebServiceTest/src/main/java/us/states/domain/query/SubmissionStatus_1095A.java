package us.states.domain.query;

import java.util.Date;

import javax.persistence.EntityResult;
import javax.persistence.FieldResult;
import javax.persistence.MappedSuperclass;
import javax.persistence.NamedNativeQuery;
import javax.persistence.SqlResultSetMapping;

import org.springframework.stereotype.Component;

import lombok.Data;

//@MappedSuperclass
//@Data
@Component
/*
@NamedNativeQuery(name = "SubmissionStatus1095A", 
	query = "SELECT  \r\n" + 
			"	 A.UNIQUE_TRANS_ID, A.GSO_ID, A.FORM_TYPE \r\n" + 
			"    ,B.STATUS, to_char(B.CREATE_DT, 'yyyy-mm-dd HH:mi:ss') as CREATE_DT \r\n" + 
			"    ,D.submission_status_txt \r\n" +
			"FROM TRANSMITTER_BC A \r\n" +
			"    FULL OUTER JOIN IFS_STATUS B ON A.GSO_ID = B.GSO_ID \r\n" +
			"    FULL OUTER JOIN SUBMISSION_BC D ON B.GSO_ID = D.GSO_ID \r\n" +
			"WHERE A.GSO_ID = ?1"
	,
	resultSetMapping = "StatusResult1095A")

@SqlResultSetMapping(name = "StatusResult1095A",
	entities = { 
		@EntityResult(entityClass = us.states.domain.TRANSMITTER_BC.class, fields = {
			@FieldResult(name = "UNIQUE_TRANS_ID", column = "UNIQUE_TRANS_ID"),
			@FieldResult(name = "GSO_ID", column = "GSO_ID"),
			@FieldResult(name = "FORM_TYPE", column = "FORM_TYPE")
		}),
		@EntityResult(entityClass = us.states.domain.IFS_STATUS.class, fields = {
			@FieldResult(name = "STATUS", column = "STATUS"),
			@FieldResult(name = "CREATE_DT", column = "CREATE_DT")				
		}),
		@EntityResult(entityClass = us.states.domain.SUBMISSION_BC.class, fields = {
			@FieldResult(name = "SUBMISSION_STATUS_TXT", column = "SUBMISSION_STATUS_TXT")	
		})
	}
)
*/
public class SubmissionStatus_1095A {

	public SubmissionStatus_1095A() {
		
	}
	
	public SubmissionStatus_1095A(String uNIQUE_TRANS_ID, String gSO_ID, String fORM_TYPE, String sTATUS,
			Date cREATE_DT, String sUBMISSION_STATUS_TXT) {
		//super();
		this.UNIQUE_TRANS_ID = uNIQUE_TRANS_ID;
		this.GSO_ID = gSO_ID;
		this.FORM_TYPE = fORM_TYPE;
		this.STATUS = sTATUS;
		this.CREATE_DT = cREATE_DT;
		this.SUBMISSION_STATUS_TXT = sUBMISSION_STATUS_TXT;
	}
	
	private String UNIQUE_TRANS_ID; 
	
	private String GSO_ID;	

	private String FORM_TYPE;  
	
	private String STATUS;
	
	private Date CREATE_DT;
	
	private String SUBMISSION_STATUS_TXT;

	public String getUNIQUE_TRANS_ID() {
		return UNIQUE_TRANS_ID;
	}

	public void setUNIQUE_TRANS_ID(String uNIQUE_TRANS_ID) {
		UNIQUE_TRANS_ID = uNIQUE_TRANS_ID;
	}

	public String getGSO_ID() {
		return GSO_ID;
	}

	public void setGSO_ID(String gSO_ID) {
		GSO_ID = gSO_ID;
	}

	public String getFORM_TYPE() {
		return FORM_TYPE;
	}

	public void setFORM_TYPE(String fORM_TYPE) {
		FORM_TYPE = fORM_TYPE;
	}

	public String getSTATUS() {
		return STATUS;
	}

	public void setSTATUS(String sTATUS) {
		STATUS = sTATUS;
	}

	public Date getCREATE_DT() {
		return CREATE_DT;
	}

	public void setCREATE_DT(Date cREATE_DT) {
		CREATE_DT = cREATE_DT;
	}

	public String getSUBMISSION_STATUS_TXT() {
		return SUBMISSION_STATUS_TXT;
	}

	public void setSUBMISSION_STATUS_TXT(String sUBMISSION_STATUS_TXT) {
		SUBMISSION_STATUS_TXT = sUBMISSION_STATUS_TXT;
	}
	
}
