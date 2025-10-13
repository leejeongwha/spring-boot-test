package com.naver.test.notice.exception;

/**
 * 공지사항을 찾을 수 없을 때 발생하는 예외
 */
public class NoticeNotFoundException extends RuntimeException {
    
    private static final long serialVersionUID = 1L;
    
    public NoticeNotFoundException(String message) {
        super(message);
    }
    
    public NoticeNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public NoticeNotFoundException(Integer seq) {
        super("공지사항을 찾을 수 없습니다. seq: " + seq);
    }
}
