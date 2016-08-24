package com.naver.test.redis;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.StringRedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 레디스 파이프라인 테스트
 * 
 * @author NAVER
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class RedisPipelineTests {
	@Autowired
	private RedisTemplate<String, String> redisTemplate;

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void popTest() {
		int batchSize = 10000;

		List<Object> results = redisTemplate.executePipelined(new RedisCallback<Object>() {
			public Object doInRedis(RedisConnection connection) throws DataAccessException {
				StringRedisConnection stringRedisConn = (StringRedisConnection) connection;
				for (int i = 0; i < batchSize; i++) {
					stringRedisConn.rPop("myqueue");
				}
				return null;
			}
		});

		for (Object value : results) {
			System.out.println(value.toString());
		}
	}

	@Test
	public void pushTest() {
		int batchSize = 10000;

		redisTemplate.executePipelined(new RedisCallback<Object>() {
			public Object doInRedis(RedisConnection connection) throws DataAccessException {
				StringRedisConnection stringRedisConn = (StringRedisConnection) connection;
				for (int i = 0; i < batchSize; i++) {
					stringRedisConn.lPush("myqueue", Integer.toString(i));
				}
				return null;
			}
		});
	}
}
