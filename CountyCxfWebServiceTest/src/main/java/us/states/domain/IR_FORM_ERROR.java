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
@Table(name="IR_FORM_ERROR")
@Data
public class IR_FORM_ERROR {
/*
 * 	IR_FORM_ERROR_ID      INT NOT NULL,   
	ERROR_ID              INT NOT NULL,   
	IR_REC_SEQ_NUM        VARCHAR(100) NOT NULL,
	GSO_ID                VARCHAR(240) NOT NULL,
	FORMTYPE              VARCHAR(20), 
	ELEMENT_NAME_TXT      VARCHAR(240) NOT NULL,
	ELEMENT_INSTANCE_TXT  VARCHAR(50), 
	INSERTED_BY_TXT       VARCHAR(15) NOT NULL, 
	INSERTED_DT           TIMESTAMP NOT NULL, 
	PRIMARY KEY (IR_FORM_ERROR_ID)
 */
	@Id
	private int IR_FORM_ERROR_ID;   
	
	@Column @NotNull
	private int ERROR_ID;    
	
	@Column @NotNull
	private String IR_REC_SEQ_NUM; 
	
	@Column @NotNull
	private String GSO_ID; 
	
	@Column
	private String FORMTYPE; 
	
	@Column @NotNull
	private String ELEMENT_NAME_TXT; 
	
	@Column
	private String ELEMENT_INSTANCE_TXT; 
	
	@Column @NotNull
	private String INSERTED_BY_TXT;  
	
	@Column @NotNull
	private Timestamp INSERTED_DT; 	
}
