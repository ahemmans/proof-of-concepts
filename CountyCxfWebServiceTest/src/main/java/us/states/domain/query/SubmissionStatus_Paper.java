package us.states.domain.query;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@Entity
@Data
public class SubmissionStatus_Paper {

	@Id
	private String UNIQUE_TRANS_ID;
	
	@Column 
	private String GSO_ID;
	
	@Column
	private String FORM_TYPE; 
}
