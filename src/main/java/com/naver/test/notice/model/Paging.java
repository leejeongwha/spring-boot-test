package com.naver.test.notice.model;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

public class Paging {
	private Integer page = 1;
	private Integer count = 10;

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

	public PageRequest getPageRequest() {
		return new PageRequest(page - 1, count);
	}

	public PageRequest getSortedPageRequest(Sort sort) {
		return new PageRequest(page - 1, count, sort);
	}
}
