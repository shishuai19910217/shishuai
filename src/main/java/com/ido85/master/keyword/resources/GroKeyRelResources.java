package com.ido85.master.keyword.resources;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ido85.master.keyword.domain.GroKeyRel;


public interface GroKeyRelResources extends JpaRepository<GroKeyRel, String> {
	/**
	 * 根据分组id获取所有的分组和关键词的关系
	 * @param groupIds
	 * @return
	 */
	@Query("select t from GroKeyRel t where t.groupId in :groupIds and t.delFlag = '0' ")
	List<GroKeyRel> getAllRelByGroupId(@Param("groupIds")List<Integer> groupIds);
}
