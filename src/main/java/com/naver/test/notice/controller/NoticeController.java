package com.naver.test.notice.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.WebUtils;

import com.naver.test.notice.annotation.AuthCheck;
import com.naver.test.notice.exception.NoticeNotFoundException;
import com.naver.test.notice.model.Notice;
import com.naver.test.notice.model.Paging;
import com.naver.test.notice.service.NoticeService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping(value = "notice")
@RequiredArgsConstructor
public class NoticeController {
	private static final Logger logger = LoggerFactory.getLogger(NoticeController.class);

	private final NoticeService noticeService;

	@RequestMapping(value = "form")
	public String form() {
		return "notice/detail";
	}

	@GetMapping({ "", "/", "list" })
	public String list(Model model, Paging paging, 
			@RequestParam(value = "keyword", required = false) String keyword) {
		
		List<Notice> noticeList;
		
		try {
			if (StringUtils.hasText(keyword)) {
				// 검색 모드
				noticeList = noticeService.searchNotices(keyword.trim(), paging);
				model.addAttribute("keyword", keyword.trim());
				logger.info("공지사항 검색 결과 - keyword: {}, size: {}, currentPage: {}", 
						keyword, noticeList.size(), paging.getPage());
			} else {
				// 일반 목록 조회
				noticeList = noticeService.getNoticeList(paging);
				logger.info("공지사항 목록 조회 - size: {}, currentPage: {}", 
						noticeList.size(), paging.getPage());
			}
			
			model.addAttribute("noticeList", noticeList);
			model.addAttribute("paging", paging);
			
		} catch (Exception e) {
			logger.error("공지사항 목록 조회 중 오류 발생", e);
			model.addAttribute("errorMessage", "목록을 불러오는 중 오류가 발생했습니다.");
			return "error/500";
		}

		return "notice/list";
	}

	@GetMapping(value = "/{seq}")
	public String detail(Model model, @PathVariable("seq") int seq) {
		try {
			Notice notice = noticeService.getNotice(seq);
			model.addAttribute("notice", notice);
			
			logger.info("공지사항 상세 조회 완료 - seq: {}", seq);
			return "notice/detail";
			
		} catch (NoticeNotFoundException e) {
			logger.warn("존재하지 않는 공지사항 조회 시도 - seq: {}", seq);
			model.addAttribute("errorMessage", e.getMessage());
			return "error/404";
		} catch (Exception e) {
			logger.error("공지사항 상세 조회 중 오류 발생 - seq: " + seq, e);
			model.addAttribute("errorMessage", "공지사항을 불러오는 중 오류가 발생했습니다.");
			return "error/500";
		}
	}

	@PostMapping(value = "save")
	@AuthCheck
	public String save(HttpServletRequest request, @Valid Notice notice, 
			BindingResult bindingResult, RedirectAttributes redirectAttributes) {
		
		// 유효성 검증 실패 시
		if (bindingResult.hasErrors()) {
			logger.warn("공지사항 저장 유효성 검증 실패 - errors: {}", bindingResult.getAllErrors());
			redirectAttributes.addFlashAttribute("errorMessage", "입력 정보를 확인해주세요.");
			redirectAttributes.addFlashAttribute("notice", notice);
			redirectAttributes.addFlashAttribute("bindingResult", bindingResult);
			return "redirect:/notice/form";
		}
		
		try {
			String userId = (String) WebUtils.getSessionAttribute(request, "admin");
			notice.setUserId(userId);
			
			noticeService.saveNotice(notice);
			
			redirectAttributes.addFlashAttribute("successMessage", "공지사항이 성공적으로 저장되었습니다.");
			logger.info("공지사항 저장 완료 - title: {}, userId: {}", notice.getTitle(), userId);
			
		} catch (Exception e) {
			logger.error("공지사항 저장 중 오류 발생", e);
			redirectAttributes.addFlashAttribute("errorMessage", "저장 중 오류가 발생했습니다.");
			return "redirect:/notice/form";
		}

		return "redirect:/notice/list";
	}
}
