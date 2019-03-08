package com.ido85.master.user.resources;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ido85.master.user.domain.Profession;



public interface ProfessionResources extends JpaRepository<Profession, String> {
	/**
	 * 根据行业id获取行业信息
	 * @param professionId
	 * @return
	 */
	@Query("SELECT t FROM Profession t where t.professionId = :professionId")
	Profession getProfessionInfoById(@Param("professionId")String professionId);
	
	
	
	
	
}
