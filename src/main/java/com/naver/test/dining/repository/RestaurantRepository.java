package com.naver.test.dining.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.naver.test.dining.domain.Restaurant;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {

}
