package com.naver.test.redis.chat.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("message")
public class RedisMessageController {
	private static final Logger logger = LoggerFactory.getLogger(RedisMessageController.class);

	@Autowired
	private RedisTemplate redisTemplate;

	@RequestMapping("send")
	@ResponseBody
	public String send(@RequestParam String message) {
		logger.info("convertAndSend : {}", message);
		redisTemplate.convertAndSend("chatroom.leejeongwha", message);
		return "success";
	}
}
