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
@Table(name="IFS_STATUS")
@Data
public class IFS_STATUS {
/*
 *	STATUS_ID     INT NOT NULL,    
	GSO_ID        VARCHAR(240) NOT NULL, 
	SUBMISSION_ID INT NOT NULL,    
	CREATE_DT     DATE,          
	UPDATE_DT     DATE,          
	NAME          VARCHAR(240), 
	SUBTYPE       VARCHAR(240), 
	STATUS        VARCHAR(100), 
	INFO          VARCHAR(4000),
	OWNER         VARCHAR(50),  
	CONTACT       VARCHAR(100),
	PRIMARY KEY(STATUS_ID)
*/
	
	@Id
	private int STATUS_ID;
	
	@Column @NotNull
	private String GSO_ID;
	
	@Column @NotNull
	private int SUBMISSION_ID;
	
	@Column
	@Temporal(TemporalType.DATE)
	private Date CREATE_DT;
	
	@Column
	@Temporal(TemporalType.DATE)
	private Date UPDATE_DT;
	
	@Column
	private String NAME;
	
	@Column
	private String SUBTYPE;
	
	@Column
	private String STATUS;
	
	@Column
	private String INFO;
	
	@Column
	private String OWNER;
	
	@Column
	private String CONTACT;
	
}
