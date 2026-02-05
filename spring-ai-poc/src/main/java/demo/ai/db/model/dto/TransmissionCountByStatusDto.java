package demo.ai.db.model.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({ "form_type", "status", "tot_num" })
public record TransmissionCountByStatusDto (
	String form_type,
	String status,
	int tot_num
	)
{
}
