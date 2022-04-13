package com.onlinecourse.service;

import com.onlinecourse.entity.UserImage;

public interface UserImageService {
	UserImage findById(int id);
	void save(UserImage userImage);
}
