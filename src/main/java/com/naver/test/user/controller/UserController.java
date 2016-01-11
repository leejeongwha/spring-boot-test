package com.naver.test.user.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.util.WebUtils;

import com.naver.test.user.mapper.UserMapper;
import com.naver.test.user.model.User;

@Controller
@RequestMapping(value = "login")
public class UserController {
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private UserMapper userMapper;

	@RequestMapping(value = "form")
	public String form() {
		return "user/loginForm";
	}

	@RequestMapping(value = "submit", method = RequestMethod.POST)
	public String save(HttpServletRequest request, User user) {
		String returnURL = "redirect:/login/form";

		User admin = userMapper.getUser(user.getId());

		if (admin != null && StringUtils.equals(user.getPasswd(), admin.getPasswd())) {
			WebUtils.setSessionAttribute(request, "admin", admin.getId());
			returnURL = "redirect:/notice/list";
		} else {
			request.getSession().removeAttribute("admin");
		}

		return returnURL;
	}
}
