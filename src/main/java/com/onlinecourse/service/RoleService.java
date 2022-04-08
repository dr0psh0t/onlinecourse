package com.onlinecourse.service;

import java.util.List;

import com.onlinecourse.entity.Role;

public interface RoleService {
	List<Role> findAll();
	Role findOne(int id);
}
