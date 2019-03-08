package com.ido85.master.user.resources;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ido85.master.user.domain.Tenant;



public interface TenantResources extends JpaRepository<Tenant, String> {
	@Query("select t from Tenant t where t.delFalg='0'")
	public List<Tenant> getTenantList();
	
	
}
