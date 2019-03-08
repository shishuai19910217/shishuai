package com.ido85.master.keyword.resources;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ido85.master.keyword.domain.GroupKeyRel;


public interface GroupKeyRelResources extends JpaRepository<GroupKeyRel, String> {
	/***
	 * 删除关键词和分组的关系
	 * @param delFlag
	 * @param updateBy
	 * @param updateDate
	 * @param groupIds
	 * @return
	 */
	@Transactional
	@Modifying
	@Query("update GroupKeyRel r set r.delFlag=:delFlag,r.updateBy = :updateBy , r.updateDate=:updateDate"
			+ "   where r.group.groupId in :groupIds")
	int deleteBygroupIds(@Param("delFlag") String delFlag,@Param("updateBy") Integer updateBy ,@Param("updateDate") Date updateDate,
			 @Param("groupIds") List<Integer> groupIds);
	
	/***
	 * 根据关键词和项目的id和分组id，删除关键词和分组的关系
	 * @param delFlag
	 * @param updateBy
	 * @param updateDate
	 * @param groupIds
	 * @return
	 */
	@Transactional
	@Modifying
	@Query("update GroupKeyRel r set r.delFlag=:delFlag,r.updateBy = :updateBy , r.updateDate=:updateDate"
			+ "   where r.group.groupId in :groupIds and r.proKeyword.proKeyRelId in :proKeyRelId ")
	int deleteBygroupIds(@Param("delFlag") String delFlag,@Param("updateBy") Integer updateBy ,@Param("updateDate") Date updateDate,
			 @Param("groupIds") List<Integer> groupIds, @Param("proKeyRelId")List<Integer> proKeyRelId);
	
	/**
	 * 根据关键词与项目的关系删除 分组
	 * @param delFlag
	 * @param updateBy
	 * @param updateDate
	 * @param prokeyRelIds
	 * @return
	 */
	@Transactional
	@Modifying
	@Query("update GroupKeyRel r set r.delFlag=:delFlag,r.updateBy = :updateBy , r.updateDate=:updateDate"
			+ "   where r.proKeyword.proKeyRelId in :prokeyRelIds")
	int deleteByRel(@Param("delFlag") String delFlag,@Param("updateBy") Integer updateBy ,@Param("updateDate") Date updateDate,
			 @Param("prokeyRelIds") List<Integer> prokeyRelIds);
	/***
	 * 根据分组id查找 关系
	 * @param groupId
	 * @return
	 */
	@Query("select g from GroupKeyRel g where g.group.groupId=:groupId")
	List<GroupKeyRel> getGroupKeyRelBygroupId(@Param("groupId") Integer groupId);
	
	/***
	 * 根据分组id查找 关系
	 * @param groupIds
	 * @return
	 */
	@Query("select g from GroupKeyRel g where g.group.groupId in :groupIds")
	List<GroupKeyRel> getGroupKeyRelBygroupIds(@Param("groupIds") List<Integer> groupIds);

}
