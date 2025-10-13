package com.naver.test.notice.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * 글로벌 예외 처리 핸들러
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    
    /**
     * 공지사항을 찾을 수 없는 경우
     */
    @ExceptionHandler(NoticeNotFoundException.class)
    public String handleNoticeNotFoundException(NoticeNotFoundException e, Model model) {
        logger.warn("공지사항을 찾을 수 없음: {}", e.getMessage());
        model.addAttribute("errorMessage", e.getMessage());
        model.addAttribute("errorCode", "NOTICE_NOT_FOUND");
        return "error/404";
    }
    
    /**
     * 잘못된 검색 키워드인 경우
     */
    @ExceptionHandler(InvalidSearchKeywordException.class)
    public String handleInvalidSearchKeywordException(InvalidSearchKeywordException e, 
                                                     RedirectAttributes redirectAttributes) {
        logger.warn("잘못된 검색 키워드: {}", e.getMessage());
        redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        return "redirect:/notice/list";
    }
    
    /**
     * 일반적인 런타임 예외
     */
    @ExceptionHandler(RuntimeException.class)
    public String handleRuntimeException(RuntimeException e, Model model) {
        logger.error("런타임 예외 발생", e);
        model.addAttribute("errorMessage", "시스템 오류가 발생했습니다. 잠시 후 다시 시도해주세요.");
        model.addAttribute("errorCode", "RUNTIME_ERROR");
        return "error/500";
    }
    
    /**
     * 모든 예외에 대한 기본 처리
     */
    @ExceptionHandler(Exception.class)
    public String handleException(Exception e, Model model) {
        logger.error("예상치 못한 예외 발생", e);
        model.addAttribute("errorMessage", "예상치 못한 오류가 발생했습니다. 관리자에게 문의해주세요.");
        model.addAttribute("errorCode", "UNEXPECTED_ERROR");
        return "error/500";
    }
}
