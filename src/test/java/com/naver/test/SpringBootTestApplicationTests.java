package com.naver.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class SpringBootTestApplicationTests {

	@Autowired
	private RedisTemplate<String, String> redisTemplate;

	@Test
	public void contextLoads() {
		// redisTemplate.opsForValue().set("user:name", "leejeongwha");

		System.out.println(redisTemplate.opsForValue().get("user:name"));
	}

}
