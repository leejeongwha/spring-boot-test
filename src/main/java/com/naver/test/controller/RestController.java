package com.naver.test.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.naver.test.domain.GithubUser;
import com.naver.test.domain.RawUser;
import com.naver.test.domain.Repository;
import com.naver.test.service.UserAsyncService;
import com.naver.test.service.UserService;

@org.springframework.web.bind.annotation.RestController
@RequestMapping("/users")
public class RestController {
	private static final Logger log = LoggerFactory.getLogger(RestController.class);

	@Autowired
	private UserService userService;

	@Autowired
	private UserAsyncService userAsyncService;

	@RequestMapping("rxjava/{login}")
	public GithubUser getRxjavaUser(@PathVariable String login) {
		log.info("Get github.com/{} infos", login);

		return userService.getUser(login);
	}

	@RequestMapping("async/{login}")
	public GithubUser getAsyncUser(@PathVariable String login) throws InterruptedException, ExecutionException {
		log.info("Get github.com/{} infos", login);

		// proxy 기반의 AOP가 사용되므로 동일 클래스 내부에서 호출될 경우 async가 동작하지 않음
		List<Future> futureList = new ArrayList<Future>();
		futureList.add(userAsyncService.getRawUserObservable(login));
		futureList.add(userAsyncService.getFollowersObservable(login));
		futureList.add(userAsyncService.getReposObservable(login));

		while (!userAsyncService.checkFutureListComplete(futureList)) {
			Thread.sleep(10);
		}

		return new GithubUser((RawUser) futureList.get(0).get(), (List<RawUser>) futureList.get(1).get(),
				(List<Repository>) futureList.get(2).get());
	}
}
