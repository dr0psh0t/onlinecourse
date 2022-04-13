package com.onlinecourse.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.onlinecourse.entity.UserImage;

public interface UserImageRepo extends JpaRepository<UserImage, Integer>{
	UserImage findById(int id);
}
