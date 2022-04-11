package com.onlinecourse.service;

import java.util.List;
import javax.transaction.Transactional;
import org.springframework.stereotype.Service;

import com.onlinecourse.dao.PlaceRepo;
import com.onlinecourse.entity.Place;

@Service
@Transactional
public class PlaceServiceImpl implements PlaceService {
	
	private PlaceRepo placeRepo;
	
	public PlaceServiceImpl(PlaceRepo placeRepo) {
		this.placeRepo = placeRepo;
	}

	@Override
	public List<Place> findAll() {
		return placeRepo.findAll();
	}

	@Override
	public Place findOne(int id) {
		return placeRepo.findById(id).orElseThrow(RuntimeException::new);
	}
}