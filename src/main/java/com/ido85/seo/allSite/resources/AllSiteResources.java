package com.ido85.seo.allSite.resources;

import java.util.Date;
import java.util.List;

import javax.inject.Named;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ido85.seo.allSite.domain.PageDetail;
import com.ido85.seo.allSite.domain.PageMain;
import com.ido85.seo.allSite.domain.PageRealate;
import com.ido85.seo.allSite.dto.OutIssuesItemDto;
import com.ido85.seo.dashboard.domain.DashboardVisibility;
@Named
public interface AllSiteResources extends JpaRepository<DashboardVisibility, Long> {
	
	/**
	* @Description: 查询不同等级的问题列表
	* @param @param project_id 项目id
	* @param @param issuesCode 问题编码
	* @param @param issuesLevel 问题级别/问题类型
	* @param @return    设定文件 
	* @return List<PageDetail>    返回类型 
	* @throws
	 */
//	@Query("select d from PageRealate r,PageDetail d "
//			+ " where r.detailId = d.detailId and d.projectId = :projectId "
//			+ " and r.issuesCode = :issuesCode and r.issuesLevel = :issuesLevel "
//			+ " group by d.detailId")
//	public List<PageDetail> getIssuesInfoDetail(@Param("projectId") int projectId,@Param("issuesCode") String issuesCode,@Param("issuesLevel") String issuesLevel);
//	
	
	/**
	* @Description: 查询不同等级的问题列表
	* @param @param project_id 项目id
	* @param @param issuesLevel 问题级别/问题类型
	* @param @return    设定文件 
	* @return List<PageDetail>    返回类型 
	* @throws
	 */
//	@Query("select d from PageRealate r,PageDetail d "
//			+ " where r.detailId = d.detailId and d.projectId = :projectId "
//			+ " and r.issuesLevel = :issuesLevel "
//			+ " group by d.detailId")
//	public List<PageDetail> getIssuesInfoDetail(@Param("projectId") int projectId,@Param("issuesLevel") String issuesLevel);
//	
	

	/**
	* @Description: 获取最近几次统计的日期
	* @param @return    设定文件 
	* @return List<Date>    返回类型 
	* @throws
	 */
	@Query("select createDate from SiteStatistic s "
		    + " group by createDate"
			)
	public List<Date> getLastDate(Pageable pageable);
	
	/**
	* @Description: 统计项目在某天的所有问题
	* @param @param project_id
	* @param @return    设定文件 
	* @return Map    返回类型 
	* @throws
	 */
	@Query("SELECT\n" +
			" new com.ido85.seo.allSite.dto.OutIssuesItemDto(s.createDate,sum(s.num)) \n" +
			"FROM\n" +
			"	SiteStatistic s\n" +
			"WHERE\n" +
			"	s.projectId = :projectId and s.issuesCode != -1 and s.issuesLevel != -1  \n" +
			"and  s.createDate = :createDate\n" )
	public List<OutIssuesItemDto> getHistoryIssuesInfo(@Param("projectId") int projectId,@Param("createDate") Date createDate);
	
	/**
	* @Description: 统计项目某天某个级别的问题数量
	* @param @param project_id
	* @param @return    设定文件 
	* @return Map    返回类型 
	* @throws
	 */
	@Query("SELECT\n" +
			" new com.ido85.seo.allSite.dto.OutIssuesItemDto(s.createDate,sum(s.num)) \n" +
			"FROM\n" +
			"	SiteStatistic s\n" +
			"WHERE\n" +
			"	s.projectId = :projectId and s.issuesLevel = :issuesLevel\n" +
			"and  s.createDate = :createDate\n" )
	public List<OutIssuesItemDto> getHistoryIssuesInfo(@Param("projectId") int projectId,@Param("issuesLevel") String issuesLevel,@Param("createDate") Date createDate);
	
	/**
	* @Description: 统计项目某天某个错误等级某个错误码的问题数量
	* @param @param project_id
	* @param @return    设定文件 
	* @return Map    返回类型 
	* @throws
	 */
	@Query("SELECT\n" +
	" new com.ido85.seo.allSite.dto.OutIssuesItemDto(s.createDate,sum(s.num)) \n" +
	"FROM\n" +
	"	SiteStatistic s\n" +
	"WHERE\n" +
	"	s.projectId = :projectId and s.issuesLevel = :issuesLevel\n" +
	"and s.issuesCode = :issuesCode\n" +
	"and  s.createDate = :createDate\n" )
	public List<OutIssuesItemDto> getHistoryIssuesInfo(@Param("projectId") int projectId,@Param("issuesLevel") String issuesLevel,@Param("issuesCode") String issuesCode,@Param("createDate") Date createDate);
	
	
	@Query("select m from PageMain m where m.projectId = :projectId and m.crawlDate = :crawlDate")
	public PageMain getPageMainByProject(@Param("projectId") int projectId,@Param("crawlDate") Date crawlDate);


	@Query("select d from PageDetail d "
			+ " where d.pageUrl = :pageUrl and d.projectId = :projectId and d.crawlDate = :crawlDate"
			)
	public List<PageDetail> getDetailByUrl(@Param("projectId") int projectId,@Param("pageUrl") String pageUrl,@Param("crawlDate") Date crawlDate);
	
	@Query("select r from PageRealate r "
			+ " where r.detailId = :detailId and r.crawlDate = :crawlDate"
			)
	public List<PageRealate> getPageRealateByUrl(@Param("detailId") String detailId,@Param("crawlDate") Date crawlDate);
	
	
	@Query("select d from PageDetail d "
			+ " where d.title = :title and d.projectId = :projectId and d.crawlDate = :crawlDate"
			)
	public List<PageDetail> getRepeatTitle(@Param("projectId") int projectId,@Param("title") String title,@Param("crawlDate") Date crawlDate);
	
	
}
