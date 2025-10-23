package com.naver.test.webhook.service;

import com.naver.test.webhook.dto.GitHubCommentRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class GitHubService {
	private static final Logger logger = LoggerFactory.getLogger(GitHubService.class);

	@Value("${github.api.token:}")
	private String githubToken;

	private final RestTemplate restTemplate;

	public GitHubService() {
		this.restTemplate = new RestTemplate();
	}

	/**
	 * PR의 diff 내용을 가져옴
	 *
	 * @param diffUrl PR의 diff URL
	 * @return diff 내용
	 */
	public String fetchPullRequestDiff(String diffUrl) {
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set("Accept", "application/vnd.github.v3.diff");
			if (githubToken != null && !githubToken.isEmpty()) {
				headers.set("Authorization", "token " + githubToken);
			}

			HttpEntity<String> entity = new HttpEntity<>(headers);

			logger.info("Fetching PR diff from: {}", diffUrl);

			ResponseEntity<String> response = restTemplate.exchange(
					diffUrl,
					HttpMethod.GET,
					entity,
					String.class
			);

			String diff = response.getBody();
			logger.info("Successfully fetched diff. Length: {} characters", diff != null ? diff.length() : 0);

			return diff;

		} catch (Exception e) {
			logger.error("Error fetching PR diff from GitHub", e);
			throw new RuntimeException("Failed to fetch PR diff: " + e.getMessage(), e);
		}
	}

	/**
	 * PR에 코멘트 추가
	 *
	 * @param commentsUrl PR의 comments URL
	 * @param reviewComment 리뷰 코멘트 내용
	 */
	public void postReviewComment(String commentsUrl, String reviewComment) {
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			if (githubToken != null && !githubToken.isEmpty()) {
				headers.set("Authorization", "token " + githubToken);
			}

			// 코멘트에 AI 리뷰 마크 추가
			String formattedComment = formatReviewComment(reviewComment);

			GitHubCommentRequest commentRequest = GitHubCommentRequest.builder()
					.body(formattedComment)
					.build();

			HttpEntity<GitHubCommentRequest> entity = new HttpEntity<>(commentRequest, headers);

			logger.info("Posting review comment to: {}", commentsUrl);
			logger.debug("Comment length: {} characters", formattedComment.length());

			ResponseEntity<String> response = restTemplate.postForEntity(
					commentsUrl,
					entity,
					String.class
			);

			if (response.getStatusCode().is2xxSuccessful()) {
				logger.info("Successfully posted review comment to GitHub PR");
			} else {
				logger.error("Failed to post comment. Status: {}", response.getStatusCode());
			}

		} catch (Exception e) {
			logger.error("Error posting review comment to GitHub", e);
			throw new RuntimeException("Failed to post review comment: " + e.getMessage(), e);
		}
	}

	/**
	 * 리뷰 코멘트 포맷팅 (CodeRabbit 스타일)
	 */
	private String formatReviewComment(String reviewContent) {
		StringBuilder formatted = new StringBuilder();
		formatted.append("## 🤖 AI Code Review\n\n");
		formatted.append(reviewContent);
		formatted.append("\n\n---\n");
		formatted.append("*이 리뷰는 Ollama (qwen2.5-coder:7b)를 사용하여 자동으로 생성되었습니다.*\n");

		return formatted.toString();
	}
}
