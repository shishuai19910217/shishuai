package com.ido85.master.user.resources;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ido85.master.user.domain.TenantRelation;



public interface TenantRelationResources extends JpaRepository<TenantRelation, String> {
	/**
	 * 根据用户和租户关系id，获取用户和租户关系详细信息
	 * @param id
	 * @return
	 */
	@Query("SELECT t FROM TenantRelation t where t.tenantRelationId = :tenantRelationId")
	TenantRelation getTenantRelationInfo(@Param("tenantRelationId")String id);
	/**
	 * 根据用户id获取用户信息
	 * @param userId
	 * @return
	 */
	@Query("SELECT t FROM TenantRelation t where t.userId = :userId and t.delFlag = '0'")
	List<TenantRelation> getTenantRelationInfoByUserId(@Param("userId")String userId);
	
	
}
