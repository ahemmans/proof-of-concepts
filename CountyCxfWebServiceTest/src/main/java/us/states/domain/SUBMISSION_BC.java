package us.states.domain;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@Entity
@Table(name="SUBMISSION_BC")
@Data
public class SUBMISSION_BC {
/*
 * 	SUBMISSION_BC_ID             	SERIAL PRIMARY KEY,                                       
	TRANSMITTER_BC_ID               INT NOT NULL,                                       
	TAX_YR                          VARCHAR(4) NOT NULL,                                       
	TRANSMIT_MODE_IND               VARCHAR(1) NOT NULL,                                       
	UNIQUE_SUBMISSION_ID            VARCHAR(100) NOT NULL,                                      
	ORIGINAL_UNIQUE_SUBMISSION_ID   VARCHAR(100),                                     
	REPLACEMENT_UNIQUE_SUB_ID       VARCHAR(100),                                     
	ADJUSTED_RECEIPT_DT             TIMESTAMP,                                      
	GSO_ID                          VARCHAR(240) NOT NULL,                                      
	FORM_TYPE                       VARCHAR(20),                                      
	EIN                             VARCHAR(9) NOT NULL,                                       
	SUBMISSION_STATUS_TXT           VARCHAR(50),                                     
	FILER_EMPLOYER_NM_TXT           VARCHAR(150),                                     
	DELINQUENT_IND                  VARCHAR(2),                                      
	DELINQUENT_DT                   TIMESTAMP,                                      
	CORRESPONDENCE_CD               CHAR(3),                                      
	REPORTED_PAYEE_NUM              INT,                                     
	ACTUAL_PAYEE_NUM                INT,                                    
	THRESHOLD_VAL_STATUS_CD         VARCHAR(10),                                    
	INSERTED_BY_TXT                 VARCHAR(15) NOT NULL,                                    
	INSERTED_DT                     TIMESTAMP NOT NULL,                                     
	UPDATED_BY_TXT                  VARCHAR(15),                                    
	UPDATED_DT                      TIMESTAMP,                                     
	REPORTED_ORIG_PAYEE_NUM         INT,                                      
	REPORTED_ORIG_SBP_PAYEE_NUM     INT,                                     
	REPORTED_AMEND_PAYEE_NUM        INT,                                      
	REPORTED_AMEND_SBP_PAYEE_NUM    INT,                                      
	AMENDED_DOC_CD                  CHAR(3),                                      
	ACTUAL_AMEND_PAYEE_NUM          INT,                                    
	ACTUAL_ORIG_PAYEE_NUM           INT
 */
	@Id
	private int SUBMISSION_BC_ID;                                      
	
	@Column @NotNull
	private int TRANSMITTER_BC_ID;                                       
	
	@Column @NotNull
	private String TAX_YR;                                       
	
	@Column @NotNull
	private String TRANSMIT_MODE_IND;                                      
	
	@Column @NotNull
	private String UNIQUE_SUBMISSION_ID;                                     
	
	@Column
	private String ORIGINAL_UNIQUE_SUBMISSION_ID; 
	
	@Column
	private String REPLACEMENT_UNIQUE_SUB_ID;                                     
	
	@Column
	private Timestamp ADJUSTED_RECEIPT_DT;                                       
	
	@Column @NotNull
	private String GSO_ID;                                      
	
	@Column
	private String FORM_TYPE;                                      
	
	@Column @NotNull
	private String EIN;                                        
	
	@Column
	private String SUBMISSION_STATUS_TXT;                                    
	
	@Column
	private String FILER_EMPLOYER_NM_TXT;                                      
	
	@Column
	private String DELINQUENT_IND;                                       
	
	@Column
	private Timestamp DELINQUENT_DT;                                      
	
	@Column
	private char CORRESPONDENCE_CD;                                       
	
	@Column
	private int REPORTED_PAYEE_NUM;                                      
	
	@Column
	private int ACTUAL_PAYEE_NUM;                                    
	
	@Column
	private String THRESHOLD_VAL_STATUS_CD;                                     
	
	@Column @NotNull
	private String INSERTED_BY_TXT;                                   
	
	@Column @NotNull
	private Timestamp INSERTED_DT;                                     
	
	@Column
	private String UPDATED_BY_TXT;                                   
	
	@Column
	private Timestamp UPDATED_DT;                                      
	
	@Column
	private int REPORTED_ORIG_PAYEE_NUM;                                      
	
	@Column
	private int REPORTED_ORIG_SBP_PAYEE_NUM;                                     
	
	@Column
	private int REPORTED_AMEND_PAYEE_NUM;                                      
	
	@Column
	private int REPORTED_AMEND_SBP_PAYEE_NUM;                                      
	
	@Column
	private char AMENDED_DOC_CD;                                      
	
	@Column
	private int ACTUAL_AMEND_PAYEE_NUM;                                    
	
	@Column
	private int ACTUAL_ORIG_PAYEE_NUM; 
	
}
