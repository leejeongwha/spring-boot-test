package com.naver.test.user.mapper;

import org.springframework.stereotype.Repository;

import com.naver.test.user.model.User;

@Repository
public interface UserMapper {
	User getUser(String id);
}
