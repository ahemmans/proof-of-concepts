package demo.ai.db.model.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({ "receiptId", "status" })
public record TransmissionStatusDto (
	String receiptId,
	String status
	)
{
}
