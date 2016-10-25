package com.naver.test.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.naver.test.domain.GithubUser;
import com.naver.test.service.UserService;

@org.springframework.web.bind.annotation.RestController
@RequestMapping("/users")
public class RestController {
	private static final Logger log = LoggerFactory.getLogger(RestController.class);

	@Autowired
	private UserService userService;

	@RequestMapping("{login}")
	public GithubUser getUser(@PathVariable String login) {
		log.info("Get github.com/{} infos", login);

		return userService.getUser(login);
	}
}
