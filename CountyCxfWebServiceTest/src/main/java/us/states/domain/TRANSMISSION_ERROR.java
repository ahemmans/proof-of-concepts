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
@Table(name="TRANSMISSION_ERROR")
@Data
public class TRANSMISSION_ERROR {
/*
 * 	TRANSMISSION_ERROR_ID  INT NOT NULL,   
	TRANSMISSIONID_NUM     INT NOT NULL,   
	GSO_ID                 VARCHAR(240) NOT NULL,
	BATCH_CATEGORY_CD      VARCHAR(80) NOT NULL, 
	ERROR_CD               VARCHAR(50) NOT NULL, 
	ERROR_MSG_TXT          VARCHAR(240),
	ERROR_LEVEL_TXT        VARCHAR(50), 
	INSERTED_BY_TXT        VARCHAR(15), 
	INSERTED_DT            TIMESTAMP, 
	UPDATED_BY_TXT         VARCHAR(15), 
	UPDATED_DT             TIMESTAMP, 
	PRIMARY KEY (TRANSMISSION_ERROR_ID)
 */
	@Id
	private int TRANSMISSION_ERROR_ID;    
	
	@Column @NotNull
	private int TRANSMISSIONID_NUM;    
	
	@Column @NotNull
	private String GSO_ID; 
	
	@Column @NotNull
	private String BATCH_CATEGORY_CD;  
	
	@Column @NotNull
	private String ERROR_CD;  
	
	@Column
	private String ERROR_MSG_TXT; 
	
	@Column
	private String ERROR_LEVEL_TXT;  
	
	@Column
	private String INSERTED_BY_TXT;  
	
	@Column
	private Timestamp INSERTED_DT;  
	
	@Column
	private String UPDATED_BY_TXT; 
	
	@Column
	private Timestamp UPDATED_DT; 
}
