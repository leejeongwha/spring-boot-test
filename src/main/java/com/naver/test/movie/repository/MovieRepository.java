package com.naver.test.movie.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.naver.test.movie.domain.Movie;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Integer> {

}
