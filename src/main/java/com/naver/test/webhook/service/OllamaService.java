package com.naver.test.webhook.service;

import com.naver.test.webhook.dto.OllamaRequest;
import com.naver.test.webhook.dto.OllamaResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Service
public class OllamaService {
	private static final Logger logger = LoggerFactory.getLogger(OllamaService.class);

	@Value("${ollama.api.url:http://10.162.5.224:11434/api/generate}")
	private String ollamaApiUrl;

	@Value("${ollama.model:qwen2.5-coder:7b}")
	private String ollamaModel;

	@Value("classpath:prompts/code-review-prompt.txt")
	private Resource promptResource;

	private final RestTemplate restTemplate;

	public OllamaService() {
		this.restTemplate = new RestTemplate();
	}

	/**
	 * Ollama API를 사용하여 코드 리뷰 수행
	 *
	 * @param diff PR의 diff 내용
	 * @return 코드 리뷰 결과
	 */
	public String reviewCode(String diff) {
		try {
			// 프롬프트 템플릿 로드
			String promptTemplate = loadPromptTemplate();

			// diff를 프롬프트에 삽입
			String prompt = promptTemplate.replace("{diff}", diff);

			// Ollama 요청 생성
			OllamaRequest request = OllamaRequest.builder()
					.model(ollamaModel)
					.prompt(prompt)
					.stream(false)
					.temperature(0.7)
					.maxTokens(2000)
					.build();

			// HTTP 헤더 설정
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);

			HttpEntity<OllamaRequest> entity = new HttpEntity<>(request, headers);

			logger.info("Requesting code review from Ollama: {}", ollamaApiUrl);
			logger.debug("Prompt length: {} characters", prompt.length());

			// Ollama API 호출
			ResponseEntity<OllamaResponse> response = restTemplate.postForEntity(
					ollamaApiUrl,
					entity,
					OllamaResponse.class
			);

			if (response.getBody() != null && response.getBody().getResponse() != null) {
				String reviewResult = response.getBody().getResponse();
				logger.info("Code review completed. Response length: {} characters", reviewResult.length());
				return reviewResult;
			} else {
				logger.error("Empty response from Ollama");
				return "코드 리뷰를 수행할 수 없습니다. Ollama 응답이 비어있습니다.";
			}

		} catch (Exception e) {
			logger.error("Error during code review with Ollama", e);
			return "코드 리뷰 중 오류가 발생했습니다: " + e.getMessage();
		}
	}

	/**
	 * 프롬프트 템플릿 파일 로드
	 */
	private String loadPromptTemplate() throws IOException {
		return StreamUtils.copyToString(
				promptResource.getInputStream(),
				StandardCharsets.UTF_8
		);
	}
}
