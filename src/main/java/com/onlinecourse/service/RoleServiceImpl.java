package com.onlinecourse.service;

import java.util.List;

import javax.transaction.Transactional;
import org.springframework.stereotype.Service;
import com.onlinecourse.dao.RoleRepo;
import com.onlinecourse.entity.Role;
import com.onlinecourse.utils.Log;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {
	
	private RoleRepo roleRepo;
	
	public RoleServiceImpl(RoleRepo roleRepo) {
		this.roleRepo = roleRepo;
	}

	@Override
	public List<Role> findAll() {
		Log.info("fetching all Roles");
		return roleRepo.findAll();
	}

	@Override
	public Role findOne(int id) {
		Log.info("getting role with an id of "+id);
		return roleRepo.findById(id).orElseThrow(RuntimeException::new);
	}
}