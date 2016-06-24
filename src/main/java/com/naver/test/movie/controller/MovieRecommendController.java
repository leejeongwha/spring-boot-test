package com.naver.test.movie.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.naver.test.movie.domain.Movie;
import com.naver.test.movie.repository.MovieRepository;
import com.naver.test.movie.service.RecommendService;

@RestController
@RequestMapping("recommend")
public class MovieRecommendController {

	@Autowired
	private RecommendService recommendService;

	@Autowired
	private MovieRepository movieRepository;

	@RequestMapping("item")
	public List<Movie> item(@RequestParam Integer itemId, @RequestParam Integer count) throws Exception {
		List<Movie> resultList = new ArrayList<Movie>();

		List<Integer> movieIdList = recommendService.itemBasedRecommend(itemId, count);

		for (Integer movieId : movieIdList) {
			resultList.add(movieRepository.findOne(movieId));
		}

		return resultList;
	}
}
