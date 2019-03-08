package com.ido85.master.spider;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.jdbc.core.JdbcTemplate;

import com.ido85.config.properties.WiConstantsProperties;
import com.ido85.master.project.resources.CompetitorResources;
import com.ido85.master.project.resources.ProjectWiResources;
//import com.ido85.master.service.CommonService;
//import com.ido85.master.statistics.resource.ProjectExtendResources;

//@Named
public class GetBaiduBr {
	@Inject
	private WiConstantsProperties wiConstantsProperties;
//	@Inject
//	private CommonService commonService;
//	@Inject
//	private ProjectExtendResources proRe;
	@Inject
	private EntityManager em;
	@Inject
	private ProjectWiResources projectWiResources;
	
	@Inject
	private JdbcTemplate jdbcTemplate;
	
	@Inject
	private CompetitorResources competitorResources;
	
	//@Scheduled(fixedDelay=1000*60*60*24*7)
	public  void getBaiduBr() {
//		String userId = "####sys";
//		List<Map<String, Object>> urls = getList();
//		String sql = "INSERT INTO tf_f_project_extend VALUES"
//				+ " ("
//				+ "?, ?, ?, ?, ?,"
//				+ " ?, ?, ?, '0', '####sys', ?, '####sys', ?, '0'"
//				+ ")";
//		
//		System.out.println("--------------------------定时任务开始-------------------------");
//		String lastTime =  wiConstantsProperties.getValue("crawlDate").toString();//项目数
//		Date lastDate = DateUtils.parseDate(lastTime);
//		Date nextDate = commonService.getNextUpdateTime(lastDate);//获取本次的更新时间
//		String nextTime = "";
//			if(nextDate!=null){
//				nextTime = DateUtils.formatDate(nextDate, "yyyyMMdd");
//				if(urls!=null&&urls.size()>0){
//					for(Map<String, Object> url : urls){
//						int br = 0;
//						br = getbrmytool(url.get("url").toString());
//						if(br == -1 || br == 0){
//							if(getaizhanbr(url.get("url").toString()) != -1){
//								br = getaizhanbr(url.get("url").toString());
//							}
//						}
//						//入库
//						String tenantIds = "";
//						if("0".equals(url.get("isCom"))){//是项目
//							//查询租户
//							String projectsql = "select tenant_id from tf_f_seo_project where project_id="+"'"+url.get("projectId").toString()+"'";
//							Query prquery = em.createNativeQuery(projectsql);
//							tenantIds = (String) prquery.getSingleResult();
//							
//							
//							
//						}else {//竞品
//							//查询租户
//							String projectsql = "select tenant_id from tf_f_seo_competitor where competitor_id="+"'"+url.get("competitorId").toString()+"'";
//							Query prquery = em.createNativeQuery(projectsql);
//							tenantIds = (String) prquery.getSingleResult();
//							
//						}
//						
//						jdbcTemplate.update(sql,KeysUtils.uuid(),tenantIds,url.get("projectId"), url.get("competitorId"), url.get("url"),url.get("isCom")
//								,"0",br,nextDate,nextDate);
//					}
//					
//					
//					wiConstantsProperties.setValue("crawlDate", nextTime);
//				}
//			}
//		
		System.out.println("--------------------------定时任务结束-------------------------");
	}
	

	public List<Map<String, Object>> getList(){
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
//		String sql = "select project_id,project_url,tenant_id	 from tf_f_seo_project p where p.del_flag='0' and p.project_state='1'";
//		Query query = em.createNativeQuery(sql);
//		List<Object[]> projectList =  query.getResultList();
//		List<Project> pList = new ArrayList<Project>();
//		if(null!=projectList&&projectList.size()>0){
//			for(Object[] ob : projectList){
//				Project project = new Project();
//				project.setProjectId(ob[0].toString());
//				project.setProjectUrl(ob[1].toString());
//				pList.add(project);
//			}
//		}
//		
//		List<String> projectIds = new ArrayList<String>();
//		if(null!=pList&&pList.size()>0){
//			for(Project p : pList){
//				Map<String, Object> map = new HashMap<String, Object>();
//				map.put("projectId", p.getProjectId());
//				map.put("isCom", "0");
//				map.put("competitorId", "");
//				map.put("url",p.getProjectUrl());
//				list.add(map);
//				projectIds.add(p.getProjectId());
//				
//			}
//		}
//		if(projectIds.size()>0){
//			String csql = "select competitor_id,competitor_url,project_id from tf_f_seo_competitor t where t.del_flag = '0' and t.project_id in (";
//			for(int i=0;i<projectIds.size();i++){
//				csql = csql+ "'"+projectIds.get(i) + "'";
//				if(i<projectIds.size()-1){
//					csql = csql+",";
//				}
//				
//			}
//			
//			csql = csql +")";		
//			Query cquery = em.createNativeQuery(csql);
//			List<Object[]> listc =  cquery.getResultList();
//			List<Competitor> cList = new ArrayList<Competitor>();
//			if(null!=list&&list.size()>0){
//				for(Object[] ob : listc){
//					Competitor competitor  = new Competitor();
//					competitor.setCompetitorId(ob[0].toString());
//					competitor.setCompetitorUrl(ob[1].toString());
//					competitor.setProjectId(ob[2].toString());
//					
//				}
//			}
//			
//			
//			
//			
//			
//			
//			
//			
//			
//			if(null!=cList&&cList.size()>0){
//				for(Competitor p : cList){
//					Map<String, Object> map = new HashMap<String, Object>();
//					map.put("projectId", p.getProjectId());
//					map.put("isCom", "1");
//					map.put("competitorId", p.getCompetitorId());
//					map.put("url",p.getCompetitorUrl());
//					list.add(map);
//					
//				}
//			}
//		}
		
		return list;
	}
	
	
	
	
	
	/**
	 * 通过站长工具获取百度权重
	 * 
	 * @param eurl
	 * @return
	 * @return
	 */
	public static int getbrmytool(String eurl) {
		// System.out.println("开始调取我的工具接口获取BR------------");
		String url = eurl.replaceAll(" ", "");

		Document doc = null;
		boolean conntype = false;
		String br = "";
		int i = 0;
		while (i < 3) {
			try {
				doc = Jsoup.connect(
						"http://mytool.chinaz.com/baidusort.aspx?host=" + url)
						.get();
				conntype = true;
				break;
			} catch (IOException e) {
				i++;
				continue;
				// System.out.println("链接错误，by：" + e.getMessage() + "正在进行第" + i
				// +
				// "次重试");
				// TODO Auto-generated catch block
				// e.printStackTrace();
			}	
		}
		if (conntype == true && doc != null && !"".equals(doc)) {
			if (doc.select("div.siteinfo").toString().contains("font")) {
				br = doc.select("div.siteinfo font").first().text();
			}else{
				br = "0";
			}
		}

		if (conntype == false || null == br || "".equals(br)) {
			br = "-1";
		}
		return Integer.parseInt(br);
	}
	
	
	/**
	 * 通过爱站网获取百度权重
	  * getaizhanbr  这个方法不大稳定，---------慎用--------
	  * TODO(这里描述这个方法适用条件 – 可选)
	  * TODO(这里描述这个方法的执行流程 – 可选)
	  * TODO(这里描述这个方法的使用方法 – 可选)
	  * TODO(这里描述这个方法的注意事项 – 可选)
	  *
	  * @Title: getaizhanbr
	  * @Description: TODO
	  * @param @param eurl
	  * @param @return    设定文件
	  * @return int    返回类型
	  * @throws
	 */
	public static int getaizhanbr(String eurl) {
		//System.out.println("开始调取爱站网接口获取BR------------");
		String url = eurl.replaceAll(" ", "");

		int i =0 ;
		Document doc = null;
		boolean conntype = false;
		
		while (i < 3) {
			try {
				doc = Jsoup
						.connect("http://baidurank.aizhan.com/baidu/" + url + "/")
						.userAgent(
								"Baiduspider+(+http://www.baidu.com/search/spider.htm)")
						.ignoreHttpErrors(true).timeout(20000)
						.followRedirects(true).execute().parse();
				conntype = true;
				break;
				
			} catch (IOException e) {
				i++;
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					//e1.printStackTrace();
				}
				//System.out.println("链接错误，by：" + e.getMessage() + "正在进行第" + i + "次重试");
				// TODO Auto-generated catch block
				continue;
			}
		}
		if(conntype == true && doc!=null){
			
			String Br = "";
			if(doc.body().select("table.table").select("img")!=null){
				if(doc.body().select("table.table").select("img").first() != null){
					Br = doc.body().select("table.table").select("img").first().attr("src");
				}else{
					return 0;
				}
			}else{
				return 0;
			}
			
			if (null == Br || "".equals(Br)) {
				return 0;
			} else {
				return Integer.parseInt(Br.substring(Br.length() - 5, Br.length() - 4));
			}
		}
		return -1;
	}
	
}
