package com.ido85.seo.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ido85.config.properties.BussinessMsgCodeProperties;
import com.ido85.frame.common.Page;
import com.ido85.frame.common.restful.Resource;
import com.ido85.frame.common.utils.CopyUtil;
import com.ido85.frame.common.utils.DateUtils;
import com.ido85.frame.common.utils.StringUtils;
import com.ido85.master.project.domain.Competitor;
import com.ido85.master.project.dto.ProjectDto;
import com.ido85.seo.dashboard.domain.ProDomain;
import com.ido85.seo.dashboard.domain.ProLinksView;
import com.ido85.seo.extlinks.application.ExtLinkApplication;
import com.ido85.seo.extlinks.domain.ProAnchor;
import com.ido85.seo.extlinks.domain.ProDownUrl;
import com.ido85.seo.extlinks.domain.ProLinks;
import com.ido85.seo.extlinks.dto.InComcontrastDto;
import com.ido85.seo.extlinks.dto.InCommonDto;
import com.ido85.seo.extlinks.dto.OutAnchorDistriDto;
import com.ido85.seo.extlinks.dto.OutAnchorDto;
import com.ido85.seo.extlinks.dto.OutDomainDistriDto;
import com.ido85.seo.extlinks.dto.OutDomainDto;
import com.ido85.seo.extlinks.dto.OutDownUrlDto;
import com.ido85.seo.extlinks.dto.OutLinkItemDto;
import com.ido85.seo.extlinks.dto.OutLinksDto;
import com.ido85.seo.extlinks.dto.OutLinksViewDto;
import com.ido85.seo.extlinks.dto.OutProLinkTrendDto;
import com.ido85.seo.extlinks.dto.OutTrendDto;
import com.ido85.seo.extlinks.dto.OutTrendItemDto;
import com.ido85.services.project.ProjectApi;
import com.ido85.services.time.TimeInsideApi;

/**
 * @author fire
 */
@RestController
public class ExtLinksController {
	@Inject
	private BussinessMsgCodeProperties prop;
	@Inject
	private ProjectApi projectService;
	@Inject
	private ExtLinkApplication extLink;
	@Inject
	private TimeInsideApi timeApi;

	/**
	 * 外部链接概览 域名分布
	 * 
	 * @param request
	 * @param projectId
	 * @return
	 */
	@RequestMapping("/seo/domainDistri/{projectId}")
	public Resource<OutDomainDistriDto> domainDistri(HttpServletRequest request, @PathVariable String projectId)
			throws Exception {
		// 校验入参不能为空
		if (null == projectId || "".equals(projectId)) {
			return new Resource<OutDomainDistriDto>(prop.getProcessStatus("PARAM_ERROR"));
		}

		// 校验项目id是否正确
		ProjectDto projectDto = projectService.getProjectInfoById(projectId);
		if (null == projectDto || "1".equals(projectDto.getDelFlag()) || "0".equals(projectDto.getProjectState())) {
			return new Resource<OutDomainDistriDto>(prop.getProcessStatus("PROJECT_ERROR"));
		}

		OutDomainDistriDto out = new OutDomainDistriDto();
		// 取最新的一次数据获取返回值
		OutLinksViewDto links = extLink.getLinkInfoByProjectId(projectId, 1, null);
		if (null != links) {
			long res = extLink.getSumPageDomain(StringUtils.toInteger(projectId));
			out.setRefDomains(res >= 0 ? res : 0);
		} else {
			return new Resource<OutDomainDistriDto>(out);
		}

		Page<ProDomain> page = new Page<>(0, 10);
		extLink.getPageDomain(projectId, page, "0");

		if (null != page.getList() && page.getList().size() > 0) {
			List<OutDomainDto> proDomains = new ArrayList<OutDomainDto>();
			OutDomainDto domainDto = null;
			for (ProDomain proDomain : page.getList()) {
				domainDto = new OutDomainDto();
				domainDto.setDomain(proDomain.getDomain());
				domainDto.setExtLinks(proDomain.getExtLinks());
				proDomains.add(domainDto);
			}
			out.setDomains(proDomains);
		}
		return new Resource<OutDomainDistriDto>(out);
	}

	/**
	 * 外部链接概览 域名数量趋势和外链数量趋势
	 * 
	 * @param request
	 * @param projectId
	 * @return
	 */
	@RequestMapping("/seo/projectLinksTrend/{projectId}")
	public Resource<Map<String, List<OutProLinkTrendDto>>> projectLinksTrend(HttpServletRequest request,
			@PathVariable String projectId) throws Exception {
		// 校验入参不能为空
		if (null == projectId || "".equals(projectId)) {
			return new Resource<Map<String, List<OutProLinkTrendDto>>>(prop.getProcessStatus("PARAM_ERROR"));
		}

		// 校验项目id是否正确
		if (!projectService.checkProjectId(projectId, "1")) {
			return new Resource<Map<String, List<OutProLinkTrendDto>>>(prop.getProcessStatus("PROJECT_ERROR"));
		}

		// 获取返回结果
		Page<ProLinksView> page = new Page<>(0, 12);
		extLink.getPageProLinks(projectId, page, "0", "0", null);

		Map<String, List<OutProLinkTrendDto>> out = new HashMap<String, List<OutProLinkTrendDto>>();

		if (null != page.getList() && page.getList().size() > 0) {
			List<OutProLinkTrendDto> outList = new ArrayList<OutProLinkTrendDto>();
			OutProLinkTrendDto outItem = null;
			for (ProLinksView proLinksView : page.getList()) {
				outItem = new OutProLinkTrendDto();
				outItem.setCrawlDate(DateUtils.formatDate(proLinksView.getCrawlDate(), "yyyy-MM-dd"));
				outItem.setExtBackLinks(proLinksView.getExtBackLinks());
				outItem.setRefDomains(proLinksView.getRefDomains());
				outList.add(outItem);
			}
			out.put("domains", outList);
		}

		return new Resource<Map<String, List<OutProLinkTrendDto>>>(out);
	}

	/**
	 * 外部链接概览 链接对象接口
	 * 
	 * @param request
	 * @param projectId
	 * @return
	 */
	@RequestMapping("/seo/linkInfo/{projectId}")
	public Resource<OutLinksViewDto> updateUserInfo(HttpServletRequest request, @PathVariable String projectId)
			throws Exception {
		// 校验入参不能为空
		if (null == projectId || "".equals(projectId)) {
			return new Resource<OutLinksViewDto>(prop.getProcessStatus("PARAM_ERROR"));
		}

		// 校验项目id是否正确
		ProjectDto projectDto = projectService.getProjectInfoById(projectId);
		if (null == projectDto || "1".equals(projectDto.getDelFlag()) || "0".equals(projectDto.getProjectState())) {
			return new Resource<OutLinksViewDto>(prop.getProcessStatus("PROJECT_ERROR"));
		}

		// 取最新的两次数据获取返回值
		OutLinksViewDto out = extLink.getLinkInfoByProjectId(projectId, 2, null);
		if (null == out) {
			out = new OutLinksViewDto();
		} else {
			out.setLastUpdate(DateUtils.formatDate(out.getCrawlDate(), "yyyy-MM-dd"));
		}
		out.setProjectId(projectId);
		out.setProjectName(projectDto.getProjectName());
		out.setUrl(projectDto.getProjectUrl());

		return new Resource<OutLinksViewDto>(out);
	}

	/**
	 * 外部链接概览 锚文本分布
	 * 
	 * @param request
	 * @param projectId
	 * @return
	 */
	@RequestMapping("/seo/anchorDistri/{projectId}")
	public Resource<OutAnchorDistriDto> anchorDistri(HttpServletRequest request, @PathVariable String projectId)
			throws Exception {
		// 校验入参不能为空
		if (null == projectId || "".equals(projectId)) {
			return new Resource<OutAnchorDistriDto>(prop.getProcessStatus("PARAM_ERROR"));
		}

		// 校验项目id是否正确
		if (!projectService.checkProjectId(projectId, "1")) {
			return new Resource<OutAnchorDistriDto>(prop.getProcessStatus("PROJECT_ERROR"));
		}

		OutAnchorDistriDto out = new OutAnchorDistriDto();
		// 取最新的一次数据获取返回值
		OutLinksViewDto links = extLink.getLinkInfoByProjectId(projectId, 1, null);
		if (null != links) {
			long link = extLink.getSumPageAnchor(StringUtils.toInteger(projectId));
			out.setExtBackLinks(link >= 0 ? link : 0);
		} else {
			return new Resource<OutAnchorDistriDto>(out);
		}

		Page<ProAnchor> page = new Page<>(0, 10);
		extLink.getPageAnchor(projectId, page, "0");

		if (null != page.getList() && page.getList().size() > 0) {
			List<OutAnchorDto> outList = new ArrayList<OutAnchorDto>();
			OutAnchorDto outItem = null;
			for (ProAnchor proAnchor : page.getList()) {
				outItem = new OutAnchorDto();
				outItem.setTotalLinks(proAnchor.getTotalLinks());
				outItem.setAnchor(proAnchor.getAnchor());
				outList.add(outItem);
			}
			out.setAnchors(outList);
		}

		return new Resource<OutAnchorDistriDto>(out);
	}

	/**
	 * 外链明细 域名接口 id: 404
	 * 
	 * @param request
	 * @param in
	 * @param model
	 * @return
	 */
	@RequestMapping("/seo/domains")
	public Resource<Map<String, Object>> getDomains(HttpServletRequest request, @RequestBody InCommonDto in,
			Model model) throws Exception {
		// 校验项目id是否正确
		if (!projectService.checkProjectId(in.getProjectId(), "1")) {
			return new Resource<Map<String, Object>>(prop.getProcessStatus("PROJECT_ERROR"));
		}

		// 获取返回结果
		Page<ProDomain> page = new Page<>(in.getPageNo() - 1, in.getPageSize());
		extLink.getPageDomain(in.getProjectId(), page, "1");

		// 组织返回结果
		Map<String, Object> res = new HashMap<String, Object>();
		List<OutDomainDto> proDomains = new ArrayList<OutDomainDto>();
		if (null != page.getList() && page.getList().size() > 0) {
			OutDomainDto domainDto = null;
			res.put("count", page.getCount());
			res.put("crawlDate", DateUtils.formatDate(page.getList().get(0).getCrawlDate(), "yyyy-MM-dd"));
			for (ProDomain proDomain : page.getList()) {
				domainDto = new OutDomainDto();
				domainDto.setDomain(proDomain.getDomain());
				domainDto.setExtLinks(proDomain.getExtLinks());
				domainDto.setRefDomains(proDomain.getRefDomains());
				domainDto.setTotalLinks(proDomain.getTotalLinks());
				proDomains.add(domainDto);
			}
			res.put("domainsInfo", proDomains);
			return new Resource<Map<String, Object>>(res);
		}

		return new Resource<Map<String, Object>>(res);
	}

	/**
	 * 外链明细 外部链接tab id: 405
	 * 
	 * @param request
	 * @param in
	 * @param model
	 * @return
	 */
	@RequestMapping("/seo/extLinks")
	public Resource<Map<String, Object>> getExtLinks(HttpServletRequest request, @RequestBody InCommonDto in,
			Model model) throws Exception {
		// 校验项目id是否正确
		if (!projectService.checkProjectId(in.getProjectId(), "1")) {
			return new Resource<Map<String, Object>>(prop.getProcessStatus("PROJECT_ERROR"));
		}

		// 获取返回结果
		Page<ProLinks> page = new Page<>(in.getPageNo(), in.getPageSize());
		extLink.getPageExtLinks(in.getProjectId(), page, "1");

		// 组织返回结果
		Map<String, Object> res = new HashMap<String, Object>();
		if (null != page.getList() && page.getList().size() > 0) {
			List<OutLinksDto> proLinksDtos = new ArrayList<OutLinksDto>();
			OutLinksDto outItem = null;
			for (ProLinks proLinks : page.getList()) {
				outItem = new OutLinksDto();
				CopyUtil.copyProperties(outItem, proLinks);
				if (null != proLinks.getDateLost() && !"".equals(proLinks.getDateLost())) {
					outItem.setDateLost(DateUtils.formatDate(proLinks.getDateLost(), "yyyy-MM-dd"));
				}
				if (null != proLinks.getFirstIndexedDate() && !"".equals(proLinks.getFirstIndexedDate())) {
					outItem.setFirstIndexedDate(DateUtils.formatDate(proLinks.getFirstIndexedDate(), "yyyy-MM-dd"));
				}
				if (null != proLinks.getLastSeenDate() && !"".equals(proLinks.getLastSeenDate())) {
					outItem.setLastSeenDate(DateUtils.formatDate(proLinks.getLastSeenDate(), "yyyy-MM-dd"));
				}
				proLinksDtos.add(outItem);
			}
			res.put("linkInfos", proLinksDtos);
			res.put("crawlDate", DateUtils.formatDate(page.getList().get(0).getCrawlDate(), "yyyy-MM-dd"));
			res.put("count", page.getCount());
			return new Resource<Map<String, Object>>(res);
		}

		return new Resource<Map<String, Object>>(res);
	}

	/**
	 * 外链明细 着陆页 id: 406
	 * 
	 * @param request
	 * @param in
	 * @param model
	 * @return
	 */
	@RequestMapping("/seo/downUrl")
	public Resource<Map<String, Object>> getDownUrl(HttpServletRequest request, @RequestBody InCommonDto in,
			Model model) throws Exception {

		// 校验项目id是否正确
		if (!projectService.checkProjectId(in.getProjectId(), "1")) {
			return new Resource<Map<String, Object>>(prop.getProcessStatus("PROJECT_ERROR"));
		}

		// 获取返回结果
		Page<ProDownUrl> page = new Page<>(in.getPageNo(), in.getPageSize());
		extLink.getPageDownUrl(in.getProjectId(), page, "1");

		// 组织返回结果
		Map<String, Object> res = new HashMap<String, Object>();
		if (null != page.getList() && page.getList().size() > 0) {
			List<OutDownUrlDto> outList = new ArrayList<OutDownUrlDto>();
			OutDownUrlDto outItem = null;
			for (ProDownUrl item : page.getList()) {
				outItem = new OutDownUrlDto();
				CopyUtil.copyProperties(outItem, item);
				if (null != item.getCreateDate() && !"".equals(item.getCreateDate())) {
					outItem.setLastDate(DateUtils.formatDate(item.getCreateDate(), "yyyy-MM-dd"));
				}
				outList.add(outItem);
			}
			res.put("downInfos", outList);
			res.put("crawlDate", DateUtils.formatDate(page.getList().get(0).getCrawlDate(), "yyyy-MM-dd"));
			res.put("count", page.getCount());
			return new Resource<Map<String, Object>>(res);
		}

		return new Resource<Map<String, Object>>(res);
	}

	/**
	 * 外链明细 锚文本 id: 407
	 * 
	 * @param request
	 * @param in
	 * @param model
	 * @return
	 */
	@RequestMapping("/seo/anchorText")
	public Resource<Map<String, Object>> getAnchorText(HttpServletRequest request, @RequestBody InCommonDto in,
			Model model) throws Exception {

		// 校验项目id是否正确
		if (!projectService.checkProjectId(in.getProjectId(), "1")) {
			return new Resource<Map<String, Object>>(prop.getProcessStatus("PROJECT_ERROR"));
		}

		// 获取返回结果
		Page<ProAnchor> page = new Page<>(in.getPageNo(), in.getPageSize());
		if (null != in.getAnchorText() && !"".equals(in.getAnchorText())) {
			page.setFuncParam(in.getAnchorText());
		}
		extLink.getPageAnchor(in.getProjectId(), page, "1");

		// 组织返回结果
		Map<String, Object> res = new HashMap<String, Object>();
		if (null != page.getList() && page.getList().size() > 0) {
			List<OutAnchorDto> outList = new ArrayList<OutAnchorDto>();
			OutAnchorDto outItem = null;
			for (ProAnchor item : page.getList()) {
				outItem = new OutAnchorDto();
				CopyUtil.copyProperties(outItem, item);
				outList.add(outItem);
			}
			res.put("anchorInfos", outList);
			res.put("crawlDate", DateUtils.formatDate(page.getList().get(0).getCrawlDate(), "yyyy-MM-dd"));
			res.put("count", page.getCount());
			return new Resource<Map<String, Object>>(res);
		}

		return new Resource<Map<String, Object>>(res);
	}

	/**
	 * 竞品对比 竞品对比接口 id: 408
	 * 
	 * @param request
	 * @param in
	 * @param model
	 * @return
	 */
	@RequestMapping("/seo/competitorContrast")
	public Resource<OutTrendDto> competitorContrast(HttpServletRequest request, @RequestBody InComcontrastDto in,
			Model model) throws Exception {
		// 校验项目id是否正确
		ProjectDto projectDto = projectService.getProjectInfoById(in.getProjectId());
		if (null == projectDto || "1".equals(projectDto.getDelFlag()) || "0".equals(projectDto.getProjectState())) {
			return new Resource<OutTrendDto>(prop.getProcessStatus("PROJECT_ERROR"));
		}

		OutTrendDto res = new OutTrendDto();
		// 获取所有项目的竞品信息
		List<Competitor> competitors = projectService.getCompetitorsByProjectId(in.getProjectId());
		if (null == in.getQueryFlag() || "".equals(in.getQueryFlag())) {
			// 取最新的一次项目数据获取返回值
			OutLinksViewDto out = extLink.getLinkInfoByProjectId(in.getProjectId(), 1,
					DateUtils.parse(in.getEndDate(), "yyyy-MM-dd"));
			System.out.println("取最新的一次项目数据获取返回值");
			if (null != out) {
				System.out.println("OutLinksViewDto 不是空");
				res.setCrawlDate(DateUtils.formatDate(out.getCrawlDate(), "yyyy-MM-dd"));
				Page<ProLinksView> page = new Page<>(0, 12);
				extLink.getPageProLinks(in.getProjectId(), page, "0", "1", out.getCrawlDate());
				
				if (null != page.getList() && page.getList().size() > 0) {
					System.out.println("page 不是空");
					List<ProLinksView> list = page.getList();
					List<OutLinkItemDto> linksInfos = new ArrayList<OutLinkItemDto>();
					List<OutLinkItemDto> domainInfos = new ArrayList<OutLinkItemDto>();
					OutLinkItemDto itemDto = null;
					OutLinkItemDto itemDomainDto = null;
					for (int j = 0; j < competitors.size(); j++) {
						for (int i = 0; i < list.size(); i++) {
							itemDto = new OutLinkItemDto();
							itemDomainDto = new OutLinkItemDto();
							if (competitors.get(j).getCompetitorId().equals(list.get(i).getCompetitorId())) {
								itemDto.setFlag("1");
								itemDto.setName(competitors.get(j).getCompetitorName());
								itemDto.setUrl(competitors.get(j).getCompetitorUrl());
							} else if (projectDto.getProjectId().equals(list.get(i).getCompetitorId() + "") && j <= 0) {
								itemDto.setFlag("0");
								itemDto.setName(projectDto.getProjectName());
								itemDto.setUrl(projectDto.getProjectUrl());
							} else {
								continue;
							}
							itemDto.setNum(list.get(i).getExtBackLinks());
							linksInfos.add(itemDto);
							CopyUtil.copyProperties(itemDomainDto, itemDto);
							itemDto.setNum(list.get(i).getRefDomains());
							domainInfos.add(itemDomainDto);
						}
					}

					res.setDomainInfos(domainInfos);
					res.setLinksInfos(linksInfos);
				}
			}
		}

		// 返回域名和外链趋势图表
		List<ProLinksView> out = extLink.getLinkInfo(in.getProjectId(), DateUtils.parse(in.getStartDate(), "yyy-MM-dd"),
				DateUtils.parse(in.getEndDate(), "yyy-MM-dd"));
		if (null != out && out.size() > 0) {
			System.out.println("List<ProLinksView> 不是空");
			List<Date> updateList = null;
			if ("1".equals(in.getFlag()) && StringUtils.isNotBlank(in.getEndDate())
					&& StringUtils.isNotBlank(in.getStartDate())) {
				// 获取所有的月时间点

				updateList = timeApi.getCrawlDate(DateUtils.parse(in.getStartDate(), "yyy-MM-dd"),
						DateUtils.addDays(DateUtils.parse(in.getEndDate(), "yyy-MM-dd"), 1),
						StringUtils.toInteger(in.getProjectId()), "2");
				
				if (null == updateList || updateList.size() <= 0) {
					System.out.println("updateList" +"返回");
					return new Resource<OutTrendDto>(res);
				}
			} else if ("1".equals(in.getFlag())) {
				return new Resource<OutTrendDto>(prop.getProcessStatus("PARAM_ERROR"));
			}

			List<OutTrendItemDto> trendDtos = new ArrayList<OutTrendItemDto>();
			OutTrendItemDto itemDto = null;
			for (int k = 0; k < out.size(); k++) {
				if ("1".equals(in.getFlag())) {// 取每个月的数据
					for (int i = 0; i < updateList.size(); i++) {
						if (DateUtils.isSameDate(updateList.get(i), out.get(k).getCrawlDate())) {
							for (int j = 0; j < competitors.size(); j++) {
								itemDto = new OutTrendItemDto();
								itemDto.setCrawlDate(DateUtils.formatDate(updateList.get(i), "yyyy-MM-dd"));
								itemDto.setExtBackLinks(out.get(k).getExtBackLinks());
								itemDto.setRefDomains(out.get(k).getRefDomains());
								if (competitors.get(j).getCompetitorId().equals(out.get(k).getCompetitorId())) {
									itemDto.setId(competitors.get(j).getCompetitorId().toString());
									itemDto.setName(competitors.get(j).getCompetitorName());
								} else if (projectDto.getProjectId().equals(out.get(k).getCompetitorId() + "")) {
									itemDto.setId(projectDto.getProjectId());
									itemDto.setName(projectDto.getProjectName());
									trendDtos.add(itemDto);
									break;
								} else {
									continue;
								}

								trendDtos.add(itemDto);
							}
						}
					}
				} else {// 按周数据
					for (int j = 0; j < competitors.size(); j++) {
						itemDto = new OutTrendItemDto();
						itemDto.setCrawlDate(DateUtils.formatDate(out.get(k).getCrawlDate(), "yyyy-MM-dd"));
						itemDto.setExtBackLinks(out.get(k).getExtBackLinks());
						itemDto.setRefDomains(out.get(k).getRefDomains());
						if (competitors.get(j).getCompetitorId().equals(out.get(k).getCompetitorId())) {
							itemDto.setId(competitors.get(j).getCompetitorId().toString());
							itemDto.setName(competitors.get(j).getCompetitorName());
						} else if (projectDto.getProjectId().equals(out.get(k).getCompetitorId() + "")) {
							itemDto.setId(projectDto.getProjectId());
							itemDto.setName(projectDto.getProjectName());
							trendDtos.add(itemDto);
							break;
						} else {
							continue;
						}
						trendDtos.add(itemDto);
					}
				}
			}
			res.setTrend(trendDtos);
		}
System.out.println("正常返回哦");
		return new Resource<OutTrendDto>(res);
	}
}
