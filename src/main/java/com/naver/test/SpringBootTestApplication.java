package com.naver.test;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.naver.test.movie.domain.Movie;
import com.naver.test.movie.repository.MovieRepository;

@SpringBootApplication
public class SpringBootTestApplication implements ApplicationRunner {
	@Autowired
	private MovieRepository movieRepository;

	public static void main(String[] args) {
		SpringApplication.run(SpringBootTestApplication.class, args);
	}

	@Override
	public void run(ApplicationArguments arg0) throws Exception {
		Movie movie = new Movie();
		movie.setTitle("test");
		movie.setReleaseDate(new Date());

		movieRepository.save(movie);

	}
}
