package com.user.service;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.user.entity.Role;
import com.user.repository.RoleRepository;

@Service
public class RoleService {
	
	private Logger logger = LoggerFactory.getLogger(RoleService.class);
	
	@Autowired
	private RoleRepository roleRepository;
	
	
	public Role getByName(String roleName) {
		logger.info(" get by role name {}",roleName);
		try {
			Optional<Role> role=roleRepository.findByName(roleName);
			if(role.isPresent()) {
				return role.get();
			}
			return null;
		}catch (Exception e) {
			logger.error("getByName() : Exception occured, message={}", e.getMessage(), e);
			return null;
		}
	}
	
	public Role getById(Long roeId) {
		logger.info(" get by role id {}",roeId);
		try {
			Optional<Role> role=roleRepository.findById(roeId);
			if(role.isPresent()) {
				return role.get();
			}
			return null;
		}catch (Exception e) {
			logger.error("getById() : Exception occured, message={}", e.getMessage(), e);
			return null;
		}
	}

}
