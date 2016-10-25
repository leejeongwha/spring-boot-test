package com.naver.test.service;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.naver.test.domain.GithubUser;
import com.naver.test.domain.RawUser;
import com.naver.test.domain.Repository;

import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;

@Service
public class UserService {
	@Autowired
	private RestClient restClient;

	private static final Logger log = LoggerFactory.getLogger(UserService.class);

	/**
	 * subscribeOn의 스케줄러 설정을 통해서 어느 쓰레드에서 수행될지 결정 (현재는 아래 3번의 http 통신이 동시에 이루어
	 * 지는 것을 볼 수 있음)
	 * 
	 * @param login
	 * @return
	 */
	public GithubUser getUser(String login) {
		Observable<RawUser> getRawUser = getRawUserObservable(login);
		Observable<RawUser[]> getFollowers = getFollowersObservable(login);
		Observable<Repository[]> getRepositories = getReposObservable(login);

		Observable<GithubUser> fullUser = Observable.zip(Arrays.asList(getRawUser, getFollowers, getRepositories),
				objects -> {
					RawUser rawUser = (RawUser) objects[0];
					RawUser[] followers = (RawUser[]) objects[1];
					Repository[] repositories = (Repository[]) objects[2];

					return new GithubUser(rawUser, Arrays.asList(followers), Arrays.asList(repositories));
				});

		return fullUser.toBlocking().first();
	}

	private Observable<RawUser> getRawUserObservable(String login) {
		return Observable.create((Subscriber<? super RawUser> s) -> s.onNext(restClient.getUser(login)))
				.onErrorReturn(throwable -> {
					log.error("Failed to retrieve user {}", login, throwable);

					return new RawUser("???", null, null);
				}).subscribeOn(Schedulers.computation());
	}

	private Observable<RawUser[]> getFollowersObservable(String login) {
		return Observable.create((Subscriber<? super RawUser[]> s) -> s.onNext(restClient.getFollowers(login)))
				.onErrorReturn(throwable -> {
					log.error("Failed to retrieve {} followers", login, throwable);

					return new RawUser[] {};
				}).subscribeOn(Schedulers.computation());
	}

	private Observable<Repository[]> getReposObservable(String login) {
		return Observable.create((Subscriber<? super Repository[]> s) -> s.onNext(restClient.getRepositories(login)))
				.onErrorReturn(throwable -> {
					log.error("Failed to retrieve {} repos", login, throwable);

					return new Repository[] {};
				}).subscribeOn(Schedulers.computation());
	}
}
