package com.onlinecourse.service;

import java.util.List;
import java.util.Optional;

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
		Optional<Role> roleOption = roleRepo.findById(id);
		
		if (roleOption.isPresent()) {
			return roleOption.get();
		} else {
			throw new RuntimeException("No role found with id of "+id);
		}
	}
}