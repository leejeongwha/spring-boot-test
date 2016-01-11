package com.naver.test.notice.mapper;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.naver.test.notice.model.Notice;
import com.naver.test.notice.model.Paging;

@Repository
public interface NoticeMapper {
	Notice getNotice(int seq);

	void save(Notice notice);

	List<Notice> getNoticeList(Paging paging);
}
