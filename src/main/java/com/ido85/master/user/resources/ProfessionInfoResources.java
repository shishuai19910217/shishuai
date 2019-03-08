package com.ido85.master.user.resources;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ido85.master.user.domain.ProfessionInfo;



public interface ProfessionInfoResources extends JpaRepository<ProfessionInfo, String> {
	/**
	 * 根据用户id获取用户信息
	 * @param userId
	 * @return
	 */
	@Query("SELECT t FROM ProfessionInfo t where t.userId = :userId and t.delFlag = '0'")
	List<ProfessionInfo> getProfessionInfo(@Param("userId")String userId);
	
	
	
	
	
}
