package com.naver.test.dining.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.naver.test.dining.domain.Restaurant;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
	List<Restaurant> findByNameContaining(String name);
}
