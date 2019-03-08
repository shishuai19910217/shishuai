package com.ido85.seo.dashboard.resources;

import java.util.Date;
import java.util.List;

import javax.inject.Named;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ido85.seo.dashboard.domain.ProLinksView;

@Named
public interface ProLinkViewResourced extends JpaRepository<ProLinksView, Long> {
	/***
	 * 项目本身的
	 *	外部链接域名个数
	 * @param projectId
	 * @param crawlTimes
	 * @return
	 */
	@Query("select t from ProLinksView t where t.projectId =:projectId  "
			+ " and t.competitorId = :projectId "
			+ " and t.crawlDate in :crawlTimes")
	public List<ProLinksView> getExtLinkView(@Param("projectId")Integer projectId,
			@Param("crawlTimes")List<Date> crawlTimes);
	@Query("select t from ProLinksView t where t.projectId =:projectId  "
			+ " and t.competitorId <> :projectId "
			+ " and t.crawlDate = :crawlTime")
	public List<ProLinksView> getComExtLinkView(@Param("projectId") Integer projectId, @Param("crawlTime") Date crawlTime);
	
	
}
