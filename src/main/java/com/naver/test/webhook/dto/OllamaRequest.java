package com.naver.test.webhook.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OllamaRequest {

	@JsonProperty("model")
	private String model;

	@JsonProperty("prompt")
	private String prompt;

	@JsonProperty("stream")
	private boolean stream;

	@JsonProperty("temperature")
	private Double temperature;

	@JsonProperty("max_tokens")
	private Integer maxTokens;
}
