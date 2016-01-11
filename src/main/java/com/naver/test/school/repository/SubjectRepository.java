package com.naver.test.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.naver.test.school.model.Subject;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, Integer> {
	Subject findBySubjectName(String subjectName);
}
