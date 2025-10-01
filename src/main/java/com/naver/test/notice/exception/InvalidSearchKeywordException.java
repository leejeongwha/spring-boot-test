package com.naver.test.notice.exception;

/**
 * 잘못된 검색 키워드일 때 발생하는 예외
 */
public class InvalidSearchKeywordException extends RuntimeException {
    
    private static final long serialVersionUID = 1L;
    
    public InvalidSearchKeywordException(String message) {
        super(message);
    }
    
    public static InvalidSearchKeywordException forKeyword(String keyword) {
        return new InvalidSearchKeywordException("유효하지 않은 검색 키워드입니다: " + keyword);
    }
}
