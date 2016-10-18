package com.naver.test.repository;

import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.naver.test.entity.Person;

public interface PersonRepository extends JpaRepository<Person, Long> {

	CompletableFuture<Person> findById(Long id);

	@Query("select p from Person p")
	Stream<Person> all();
}
