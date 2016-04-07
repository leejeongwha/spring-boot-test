package com.naver.test.hello.controller;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HelloController {
	@Value("${application.message:Hello World}")
	private String message = "Hello World";

	@RequestMapping("/hello")
	public String welcome(@RequestParam String name, Model model) throws Exception {
		model.addAttribute("time", new Date());
		model.addAttribute("message", this.message);

		if (StringUtils.equals(name, "500")) {
			throw new Exception("Name is 500");
		}

		return "welcome";
	}
}
