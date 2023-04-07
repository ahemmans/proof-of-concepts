package us.states.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@Entity
@Table(name="IFS_ERROR")
@Data
public class IFS_ERROR {
/*
 * 	ERROR_ID        INT NOT NULL,    
	GSO_ID          VARCHAR(240) NOT NULL, 
	SUBMISSION_ID   INT NOT NULL,    
	ERRORTYPE       VARCHAR(1000),
	ERRORMSG        VARCHAR(4000),
	ERRORLEVEL      VARCHAR(50),  
	XPATH           VARCHAR(255), 
	LINE_INT        VARCHAR(255), 
	ADDITIONAL_INFO VARCHAR(2000),
	PRIMARY KEY (ERROR_ID)
 */
	
	@Id
	private int ERROR_ID;
	
	@Column @NotNull
	private String GSO_ID ;
	
	@Column
	private int SUBMISSION_ID;
	
	@Column
	private String ERRORTYPE;
	
	@Column
	private String ERRORMSG;
	
	@Column
	private String ERRORLEVEL;
	
	@Column
	private String XPATH;
	
	@Column
	private String LINE_INT;
	
	@Column
	private String ADDITIONAL_INFO;
}
