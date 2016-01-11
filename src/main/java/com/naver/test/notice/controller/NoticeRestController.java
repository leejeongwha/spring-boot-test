package com.naver.test.notice.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.naver.test.notice.mapper.NoticeMapper;
import com.naver.test.notice.model.Notice;
import com.naver.test.notice.model.Paging;

@RestController
@RequestMapping(value = "notice/json")
public class NoticeRestController {
	private static final Logger logger = LoggerFactory.getLogger(NoticeRestController.class);

	@Autowired
	private NoticeMapper noticeMapper;

	@RequestMapping(value = "list")
	public List<Notice> list(Paging paging) {
		List<Notice> noticeList = noticeMapper.getNoticeList(paging);
		return noticeList;
	}

	@RequestMapping(value = "/{seq}")
	public Notice seq(@PathVariable("seq") int seq) {
		return noticeMapper.getNotice(seq);
	}
}
