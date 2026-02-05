package demo.ai.db.model.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({ "form_type", "year", "tot_num" })
public record TransmissionCountByYear (
	String form_type,	
	double year,
	int tot_num
	)
{
}
