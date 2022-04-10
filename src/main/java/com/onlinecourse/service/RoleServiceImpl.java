package com.onlinecourse.service;

import java.util.List;

import javax.transaction.Transactional;
import org.springframework.stereotype.Service;
import com.onlinecourse.dao.RoleRepo;
import com.onlinecourse.entity.Role;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {
	
	private RoleRepo roleRepo;
	
	public RoleServiceImpl(RoleRepo roleRepo) {
		this.roleRepo = roleRepo;
	}

	@Override
	public List<Role> findAll() {
		return roleRepo.findAll();	
	}

	@Override
	public Role findOne(int id) {
		return roleRepo.findById(id).orElseThrow(RuntimeException::new);
	}
}