package com.naver.test.school.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity(name = "grade")
public class Grade {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Integer id;

	@Column(name = "user_id")
	private int userId;

	@Column(name = "subject_id")
	private int subjectId;

	@Max(100)
	@Min(0)
	@Column(name = "score")
	private int score;

	@ManyToOne
	@JoinColumn(name = "user_id", insertable = false, updatable = false)
	@JsonIgnore
	private SchoolUser schoolUser;

	@ManyToOne
	@JoinColumn(name = "subject_id", insertable = false, updatable = false)
	@JsonIgnore
	private Subject subject;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(int subjectId) {
		this.subjectId = subjectId;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public SchoolUser getSchoolUser() {
		return schoolUser;
	}

	public void setSchoolUser(SchoolUser schoolUser) {
		this.schoolUser = schoolUser;
	}

	public Subject getSubject() {
		return subject;
	}

	public void setSubject(Subject subject) {
		this.subject = subject;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
}
