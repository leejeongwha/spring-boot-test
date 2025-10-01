package com.naver.test.notice.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;


@Entity(name = "notice")
public class Notice {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer seq;
	
	@NotBlank(message = "제목은 필수 입력 항목입니다.")
	@Size(min = 1, max = 200, message = "제목은 1자 이상 200자 이하로 입력해주세요.")
	private String title;
	
	@NotBlank(message = "내용은 필수 입력 항목입니다.")
	@Size(min = 1, max = 4000, message = "내용은 1자 이상 4000자 이하로 입력해주세요.")
	private String content;
	
	@NotBlank(message = "작성자는 필수 입력 항목입니다.")
	private String userId;
	
	// Default constructor
	public Notice() {}
	
	// All args constructor
	public Notice(Integer seq, String title, String content, String userId) {
		this.seq = seq;
		this.title = title;
		this.content = content;
		this.userId = userId;
	}
	
	// Getters and Setters
	public Integer getSeq() {
		return seq;
	}
	
	public void setSeq(Integer seq) {
		this.seq = seq;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	
	public String getUserId() {
		return userId;
	}
	
	public void setUserId(String userId) {
		this.userId = userId;
	}
}
