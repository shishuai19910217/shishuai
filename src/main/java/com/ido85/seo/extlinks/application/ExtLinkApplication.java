package com.ido85.seo.extlinks.application;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.ido85.frame.common.Page;
import com.ido85.seo.dashboard.domain.ProDomain;
import com.ido85.seo.dashboard.domain.ProLinksView;
import com.ido85.seo.extlinks.domain.ProAnchor;
import com.ido85.seo.extlinks.domain.ProDownUrl;
import com.ido85.seo.extlinks.domain.ProLinks;
import com.ido85.seo.extlinks.dto.ExtLinkDomainsDto;
import com.ido85.seo.extlinks.dto.ExtLinkDto;
import com.ido85.seo.extlinks.dto.OutLinksViewDto;

public interface ExtLinkApplication {
	/**
	 * 根据项目id来获取外链概览中项目的根据时间倒叙排序的前limitNum条数据的第一条和最后一条的变化值
	 * @param projectId
	 * @param limitNum
	 * @return
	 */
	OutLinksViewDto getLinkInfoByProjectId(String projectId, int limitNum, Date crawlDate);
	/**
	 * 根据项目id和时间获取所有的数据
	 * @param projectId
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	List<ProLinksView> getLinkInfo(String projectId, Date startDate, Date endDate);
	/**
	 * 根据项目id来获取td_b_pro_links_view表中根据时间倒叙排序的分页数据
	 * @param projectId
	 * @param page
	 * @param flag  是否需要查询分页总数 0：否，1：是
	 * @param queryTag 是否查询项目竞品数据，1：是，0：否
	 * @param crawlDate 如果传入时间则取固定时间的数据
	 * @return
	 */
	void getPageProLinks(String projectId, Page<ProLinksView> page, String flag, String queryTag, Date crawlDate);
	/**
	 * 根据项目id来获取tf_f_pro_domain表中根据时间倒叙排序的分页数据
	 * @param projectId
	 * @param page
	 * @param flag 是否需要查询分页总数 0：否，1：是
	 * @return
	 */
	void getPageDomain(String projectId, Page<ProDomain> page, String flag);
	/**
	 * 根据项目id来获取tf_f_pro_anchor表中根据时间倒叙排序的分页数据
	 * @param projectId
	 * @param page
	 * @param flag 是否需要查询分页总数 0：否，1：是
	 * @return
	 */
	void getPageAnchor(String projectId, Page<ProAnchor> page, String flag);
	/**
	 * 根据项目id来获取td_b_pro_links表中根据时间倒叙排序的分页数据
	 * @param projectId
	 * @param page
	 * @param flag 是否需要查询分页总数 0：否，1：是
	 * @return
	 */
	void getPageExtLinks(String projectId, Page<ProLinks> page, String flag);
	/**
	 * 根据项目id来获取td_b_pro_down_url表中根据时间倒叙排序的分页数据
	 * @param projectId
	 * @param page
	 * @param flag 是否需要查询分页总数 0：否，1：是
	 * @return
	 */
	void getPageDownUrl(String projectId, Page<ProDownUrl> page, String flag);
	/**
	 * 
		外部链接域名个数
	 * @param param
	 * @return
	 */
	ExtLinkDomainsDto getExtLinkDomains(Map<String, Object> param);
	/***
	 * 外链个数
	 * @param param
	 * @return
	 */
	ExtLinkDto getExtLink(Map<String, Object> param);
	/**
	 * 根据项目id来获取tf_f_pro_anchor表中总数据
	 * @param projectId
	 * @return
	 */
	long getSumPageAnchor(Integer projectId);
	/**
	 * 根据项目id来获取tf_f_pro_domain表中总数据
	 * @param projectId
	 * @return
	 */
	long getSumPageDomain(Integer projectId);
}
