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
@Table(name="TIN_VALIDATION_ERROR")
@Data
public class TIN_VALIDATION_ERROR {
/*
 * 	TIN_VAL_ERROR_ID     INT NOT NULL,   
	GSO_ID               VARCHAR(240) NOT NULL,
	BULK_ID              VARCHAR(70) NOT NULL, 
	TIN_TXT              VARCHAR(20), 
	TAXPAYER_NAME_TXT    VARCHAR(140),
	TIN_REQUEST_TYPE_CD  VARCHAR(20), 
	VAL_RESULT_CD        VARCHAR(20), 
	ERROR_MESSAGE_CD     VARCHAR(40), 
	ERROR_MESSAGE_TXT    VARCHAR(240),
	ELEMENT_NAME_TXT     VARCHAR(240),
	RECORD_SEQ_NUM       VARCHAR(100),
	POLICY_NUM           VARCHAR(100),
	INSERTED_DT          TIMESTAMP, 
	PRIMARY KEY (TIN_VAL_ERROR_ID)
 */

	@Id
	private int TIN_VAL_ERROR_ID;   
	
	@Column @NotNull
	private String GSO_ID; 
	
	@Column @NotNull
	private String BULK_ID;  
	
	@Column
	private String TIN_TXT;  
	
	@Column
	private String TAXPAYER_NAME_TXT; 
	
	@Column
	private String TIN_REQUEST_TYPE_CD; 
	
	@Column
	private String VAL_RESULT_CD;  
	
	@Column
	private String ERROR_MESSAGE_CD;  
	
	@Column
	private String ERROR_MESSAGE_TXT; 
	
	@Column
	private String ELEMENT_NAME_TXT; 
	
	@Column
	private String RECORD_SEQ_NUM; 
	
	@Column
	private String POLICY_NUM; 
	
	@Column
	private Timestamp INSERTED_DT;  
	
}
