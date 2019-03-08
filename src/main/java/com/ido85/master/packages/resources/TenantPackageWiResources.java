package com.ido85.master.packages.resources;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ido85.master.packages.domain.TenantPackage;
import com.ido85.master.packages.dto.ManageTenantPackageDto;


public interface TenantPackageWiResources  extends JpaRepository<TenantPackage, Integer>{
	
	/****
	 * 根据套餐id查询套餐 + 时间必须是有效的
	 * @param tenantId
	 * @return
	 */
	@Query("select p from  TenantPackage  p  "
			+ " where p.delFlag='0' and p.tenantId= :tenantId and p.appType='0' "
			+ " and p.packageStartDate <=:nowDate and p.packageEndDate >= :nowDate")
	public TenantPackage getTenantPackageInfo(@Param("tenantId")Integer tenantId,@Param("nowDate") Date nowDate );
	/***
	 * 查询租户下所有的套餐实例  包含到期的   删除的
	 * @param tenantId
	 * @param appType
	 * @return
	 */
	@Query("select p from  TenantPackage  p  "
			+ " where p.appType= :appType and p.tenantId= :tenantId  order by p.packageEndDate ")
	public List<TenantPackage> getPackageExampleAllListByTenantId(@Param("tenantId")Integer tenantId,@Param("appType")String appType);
	
	
	/*** 
	 * wi
	 * 根据租户查询所有的套餐实例 时间有效的
	 * @param id
	 * @return
	 */
	
	@Query("select p from  TenantPackage  p  "
			+ " where p.delFlag='0'   and   p.tenantId= :tenantId"
			+ " and p.packageStartDate <=:nowDate and p.packageEndDate >= :nowDate")
	public List<TenantPackage> getPackageExampleListByTenantId(@Param("tenantId")Integer tenantId,
			@Param("nowDate") Date nowDate);
	/****
	 * 根据 套餐id  租户id查询实例  时间有效的
	 * @param tenantId
	 * @param packageId
	 * @return
	 */
	@Query("select p from  TenantPackage  p  "
			+ " where p.delFlag='0'   and   p.tenantId= :tenantId"
			+ " and p.packageId = :packageId "
			+ " and p.packageStartDate <=:nowDate and p.packageEndDate >= :nowDate"
			)
	public TenantPackage getTenantpackageByPackageId(@Param("tenantId")Integer tenantId,@Param("packageId")Integer packageId,
			@Param("nowDate") Date nowDate);
	
	/****
	 * 根据 套餐id  租户id查询实例
	 * @param tenantId
	 * @param packageId
	 * @return
	 */
	@Query("select p from  TenantPackage  p  "
			+ " where p.tenantPackageId=:tenantPackageId"
			)
	public TenantPackage getTenantpackageByTenantpackageId(@Param("tenantPackageId")Integer tenantPackageId);
	
	/***
	 * 修改实例的开始结束时间
	 * @param updateBy
	 * @param updateDate
	 * @param packageStartDate
	 * @param packageEndDate
	 * @param tenantPackageId
	 * @return
	 */
	@Transactional
	@Modifying
	@Query("UPDATE TenantPackage t set t.updateBy= :updateBy ,t.updateDate = :updateDate ,"
			+ "t.packageEndDate = :packageEndDate"
			+ " where t.tenantPackageId = :tenantPackageId")
	public int  updateTenantPackageByTenantPackageId(@Param("updateBy") Integer updateBy,
			@Param("updateDate") Date updateDate,
			@Param("packageEndDate") Date packageEndDate,
			@Param("tenantPackageId") Integer  tenantPackageId);
	
	
	/***
	 * 修改套餐
	 * 
	 * @param delFlag
	 * @param packageEndDate
	 * @param tenantPackageId
	 * @return
	 */
	@Transactional
	@Modifying
	@Query("UPDATE TenantPackage t set t.packageEndDate=:packageEndDate,t.delFlag = :delFlag,t.updateBy= :updateBy ,t.updateDate = :updateDate"
			+ " where t.tenantPackageId = :tenantPackageId")
	public int updateTenantPackageByTenantPackageId(@Param("packageEndDate") Date packageEndDate,
			@Param("delFlag") String delFlag, @Param("updateBy") Integer updateBy, @Param("updateDate") Date updateDate,
			@Param("tenantPackageId") Integer tenantPackageId);

	/***
	 * 修改实例的开始结束时间
	 * @param updateBy
	 * @param updateDate
	 * @param packageStartDate
	 * @param packageEndDate
	 * @param tenantPackageId
	 * @return
	 */
	@Transactional
	@Modifying
	@Query("UPDATE TenantPackage t set t.updateBy= :updateBy ,t.updateDate = :updateDate ,"
			+ "t.packageStartDate = :packageStartDate,"
			+ "t.packageEndDate = :packageEndDate"
			+ " where t.tenantPackageId = :tenantPackageId")
	public int  updateTenantPackageByTenantPackageId(@Param("updateBy") Integer updateBy,
			@Param("updateDate") Date updateDate,
			@Param("packageStartDate") Date packageStartDate,
			@Param("packageEndDate") Date packageEndDate,
			@Param("tenantPackageId") Integer  tenantPackageId);
	/***
	 * 置为失效
	 * @param delFlag
	 * @param packageEndDate
	 * @param tenantPackageId
	 * @return
	 */
	@Transactional
	@Modifying
	@Query("UPDATE TenantPackage t set t.packageEndDate=:packageEndDate"
			+ " where t.tenantPackageId = :tenantPackageId")
	public int  updateTenantPackageByTenantPackageId(@Param("packageEndDate") Date packageEndDate,
			@Param("tenantPackageId") Integer tenantPackageId);
	
	/***
	 * 修改租户套餐状态
	 * 
	 * @param tenantId
	 * @param delFlag
	 * @return
	 * @throws Exception 
	 */
	@Transactional
	@Modifying
	@Query("update TenantPackage p set p.delFlag = :delFlag where p.tenantId = :tenantId")
	public void updateTenantPackageDelFlag(@Param("tenantId")Integer tenantId, @Param("delFlag")String delFlag);
	
	
	
	@Query("select p from  TenantPackage  p  "
			+ " where p.tenantPackageId in :tenantPackageIds"
			)
	public List<TenantPackage> getListByTenantPackageId(@Param("tenantPackageIds") List<Integer> tenantPackageIds);
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/*
	 * **********上方是应用服务私有方法不对别的模块和别的应用直接提供提供***********
	 */
	
	/*
	 * ********************此处之下是service服务层调用的公共方法，若要修改，请慎重操作*********************
	 */
	
	/*** 
	 * wi
	 * 根据租户查询某个应用下的套餐实例的规格 +有效时间
	 * @param id
	 * @return
	 */
	@Query("select p from  TenantPackage  p  "
			+ " where p.delFlag='0'   and p.appType= :appType and   p.tenantId= :tenantId"
			+ " and p.packageStartDate <=:nowDate and p.packageEndDate >= :nowDate")
	public List<TenantPackage> getPackageExampleListByTenantId(@Param("tenantId")Integer tenantId,
			@Param("appType")String appType,@Param("nowDate") Date nowDate);
	/*** 
	 * wi
	 * 根据租户查询某个应用下的套餐实例的规格 +有效时间
	 * @param id
	 * @return
	 */
	@Query("select p from  TenantPackage p  "
			+ " where p.delFlag='0' and p.appType= :appType and p.tenantId in :tenantIds"
			+ " and p.packageStartDate <=:nowDate and p.packageEndDate >= :nowDate")
	public List<TenantPackage> getBatchPackageExampleListByTenantId(@Param("tenantIds")List<Integer> tenantIds,
			@Param("appType")String appType,@Param("nowDate")Date nowDate);
	
	@Query("select new com.ido85.master.packages.dto.ManageTenantPackageDto( "
			+ "p.tenantId, p.packageEndDate, p.packageId,p.packageStartDate, p.appType, p.tenantPackageId "
			+ " ) "
			+ " from  TenantPackage  p  "
			+ " where p.delFlag='0'   and   p.tenantId in :tenantIds"
			+ " and p.packageStartDate <=:nowDate and p.packageEndDate >= :nowDate")
	public List<ManageTenantPackageDto> getTenantPackageList(@Param("tenantIds") List<Integer> tenantIds, @Param("nowDate") Date nowDate);
	
	/***
	 * 修改套餐 时间
	 * 
	 * @param tenantId
	 * @param delFlag
	 * @return
	 * @throws Exception
	 */
	@Transactional
	@Modifying
	@Query("update TenantPackage p set p.packageEndDate = :packageEndDate where p.tenantId = :tenantId")
	public void updateTenantPackagePackageEndDate(@Param("tenantId") Integer tenantId,
			@Param("packageEndDate") Date packageEndDate);
	
}
