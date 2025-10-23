package com.naver.test.webhook.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class WebhookAsyncService {
	private static final Logger logger = LoggerFactory.getLogger(WebhookAsyncService.class);

	private final GitHubService gitHubService;
	private final OllamaService ollamaService;

	@Autowired
	public WebhookAsyncService(GitHubService gitHubService, OllamaService ollamaService) {
		this.gitHubService = gitHubService;
		this.ollamaService = ollamaService;
	}

	/**
	 * 코드 리뷰 프로세스 수행 (비동기)
	 */
	@Async
	public void processCodeReview(Map<String, Object> payload) {
		try {
			// PR 정보 추출
			@SuppressWarnings("unchecked")
			Map<String, Object> pullRequest = (Map<String, Object>) payload.get("pull_request");

			if (pullRequest == null) {
				logger.error("Pull request data not found in payload");
				return;
			}

			String diffUrl = (String) pullRequest.get("diff_url");
			String commentsUrl = (String) pullRequest.get("comments_url");
			Integer prNumber = (Integer) pullRequest.get("number");

			logger.info("Processing code review for PR #{}", prNumber);
			logger.info("Diff URL: {}", diffUrl);
			logger.info("Comments URL: {}", commentsUrl);

			// 1. GitHub에서 PR의 diff 가져오기
			String diff = gitHubService.fetchPullRequestDiff(diffUrl);

			if (diff == null || diff.trim().isEmpty()) {
				logger.warn("Empty diff for PR #{}. Skipping review.", prNumber);
				return;
			}

			// 2. Ollama를 사용하여 코드 리뷰 수행
			String reviewResult = ollamaService.reviewCode(diff);

			// 3. GitHub PR에 리뷰 코멘트 등록
			gitHubService.postReviewComment(commentsUrl, reviewResult);

			logger.info("Code review completed successfully for PR #{}", prNumber);

		} catch (Exception e) {
			logger.error("Error during code review process", e);
		}
	}
}
