package com.onlinecourse.service;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.onlinecourse.dao.UserImageRepo;
import com.onlinecourse.entity.UserImage;

@Service
@Transactional
public class UserImageServiceImpl implements UserImageService {
	
	private final UserImageRepo userImageRepo;
	
	public UserImageServiceImpl(UserImageRepo userImageRepo) {
		this.userImageRepo = userImageRepo;
	}

	@Override
	public UserImage findById(int id) {
		return userImageRepo.findById(id);
	}

	@Override
	public void save(UserImage userImage) {
		userImageRepo.save(userImage);
	}

}
