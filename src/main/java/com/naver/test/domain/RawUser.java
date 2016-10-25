package com.naver.test.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RawUser {
	private String login;
	private String name;
	@JsonProperty("avatar_url")
	private String avatarUrl;

	public RawUser() {
	}

	public RawUser(String login, String name, String avatarUrl) {
		this.login = login;
		this.name = name;
		this.avatarUrl = avatarUrl;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAvatarUrl() {
		return avatarUrl;
	}

	public void setAvatarUrl(String avatarUrl) {
		this.avatarUrl = avatarUrl;
	}

}
