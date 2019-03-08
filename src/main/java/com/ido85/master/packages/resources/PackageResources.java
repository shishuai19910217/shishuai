package com.ido85.master.packages.resources;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ido85.master.packages.domain.Packages;


public interface PackageResources extends JpaRepository<Packages, String> {
	/***
	 * 根据id查询套餐
	 * @param packageId
	 * @param appType
	 * @return
	 */
	
	@Query("select  p from  Packages  p  "
			+ " where p.delFlag='0' "
			+ " and p.appType = :appType "
			+ " and p.packageId= :packageId ")
	public Packages getPackageInfoByPackageId(@Param("packageId")Integer packageId,@Param("appType")String appType);
	
	
	/***
	 * 根据id查询套餐
	 * @param packageId
	 * @return
	 */
	
	@Query("select  p from  Packages  p  "
			+ " where p.packageId = :packageId  "
			+ " and  p.delFlag='0' ")
	public Packages getPackageInfoByPackageId(@Param("packageId") Integer packageId);
	
	/***
	 * 查询套餐
	 * @param packageId
	 * @param appType
	 * @return
	 */
	@Query("select p  from  Packages  p  "
			)
	public List<Packages> getPackageList();
	/***
	 * 查询套餐
	 * @param packageIds 
	 * @param appType
	 * @return
	 */
	@Query("select new com.ido85.master.packages.domain.Packages(p.packageId,p.packageName, "
			+ "p.packageType,p.packagePriceMontn, p.packagePriceYear, p.packageOldPriceMonth,"
			+ "p.packageOldPriceYear, p.appType, p.delFlag, p.endDate, p.reserveField1, p.reserveField2,p.packageLevel)  "
			+ " from  Packages  p  where p.packageId in :packageIds "
			)
	public List<Packages> getPackageListById(@Param("packageIds")List<Integer> packageIds);
}
