package com.ido85.master.project.resources;

import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ido85.master.project.domain.OrderProjectTenantExampleRel;

public interface OrderProjectTenantExampleRelWiResources  extends JpaRepository<OrderProjectTenantExampleRel, String>{
	/***
	 * 根据租户id修改关系表中的实例id
	 * @param tenantPackageId
	 * @param updateByf
	 * @param updateDate
	 * @return
	 */
	@Transactional
	@Modifying
	@Query("update OrderProjectTenantExampleRel r "
			+ " set r.tenantPackageId=:tenantPackageId,r.updateBy = :updateBy , r.updateDate=:updateDate where 1=1"
			+ " and  r.tenantId=:tenantId"
			)
	int updateTenantPackageIdByTenantId(@Param("tenantPackageId") Integer tenantPackageId,
			@Param("updateBy") Integer updateBy,@Param("updateDate") Date updateDate,
			@Param("tenantId") Integer tenantId
			);
	
	
	/***
	 * 根据租户id修改关系表中的实例id
	 * @param tenantPackageId
	 * @param updateByf
	 * @param updateDate
	 * @return
	 */
	@Transactional
	@Modifying
	@Query("update OrderProjectTenantExampleRel r "
			+ " set r.tenantPackageId=:tenantPackageId,r.updateBy = :updateBy , r.updateDate=:updateDate where 1=1"
			+ " and  r.tenantPackageId=:oldTenantPackageId"
			)
	int updateTenantPackageIdByTenantPackageId(@Param("tenantPackageId") Integer tenantPackageId,
			@Param("updateBy") Integer updateBy,@Param("updateDate") Date updateDate,
			@Param("oldTenantPackageId") Integer oldTenantPackageId
			);
}
