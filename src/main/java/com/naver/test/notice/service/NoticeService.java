package com.naver.test.notice.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.naver.test.notice.exception.InvalidSearchKeywordException;
import com.naver.test.notice.exception.NoticeNotFoundException;
import com.naver.test.notice.mapper.NoticeMapper;
import com.naver.test.notice.model.Notice;
import com.naver.test.notice.model.Paging;

import lombok.RequiredArgsConstructor;

/**
 * 공지사항 서비스
 * 비즈니스 로직을 처리합니다.
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NoticeService {
    
    private static final Logger logger = LoggerFactory.getLogger(NoticeService.class);
    private static final int MIN_SEARCH_KEYWORD_LENGTH = 2;
    private static final int MAX_SEARCH_KEYWORD_LENGTH = 50;
    
    private final NoticeMapper noticeMapper;
    
    /**
     * 공지사항 목록 조회
     */
    public List<Notice> getNoticeList(Paging paging) {
        logger.debug("공지사항 목록 조회 - 페이지: {}, 개수: {}", paging.getPage(), paging.getCount());
        
        int totalCount = noticeMapper.getTotalCount();
        paging.setTotalCount(totalCount);
        
        List<Notice> notices = noticeMapper.getNoticeList(paging);
        logger.info("공지사항 목록 조회 완료 - 총 {}개 중 {}개 조회", totalCount, notices.size());
        
        return notices;
    }
    
    /**
     * 공지사항 상세 조회
     */
    public Notice getNotice(int seq) {
        logger.debug("공지사항 상세 조회 - seq: {}", seq);
        
        Notice notice = noticeMapper.getNotice(seq);
        if (notice == null) {
            logger.warn("공지사항을 찾을 수 없습니다 - seq: {}", seq);
            throw new NoticeNotFoundException(seq);
        }
        
        logger.info("공지사항 상세 조회 완료 - seq: {}, title: {}", seq, notice.getTitle());
        return notice;
    }
    
    /**
     * 공지사항 저장
     */
    @Transactional
    public void saveNotice(Notice notice) {
        logger.debug("공지사항 저장 - title: {}, userId: {}", notice.getTitle(), notice.getUserId());
        
        noticeMapper.save(notice);
        
        logger.info("공지사항 저장 완료 - seq: {}, title: {}", notice.getSeq(), notice.getTitle());
    }
    
    /**
     * 공지사항 검색
     */
    public List<Notice> searchNotices(String keyword, Paging paging) {
        logger.debug("공지사항 검색 - keyword: {}, 페이지: {}", keyword, paging.getPage());
        
        validateSearchKeyword(keyword);
        
        int searchCount = noticeMapper.getSearchCount(keyword);
        paging.setTotalCount(searchCount);
        
        List<Notice> notices = noticeMapper.searchNotices(keyword, paging);
        logger.info("공지사항 검색 완료 - keyword: {}, 총 {}개 중 {}개 조회", 
                   keyword, searchCount, notices.size());
        
        return notices;
    }
    
    /**
     * 검색 키워드 유효성 검증
     */
    private void validateSearchKeyword(String keyword) {
        if (!StringUtils.hasText(keyword)) {
            throw new InvalidSearchKeywordException("검색 키워드는 필수입니다.");
        }
        
        String trimmedKeyword = keyword.trim();
        if (trimmedKeyword.length() < MIN_SEARCH_KEYWORD_LENGTH) {
            throw new InvalidSearchKeywordException(
                String.format("검색 키워드는 %d자 이상이어야 합니다.", MIN_SEARCH_KEYWORD_LENGTH));
        }
        
        if (trimmedKeyword.length() > MAX_SEARCH_KEYWORD_LENGTH) {
            throw new InvalidSearchKeywordException(
                String.format("검색 키워드는 %d자 이하여야 합니다.", MAX_SEARCH_KEYWORD_LENGTH));
        }
    }
}
