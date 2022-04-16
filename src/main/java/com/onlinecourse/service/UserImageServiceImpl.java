package com.onlinecourse.service;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.onlinecourse.dao.UserImageRepo;
import com.onlinecourse.entity.UserImage;
import com.onlinecourse.utils.Log;

@Service
@Transactional
public class UserImageServiceImpl implements UserImageService {
	
	private final UserImageRepo userImageRepo;
	
	public UserImageServiceImpl(UserImageRepo userImageRepo) {
		this.userImageRepo = userImageRepo;
	}

	@Override
	public UserImage findById(int id) {
		Log.info("getting an image with an id of "+id);
		return userImageRepo.findById(id);
	}

	@Override
	public void save(UserImage userImage) {
		Log.info("saving an image "+userImage.getId());
		userImageRepo.save(userImage);
	}

}
