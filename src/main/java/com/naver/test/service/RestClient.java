package com.naver.test.service;

import static java.lang.String.format;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.naver.test.domain.RawUser;
import com.naver.test.domain.Repository;

@Service
public class RestClient {
	private static final Logger log = LoggerFactory.getLogger(RestClient.class);

	public static final String API_GITHUB_USERS = "https://api.github.com/users/%s";
	public static final String API_GITHUB_FOLLOWERS = API_GITHUB_USERS + "/followers";
	public static final String API_GITHUB_REPOS = API_GITHUB_USERS + "/repos";

	@Autowired
	private RestTemplate restTemplate;

	public RawUser getUser(String login) {
		log.info("Get user {}", login);

		return restTemplate.getForObject(format(API_GITHUB_USERS, login), RawUser.class);
	}

	public RawUser[] getFollowers(String login) {
		log.info("Get followers {}", login);

		return restTemplate.getForObject(format(API_GITHUB_FOLLOWERS, login), RawUser[].class);
	}

	public Repository[] getRepositories(String login) {
		log.info("Get repos {}", login);

		return restTemplate.getForObject(format(API_GITHUB_REPOS, login), Repository[].class);
	}
}
