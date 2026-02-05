package demo.ai.db.model.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({ "form_type", "tot_num" })
public record TransmissionCountDto (
	String form_type,
	int tot_num
	)
{
}
