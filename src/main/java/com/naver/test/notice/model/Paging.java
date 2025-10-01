package com.naver.test.notice.model;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

public class Paging {
	private Integer page = 1;
	private Integer count = 10;
	private Integer offset = 0;
	private Integer totalCount = 0;
	private Integer totalPage = 0;
	private Integer startPage = 1;
	private Integer endPage = 1;
	private Integer displayPageNum = 10; // 한 번에 보여줄 페이지 번호 개수

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public Integer getOffset() {
		return (page - 1) * count;
	}

	public void setOffset(Integer offset) {
		this.offset = offset;
	}

	public PageRequest getPageRequest() {
		return PageRequest.of(page - 1, count);
	}

	public PageRequest getSortedPageRequest(Sort sort) {
		return PageRequest.of(page - 1, count, sort);
	}
	
	public Integer getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
		calcData();
	}

	public Integer getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(Integer totalPage) {
		this.totalPage = totalPage;
	}

	public Integer getStartPage() {
		return startPage;
	}

	public void setStartPage(Integer startPage) {
		this.startPage = startPage;
	}

	public Integer getEndPage() {
		return endPage;
	}

	public void setEndPage(Integer endPage) {
		this.endPage = endPage;
	}

	public Integer getDisplayPageNum() {
		return displayPageNum;
	}

	public void setDisplayPageNum(Integer displayPageNum) {
		this.displayPageNum = displayPageNum;
	}
	
	// 페이징 계산
	private void calcData() {
		// 총 페이지 수 계산
		totalPage = (int) Math.ceil((double) totalCount / count);
		
		// 시작 페이지 계산
		startPage = ((page - 1) / displayPageNum) * displayPageNum + 1;
		
		// 끝 페이지 계산
		endPage = startPage + displayPageNum - 1;
		
		// 끝 페이지가 총 페이지 수보다 크면 총 페이지 수로 설정
		if (endPage > totalPage) {
			endPage = totalPage;
		}
	}
	
	// 이전 페이지 그룹 존재 여부
	public boolean isPrev() {
		return startPage > 1;
	}
	
	// 다음 페이지 그룹 존재 여부
	public boolean isNext() {
		return endPage < totalPage;
	}
}
