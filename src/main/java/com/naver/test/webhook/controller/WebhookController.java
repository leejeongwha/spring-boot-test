package com.naver.test.webhook.controller;

import com.naver.test.webhook.service.WebhookAsyncService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class WebhookController {
	private static final Logger logger = LoggerFactory.getLogger(WebhookController.class);

	private final WebhookAsyncService webhookAsyncService;

	@Autowired
	public WebhookController(WebhookAsyncService webhookAsyncService) {
		this.webhookAsyncService = webhookAsyncService;
	}

	/**
	 * GitHub Webhook을 받는 엔드포인트
	 *
	 * @param payload GitHub에서 전송하는 JSON 데이터
	 * @param event GitHub Event 타입 (X-GitHub-Event 헤더)
	 * @return ResponseEntity
	 */
	@PostMapping(value = "/postreceive", consumes = "application/json")
	public ResponseEntity<Map<String, String>> handleWebhook(
			@RequestBody Map<String, Object> payload,
			@RequestHeader(value = "X-GitHub-Event", required = false) String event) {

		logger.info("GitHub Webhook received");
		logger.info("Event Type: {}", event);

		try {
			// Pull Request 이벤트 처리
			if ("pull_request".equals(event)) {
				String action = (String) payload.get("action");
				logger.info("Pull request action: {}", action);

				// opened 또는 synchronize (새 커밋 푸시) 또는 edited 이벤트에만 반응
				if ("opened".equals(action) || "synchronize".equals(action) || "edited".equals(action)) {
					// 비동기로 코드 리뷰 수행
					webhookAsyncService.processCodeReview(payload);
				}
			}

			return ResponseEntity
					.status(HttpStatus.OK)
					.body(Map.of("status", "success", "message", "Webhook received"));

		} catch (Exception e) {
			logger.error("Error processing webhook", e);
			return ResponseEntity
					.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(Map.of("status", "error", "message", e.getMessage()));
		}
	}

}
