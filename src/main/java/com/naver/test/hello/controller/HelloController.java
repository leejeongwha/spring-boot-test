package com.naver.test.hello.controller;

import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HelloController {
	@Value("${application.message:Hello World}")
	private String message = "Hello World";

	@RequestMapping("/hello")
	public ModelAndView welcome(Map<String, Object> model) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("welcome");
		mav.addObject("time", new Date());
		mav.addObject("message", this.message);

		return mav;
	}
}
