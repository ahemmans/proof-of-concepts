package demo.ai.db.model.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({ "form_type", "year", "month_num", "month", "tot_num" })
public record TransmissionCountByYearAndMonth (
	String form_type,	
	double year,
	double month_num,
	String month,
	int tot_num
	)
{
}
