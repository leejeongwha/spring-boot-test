package com.naver.test.dining.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

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
	public String list(Model model) {
		List<Restaurant> restaurantList = restaurantRepository.findAll();
		model.addAttribute("restaurantList", restaurantList);
		return "dining/list";
	}

	/**
	 * 맛집 조회
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "search", method = RequestMethod.GET)
	public String search(Model model, Restaurant restaurant) {
		List<Restaurant> restaurantList = restaurantRepository.findByNameContaining(restaurant.getName());
		model.addAttribute("restaurantList", restaurantList);
		return "dining/list";
	}

	/**
	 * 맛집 관리 폼
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "form", method = RequestMethod.GET)
	public String form(Model model) {
		return "dining/form";
	}

	/**
	 * 맛집 저장
	 * 
	 * @param restaurant
	 * @return
	 */
	@RequestMapping(value = "save", method = RequestMethod.POST)
	@ResponseBody
	public String modify(Restaurant restaurant) {
		restaurantRepository.save(restaurant);
		return "redirect:" + "/dining";
	}
}
