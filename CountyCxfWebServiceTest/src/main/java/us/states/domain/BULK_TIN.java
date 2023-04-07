package us.states.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@Entity
@Table(name="BULK_TIN")
@Data
public class BULK_TIN {
/*
 * 	BULK_ID             VARCHAR(70) NOT NULL,  
	GSO_ID              VARCHAR(240) NOT NULL, 
	CREATE_DT           DATE,          
	UPDATE_DT           DATE,          
	COMPLETED_IND       VARCHAR(5),   
	PROCESSED_IND       VARCHAR(5),   
	FORM_LIST           BYTEA,          
	SET_NUM             INT,    
	SUBSET_NUM          INT,    
	SUBMISSION_ID       VARCHAR(100), 
	HOST_NAME           VARCHAR(100), 
	IRID_SEQ_NBR        INT,    
	TIN_SERVICE_TYPE    VARCHAR(15),  
	TIN_REQ_STATUS      VARCHAR(4000),
	TIN_REQ_COUNT_NUM   INT,    
	TIN_RESP_COUNT_NUM  INT,
	PRIMARY KEY (BULK_ID) 
 */
	@Id
	private String BULK_ID;  
	
	@Column @NotNull
	private String GSO_ID; 
	
	@Column
	@Temporal(TemporalType.DATE)
	private Date CREATE_DT;          
	
	@Column
	@Temporal(TemporalType.DATE)
	private Date UPDATE_DT;           
	
	@Column
	private String COMPLETED_IND;    
	
	@Column
	private String PROCESSED_IND;   
	
	@Column
	private byte[] FORM_LIST;           
	
	@Column
	private int SET_NUM;    
	
	@Column
	private int SUBSET_NUM;     
	
	@Column
	private String SUBMISSION_ID;  
	
	@Column
	private String HOST_NAME;  
	
	@Column
	private int IRID_SEQ_NBR;     
	
	@Column
	private String TIN_SERVICE_TYPE;  
		
	@Column
	private String TIN_REQ_STATUS; 
	
	@Column
	private int TIN_REQ_COUNT_NUM;     
	
	@Column
	private int TIN_RESP_COUNT_NUM; 

}
