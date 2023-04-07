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
@Table(name="DS_EOY_FORMS_TRANSMIT")
@Data
public class DS_EOY_FORMS_TRANSMIT {
/*
 * 	GSO_ID                     VARCHAR(240) NOT NULL, 
	FORM_TYPE                  VARCHAR(20) NOT NULL,  
	CREATE_DT                  DATE NOT NULL,          
	UPDATED_DT                 DATE NOT NULL,          
	TRANS_FILE_NAME_TXT        VARCHAR(256) NOT NULL, 
	TRANS_REC_COUNT            INT NOT NULL,    
	TRANS_STATUS_IND           CHAR(1) NOT NULL,       
	EOD_IND                    CHAR(1) NOT NULL,       
	REASON_TXT                 VARCHAR(1024),
	TRANS_PAYER_REC_COUNT_NUM  INT,  
	PRIMARY KEY (GSO_ID)
 */
	@Id
	private String GSO_ID; 
	
	@Column @NotNull
	private String FORM_TYPE; 
	
	@Column @NotNull
	@Temporal(TemporalType.DATE)
	private Date CREATE_DT;           
	
	@Column @NotNull
	@Temporal(TemporalType.DATE)
	private Date UPDATED_DT;           
	
	@Column @NotNull
	private String TRANS_FILE_NAME_TXT; 
	
	@Column @NotNull
	private int TRANS_REC_COUNT;     
	
	@Column @NotNull
	private char TRANS_STATUS_IND;        
	
	@Column @NotNull
	private char EOD_IND;        
	
	@Column
	private String REASON_TXT; 
	
	@Column
	private int TRANS_PAYER_REC_COUNT_NUM; 
}
