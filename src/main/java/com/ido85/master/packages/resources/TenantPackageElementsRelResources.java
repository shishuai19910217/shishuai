package com.ido85.master.packages.resources;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ido85.master.packages.domain.TenantPackageElementsRel;

public interface TenantPackageElementsRelResources extends JpaRepository<TenantPackageElementsRel, String> {
	/***
	 * 删除实例与元素的关系
	 * @param delflag
	 * @param tenantPackageId
	 * @return
	 */
	@Transactional
	@Modifying
	@Query("UPDATE TenantPackageElementsRel rel set rel.delFlag = :delFlag where 1=1 "
			+ " and rel.tenantPackage.tenantPackageId = :tenantPackageId")
	public int updateByTenantPackageId(@Param("delFlag") String delFlag,@Param("tenantPackageId") Integer tenantPackageId);
	
	@Transactional
	@Modifying
	@Query("DELETE from  TenantPackageElementsRel rel  where 1=1 "
			+ " and rel.tenantPackage.tenantPackageId = :tenantPackageId")
	public int deleteByTenantPackageId(@Param("tenantPackageId") Integer tenantPackageId);
	

}
