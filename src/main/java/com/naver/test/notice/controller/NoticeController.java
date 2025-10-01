package com.naver.test.notice.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.WebUtils;

import com.naver.test.notice.annotation.AuthCheck;
import com.naver.test.notice.mapper.NoticeMapper;
import com.naver.test.notice.model.Notice;
import com.naver.test.notice.model.Paging;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping(value = "notice")
@RequiredArgsConstructor
public class NoticeController {
	private static final Logger logger = LoggerFactory.getLogger(NoticeController.class);

	private final NoticeMapper noticeMapper;

	@RequestMapping(value = "form")
	public String form() {
		return "notice/detail";
	}

	@RequestMapping({ "", "/", "list" })
	public String list(Model model, Paging paging, 
			@RequestParam(value = "keyword", required = false) String keyword) {
		
		List<Notice> noticeList;
		int totalCount;
		
		// 검색 키워드가 있는 경우
		if (keyword != null && !keyword.trim().isEmpty()) {
			keyword = keyword.trim();
			totalCount = noticeMapper.getSearchCount(keyword);
			paging.setTotalCount(totalCount);
			noticeList = noticeMapper.searchNotices(keyword, paging);
			
			model.addAttribute("keyword", keyword);
			logger.info("검색 결과 - keyword: {}, size: {}, totalCount: {}, currentPage: {}", 
					keyword, noticeList.size(), totalCount, paging.getPage());
		} else {
			// 일반 목록 조회
			totalCount = noticeMapper.getTotalCount();
			paging.setTotalCount(totalCount);
			noticeList = noticeMapper.getNoticeList(paging);
			
			logger.info("전체 목록 - size: {}, totalCount: {}, currentPage: {}", 
					noticeList.size(), totalCount, paging.getPage());
		}

		model.addAttribute("noticeList", noticeList);
		model.addAttribute("paging", paging);

		return "notice/list";
	}

	@RequestMapping(value = "/{seq}")
	public String search(Model model, @PathVariable("seq") int seq) {
		Notice notice = noticeMapper.getNotice(seq);

		model.addAttribute("notice", notice);

		return "notice/detail";
	}

	@PostMapping(value = "save")
	@AuthCheck
	public String save(HttpServletRequest request, Notice notice) {
		String userId = (String) WebUtils.getSessionAttribute(request, "admin");
		notice.setUserId(userId);
		noticeMapper.save(notice);

		return "redirect:/notice/list";
	}
}
