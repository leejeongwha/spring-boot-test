package com.naver.test.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Repository {
	private String name;
	@JsonProperty("html_url")
	private String url;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
