package com.naver.test.school.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity(name = "subject")
public class Subject {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Integer id;

	@Column(name = "subject_name")
	private String subjectName;

	@OneToMany(mappedBy = "subject")
	@JsonIgnore
	private List<SchoolUser> schoolUsers = new ArrayList<SchoolUser>();

	@OneToMany(mappedBy = "subjectId", cascade = CascadeType.REMOVE)
	@JsonIgnore
	private List<Grade> grades = new ArrayList<Grade>();

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getSubjectName() {
		return subjectName;
	}

	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	public List<SchoolUser> getSchoolUsers() {
		return schoolUsers;
	}

	public void setSchoolUsers(List<SchoolUser> schoolUsers) {
		this.schoolUsers = schoolUsers;
	}

	public List<Grade> getGrades() {
		return grades;
	}

	public void setGrades(List<Grade> grades) {
		this.grades = grades;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
}