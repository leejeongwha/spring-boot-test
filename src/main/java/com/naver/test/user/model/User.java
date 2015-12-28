package com.naver.test.user.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "user")
public class User {
	@Id
	private String id;
	private String passwd;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}
}
