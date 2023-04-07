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
@Table(name="TRANSMISSION_1095A")
@Data
public class TRANSMISSION_1095A {
/*
 * 	TRANSMISSION_1095A_ID_NUM  INT NOT NULL,   
	TRANSMISSIONID_NUM         INT NOT NULL,   
	GSO_ID                     VARCHAR(240) NOT NULL,
	CORRELATION_ID             VARCHAR(80), 
	EXCHANGEID_NUM             VARCHAR(28) NOT NULL, 
	PAYER_DLN                  VARCHAR(14), 
	PAYEE_DLN                  VARCHAR(14), 
	BATCH_ID                   VARCHAR(240) NOT NULL,
	CHECKSUM_TXT               VARCHAR(64), 
	ORIGINAL_BATCH_ID          VARCHAR(240),
	BATCH_ATTCH_TRANS_QTY      INT NOT NULL,   
	BATCH_CATEGORY_CD          VARCHAR(100) NOT NULL,
	BATCH_TRANS_QTY            INT,   
	TRANS_ATTCH_QTY            INT NOT NULL,   
	TRANS_SEQUENCE_NUM         INT NOT NULL,   
	REPORT_PERIOD_DT           VARCHAR(10) NOT NULL, 
	ATTCH_BYTE_SIZE_NUM        VARCHAR(10), 
	DOC_SYSTEM_FILE_NAME_TXT   VARCHAR(100) NOT NULL,
	DOC_SEQUENCE_NUM           VARCHAR(5) NOT NULL,  
	SYS_DOC_ID                 VARCHAR(10), 
	INSERTED_BY_TXT            VARCHAR(15) NOT NULL, 
	INSERTED_DT                TIMESTAMP NOT NULL, 
	UPDATED_BY_TXT             VARCHAR(15), 
	UPDATED_DT                 TIMESTAMP,
	PRIMARY KEY (TRANSMISSION_1095A_ID_NUM) 
 */
	@Id
	private int TRANSMISSION_1095A_ID_NUM;    
		
	@Column  @NotNull	
	private int TRANSMISSIONID_NUM;    
		
	@Column	@NotNull
	private String GSO_ID; 
		
	@Column	
	private String CORRELATION_ID; 
		
	@Column	@NotNull
	private String EXCHANGEID_NUM;  
		
	@Column	
	private String PAYER_DLN;  
		
	@Column	
	private String PAYEE_DLN;  
		
	@Column	@NotNull
	private String BATCH_ID; 
		
	@Column	
	private String CHECKSUM_TXT;  
		
	@Column
	private String ORIGINAL_BATCH_ID; 
		
	@Column	@NotNull
	private int BATCH_ATTCH_TRANS_QTY;    
		
	@Column	@NotNull
	private String BATCH_CATEGORY_CD; 
		
	@Column	
	private int BATCH_TRANS_QTY;    
		
	@Column	@NotNull
	private int TRANS_ATTCH_QTY;    
		
	@Column	@NotNull
	private int TRANS_SEQUENCE_NUM;   
		
	@Column	@NotNull
	private String REPORT_PERIOD_DT;  
		
	@Column	
	private String ATTCH_BYTE_SIZE_NUM;  
		
	@Column	@NotNull
	private String DOC_SYSTEM_FILE_NAME_TXT; 
		
	@Column	@NotNull
	private String DOC_SEQUENCE_NUM;  
		
	@Column	
	private String SYS_DOC_ID;  
		
	@Column	@NotNull
	private String INSERTED_BY_TXT;  
		
	@Column	@NotNull
	private Timestamp INSERTED_DT;  
		
	@Column	
	private String UPDATED_BY_TXT;  
				
	@Column	
	private Timestamp UPDATED_DT; 
}
