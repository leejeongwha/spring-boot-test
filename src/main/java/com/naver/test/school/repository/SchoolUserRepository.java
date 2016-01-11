package com.naver.test.school.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.naver.test.school.model.Role;
import com.naver.test.school.model.SchoolUser;

@Repository
public interface SchoolUserRepository extends JpaRepository<SchoolUser, Integer> {
	List<SchoolUser> findByName(String name);

	List<SchoolUser> findByNameAndRole(String name, Role role);

	Long deleteBySubjectId(Integer subjectId);
}
