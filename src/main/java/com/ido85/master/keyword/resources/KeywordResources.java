package com.ido85.master.keyword.resources;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ido85.master.keyword.domain.Keyword;
import com.ido85.master.keyword.domain.ProKeyword;


public interface KeywordResources extends JpaRepository<Keyword, String> {
	/**
	 * 根据用户id获取用户信息
	 * @param userId
	 * @return
	 
	@Query("SELECT t FROM User t where t.userId = :userId")
	User getUserInfo(@Param("userId")String id);*/
	/**
	 * 查询所有的关键词
	 * @return
	 */
	@Query("select k from Keyword k where k.delFlag = '0' ")
	public List<Keyword> getKeyWords();
	/**
	 * 查询所有的关键词
	 * @return
	 */
	@Query("select k from Keyword k,ProKeyword p where p.projectId = :projectId and p.keywordId = k.keywordId and"
			+ " p.delFlag = '0' and k.delFlag = '0' ")
	public List<Keyword> getProKeyWords(@Param("projectId")Integer projectId);
	/**
	 * 查询所有的关键词
	 * @return
	 */
	@Query("select k.keywordId from Keyword k,ProKeyword p where p.projectId = :projectId and p.keywordId = k.keywordId "
			+ " and p.isBrand = :brand and p.delFlag = '0' and k.delFlag = '0' ")
	public List<String> getProKeyWords(@Param("projectId")Integer projectId, @Param("brand")String brand);
	
	/**
	 * 查询所有的关键词
	 * @return
	 */
	@Query("select k.keywordId,k.keywordName,p.isBrand,p.proKeyRelId from Keyword k,ProKeyword p where p.projectId = :projectId and p.keywordId = k.keywordId "
			+ " and  p.delFlag = '0' and k.delFlag = '0' ")
	public List<Object[]> getKeyWordsAndBrand(@Param("projectId")Integer projectId);

	/**
	 * 查询关键词的优化难度
	 * @param keyword
	 * @return
	 */
	@Query("select k from Keyword k where k.keywordId = :keyword and k.delFlag = '0'")
	public List<Keyword> getDifficultyNum(@Param("keyword")Integer keyword);

}
