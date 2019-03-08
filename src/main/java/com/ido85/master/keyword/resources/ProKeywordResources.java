package com.ido85.master.keyword.resources;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ido85.master.keyword.domain.ProKeyword;
import com.ido85.master.keyword.dto.KeywordGroupInfoDto;


public interface ProKeywordResources extends JpaRepository<ProKeyword, String> {
	/**
	 * 获取项目的所有关键词的总数
	 * @param projectId
	 * @return
	 */
	@Query("SELECT count(1) FROM ProKeyword t WHERE t.projectId = :projectId and t.delFlag = '0'")
	int getProjectKeywordNum(@Param("projectId")Integer projectId);
	
	/**
	 * 获取项目的所有关键词的总数
	 * @param projectId
	 * @return
	 */
	@Query("SELECT count(1) FROM ProKeyword t WHERE t.projectId = :projectId and t.delFlag = '0' and t.isBrand = :isBrand")
	int getProjectKeywordNum(@Param("projectId")Integer projectId,@Param("isBrand")String isBrand);
	/**
	 * 获取项目下所有的关键词
	 * @return
	 */
	@Query("select k from ProKeyword k where k.projectId = :projectId and k.delFlag = '0'")
	public List<ProKeyword> getKeyWords(@Param("projectId")Integer projectId);
	/**
	 * 根据项目和关键词关系id 获取项目的所有的关键词id
	 * @return
	 */
	@Query("select k.keywordId from ProKeyword k where k.proKeyRelId in :proKeyRelId and k.delFlag = '0'")
	public List<Integer> getProKeyById(@Param("proKeyRelId")List<Integer> proKeyRelId);
	/**
	 * 根据关键词id和项目id，获取项目下的单个关键词信息
	 * @param projectId
	 * @param keyword
	 * @return
	 */
	@Query("select k from ProKeyword k where k.projectId = :projectId and k.keywordId = :keyword and k.delFlag = '0'")
	public List<ProKeyword> getProKeywordInfo(@Param("projectId")Integer projectId, @Param("keyword")Integer keyword);
	/**
	 * 根据关键词id和项目id，批量获取项目下的关键词信息
	 * @param projectId
	 * @param keywords
	 * @return
	 */
	@Query("select k from ProKeyword k where k.projectId = :projectId and k.keywordId in :keywords and k.delFlag = '0'")
	public List<ProKeyword> getProKeywordInfo(@Param("projectId")Integer projectId, @Param("keywords")List<Integer> keywords);
	/**
	 * 根据项目和关键词关系表id和项目id，批量获取项目下的关键词信息
	 * @param projectId
	 * @param proKeywordRelIds
	 * @return
	 */
	@Query("select k from ProKeyword k where k.projectId = :projectId and k.proKeyRelId in :proKeywordRelIds and k.delFlag = '0'")
	public List<ProKeyword> getProKeywordInfos(@Param("projectId")Integer projectId, @Param("proKeywordRelIds")List<Integer> proKeywordRelIds);
	
	/**
	 * 根据关键词id和项目id，批量给关键词设置品牌属性
	 * @param projectId
	 * @param keywordIds
	 * @return
	 */
	@Transactional
	@Modifying
	@Query("update ProKeyword k set k.isBrand = '1' where k.projectId = :projectId and k.keywordId in :keywordIds and k.delFlag = '0'")
	public int setKeywordBrand(@Param("projectId")Integer projectId, @Param("keywordIds")List<Integer> keywordIds);
	
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
	@Query("update ProKeyword r set r.delFlag=:delFlag,r.updateBy = :updateBy , r.updateDate=:updateDate"
			+ "   where r.projectId=:projectId")
	int deleteByProjectId(@Param("delFlag") String delFlag,@Param("updateBy") Integer updateBy ,@Param("updateDate") Date updateDate,
			 @Param("projectId") Integer projectId);
	
	
	
	/***
	 * 删除 关键词与项目的关系   
	 * @param delFlag
	 * @param updateBy
	 * @param updateDate
	 * @param projectId
	 * @param keywordIds
	 * @return
	 */
	@Transactional
	@Modifying
	@Query("update ProKeyword r set r.delFlag=:delFlag,r.updateBy = :updateBy , r.updateDate=:updateDate"
			+ "   where r.projectId=:projectId and r.keywordId in :keywordIds")
	int deleteByProjectIdAndKeywordId(@Param("delFlag") String delFlag,@Param("updateBy") Integer updateBy ,@Param("updateDate") Date updateDate,
			 @Param("projectId") Integer projectId,@Param("keywordIds") List<Integer> keywordIds);
	/***
	 * 删除 关键词与项目的关系   
	 * @param delFlag
	 * @param updateBy
	 * @param updateDate
	 * @param projectId
	 * @param keywordIds
	 * @return
	 */
	@Transactional
	@Modifying
	@Query("update ProKeyword r set r.delFlag=:delFlag,r.updateBy = :updateBy , r.updateDate=:updateDate"
			+ "   where r.proKeyRelId in :proKeyRelIds")
	int deleteByProkeywordRelIds(@Param("delFlag") String delFlag,@Param("updateBy") Integer updateBy ,@Param("updateDate") Date updateDate,
			 @Param("proKeyRelIds") List<Integer> proKeyRelIds);
	
	
	/***
	 * 修改 品牌
	 * @param updateBy
	 * @param updateDate
	 * @param projectId
	 * @param keywordIds
	 * @return
	 */
	@Transactional
	@Modifying
	@Query("update ProKeyword r set r.isBrand=:isBrand,r.updateBy = :updateBy , r.updateDate=:updateDate"
			+ "   where r.proKeyRelId in :proKeyRelIds")
	int updateBrandByProkeywordRelIds(@Param("isBrand") String isBrand,@Param("updateBy") Integer updateBy ,@Param("updateDate") Date updateDate,
			 @Param("proKeyRelIds") List<Integer> proKeyRelIds);
	
	/**
	 * 根据项目id和项目与关键词关系表id获取项目下的所有的关键词的分组信息
	 * @param projectId
	 * @return
	 */
	@Query("SELECT new com.ido85.master.keyword.dto.KeywordGroupInfoDto(pk.keywordId,pk.projectId,g.groupId,r.groupKeyId,pk.proKeyRelId,g.groupName,k.keywordName) FROM "
			+ " ProKeyword pk, GroKeyRel r, Group g, Keyword k \n" +
			" WHERE pk.projectId = :projectId and g.projectId = :projectId and pk.proKeyRelId in :proKeyRelIds and pk.proKeyRelId = r.proKeyRelId and pk.keywordId = k.keywordId  \n" +
			" and r.groupId = g.groupId and pk.delFlag = '0' and r.delFlag = '0' and g.delFlag = '0' and k.delFlag = '0' ")
	List<KeywordGroupInfoDto> getAllKeywordGroupInfo(@Param("projectId")Integer projectId, @Param("proKeyRelIds")List<Integer> proKeyRelIds);
	
}
