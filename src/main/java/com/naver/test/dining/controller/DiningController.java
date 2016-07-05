package com.naver.test.dining.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.naver.test.dining.domain.Restaurant;
import com.naver.test.dining.repository.RestaurantRepository;

@RestController
@RequestMapping("/dining")
public class DiningController {
	@Autowired
	private RestaurantRepository restaurantRepository;

	@RequestMapping(method = RequestMethod.GET)
	public List<Restaurant> list() {
		return restaurantRepository.findAll();
	}

	@RequestMapping(method = RequestMethod.POST)
	public Restaurant insert(Restaurant restaurant) {
		return restaurantRepository.save(restaurant);
	}
}
