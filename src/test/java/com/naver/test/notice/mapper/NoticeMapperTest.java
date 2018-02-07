package com.naver.test.notice.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

import com.naver.test.notice.model.Notice;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class NoticeMapperTest {

	@Autowired
	private NoticeMapper noticeMapper;

	@Test
	public void test() {
		Notice notice = noticeMapper.getNotice(1);

		assertThat(notice.getTitle()).isEqualTo("유승옥·정아름·이연·예정화…머슬글래머가 뜬다");
		assertThat(notice.getUserId()).isEqualTo("admin");
	}

}
