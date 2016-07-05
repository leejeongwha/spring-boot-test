package com.naver.test.dining.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.naver.test.dining.domain.Restaurant;
import com.naver.test.dining.repository.RestaurantRepository;

@Controller
@RequestMapping("/dining")
public class DiningController {
	@Autowired
	private RestaurantRepository restaurantRepository;

	/**
	 * 맛집 리스트
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String list(Model model, Restaurant restaurant) {
		List<Restaurant> restaurantList = new ArrayList<>();
		if (StringUtils.isNotEmpty(restaurant.getName())) {
			restaurantList = restaurantRepository.findByNameContaining(restaurant.getName());
		} else {
			restaurantList = restaurantRepository.findAll();
		}

		model.addAttribute("restaurantList", restaurantList);
		model.addAttribute("searchParam", restaurant.getName());
		return "dining/list";
	}

	/**
	 * 맛집 관리 폼
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = { "/{id}", "form" }, method = RequestMethod.GET)
	public String form(Model model, @PathVariable Optional<Long> id) {
		if (id.isPresent()) {
			model.addAttribute("restaurant", restaurantRepository.findOne(id.get()));
		}

		return "dining/form";
	}

	/**
	 * 맛집 저장
	 * 
	 * @param restaurant
	 * @return
	 */
	@RequestMapping(value = "save", method = RequestMethod.POST)
	public String modify(Restaurant restaurant) {
		restaurantRepository.save(restaurant);
		return "redirect:" + "/dining";
	}
}
