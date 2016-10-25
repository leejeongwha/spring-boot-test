package com.naver.test.domain;

import java.util.List;

public class GithubUser {
	private RawUser user;
	private List<RawUser> followers;
	private List<Repository> repositories;

	public GithubUser() {

	}

	public GithubUser(RawUser rawUser, List<RawUser> followers, List<Repository> repositories) {
		this.user = rawUser;
		this.followers = followers;
		this.repositories = repositories;
	}

	public RawUser getUser() {
		return user;
	}

	public void setUser(RawUser user) {
		this.user = user;
	}

	public List<RawUser> getFollowers() {
		return followers;
	}

	public void setFollowers(List<RawUser> followers) {
		this.followers = followers;
	}

	public List<Repository> getRepositories() {
		return repositories;
	}

	public void setRepositories(List<Repository> repositories) {
		this.repositories = repositories;
	}

}
