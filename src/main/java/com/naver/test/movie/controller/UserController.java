package com.naver.test.movie.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.naver.test.movie.domain.User;
import com.naver.test.movie.repository.UserRepository;

@RestController
@RequestMapping("users")
public class UserController {
	@Autowired
	private UserRepository userRepository;

	@RequestMapping(method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.CREATED)
	public User insert(User user) {
		return userRepository.save(user);
	}

	@RequestMapping(method = RequestMethod.GET)
	public List<User> list() {
		return userRepository.findAll();
	}

	@RequestMapping(value = "{id}", method = RequestMethod.DELETE)
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Integer id) {
		userRepository.delete(id);
	}

	@RequestMapping(value = "{id}", method = RequestMethod.PUT)
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public User update(@PathVariable Integer id, User user) {
		user.setId(id);
		return userRepository.save(user);
	}

	@RequestMapping(value = "{id}", method = RequestMethod.GET)
	public User get(@PathVariable Integer id) {
		return userRepository.findOne(id);
	}
}
