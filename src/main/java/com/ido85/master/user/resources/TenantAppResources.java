package com.ido85.master.user.resources;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ido85.master.user.domain.TenantApp;



public interface TenantAppResources extends JpaRepository<TenantApp, String> {
	
	
	
}
