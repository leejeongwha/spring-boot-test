package com.naver.test.dining.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.naver.test.dining.domain.Restaurant;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
	Page<Restaurant> findByNameContaining(String name, Pageable pageable);

	Page<Restaurant> findAll(Pageable pageable);
}
