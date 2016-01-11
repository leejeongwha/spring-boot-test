package com.naver.test.school.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.naver.test.school.model.Grade;

@Repository
public interface GradeRepository extends JpaRepository<Grade, Integer> {

	List<Grade> findByUserId(Integer userId);

	List<Grade> findBySubjectId(Integer subjectId);

	Long deleteBySubjectId(Integer subjectId);

	@Query("select AVG(u.score) from grade u")
	Number getAllAverage();

	@Query("select AVG(u.score) from grade u, school_user s where u.userId = s.id and s.name = :name")
	Number getUserAverage(@Param("name") String name);
}
