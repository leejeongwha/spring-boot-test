package com.naver.test.notice.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.util.WebUtils;

import com.naver.test.notice.annotation.AuthCheck;
import com.naver.test.notice.mapper.NoticeMapper;
import com.naver.test.notice.model.Notice;
import com.naver.test.notice.model.Paging;

@Controller
@RequestMapping(value = "notice")
public class NoticeController {
	private static final Logger logger = LoggerFactory.getLogger(NoticeController.class);

	@Autowired
	private NoticeMapper noticeMapper;

	@RequestMapping(value = "form")
	public String form() {
		return "notice/detail";
	}

	@RequestMapping({ "", "/", "list" })
	public String list(Model model, Paging paging) {
		List<Notice> noticeList = noticeMapper.getNoticeList(paging);

		logger.info("noticeList size : " + noticeList.size());

		model.addAttribute("noticeList", noticeList);

		return "notice/list";
	}

	@RequestMapping(value = "/{seq}")
	public String search(Model model, @PathVariable("seq") int seq) {
		Notice notice = noticeMapper.getNotice(seq);

		model.addAttribute("notice", notice);

		return "notice/detail";
	}

	@RequestMapping(value = "save", method = RequestMethod.POST)
	@AuthCheck
	public String save(HttpServletRequest request, Notice notice) {
		String userId = (String) WebUtils.getSessionAttribute(request, "admin");
		notice.setUserId(userId);
		noticeMapper.save(notice);

		return "redirect:/notice/list";
	}
}
