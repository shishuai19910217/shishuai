package com.ido85.services.packages.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import com.ido85.config.properties.WiConstantsProperties;
import com.ido85.frame.common.utils.DateUtils;
import com.ido85.master.packages.application.TenantPackageApplication;
import com.ido85.master.packages.domain.Packages;
import com.ido85.master.packages.domain.TenantPackage;
import com.ido85.master.packages.domain.TenantPackageElementsRel;
import com.ido85.master.packages.dto.PackagePersonalDto;
import com.ido85.master.packages.dto.PackagesDto;
import com.ido85.master.packages.dto.TenantPackageDto;
import com.ido85.master.packages.resources.PackageResources;
import com.ido85.services.packages.PackageApi;

@Named
public class PackageApiImpl implements PackageApi {

	@Inject
	private TenantPackageApplication tenantPackageApp;// 套餐实例
	@Inject
	private WiConstantsProperties wiConstantsProperties;

	@Inject
	private PackageResources packageResources;

	/***
	 * 公共方法 根据租户查找所有 的套餐规格 有效的
	 * 
	 * @param tenantId
	 *            租户id
	 * @param appType
	 *            应用类型
	 * @return
	 */
	@Override
	public List<TenantPackage> getPackageExampleListByTenantId(String tenantId, String appType) {
		return tenantPackageApp.getPackageExampleListByTenantId(tenantId, appType);
	}
	/***
	 * 公共方法 根据租户批量查找所有的套餐规格 有效的
	 * 
	 * @param tenantId
	 *            租户id
	 * @param appType
	 *            应用类型
	 * @return
	 */
	@Override
	public List<TenantPackage> getBatchPackageExampleListByTenantId(List<Integer> tenantIds) {
		return tenantPackageApp.getBatchPackageExampleListByTenantId(tenantIds);
	}

	/****
	 * 公共方法 查询租户下 某个应用下的所有套餐 包含到期的
	 * 
	 * @param tenantId
	 * @param appType
	 * @return
	 */
	@Override
	public List<TenantPackage> getPackageExampleAllListByTenantId(String tenantId, String appType) {
		return tenantPackageApp.getPackageExampleAllListByTenantId(tenantId, appType);
	}

	/***
	 * 公共方法 根据租户查找所有 的套餐规格 实例
	 * 
	 * @param tenantId
	 *            租户id
	 * @param appType
	 * @return
	 */
	@Override
	public List<TenantPackageDto> getPackageExampleListByTenantId(String tenantId) {
		List<TenantPackageDto> dtoList = new ArrayList<TenantPackageDto>();
		List<TenantPackage> list = tenantPackageApp.getPackageExampleListByTenantId(tenantId);// 实例信息
		if (null != list && list.size() > 0) {
			List<Packages> pList = new ArrayList<Packages>();// 套餐信息
			List<Integer> paramList = new ArrayList<Integer>();// 套餐id
			for (TenantPackage t : list) {
				paramList.add(t.getPackageId());
			}
			pList = packageResources.getPackageListById(paramList);// 根据id取套餐
			Map<Integer, Packages> packagesMap = new HashMap<Integer, Packages>();

			for (Packages t : pList) {
				System.out.println("llz===="+t.getPackageId()+"==name="+t.getPackageName());
				packagesMap.put(t.getPackageId(), t);
			}

			for (TenantPackage t : list) {
				TenantPackageDto dto = new TenantPackageDto();
				dto.setAppType(t.getAppType());
				dto.setEndDate(t.getPackageEndDate() == null ? ""
						: DateUtils.formatDate(t.getPackageEndDate(), "yyyyMMddHHmmss"));
				dto.setName(packagesMap.get(t.getPackageId()).getPackageName());
				dto.setPriceMonth(packagesMap.get(t.getPackageId()).getPackagePriceMontn());
				dto.setPriceYear(packagesMap.get(t.getPackageId()).getPackagePriceYear());
				dto.setTrialFlag(t.getIsProbation());
				dto.setPackageId(t.getPackageId()+"");
				dto.setTenantPackageId(t.getTenantPackageId()+"");
				dto.setPackageLevel(t.getPackageLevel());

				dto.setStartDate(t.getPackageStartDate() == null ? ""
						: DateUtils.formatDate(t.getPackageStartDate(), "yyyyMMddHHmmss"));

				List<TenantPackageElementsRel> tenantPackageElementsRelList = t.getTenantPackageElementsRelList();
				if (tenantPackageElementsRelList.size() > 0) {
					String proNumelementId = wiConstantsProperties.getValue("proNum").toString();// 项目数
					String comNumelementId = wiConstantsProperties.getValue("comNum").toString();// 竞品数
					String keyordNumelementId = wiConstantsProperties.getValue("keywordNum").toString();// 关键词书
					int proNum = 0;// 规定项目数
					int comNum = 0;// 竞品数
					int keyordNum = 0;// 关键词数
					// 规格

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
					dto.setCompetitorNum(comNum);
					dto.setKeywordNum(keyordNum);
					dto.setProjectNum(proNum);
					dto.setYearOrMonth(t.getPayType());
					dtoList.add(dto);
				}
			}
		}

		return dtoList;
	}

	/**
	 * 根据套餐id获取套餐信息
	 * 
	 * @param packageId
	 * @return
	 */
	@Override
	public PackagePersonalDto getPackagePersonalInfoByPackageId(String packageId) {
		return tenantPackageApp.getPackagePersonalInfoByPackageId(packageId);
	}

	/**
	 * 公共方法 根据套餐id查询套餐的所有信息
	 * 
	 * @param packageId
	 * @return
	 */
	@Override
	public PackagesDto getPackegeInfoById(String packageId) {
		return tenantPackageApp.getPackageInfoByPackageId(packageId);
	}

	/**
	 * 用户订购套餐，保存套餐实例信息、套餐实例和元素关系
	 * 
	 * @param tenantId
	 * @param packageId
	 *            调用此函数之前请先校验套餐id是否正确
	 * @return
	 * @throws Exception
	 */
	@Override
	public boolean savePackageInstance(String tenantId, String packageId) throws Exception {
		boolean res = false;
		Packages pack = packageResources.getPackageInfoByPackageId(Integer.parseInt(packageId));
		if (null == pack || "".equals(pack))
			return res;
		tenantPackageApp.savePackageInstance(pack, tenantId);
		return true;
	}
	
	/**
	 * *********************************************llz add**************************************************
	 * */
	@SuppressWarnings("unchecked")
	public String getEngine(List<Map<String,Object>> temp){
		String engine = "";
		if(null != temp && temp.size() > 0){
			Map<String, Object> map = null;
			List<Map<String,Object>> engineTemp = null;
			for (int i = 0,len = temp.size(); i < len; i++) {
				map = temp.get(i);
				if(map.containsKey("elementId") && "##engineType".equals(map.get("elementId") + "")){
					engineTemp = (List<Map<String, Object>>) map.get("value");
					break;
				}
			}
			if(null != engineTemp && engineTemp.size() > 0){
				for (int i = 0; i < engineTemp.size(); i++) {
					if(i == 0){
						engine = wiConstantsProperties.getValue(engineTemp.get(i).get("elementId") + "").toString();
					}else
						engine = engine + "," + wiConstantsProperties.getValue(engineTemp.get(i).get("elementId") + "").toString();
				}
			}
		}
		return engine;
	}
	
	public List<Map<String,Object>> getScope(List<TenantPackageElementsRel> list){
		
		if(null==list||list.size()<=0){
			return null;
		}
		List<Map<String,Object>> l = new ArrayList<Map<String,Object>>();
		List<Map<String,Object>> engineType = new ArrayList<Map<String,Object>>();//监控范围
		for(TenantPackageElementsRel rel : list){
			if(rel.getElementType().equals("0")){
				if("0".equals(rel.getValidType())){//取最大值
					Map<String,Object> m = new HashMap<String,Object>();
					m.put("value", rel.getUsedMax());//
					m.put("elementName",rel.getElements().getElementName());
					m.put("elementId", rel.getElements().getElementId());//12
					m.put("unit", rel.getUnit());//单位
					m.put("type", "0");//等于0 直接显示结果
					l.add(m);
				}
				if("1".equals(rel.getValidType())){//布尔
					if("1".equals(rel.getValidFlag())){
						Map<String,Object> m = new HashMap<String,Object>();
						m.put("value", rel.getValidFlag());//
						m.put("elementName",rel.getElements().getElementName());
						m.put("elementId", rel.getElements().getElementId());//12
						m.put("unit", "");//单位
						m.put("type", "1");
						l.add(m);
					}
					
				}
				if("2".equals(rel.getValidType())){//文字
					Map<String,Object> m = new HashMap<String,Object>();
					m.put("value", rel.getElementValue());//
					m.put("elementName",rel.getElements().getElementName());
					m.put("elementId", rel.getElements().getElementId());//12
					m.put("unit", "");//单位
					m.put("type", "2");
					l.add(m);
				}
			}else if(rel.getElementType().equals("1")){//监控
				Map<String,Object> m = new HashMap<String,Object>();
				m.put("elementName",rel.getElements().getElementName());
				m.put("elementId", rel.getElements().getElementId());//12
				engineType.add(m);
			}
			
			
		} 
		Map<String,Object> m = new HashMap<String,Object>();
		m.put("value", engineType);//12
		m.put("elementName","监控范围");
		m.put("elementId", "##engineType");//12
		m.put("type", "1");//监控范围
		l.add(m);
		return l;
	}
}
