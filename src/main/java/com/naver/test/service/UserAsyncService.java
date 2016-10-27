package com.naver.test.service;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Future;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import com.naver.test.domain.RawUser;
import com.naver.test.domain.Repository;

@Service
public class UserAsyncService {
	@Autowired
	private RestClient restClient;

	@Async
	public Future<RawUser> getRawUserObservable(String login) {
		return new AsyncResult<RawUser>(restClient.getUser(login));
	}

	@Async
	public Future<List<RawUser>> getFollowersObservable(String login) {
		return new AsyncResult<List<RawUser>>(Arrays.asList(restClient.getFollowers(login)));
	}

	@Async
	public Future<List<Repository>> getReposObservable(String login) {
		return new AsyncResult<List<Repository>>(Arrays.asList(restClient.getRepositories(login)));
	}

	public boolean checkFutureListComplete(List<Future> futureList) {
		for (Future future : futureList) {
			if (!future.isDone()) {
				return false;
			}
		}

		return true;
	}
}
