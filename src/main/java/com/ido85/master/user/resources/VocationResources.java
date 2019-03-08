package com.ido85.master.user.resources;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ido85.master.user.domain.Vocation;



public interface VocationResources extends JpaRepository<Vocation, String> {
	/**
	 * 根据职业id获取用户职业信息
	 * @param userId
	 * @return
	 */
	@Query("SELECT t FROM Vocation t where t.vocationId = :vocationId")
	Vocation getVocationInfoById(@Param("vocationId")String vocationId);
	
	
	
	
	
}
