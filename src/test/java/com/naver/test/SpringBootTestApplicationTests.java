package com.naver.test;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
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
	public void text() {
		redisTemplate.opsForValue().set("user:name", "lee");

		Assert.assertEquals("lee", redisTemplate.opsForValue().get("user:name"));

		redisTemplate.opsForValue().append("user:name", "jeongwha");

		Assert.assertEquals(redisTemplate.opsForValue().get("user:name"), "leejeongwha");

		System.out.println(redisTemplate.opsForValue().get("user:name"));
	}

	@Test
	public void list() {
		redisTemplate.opsForList().leftPush("my:list:recommand", "java");

		redisTemplate.opsForList().leftPush("my:list:recommand", "javascript");

		System.out.println(redisTemplate.opsForList().range("my:list:recommand", 0, -1));
	}

	@Test
	public void set() {
		redisTemplate.opsForSet().add("my:set:test", "my");

		redisTemplate.opsForSet().add("my:set:test", "name");

		redisTemplate.opsForSet().add("my:set:test", "is");

		redisTemplate.opsForSet().add("my:set:test", "leejeongwha");

		System.out.println(redisTemplate.opsForSet().members("my:set:test"));
	}

	@Test
	public void zSet() {
		redisTemplate.opsForZSet().add("user:ranking", "my", 1);

		redisTemplate.opsForZSet().add("user:ranking", "name", 2);

		redisTemplate.opsForZSet().add("user:ranking", "is", 3);

		redisTemplate.opsForZSet().add("user:ranking", "leejeongwha", 4);

		System.out.println(redisTemplate.opsForZSet().range("user:ranking", 0, -1));
	}

	@Test
	public void hash() {
		String id = "ljk2491";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("firstName", "Lee");
		map.put("lastName", "JeongWha");
		map.put("email", "ljk2491@naver.com");

		redisTemplate.opsForHash().putAll(id, map);

		System.out.println(redisTemplate.opsForHash().get(id, "email"));
	}
}
