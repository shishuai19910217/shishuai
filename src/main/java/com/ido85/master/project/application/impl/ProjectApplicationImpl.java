package com.ido85.master.project.application.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import com.ido85.config.properties.BussinessMsgCodeProperties;
import com.ido85.config.properties.WiConstantsProperties;
import com.ido85.frame.common.Page;
import com.ido85.frame.common.exceptions.BusinessException;
import com.ido85.frame.common.restful.Resource;
import com.ido85.frame.common.utils.DateUtils;
import com.ido85.frame.common.utils.StringUtils;
import com.ido85.frame.web.rest.utils.RestUserUtils;
import com.ido85.master.common.MasterSequenceUtil;
import com.ido85.master.keyword.domain.Group;
import com.ido85.master.keyword.domain.Keyword;
import com.ido85.master.keyword.domain.ProKeyword;
import com.ido85.master.keyword.dto.OutProKeywordDto;
import com.ido85.master.keyword.resources.GroupKeyRelResources;
import com.ido85.master.keyword.resources.GroupResources;
import com.ido85.master.keyword.resources.KeywordResources;
import com.ido85.master.keyword.resources.ProKeywordResources;
import com.ido85.master.packages.domain.TenantPackage;
import com.ido85.master.packages.domain.TenantPackageElementsRel;
import com.ido85.master.packages.resources.TenantPackageWiResources;
import com.ido85.master.project.application.ProjectApplication;
import com.ido85.master.project.domain.Competitor;
import com.ido85.master.project.domain.Project;
import com.ido85.master.project.domain.ProjectTenantExampleRel;
import com.ido85.master.project.dto.CheckUrlDto;
import com.ido85.master.project.dto.CheckUrlResultDto;
import com.ido85.master.project.dto.InCompetitorDto;
import com.ido85.master.project.dto.InUpdateProjectDto;
import com.ido85.master.project.dto.OutCheckProjectDto;
import com.ido85.master.project.dto.OutProComKwInfoDto;
import com.ido85.master.project.dto.OutProCompetitorDto;
import com.ido85.master.project.dto.OutProjectBaseInfoDto;
import com.ido85.master.project.dto.ProjectPageDto;
import com.ido85.master.project.resources.CompetitorResources;
import com.ido85.master.project.resources.ProjectTenantExampleRelWiResources;
import com.ido85.master.project.resources.ProjectWiResources;
import com.ido85.master.spider.RediectUrl;
import com.ido85.seo.common.BaseApplication;
import com.ido85.seo.dashboard.dto.OutKeywordRankDto;
import com.ido85.services.allSite.application.AllSiteApi;
import com.ido85.services.dashboard.DashboardApi;
import com.ido85.services.keyword.impl.KeywordApiImpl;
import com.ido85.services.packages.impl.PackageApiImpl;

@Named
public class ProjectApplicationImpl<T> extends BaseApplication implements ProjectApplication {
	@Inject
	private PackageApiImpl packageService;
	@Inject
	private KeywordApiImpl keywordService;

	@PersistenceContext(unitName = "system")
	protected EntityManager entity;
	@Inject
	private BussinessMsgCodeProperties msg;
	@Inject
	private ProjectTenantExampleRelWiResources projectTenantExampleRelWiResources;

	@Inject
	private ProjectWiResources projectWiResources;

	@Inject
	private WiConstantsProperties wiConstantsProperties;
	@Inject
	private CompetitorResources competitorRes;
	@Inject
	private ProKeywordResources proKeywordResources;
	@Inject
	private GroupResources groupResources;
	@Inject
	private GroupKeyRelResources groupKeyRelResources;
	@Inject
	private KeywordResources keywordResources;
	@Inject
	private RediectUrl rediectUrl;
	@Inject
	private DashboardApi dashboardApi;
	@Inject
	private TenantPackageWiResources tenantPackageWiResources;
	@Inject
	private MasterSequenceUtil masterSequenceUtil;
	@Inject
	private AllSiteApi allsiteApi;

	/***
	 * 添加项目
	 * 
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@Transactional
	public Resource<Map<String, Object>> addProject(Map<String, Object> param) throws Exception {
		TenantPackage tenanPackage = (TenantPackage) param.get("tenanPackage");
		// 添加项目基本信息
		Project pro = new Project();
		Integer projectId = masterSequenceUtil.getCommonSeq();
		pro.setProjectId(projectId);
		pro.setTenantId(tenanPackage.getTenantId());
		pro.setProjectName(param.get("projectName") == null ? "" : param.get("projectName").toString().trim());
		pro.setBusinessName(param.get("businessName") == null ? "" : param.get("businessName").toString());
		String urlteml = param.get("projectUrl") == null ? "" : param.get("projectUrl").toString().trim();
		pro.setProjectUrl(urlteml);
		pro.setIsSubdomain(param.get("subdomain") == null ? "" : param.get("subdomain").toString());
		pro.setDelFlag("0");
		pro.setProjectState("1");// 默认是活跃项目
		pro.setCreateBy(RestUserUtils.getUserInfo().getUserId());
		pro.setCreateDate(new Date());
		pro.setDelFlag("0");
		pro.setUpdateBy(RestUserUtils.getUserInfo().getUserId());
		pro.setUpdateDate(new Date());
		pro.setReceiveEmail("1");
		pro.setNextCrawlDate(DateUtils.formatDateToPattern(new Date(), "yyyy-MM-dd"));
		ProjectTenantExampleRel r = new ProjectTenantExampleRel();
		Integer pcpRelId = masterSequenceUtil.getCommonSeq();
		r.setPcpRelId(pcpRelId);
		r.setDelFlag("0");
		r.setProject(pro);
		r.setTenantId(tenanPackage.getTenantId());
		r.setTenantPackageId(tenanPackage.getTenantPackageId());
		r.setCreateBy(RestUserUtils.getUserInfo().getUserId());
		r.setCreateDate(new Date());
		r.setUpdateBy(RestUserUtils.getUserInfo().getUserId());
		r.setUpdateDate(new Date());
		ProjectTenantExampleRel temp = projectTenantExampleRelWiResources.save(r);
		// 判断关键词
		List<ProKeyword> prokeywords = new ArrayList<ProKeyword>();
		@SuppressWarnings("unchecked")
		List<Keyword> tmp = (List<Keyword>) param.get("keywordDomains");
		if (null != tmp && tmp.size() > 0) {
			for (Keyword kw : tmp) {
				ProKeyword pk = new ProKeyword();
				Integer proKeyRelId = masterSequenceUtil.getCommonSeq();
				pk.setProKeyRelId(proKeyRelId);
				pk.setKeywordId(Integer.parseInt(kw.getKeywordId()));
				pk.setProjectId(pro.getProjectId());
				pk.setTenantId(tenanPackage.getTenantId());
				pk.setIsBrand("0");
				pk.setDelFlag("0");
				pk.setCreateBy(RestUserUtils.getUserInfo().getUserId());
				pk.setCreateDate(new Date());
				pk.setDelFlag("0");
				pk.setUpdateBy(RestUserUtils.getUserInfo().getUserId());
				pk.setUpdateDate(new Date());
				prokeywords.add(pk);
			}
			// 保存关键词与项目的关系
			keywordService.batchsaveProKeyword(prokeywords);
		}
		// 保存竞品
		@SuppressWarnings("unchecked")
		List<Map<String, Object>> competitors = (List<Map<String, Object>>) param.get("competitors");
		if (null != competitors && competitors.size() > 0) {
			for (Map<String, Object> m : competitors) {
				Competitor c = new Competitor();
				Integer competitorId = masterSequenceUtil.getCommonSeq();
				c.setCompetitorId(competitorId);
				c.setTenantId(tenanPackage.getTenantId());
				c.setCompetitorName(m.get("name").toString().trim());
				String curlteml = m.get("url").toString().trim();
				c.setCompetitorUrl(curlteml);
				c.setProjectId(pro.getProjectId());
				c.setDelFlag("0");
				c.setCreateBy(RestUserUtils.getUserInfo().getUserId());
				c.setCreateDate(new Date());
				c.setDelFlag("0");
				c.setUpdateBy(RestUserUtils.getUserInfo().getUserId());
				c.setUpdateDate(new Date());
				saveCompetitor(c);
			}
		}
		Map<String, Object> temlMap = new HashMap<String, Object>();
		temlMap.put("projectId", temp.getProject().getProjectId());
		return new Resource<Map<String, Object>>(temlMap, msg.getProcessStatus("COMMON_SUCCESS"));
	}

	/***
	 * 设置活跃项目归档、删除、激活接口
	 * 
	 * @throws Exception
	 */
	@Transactional
	public String updateProjectState(Map<String, Object> param) throws Exception {

		// 判断套餐是否有效
		/*
		 * TenantPackage tenantPackage =
		 * tenantPackageWiResources.getTenantpackageByTenantpackageId(param.get(
		 * "tenantPackageId").toString()); if(null==tenantPackage){ return "-1";
		 * }
		 */
		Project project = projectWiResources.getProjectById(Integer.parseInt(param.get("projectId").toString()));// 当前项目
		if (null == project) {
			return "1";// 没有此项目
		}
		if ("1".equals(project.getDelFlag())) {
			return "1";// 没有此项目
		}
		Map<String, Object> paramNum = new HashMap<String, Object>();
		paramNum.put("appType", "0");
		Map<String, Object> map = null;
		try {
			map = getTenantPackage(paramNum);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (null == map || null == map.get("tenantPackage")) {// 判断规格
			return "3";// 未发现套餐
		}

		if ("0".equals(param.get("activeFlag").toString())) {// 设置项目归档
			if (!"1".equals(project.getProjectState())) {
				return "2";// 项目状态发生改变
			}
			project.setProjectState("0");
			project.setUpdateBy(RestUserUtils.getUserInfo() == null ? 2 : RestUserUtils.getUserInfo().getUserId());
			project.setUpdateDate(new Date());
			project.setArchiveDate(new Date());
			projectWiResources.save(project);
			// 修改项目状态
			projectWiResources.updateProjectState(project.getProjectState(), project.getArchiveDate(),
					project.getUpdateBy(), project.getUpdateDate(),
					Integer.parseInt(param.get("projectId").toString()));

		} else if ("1".equals(param.get("activeFlag").toString())) {// 设置项目激活
			// 判断是否可以激活
			int proNum = Integer.parseInt(map.get("proNum").toString());// 项目数规格
			int proNum1 = getProjectNumBy(((TenantPackage) map.get("tenantPackage")).getTenantPackageId().toString(),
					"1");// 已添加的活跃项目书、
			if (proNum1 < proNum) {
				project.setProjectState("1");
				project.setUpdateBy(RestUserUtils.getUserInfo() == null ? 2 : RestUserUtils.getUserInfo().getUserId());
				project.setUpdateDate(new Date());
				project.setArchiveDate(new Date());
				// 修改项目状态
				projectWiResources.updateProjectState(project.getProjectState(), project.getArchiveDate(),
						project.getUpdateBy(), project.getUpdateDate(),
						Integer.parseInt(param.get("projectId").toString()));
			} else {
				return "4";
			}

		} else if ("2".equals(param.get("activeFlag").toString())) {// 项目删除
			// 删除关系
			projectTenantExampleRelWiResources.deleteByProjectId("1",
					RestUserUtils.getUserInfo() == null ? 2 : RestUserUtils.getUserInfo().getUserId(), new Date(),
					DateUtils.formatDateToPattern(new Date(), "yyyyMMdd"),
					Integer.parseInt(param.get("projectId").toString()));

			// 删除项目
			project.setUpdateBy(RestUserUtils.getUserInfo() == null ? 2 : RestUserUtils.getUserInfo().getUserId());
			project.setUpdateDate(new Date());
			project.setEndDate(DateUtils.formatDate(new Date(), "yyyyMMdd"));
			project.setDelFlag("1");
			projectWiResources.save(project);
			// 删除竞品
			competitorRes.deleteByProjectId("1",
					RestUserUtils.getUserInfo() == null ? 2 : RestUserUtils.getUserInfo().getUserId(), new Date(),
					Integer.parseInt(param.get("projectId").toString()));
			// 删除关键词
			proKeywordResources.deleteByProjectId("1",
					RestUserUtils.getUserInfo() == null ? 2 : RestUserUtils.getUserInfo().getUserId(), new Date(),
					Integer.parseInt(param.get("projectId").toString()));
			// 根据projectId查询分组信息
			List<Group> groups = groupResources.getAllProGroups(Integer.parseInt(param.get("projectId").toString()));
			List<Integer> groupIds = new ArrayList<Integer>();
			if (null != groups && groups.size() >= 0) {
				for (Group group : groups) {
					groupIds.add(Integer.parseInt(group.getGroupId()));
				}
			}
			// 根据分组 删除 seo分组和项目的关键词关系表
			if (groupIds.size() > 0) {
				groupKeyRelResources.deleteBygroupIds("1",
						RestUserUtils.getUserInfo() == null ? 2 : RestUserUtils.getUserInfo().getUserId(), new Date(),
						groupIds);
			}

			// 删除分组
			groupResources.deleteByProjectId("1",
					RestUserUtils.getUserInfo() == null ? 2 : RestUserUtils.getUserInfo().getUserId(), new Date(),
					Integer.parseInt(param.get("projectId").toString()));

		} else {

		}

		return "0";
	}

	/***
	 * 项目由暂定到激活
	 * 
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@Transactional
	public String updateProjectStateStopToActive(String projectId) throws Exception {
		// Project project = projectWiResources.getProjectById(projectId);//当前项目
		// if(null==project){
		// return "1";//没有此项目
		// }
		// if("1".equals(project.getDelFlag())){
		// return "1";//没有此项目
		// }
		// Map<String, Object> paramNum = new HashMap<String,Object>();
		// paramNum.put("appType", "0");
		// Map<String, Object> map =null;
		// try {
		// map = getTenantPackage(paramNum);
		// } catch (Exception e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// if(null==map||null==map.get("tenantPackage")){//判断规格
		// return "3";//未发现套餐
		// }
		// if(!"2".equals(project.getProjectState())){
		// return "2";//想慢慢状态发生改变
		// }
		// //判断是否可以激活
		// int proNum = Integer.parseInt(map.get("proNum").toString());//项目数规格
		// int proNum1 =
		// getProjectNumBy(((TenantPackage)map.get("tenantPackage")).getTenantPackageId(),
		// "1");//已添加的活跃项目书、
		// if(proNum1<proNum){
		// project.setProjectState("1");
		// project.setUpdateBy(UserUtils.getUser()==null?"2":UserUtils.getUser().getUserId());
		// project.setUpdateDate(new Date());
		// project.setArchiveDate(new Date());
		// //修改项目状态
		// projectWiResources.upDataProject( projectId);
		// }else{
		// return "4";
		// }
		//
		//
		// return "0";
		return null;
	}

	/***
	 * 根据实例id 查询项目数
	 * 
	 * @param tenantPackageId
	 * @param projectState
	 * @return
	 */
	public int getProjectNumBy(String tenantPackageId, String projectState) {

		Object num = projectTenantExampleRelWiResources.getProjectNumBy(Integer.parseInt(tenantPackageId),
				projectState);
		if (null == num) {
			return 0;
		} else {
			return Integer.parseInt(num.toString());
		}
	}

	/***
	 * 根据项目id 查询竞品数
	 * 
	 * @param projectid
	 * @return
	 */
	public int getComNumBy(String projectId) {

		Object num = competitorRes.getComNumBy(Integer.parseInt(projectId));
		if (null == num) {
			return 0;
		} else {
			return Integer.parseInt(num.toString());
		}
	}

	@Override
	public ProjectTenantExampleRel getProTenRelById(String projectId) {
		return projectTenantExampleRelWiResources.getProTenRelById(Integer.parseInt(projectId));
	}

	@Transactional
	public void saveProject(ProjectTenantExampleRel r) {
		entity.persist(r);
	}

	@Transactional
	public void saveCompetitor(Competitor c) {
		entity.persist(c);
	}

	/***
	 * 添加项目时 检测项目信息
	 */
	@SuppressWarnings("unchecked")
	@Override
	public OutCheckProjectDto checkProject(Map<String, Object> param) {
		OutCheckProjectDto out = new OutCheckProjectDto();
		// 套餐实例
		/* 查询套餐当前租户的实例规格 */

		String tenantId = RestUserUtils.getTencentId().toString();

		List<TenantPackage> tenanPackageList = packageService.getPackageExampleListByTenantId(tenantId, "0");
		if (null == tenanPackageList || tenanPackageList.size() <= 0) {
			return out;// 此租户含有多个此应用下的套餐
		}
		if (tenanPackageList.size() > 1) {
			return out;// 此租户含有多个此应用下的套餐
		}
		TenantPackage tenanPackage = tenanPackageList.get(0);// 当前的套餐实例
		/***
		 * 判断项目是否存在
		 */
		out.setNameFlag("0");
		out.setUrlFlag("0");// 0：既没有重定向也不存在已经创建的项目网址，1：项目网址存在，2：网址有重定向
		if (null != param.get("name")) {// 项目名称
			List<ProjectTenantExampleRel> list = projectTenantExampleRelWiResources
					.getProjectByName(tenanPackage.getTenantPackageId(), param.get("name").toString().trim());
			if (null != list && list.size() > 0) {// 存在这个名字的项目
				out.setNameFlag("1");// 项目名称已经存在
			}
		}
		if (null != param.get("url")) {
			List<ProjectTenantExampleRel> list = projectTenantExampleRelWiResources
					.getProjectByUrl(tenanPackage.getTenantPackageId(), param.get("url").toString().trim());
			if (null != list && list.size() > 0) {// 存在这个名字的项目
				out.setUrlFlag("1");// 项目Url已经存在
			}
		}

		return out;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Transactional
	public Page getProjectList(Page<ProjectPageDto> page, Map<String, Object> param) throws Exception {
		/* 查询套餐当前租户的实例规格 */
		String tenantId = RestUserUtils.getTencentId().toString();
		System.out.println("--------------------------getProjectList---------------tenantId---------" + tenantId);
		List<TenantPackage> tenanPackageList = packageService.getPackageExampleListByTenantId(tenantId,
				param.get("appType").toString());

		if (null == tenanPackageList || tenanPackageList.size() <= 0) {
			throw new BusinessException(msg.getProcessStatus("PACKAGE_NOT_EXIST"));
		}
		if (tenanPackageList.size() > 1) {// PACKAGE_MORE
			throw new BusinessException(msg.getProcessStatus("PACKAGE_MORE"));// 此租户含有多个此应用下的套餐
		}
		TenantPackage tenanPackage = tenanPackageList.get(0);// 当前的套餐实例
		/**
		 * 查询某个实例的下 所有项目的竞品数 和关键词数 基本数据
		 */
		String jpaSql = " select NEW com.ido85.master.project.dto.ProjectPageDto(rel.project.projectId, ( "
				+ " SELECT	COUNT(1) FROM Competitor c WHERE c.projectId = rel.project.projectId and c.delFlag='0'), "
				+ " (select COUNT(1) from ProKeyword k where k.projectId = rel.project.projectId and k.delFlag='0')) "
				+ " FROM " + " ProjectTenantExampleRel rel  " + " where 1=1 "
				+ " and rel.tenantPackageId = :tenantPackageId "

				+ " and rel.delFlag='0' and rel.project.delFlag = '0'";

		if (null != param.get("activeFlag") && !"".equals(param.get("activeFlag").toString())) {
			jpaSql = jpaSql + " and rel.project.projectState =" + "'" + param.get("activeFlag").toString() + "'";
		}

		Query q = entity.createQuery(jpaSql, ProjectPageDto.class);
		q.setParameter("tenantPackageId", tenanPackage.getTenantPackageId());
		/**
		 * 查询条数
		 */
		String jpacount = " select count(rel) " + " FROM " + " ProjectTenantExampleRel rel  " + " where 1=1 "
				+ " and  rel.tenantPackageId = :tenantPackageId "
				+ " and rel.delFlag='0' and rel.project.delFlag = '0'";
		if (null != param.get("activeFlag") && !"".equals(param.get("activeFlag").toString())) {
			jpacount = jpacount + " and rel.project.projectState =" + "'" + param.get("activeFlag").toString() + "'";
		}
		Query qc = entity.createQuery(jpacount);
		qc.setParameter("tenantPackageId", tenanPackage.getTenantPackageId());

		Object countarr = qc.getSingleResult();

		int count = Integer.parseInt(countarr.toString());

		if (null == param.get("activeFlag")) {// 需要分页
			int pageSize = page.getPageSize();
			int start = (page.getPageNo() - 1) <= 0 ? 0 : (page.getPageNo() - 1) * pageSize;

			// 判断分页
			if (start < count && pageSize > 0) {
				q.setFirstResult(start);
				q.setMaxResults(pageSize);

			}
		} else if ("0".equals(param.get("activeFlag").toString())) {// 需要分页
			int pageSize = page.getPageSize();
			int start = (page.getPageNo() - 1) <= 0 ? 0 : (page.getPageNo() - 1) * pageSize;

			// 判断分页
			if (start < count && pageSize > 0) {
				q.setFirstResult(start);
				q.setMaxResults(pageSize);

			}
		} else {
		}

		page.setCount(count);
		if (q.getResultList().size() > 0) {
			page.setList(q.getResultList());
		} else {
			return page;// 基本数据都没有就直接返回
		}

		List<Integer> paramIds = new ArrayList<Integer>();
		Map<String, Project> projectMap = new HashMap<String, Project>();
		if (null != page.getList() && page.getList().size() > 0) {
			for (ProjectPageDto p : page.getList()) {
				paramIds.add(Integer.parseInt(p.getProjectId()));
			}
			List<Project> projectList = projectWiResources.getProjectListById(paramIds);
			if (null != projectList && projectList.size() >= 0) {
				for (Project pro : projectList) {
					projectMap.put(pro.getProjectId().toString(), pro);
				}
				List<ProjectPageDto> projectPageList = new ArrayList<>();
				for (ProjectPageDto p : page.getList()) {
					p.setProjectName(projectMap.get(p.getProjectId()).getProjectName());
					p.setReceiveEmail(projectMap.get(p.getProjectId()).getReceiveEmail());
					p.setProjectUrl(projectMap.get(p.getProjectId()).getProjectUrl());
					p.setCreateDate(projectMap.get(p.getProjectId()).getCreateDate() == null ? ""
							: DateUtils.formatDate(projectMap.get(p.getProjectId()).getCreateDate(), "yyyyMMddHHmmss"));
					if (null != param.get("activeFlag") && "0".equals(param.get("activeFlag").toString())) {
						// 归档项目不需要获取关键词前10位变化,以及爬取问题的变化情况
						p.setFilingDate(projectMap.get(p.getProjectId()).getArchiveDate() == null ? ""
								: DateUtils.formatDate(projectMap.get(p.getProjectId()).getArchiveDate(),
										"yyyyMMddHHmmss"));
					}
					if (null != param.get("activeFlag") && "1".equals(param.get("activeFlag").toString())) {
						// 获取关键词前10位变化,以及爬取问题的变化情况
						try {//关键词
							OutKeywordRankDto dto = dashboardApi
									.getProManagerKeywordRankNum(Integer.parseInt(p.getProjectId()));
							Map<String, Object> projectInfo = dto.getProject();
							Integer currentNum = 0;
							Integer lastNum = 0;
							Integer keywordChange = 0;
							if (!projectInfo.isEmpty()) {
								currentNum = Integer.parseInt(projectInfo.get("currentNum").toString());
								lastNum = Integer.parseInt(projectInfo.get("lastNum").toString());
								if (lastNum != 0 && lastNum != null) {
									keywordChange = currentNum - lastNum;
								}
								p.setKeywordRank(currentNum);
								p.setKeywordChange(keywordChange);
							}
						} catch (Exception e) {
							e.printStackTrace();
						}

						try {//爬取问题
							Map map = allsiteApi.getCrawInfo(Integer.parseInt(p.getProjectId()));
							if (map.containsKey("crawlIssues") && map.containsKey("issuesChange")) {
								p.setCrawlIssues(Integer.parseInt(map.get("crawlIssues").toString()));
								p.setIssuesChange(Integer.parseInt(map.get("issuesChange").toString()));
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					// paramIds.add(Integer.parseInt(p.getProjectId()));
					projectPageList.add(p);
				}
				page.setList(projectPageList);
			}
		}
		return page;
	}

	public Object getActiveNum(String tenantPackageId) {
		return projectTenantExampleRelWiResources.getActiveNum(Integer.parseInt(tenantPackageId));
	}

	public int getProjectNum(String tenantPackageId) {
		return projectTenantExampleRelWiResources.getProjectNum(Integer.parseInt(tenantPackageId));
	}

	@Override
	public List<Competitor> getCompetitorsInfoByProjectId(String projectId) {
		return competitorRes.getCompetitorsInfo(Integer.parseInt(projectId));
	}

	@Override
	public OutProjectBaseInfoDto getProjectInfoById(String projectId) {
		return projectWiResources.getProjectBaseInfo(Integer.parseInt(projectId));
	}

	@Override
	public Project getProjectById(String projectId) {
		return projectWiResources.getProjectById(Integer.parseInt(projectId));
	}

	@Override
	public List<Competitor> getCompetitorByProjectId(String projectId) {
		return competitorRes.getCompetitorByProjectId(Integer.parseInt(projectId));
	}

	/***
	 * 查询 某个应用下的套餐实例的规格
	 * 
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> getTenantPackage(Map<String, Object> param) throws Exception {
		/* 查询套餐当前租户的实例规格 */
		/*
		 * StatelessPrincipal principal =
		 * (StatelessPrincipal)SecurityUtils.getSubject().getPrincipal();
		 * if(null==principal){ principal = new StatelessPrincipal("",
		 * "DEFAULT"); } String tenantId = principal.getTenantID();
		 */
		String tenantId = RestUserUtils.getTencentId().toString();

		List<TenantPackage> tenanPackageList = packageService.getPackageExampleListByTenantId(tenantId,
				param.get("appType").toString());
		if (null == tenanPackageList || tenanPackageList.size() <= 0) {
			throw new BusinessException(msg.getProcessStatus("PACKAGE_NOT_EXIST"));
		}
		if (tenanPackageList.size() > 1) {
			return null;// 此租户含有多个此应用下的套餐
		}
		TenantPackage tenanPackage = tenanPackageList.get(0);// 当前的套餐实例
		String proNumelementId = wiConstantsProperties.getValue("proNum").toString();// 项目数
		String comNumelementId = wiConstantsProperties.getValue("comNum").toString();// 竞品数
		String keyordNumelementId = wiConstantsProperties.getValue("keywordNum").toString();// 关键词书
		int proNum = 0;// 规定项目数
		int comNum = 0;// 竞品数
		int keyordNum = 0;// 关键词数
		// 规格
		List<TenantPackageElementsRel> tenantPackageElementsRelList = tenanPackage.getTenantPackageElementsRelList();
		if (null == tenantPackageElementsRelList || tenantPackageElementsRelList.size() <= 0) {
			throw new BusinessException(msg.getProcessStatus("PACKAGE_NOT_RULE"));
		}
		for (TenantPackageElementsRel rel : tenantPackageElementsRelList) {
			if (proNumelementId.equals(rel.getElements().getElementId().toString())) {// 项目数
				if ("1".equals(rel.getValidType())) {// 取最大值
					proNum = rel.getUsedMax();
				}
			}
			if (comNumelementId.equals(rel.getElements().getElementId().toString())) {// 竞品书
				if ("1".equals(rel.getValidType())) {// 取最大值
					comNum = rel.getUsedMax();
				}
			}
			if (keyordNumelementId.equals(rel.getElements().getElementId().toString())) {// 关键词个数
				if ("1".equals(rel.getValidType())) {// 取最大值
					keyordNum = rel.getUsedMax();
				}
			}
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("proNum", proNum);
		map.put("keyordNum", keyordNum);
		map.put("comNum", comNum);
		map.put("tenantPackage", tenanPackage);
		return map;
	}

	@Override
	@Transactional
	public boolean updateProjectInfo(InUpdateProjectDto in) throws Exception {
		boolean res = false;
		StringBuffer sql = new StringBuffer(
				" update Project t SET t.updateDate = :updateDate, t.updateBy = :updateBy ");

		Map<String, Object> map = new HashMap<String, Object>();
		if (StringUtils.isNotBlank(in.getProjectName())) {
			sql.append(" ,t.projectName = :projectName ");
			map.put("projectName", in.getProjectName());
		}
		if (StringUtils.isNotBlank(in.getReceiveEmail())) {
			sql.append(" ,t.receiveEmail = :receiveEmail ");
			map.put("receiveEmail", in.getReceiveEmail());
		}
		sql.append(" WHERE t.projectId = :projectId and t.delFlag = '0' ");

		Query query = entity.createQuery(sql.toString());
		query.setParameter("updateDate", new Date());
		query.setParameter("updateBy", RestUserUtils.getUserInfo().getUserId());
		query.setParameter("projectId", Integer.parseInt(in.getProjectId()));

		if (map.size() > 0) {
			Set<String> set = map.keySet();
			for (String str : set) {
				query.setParameter(str, map.get(str));
			}
		}
		res = query.executeUpdate() > 0 ? true : false;

		// 修改项目的竞品信息
		if (null != in.getCompetitors() && in.getCompetitors().size() > 0) {
			Competitor competitor = new Competitor();
			for (InCompetitorDto inCompetitorDto : in.getCompetitors()) {
				competitor.setCompetitorId(Integer.parseInt(inCompetitorDto.getId()));
				competitor.setCompetitorName(inCompetitorDto.getName());
				competitor.setCompetitorUrl(inCompetitorDto.getUrl());
				competitor.setProjectId(Integer.parseInt(in.getProjectId()));
				if (updateCompetitorInfo(competitor))
					res = true;
				else {
					throw new BusinessException(msg.getProcessStatus("PROJECT_COM_UPDATA_ERROR"));
				}
			}
		}

		return res;
	}

	@Override
	@Transactional
	public boolean updateCompetitorInfo(Competitor competitor) throws Exception {
		StringBuffer sql = new StringBuffer(
				" update Competitor t SET t.updateDate = :updateDate, t.updateBy = :updateBy ");

		Map<String, Object> map = new HashMap<String, Object>();
		if (null != competitor.getCompetitorName() && !"".equals(competitor.getCompetitorName())) {
			sql.append(" ,t.competitorName = :competitorName ");
			map.put("competitorName", competitor.getCompetitorName());
		}
		if (null != competitor.getCompetitorUrl() && !"".equals(competitor.getCompetitorUrl())) {
			sql.append(" ,t.competitorUrl = :competitorUrl ");
			map.put("competitorUrl", competitor.getCompetitorUrl());
		}
		sql.append("WHERE t.projectId = :projectId and t.competitorId = :competitorId and t.delFlag = '0' ");

		map.put("competitorId", competitor.getCompetitorId());
		map.put("updateDate", new Date());
		map.put("updateBy", RestUserUtils.getUserInfo().getUserId());
		map.put("projectId", competitor.getProjectId());
		return updateEntitySys(sql.toString(), map);
	}

	/***
	 * 删除竞品
	 */
	@Transactional
	public String delCompetitor(Map<String, Object> param) throws Exception {
		String projectId = param.get("projectId").toString();
		@SuppressWarnings("unchecked")
		List<String> list = (List<String>) param.get("competitorId");
		List<Integer> comIds = new ArrayList<>();
		for (String str : list) {
			Integer competitorId = Integer.parseInt(str);
			comIds.add(competitorId);
		}
		if (null == comIds || comIds.size() <= 0) {
			throw new BusinessException(msg.getProcessStatus("PARAM_IS_NULL"));
		}
		Project project = projectWiResources.getProjectById(Integer.parseInt(projectId));// 当前项目
		if (null == project) {
			throw new BusinessException(msg.getProcessStatus("PROJECT_ERROR"));
		}
		if ("1".equals(project.getDelFlag())) {
			throw new BusinessException(msg.getProcessStatus("PROJECT_ERROR"));
		}
		Date date = new Date();
		competitorRes.deleteByComId("1",
				RestUserUtils.getUserInfo() == null ? 2 : RestUserUtils.getUserInfo().getUserId(), date, date,
				Integer.parseInt(projectId), comIds);

		return "0";
	}

	@Transactional
	public String addCompetitor(Map<String, Object> param) throws Exception {
		String projectId = param.get("projectId").toString();
		/* 查询套餐当前租户的实例规格 */
		String tenantId = RestUserUtils.getTencentId().toString();
		List<TenantPackage> tenanPackageList = packageService.getPackageExampleListByTenantId(tenantId,
				param.get("appType").toString());
		if (null == tenanPackageList || tenanPackageList.size() <= 0) {
			throw new BusinessException(msg.getProcessStatus("PACKAGE_NOT_EXIST"));
		}
		if (tenanPackageList.size() > 1) {
			throw new BusinessException(msg.getProcessStatus("PACKAGE_MORE"));
		}
		TenantPackage tenanPackage = tenanPackageList.get(0);// 当前的套餐实例
		String comNumelementId = wiConstantsProperties.getValue("comNum").toString();// 竞品数
		int oldComNum = getComNumBy(projectId);// 已有的竞品个数

		int comNum = 0;// 竞品数

		// 规格
		List<TenantPackageElementsRel> tenantPackageElementsRelList = tenanPackage.getTenantPackageElementsRelList();
		if (null == tenantPackageElementsRelList || tenantPackageElementsRelList.size() <= 0) {
			throw new BusinessException(msg.getProcessStatus("PACKAGE_NOT_RULE"));
		}
		for (TenantPackageElementsRel rel : tenantPackageElementsRelList) {
			if (comNumelementId.equals(rel.getElements().getElementId().toString())) {// 竞品数
				if ("1".equals(rel.getValidType())) {// 取最大值
					comNum = rel.getUsedMax();
				}
			}
		}
		Competitor c = new Competitor();
		Integer competitorId = masterSequenceUtil.getCommonSeq();
		c.setCompetitorId(competitorId);
		c.setTenantId(Integer.parseInt(tenantId));
		c.setCompetitorName(param.get("name").toString());
		String curlteml = param.get("url").toString();
		c.setCompetitorUrl(curlteml);
		c.setProjectId(Integer.parseInt(projectId));
		c.setDelFlag("0");
		c.setCreateBy(RestUserUtils.getUserInfo().getUserId());
		c.setCreateDate(new Date());
		c.setDelFlag("0");
		c.setUpdateBy(RestUserUtils.getUserInfo().getUserId());
		c.setUpdateDate(new Date());
		if (oldComNum >= comNum) {
			throw new BusinessException(msg.getProcessStatus("COM_NOT_ADD"));
		}
		saveCompetitor(c);
		return "0";
	}

	@Override
	public String checkArchive(String state) throws Exception {
		int res = 0;
		res = projectWiResources.checkArchive(state) > 0 ? 1 : 0;
		return StringUtils.toString(res);
	}

	/**
	 * 获取当前时间的后一天
	 * 
	 * @return
	 */
	private Date getNextDate() {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_YEAR, 1);
		Date date = DateUtils.formatDateToPattern(calendar.getTime(), "yyyy-MM-dd");
		return date;
	}

	public CheckUrlResultDto checkUrl(CheckUrlDto checkUrlDto) throws Exception {
		CheckUrlResultDto checkUrlResultDto = new CheckUrlResultDto();
		checkUrlResultDto.setUrlFlag("0");

		/***
		 * 判断竞品是否有重定向
		 * 
		 */
		List<Map<String, String>> maplist = new ArrayList<Map<String, String>>();
		if (null != checkUrlDto.getUrl() && !"".equals(checkUrlDto.getUrl())) {
			Map<String, String> ma1 = new HashMap<String, String>();
			ma1.put("urlFlag", "0");
			ma1.put("url", checkUrlDto.getUrl().trim());
			ma1.put("redirectUrl", "");
			maplist.add(ma1);
		}
		if (maplist.size() > 0) {
			rediectUrl.redirect(maplist);
			for (Map<String, String> map : maplist) {
				checkUrlResultDto.setRedirectUrl(map.get("redirectUrl").toString());
				checkUrlResultDto.setUrlFlag(map.get("urlFlag").toString());
			}

		}
		return checkUrlResultDto;
	}

	/*
	 * **********上方是应用服务私有方法不对别的模块和别的应用直接提供提供***********
	 */

	/*
	 * ********************此处之下是service服务层调用的公共方法，若要修改，请慎重操作********************
	 * *
	 */
	@Override
	public OutProComKwInfoDto getProComKwInfoById(Integer projectId) {
		OutProComKwInfoDto out = projectWiResources.getProjectBaseInfo2Dto(projectId);
		if (null != out) {
			List<Competitor> list = getCompetitorsInfoByProjectId(projectId.toString());
			if (null != list && !"".equals(list) && list.size() > 0) {
				List<OutProCompetitorDto> outProCompetitorDtos = new ArrayList<OutProCompetitorDto>();
				OutProCompetitorDto outProCompetitorDto = null;
				for (int i = 0; i < list.size(); i++) {
					outProCompetitorDto = new OutProCompetitorDto(list.get(i).getCompetitorId(),
							list.get(i).getCompetitorName(), list.get(i).getCompetitorUrl());
					outProCompetitorDtos.add(outProCompetitorDto);
				}
				out.setCompetitors(outProCompetitorDtos);
			}

			StringBuffer sql = new StringBuffer().append("");
			sql.append(
					"select k.keyword_id,k.keyword_name,k.is_brand,GROUP_CONCAT(CONCAT(g.group_id,'-',g.group_name)) "
							+ "from " + "(select a.keyword_id,a.keyword_name,b.is_brand,b.pro_key_rel_id,b.project_id "
							+ "from  tf_f_seo_keyword a, tf_b_seo_pro_keyword b "
							+ "where a.keyword_id = b.keyword_id and a.del_flag =0 and b.del_flag =0) k "
							+ "left JOIN td_b_seo_group_key_rel rel on k.pro_key_rel_id =rel.pro_key_rel_id and rel.del_flag=0 "
							+ "left join tf_f_seo_group g on rel.group_id = g.group_id and g.del_flag=0 "
							+ "where k.project_id =:projectId  group by k.keyword_id ");
			Query query = entity.createNativeQuery(sql.toString());
			query.setParameter("projectId", projectId);

			// List<Object[]> keywordList =
			// keywordResources.getKeyWordsAndBrand(projectId);
			@SuppressWarnings("unchecked")
			List<Object[]> keywordList = query.getResultList();
			if (null != keywordList && !"".equals(keywordList) && keywordList.size() > 0) {
				List<OutProKeywordDto> outProKeywordDtos = new ArrayList<OutProKeywordDto>();
				OutProKeywordDto outProKeywordDto = null;
				for (Object[] obj : keywordList) {
					outProKeywordDto = new OutProKeywordDto(obj[2].toString(), Integer.parseInt(obj[0].toString()),
							obj[1].toString());
					// Integer keywordId = Integer.parseInt(obj[0].toString());
					// List<ProKeyword> pro =
					// proKeywordResources.getProKeywordInfo(projectId,
					// keywordId);
					if (obj[3] != null) {
						String groupStr = obj[3].toString();
						String[] strList = groupStr.split(",");
						List<Group> groups = new ArrayList<>();
						for (String str : strList) {
							String[] groupIdName = str.split("-");
							String groupId = groupIdName[0];
							String groupName = groupIdName[1];
							Group group = new Group();
							group.setGroupId(Integer.parseInt(groupId));
							group.setGroupName(groupName);
							groups.add(group);
						}
						outProKeywordDto.setGroups(groups);
					}
					outProKeywordDtos.add(outProKeywordDto);
				}
				out.setKeywords(outProKeywordDtos);
			}

			List<Group> groups = keywordService.getAllProjectGroups(projectId.toString());
			if (null != groups && groups.size() > 0) {
				List<Integer> groupList = new ArrayList<Integer>();
				for (int i = 0, len = groups.size(); i < len; i++) {
					groupList.add(Integer.parseInt(groups.get(i).getGroupId()));
				}
				out.setGroups(groupList);
			}

			out.setKeywordNum(keywordService.getProjectKeywordNum(projectId.toString()));
			return out;
		}
		return null;
	}
	
	/**项目自动归档
	 * @return
	 */
	@Override
	@Transactional
	public void autoArchive(){
		Date date = new Date();
		List<Project> projectList = projectWiResources.getProject2Archive(date);
		for(int i =0; i<projectList.size();i++){
			projectList.get(i).setArchiveDate(date);
			projectList.get(i).setProjectState("0");
		}
		projectWiResources.save(projectList);
	}
}
