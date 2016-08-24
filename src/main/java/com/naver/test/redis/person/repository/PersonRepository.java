package com.naver.test.redis.person.repository;

import org.springframework.data.repository.CrudRepository;

import com.naver.test.redis.person.domain.Person;

public interface PersonRepository extends CrudRepository<Person, String> {

}
