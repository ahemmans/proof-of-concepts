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
@Table(name="TRANSMISSION_1095_EXT")
@Data
public class TRANSMISSION_1095_EXT {
/*
 * 	TRANSMISSION_EXTID_NUM     INT NOT NULL,   
	TRANSMISSIONID_NUM         INT NOT NULL,   
	GSO_ID                     VARCHAR(240) NOT NULL,
	BATCH_ID                   VARCHAR(240),
	ORIGINAL_BATCH_ID          VARCHAR(240),
	VERSION                    INT,       
	TAX_YR                     VARCHAR(4),  
	DUPLICATE_IND              VARCHAR(1),  
	ORIGL_RETURNS_CNT_NUM      INT,   
	REPL_RETURNS_CNT_NUM       INT,   
	COR_RETURNS_CNT_NUM        INT,   
	PROCESSED_RETURNS_CNT_NUM  INT,   
	INSERTED_BY_TXT            VARCHAR(15), 
	INSERTED_DT                TIMESTAMP NOT NULL, 
	UPDATED_BY_TXT             VARCHAR(15), 
	UPDATED_DT                 TIMESTAMP, 
	FORM_TYPE                  VARCHAR(20), 
	ACTUAL_PAYER_NUM           INT,   
	ACTUAL_PAYEE_NUM           INT,   
	SHIPMENT_REC_NUM           VARCHAR(8), 
	PRIMARY KEY (TRANSMISSION_EXTID_NUM)  
 */
	@Id
	private int TRANSMISSION_EXTID_NUM;   
	
	@Column @NotNull
	private int TRANSMISSIONID_NUM;    
	
	@Column @NotNull
	private String GSO_ID; 
	
	@Column
	private String BATCH_ID; 
	
	@Column
	private String ORIGINAL_BATCH_ID; 
	
	@Column
	private int VERSION;        
	
	@Column
	private String TAX_YR; 
	
	@Column
	private String DUPLICATE_IND;   
	
	@Column
	private int ORIGL_RETURNS_CNT_NUM;    
	
	@Column
	private int REPL_RETURNS_CNT_NUM;   
	
	@Column
	private int COR_RETURNS_CNT_NUM;    
	
	@Column
	private int PROCESSED_RETURNS_CNT_NUM;    
	
	@Column
	private String INSERTED_BY_TXT;  
	
	@Column @NotNull
	private Timestamp INSERTED_DT;  
	
	@Column
	private String UPDATED_BY_TXT;  
	
	@Column
	private Timestamp UPDATED_DT;  
	
	@Column
	private String FORM_TYPE;  
	
	@Column
	private int ACTUAL_PAYER_NUM;   
	
	@Column
	private int ACTUAL_PAYEE_NUM;    
	
	@Column
	private String SHIPMENT_REC_NUM; 
 
}
