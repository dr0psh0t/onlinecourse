package com.onlinecourse.service;

import java.util.List;

import com.onlinecourse.entity.Place;

public interface PlaceService {
	List<Place> findAll();
	Place findOne(int id);
}
