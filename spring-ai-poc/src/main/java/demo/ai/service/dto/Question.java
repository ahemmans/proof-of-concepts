package demo.ai.service.dto;

import jakarta.validation.constraints.NotBlank;

public record Question(
		@NotBlank(message="App name is required") String appName,
		@NotBlank(message="Question is required") String question
		) {


}
