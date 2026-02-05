package demo.ai.db.model.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({ "receiptId", "errorCd" })
public record TransmissionErrorDto (
	String receiptId,
	String errorCd
	)
{
}
