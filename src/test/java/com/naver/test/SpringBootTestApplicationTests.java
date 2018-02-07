package com.naver.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.naver.test.notice.model.Notice;

/**
 * 통합 테스트
 * 
 * @author mac
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class SpringBootTestApplicationTests {
	Logger logger = LoggerFactory.getLogger(SpringBootTestApplicationTests.class);

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	public void rest_컨트롤러_테스트() {
		Notice forObject = restTemplate.getForObject("/notice/json/1", Notice.class);

		logger.info("title : {}", forObject.getTitle());
		logger.info("content : {}", forObject.getContent());
		logger.info("seq : {}", forObject.getSeq());
	}

}
