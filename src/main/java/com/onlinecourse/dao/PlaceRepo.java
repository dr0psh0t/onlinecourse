package com.onlinecourse.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.onlinecourse.entity.Place;

public interface PlaceRepo extends JpaRepository<Place, Integer> {
	Place findByName(int id);
}
