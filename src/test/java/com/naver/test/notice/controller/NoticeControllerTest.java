package com.naver.test.notice.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.naver.test.notice.controller.NoticeController;
import com.naver.test.notice.mapper.NoticeMapper;
import com.naver.test.notice.model.Notice;

@RunWith(SpringRunner.class)
@WebMvcTest(NoticeController.class)
public class NoticeControllerTest {

	@Autowired
	private MockMvc mvc;

	@MockBean
	private NoticeMapper noticeMapper;

	@Test
	public void 게시글_단건_반환_컨트롤러_유닛테스트() throws Exception {
		Notice notice = new Notice();
		notice.setTitle("이정화");
		notice.setContent("이정화");

		BDDMockito.given(this.noticeMapper.getNotice(1)).willReturn(notice);

		mvc.perform(MockMvcRequestBuilders.get("/notice/1")).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.view().name("notice/detail"))
				.andExpect(MockMvcResultMatchers.model().attributeExists("notice"))
				.andExpect(MockMvcResultMatchers.model().attribute("notice", notice));
	}

}
