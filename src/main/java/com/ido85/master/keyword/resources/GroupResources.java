package com.ido85.master.keyword.resources;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ido85.master.keyword.domain.Group;


public interface GroupResources extends JpaRepository<Group, String> {
	/**
	 * 根据用户id获取用户信息
	 * @param userId
	 * @return
	 
	@Query("SELECT t FROM User t where t.userId = :userId")
	User getUserInfo(@Param("userId")String id);*/
	/**
	 * 项目下的关键词的所有分组
	 * @param keyword
	 * @param projectId
	 * @return
	 * 
	 */
	@Query("select distinct g from Group g join g.groupKeyRels gkr where gkr.proKeyword.proKeyRelId = :proKeywordId and g.projectId = :projectId and g.delFlag = '0' and gkr.delFlag = '0'")
	public List<Group> getProKeywordGroup(@Param("proKeywordId")Integer proKeywordId, @Param("projectId")Integer projectId);
	
	/**
	 * 校验项目下的分组是否全部存在
	 * @param groups
	 * @param projectId
	 * @return
	 */
	@Query("select count(g) from Group g where g.projectId = :projectId and g.groupId in :groupIdList")
	public int chechGroups(@Param("groupIdList")List<Integer> groups, @Param("projectId")Integer projectId);
	
	/**
	 * 校验项目下的关键词是否全部存在
	 * @param keywords
	 * @param projectId
	 * @return
	 */
	@Query("select count(g) from Group g where g.projectId = :projectId and g.groupId in :groupIdList")
	public int chechProKeywords(@Param("groupIdList")List<Integer> keywords, @Param("projectId")Integer projectId);
	/**
	 * 项目下的所有分组
	 * @param keyword
	 * @param projectId
	 * @return
	 * 
	 */
	@Query("select g from Group g where g.projectId = :projectId and g.delFlag = '0'")
	public List<Group> getAllProGroups(@Param("projectId")Integer projectId);
	
	
	
	/***
	 * 删除关键词
	 * @param delFlag
	 * @param updateBy
	 * @param updateDate
	 * @param projectId
	 * @return
	 */
	@Transactional
	@Modifying
	@Query("update Group r set r.delFlag=:delFlag,r.updateBy = :updateBy , r.updateDate=:updateDate"
			+ "   where r.projectId=:projectId")
	int deleteByProjectId(@Param("delFlag") String delFlag,@Param("updateBy") Integer updateBy ,@Param("updateDate") Date updateDate,
			 @Param("projectId") Integer projectId);
	/***
	 * 单个删除
	 * @param delFlag
	 * @param updateBy
	 * @param updateDate
	 * @param groupId
	 * @return
	 */
	@Transactional
	@Modifying
	@Query("update Group r set r.delFlag=:delFlag,r.updateBy = :updateBy , r.updateDate=:updateDate"
			+ "   where r.groupId=:groupId")
	int deleteByGroupId(@Param("delFlag") String delFlag,@Param("updateBy") Integer updateBy ,@Param("updateDate") Date updateDate,
			 @Param("groupId") Integer groupId);
	
	/***
	 * 多个删除
	 * @param delFlag
	 * @param updateBy
	 * @param updateDate
	 * @param groupId
	 * @return
	 */
	@Transactional
	@Modifying
	@Query("update Group r set r.delFlag=:delFlag,r.updateBy = :updateBy , r.updateDate=:updateDate"
			+ "   where r.groupId in :groupIds")
	int deleteByGroupIds(@Param("delFlag") String delFlag,@Param("updateBy") Integer updateBy ,@Param("updateDate") Date updateDate,
			 @Param("groupIds") List<Integer> groupIds);
	
}
