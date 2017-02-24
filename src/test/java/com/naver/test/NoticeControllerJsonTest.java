package com.naver.test;

import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.naver.test.notice.controller.NoticeRestController;
import com.naver.test.notice.mapper.NoticeMapper;
import com.naver.test.notice.model.Notice;

@RunWith(SpringJUnit4ClassRunner.class)
@WebMvcTest(NoticeRestController.class)
public class NoticeControllerJsonTest {
	@Autowired
	private MockMvc mvc;

	@MockBean
	private NoticeMapper noticeMapper;

	@Test
	public void 게시글_단건_반환_Rest_컨트롤러_유닛테스트() throws Exception {
		Notice notice = new Notice();
		notice.setTitle("이정화");
		notice.setContent("이정화");

		BDDMockito.given(this.noticeMapper.getNotice(1)).willReturn(notice);

		mvc.perform(MockMvcRequestBuilders.get("/notice/json/1"))
				.andExpect(MockMvcResultMatchers.content().contentType("application/json;charset=UTF-8"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.title", Matchers.is("이정화")))
				.andExpect(MockMvcResultMatchers.status().isOk());
	}
}
