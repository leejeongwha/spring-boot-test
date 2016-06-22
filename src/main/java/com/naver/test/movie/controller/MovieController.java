package com.naver.test.movie.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.naver.test.movie.domain.Movie;
import com.naver.test.movie.repository.MovieRepository;

@RestController
@RequestMapping("movies")
public class MovieController {
	@Autowired
	private MovieRepository movieRepository;

	@RequestMapping(method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.CREATED)
	public Movie insert(Movie movie) {
		return movieRepository.save(movie);
	}

	@RequestMapping(method = RequestMethod.GET)
	public List<Movie> list(Movie movie) {
		return movieRepository.findAll();
	}

	@RequestMapping(value = "{id}", method = RequestMethod.DELETE)
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Integer id) {
		movieRepository.delete(id);
	}

	@RequestMapping(value = "{id}", method = RequestMethod.PUT)
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public Movie update(@PathVariable Integer id, Movie movie) {
		movie.setId(id);
		return movieRepository.save(movie);
	}

	@RequestMapping(value = "{id}", method = RequestMethod.GET)
	public Movie get(@PathVariable Integer id) {
		return movieRepository.findOne(id);
	}
}
