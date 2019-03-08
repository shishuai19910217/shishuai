package com.ido85.master.packages.application.impl;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.data.repository.query.Param;

import com.ido85.config.properties.BussinessMsgCodeProperties;
import com.ido85.frame.common.exceptions.BusinessException;
import com.ido85.frame.common.utils.DateUtils;
import com.ido85.frame.common.utils.ListUntils;
import com.ido85.frame.common.utils.StringUtils;
import com.ido85.master.common.MasterSequenceUtil;
import com.ido85.master.packages.application.TenantPackageApplication;
import com.ido85.master.packages.domain.PackageElementsRel;
import com.ido85.master.packages.domain.Packages;
import com.ido85.master.packages.domain.TenantPackage;
import com.ido85.master.packages.domain.TenantPackageElementsRel;
import com.ido85.master.packages.dto.ManageTenantPackageDto;
import com.ido85.master.packages.dto.PackageDTO;
import com.ido85.master.packages.dto.PackagePersonalDto;
import com.ido85.master.packages.dto.PackageScopeDto;
import com.ido85.master.packages.dto.PackagesDto;
import com.ido85.master.packages.resources.PackageResources;
import com.ido85.master.packages.resources.TenantPackageElementsRelResources;
import com.ido85.master.packages.resources.TenantPackageWiResources;
import com.ido85.master.project.application.ProjectApplication;
import com.ido85.master.project.resources.OrderProjectTenantExampleRelWiResources;
import com.ido85.master.project.resources.ProjectTenantExampleRelWiResources;
import com.ido85.master.user.util.UserUtils;
import com.ido85.services.user.UserApi;

@Named
public class TenantPackageApplicationImpl implements TenantPackageApplication {
	@Inject
	private TenantPackageWiResources tenantPackageWiResources;
	@Inject
	private PackageResources packageResources;
	@Inject
	private UserApi userService;
	@PersistenceContext(unitName="system")
	private EntityManager em;
	@Inject
	private ProjectTenantExampleRelWiResources projectTenantExampleRelWiResources;
	@Inject
	private BussinessMsgCodeProperties prop;

	@Inject
	private TenantPackageElementsRelResources tenantPackageElementsRelResources;
	@Inject
	private ProjectApplication projectApp;
	@Inject
	private OrderProjectTenantExampleRelWiResources orderProjectTenantExampleRelWiResources;

	@Inject
	private MasterSequenceUtil masterSequenceUtil;
	/***
	 * 根据租户查找所有 的套餐规格
	 * 
	 * @param tenantId
	 * @return
	 */
	public List<TenantPackage> getPackageExampleListByTenantId(String tenantId, String appType) {
		Date nowDate = new Date();
		List<TenantPackage> tenantPackageList = tenantPackageWiResources.getPackageExampleListByTenantId(Integer.parseInt(tenantId),
				appType, nowDate);
		if (null != tenantPackageList && tenantPackageList.size() > 0) {
			for (TenantPackage t : tenantPackageList) {
				List<TenantPackageElementsRel> list = new ArrayList<TenantPackageElementsRel>();
				if (null != t) {
					for (TenantPackageElementsRel rel : t.getTenantPackageElementsRelList()) {
						if ("0".equals(rel.getDelFlag())) {
							list.add(rel);
						}
					}
				}
				t.setTenantPackageElementsRelList(list);
			}
		}

		return tenantPackageList;
	}
	/***
	 * 根据租户批量查找所有的套餐规格
	 * 
	 * @param tenantId
	 * @return
	 */
	public List<TenantPackage> getBatchPackageExampleListByTenantId(List<Integer> tenantIds) {
		List<TenantPackage> tenantPackageList = tenantPackageWiResources.getBatchPackageExampleListByTenantId(tenantIds,
				"0", new Date());
		if (null != tenantPackageList && tenantPackageList.size() > 0) {
			for (TenantPackage t : tenantPackageList) {
				List<TenantPackageElementsRel> list = new ArrayList<TenantPackageElementsRel>();
				if (null != t) {
					for (TenantPackageElementsRel rel : t.getTenantPackageElementsRelList()) {
						if ("0".equals(rel.getDelFlag())) {
							list.add(rel);
						}
					}
				}
				t.setTenantPackageElementsRelList(list);
			}
		}
		
		return tenantPackageList;
	}

	/***
	 * 查询租户下所有的套餐实例 包含到期的 删除的
	 * 
	 * @param tenantId
	 * @param appType
	 * @return
	 */
	public List<TenantPackage> getPackageExampleAllListByTenantId(String tenantId, String appType) {
		List<TenantPackage> tenantPackageList = tenantPackageWiResources.getPackageExampleAllListByTenantId(Integer.parseInt(tenantId),
				appType);
		if (null != tenantPackageList && tenantPackageList.size() > 0) {
			for (TenantPackage t : tenantPackageList) {
				List<TenantPackageElementsRel> list = new ArrayList<TenantPackageElementsRel>();
				if (null != t) {
					for (TenantPackageElementsRel rel : t.getTenantPackageElementsRelList()) {
						if ("0".equals(rel.getDelFlag())) {
							list.add(rel);
						}
					}
				}
				t.setTenantPackageElementsRelList(list);
			}
		}

		return tenantPackageList;
	}

	/***
	 * 订购套餐 购买套餐
	 * 
	 * @param param
	 *            packageId payType（购买方式） :0 按月购买 1 按年购买 startDate 生效开始时间
	 *            endDate 结束时间 orderType 0购买 2升级 1续费 tenantPackageId 实例id 续费时使用
	 *            tenantId 租户id
	 * @return
	 * @throws Exception 
	 */
	@Transactional
	public String orderedPackage(Map<String, Object> param) throws Exception {
		// 查找套餐
		Packages p = getPackageInfoByPackageId(param.get("packageId").toString(), param.get("appType").toString());
		if (null == p) {
			return "1";// 未找到套餐
		}
		String userId = param.get("userId") == null ? "05C686A9-14CC-4B90-9244-B46921999CBC"
				: param.get("userId").toString();
		String tenantIdOld = userService.isPrimary(userId, param.get("appType").toString());// 租户id
		String orderType = param.get("orderType") == null ? "0" : param.get("orderType").toString();// 0购买
		Date startDate = null;
		Date endDate = null;
		String endDatestr = "";
		try {
			startDate = DateUtils.parse(param.get("startDate").toString(), "yyyy-MM-dd");
			endDate = DateUtils.parse(param.get("endDate").toString(), "yyyy-MM-dd");
			endDatestr = DateUtils.formatDate(endDate, "yyyy-MM-dd");
			endDatestr = endDatestr + " 23:59:59";
			endDate = DateUtils.parse(endDatestr, "yyyy-MM-dd HH:mm:ss");
		} catch (ParseException e) {
			e.printStackTrace();
		} // 2升级
			// 1续费
		String tenantId = null;// 新的
		if ("0".equals(orderType)) {// 购买
			/***
			 * 判断此用户是否可以订购: 此应用是主用户不能订购（购买过此应用下的套餐）
			 * 
			 */
			TenantPackage tenantPackageold = null;
			TenantPackage tenantPackage = null;
			if (null != tenantIdOld) {// 是租户
				// 判断当前租户下是否存在有效的套餐实例
				List<TenantPackage> list = getPackageExampleListByTenantId(tenantIdOld,
						param.get("appType").toString());

				if (null != list && list.size() > 0) {
					tenantPackageold = list.get(0);
					if ("0".equals(tenantPackageold.getIsProbation())) {// 如果已经购买了此套餐
						return "2";// 此用户不能订购套餐
					}
				}
			}
			// 如果有试用套餐 先把试用套餐变为失效 再进行下面的操作
			if (null != tenantPackageold) {
				if ("1".equals(tenantPackageold.getIsProbation())) {// 试用的
					Date nowDate = new Date();
					// 删除原先的 将时间置位失效
					tenantPackageWiResources.updateTenantPackageByTenantPackageId(new Date(),
							tenantPackageold.getTenantPackageId());
					tenantPackageElementsRelResources.deleteByTenantPackageId(tenantPackageold.getTenantPackageId());
					// 是有租户的
					tenantId = String.valueOf(param.get("tenantId"));
					// 生成实体信息
					tenantPackage = makeTenantPackage(p, param.get("isProbation").toString(),
							param.get("payType").toString(), startDate, endDate, tenantId, userId);
					// 实例信息入库

					em.persist(tenantPackage);
					// param.put("nowTenantPackageId",
					// tenantPackage.getTenantPackageId());

				}
			} else {// 没有租户信息 只有是租户的时候才会对tenantPackageold进行赋值
					// 所有tenantPackageold为空就是没有租户信息

				// 生成租户信息
				// tenantId = userService.saveTenant(userId,
				// param.get("appType").toString());
				tenantId = String.valueOf(param.get("tenantId"));
				if (null == tenantId || "null".equals(tenantId)) {
					return "-5";
				}
				// 生成实体信息
				tenantPackage = makeTenantPackage(p, param.get("isProbation").toString(),
						param.get("payType").toString(), startDate, endDate, tenantId, userId);
				// 实例信息入库

				em.persist(tenantPackage);
				// param.put("nowTenantPackageId",
				// tenantPackage.getTenantPackageId());

			}
			// 将已有的所有项目 变更到新的实例下
			orderProjectTenantExampleRelWiResources.updateTenantPackageIdByTenantId(Integer.parseInt(tenantPackage.getTenantPackageId()+""),
					Integer.parseInt(userId), new Date(), Integer.parseInt(tenantId));

		} else if ("1".equals(orderType)) {// 续费
			// 根据套餐id 查询已订购的实例
			if (null == param.get("tenantpackageId") || "".equals(param.get("tenantpackageId").toString())) {
				return "-2";
			}
			TenantPackage tenantPackage = tenantPackageWiResources
					.getTenantpackageByTenantpackageId(Integer.parseInt(param.get("tenantpackageId").toString()));// 不需要时间判断因为可能为已到期的续费
			if (null == tenantPackage) {// 没有
				return "3";// 未发现实例
			} else {
				if ("0".equals(tenantPackage.getDelFlag())) {// 未删除
					Date packageEndDate = tenantPackage.getPackageEndDate();// 结束时间
					Date nowDate = null;
					try {
						nowDate = DateUtils.parse(DateUtils.formatDate(new Date(), "yyyyMMdd"), "yyyyMMdd");
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					if (packageEndDate.after(nowDate)) {// 未到期
						// 修改此实例的开始时间结束时间
						tenantPackageWiResources.updateTenantPackageByTenantPackageId(Integer.parseInt(userId), new Date(), endDate,
								tenantPackage.getTenantPackageId());
						// param.put("nowTenantPackageId",
						// tenantPackage.getTenantPackageId());

					}
				}
			}
		} else if ("2".equals(orderType)) {// 升级
			System.out.println("正在升级。。。。。。。");
			// 根据租户信息 删除原先的 添加现有的 判断套餐等级 必须大于等于当前的
			String payType = param.get("payType").toString();
			// 根据套餐id 查询已订购的实例
			if (null == param.get("tenantpackageId") || "".equals(param.get("tenantpackageId").toString())) {
				return "-2";
			}
			TenantPackage tenantPackage = tenantPackageWiResources
					.getTenantpackageByTenantpackageId(Integer.parseInt(param.get("tenantpackageId").toString()));// 不需要时间判断因为可能为已到期的续费
			// 删除原先的 将时间置位失效
			tenantPackageWiResources.updateTenantPackageByTenantPackageId(new Date(),
					tenantPackage.getTenantPackageId());
			tenantPackageElementsRelResources.deleteByTenantPackageId(tenantPackage.getTenantPackageId());
			tenantId = tenantPackage.getTenantId()+"";
			Date nowDate = new Date();

			// 生成实体信息

			if (null == tenantPackage) {
				return "";
			}
			if (p.getPackageId().equals(tenantPackage.getPackageId())) {// 如果是同一个套餐
				if (tenantPackage.getPayType().equals("1")) {
					return "";
				}
				if (payType.equals("1")) {// 同套餐升级成年费
					if (!tenantPackage.getPayType().equals("0")) {// 只能从月费升级到年非
						return "";
					}
					tenantPackage = makeTenantPackage(p, param.get("isProbation").toString(),
							param.get("payType").toString(), startDate, endDate, tenantId, userId);
					// 实例信息入库
					em.persist(tenantPackage);
					// param.put("nowTenantPackageId",
					// tenantPackage.getTenantPackageId());
					// 将已有的所有项目 变更到新的实例下
					orderProjectTenantExampleRelWiResources.updateTenantPackageIdByTenantId(
							Integer.parseInt(tenantPackage.getTenantPackageId()+""), Integer.parseInt(userId+""), nowDate, Integer.parseInt(tenantId+""));
				}
			} else {// 不是同一个套餐
				if (Integer.parseInt(p.getPackageLevel()) <= Integer.parseInt(tenantPackage.getPackageLevel())) {
					return "";
				}

				tenantPackage = makeTenantPackage(p, param.get("isProbation").toString(),
						param.get("payType").toString(), startDate, endDate, tenantId, userId);
				// 实例信息入库
				em.persist(tenantPackage);
				// param.put("nowTenantPackageId",
				// tenantPackage.getTenantPackageId());
				// 将已有的所有项目 变更到新的实例下
				orderProjectTenantExampleRelWiResources.updateTenantPackageIdByTenantId(
						Integer.parseInt(tenantPackage.getTenantPackageId()+""), Integer.parseInt(userId+""), new Date(), Integer.parseInt(tenantId+""));
				System.out.println("升级完成。。。。。。。");
			}

		}

		return "0";// 成功
	}

	@Transactional
	public String orderedPackageWi(Map<String, Object> param) throws Exception {

		// 查找套餐
		Packages p = getPackageInfoByPackageId(param.get("packageId").toString(), param.get("appType").toString());
		if (null == p) {
			return "1";// 未找到套餐
		}
		String userId = param.get("userId") == null ? "05C686A9-14CC-4B90-9244-B46921999CBC"
				: param.get("userId").toString();
		String tenantIdOld = userService.isPrimary(userId, param.get("appType").toString()) == null
				? param.get("tenantId").toString() : userService.isPrimary(userId, param.get("appType").toString());// 租户id
		Date startDate = null;
		Date endDate = null;
		String endDatestr = "";
		try {
			startDate = DateUtils.parse(param.get("startDate").toString(), "yyyy-MM-dd");
			endDate = DateUtils.parse(param.get("endDate").toString(), "yyyy-MM-dd");
			endDatestr = DateUtils.formatDate(endDate, "yyyy-MM-dd");
			endDatestr = endDatestr + " 23:59:59";
			endDate = DateUtils.parse(endDatestr, "yyyy-MM-dd HH:mm:ss");
		} catch (ParseException e) {
			e.printStackTrace();
		}

		/***
		 * 判断此用户是否可以订购: 此应用是主用户不能订购（购买过此应用下的套餐）
		 * 
		 */
		TenantPackage tenantPackageold = null;
		TenantPackage tenantPackage = null;
		// 判断当前租户下是否存在有效的套餐实例
		List<TenantPackage> list = getPackageExampleListByTenantId(tenantIdOld, param.get("appType").toString());
		if (null != list && list.size() > 0) {
			tenantPackageold = list.get(0);
			if (tenantPackageold.getDelFlag().equals("0")) {
				return "2";// 此用户不能订购套餐
			}
		}
		// 生成实体信息
		tenantPackage = makeTenantPackage(p, param.get("isProbation").toString(), "0", startDate, endDate, tenantIdOld,
				userId);
		// 实例信息入库
		em.persist(tenantPackage);
		return "0";// 成功
	}

	@Transactional
	public String modifyPackageWi(Map<String, Object> param) throws Exception {
		Packages p = getPackageInfoByPackageId(param.get("packageId").toString(), param.get("appType").toString());// 新套餐
		Date startDate =null;
		// 获取当前的有效套餐
		String tenantId = "";
		Date endDate = null;
		String endDatestr = "";
		try {
			startDate =DateUtils.parse(param.get("startDate").toString(), "yyyy-MM-dd"); 
			tenantId = param.get("tenantId") == null ? UserUtils.getUser().getTenantId()
					: param.get("tenantId").toString();
			endDate = DateUtils.parse(param.get("endDate").toString(), "yyyy-MM-dd");
			endDatestr = DateUtils.formatDate(endDate, "yyyy-MM-dd");
			endDatestr = endDatestr + " 23:59:59";
			endDate = DateUtils.parse(endDatestr, "yyyy-MM-dd HH:mm:ss");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// 原套餐
		TenantPackage tenantPackage = tenantPackageWiResources
				.getTenantpackageByTenantpackageId(Integer.parseInt(param.get("oldTenantPackageId").toString()));
		if (param.get("modifyType").toString().equals("1")) {// 元套餐修改时间
			if (null != tenantPackage) {
				if (tenantPackage.getDelFlag().equals("0")) {
					tenantPackageWiResources.updateTenantPackageByTenantPackageId(endDate, "0",
							Integer.parseInt(param.get("userId").toString()), new Date(), Integer.parseInt(param.get("oldTenantPackageId").toString()));
					return "0";
				} else {
					return "3";// 原套餐发生改变
				}
			} else {
				return "3";
			}
		} else {
			if (null != tenantPackage) {
				if (tenantPackage.getDelFlag().equals("0")) {
					
					if(Integer.parseInt(p.getPackageLevel())>=Integer.parseInt(tenantPackage.getPackageLevel())){
						// 将原套餐置为失效
						tenantPackageWiResources.updateTenantPackageByTenantPackageId(new Date(), "1",
								Integer.parseInt(param.get("userId").toString()), new Date(), Integer.parseInt(param.get("oldTenantPackageId").toString()));
						// 生成实体信息
						TenantPackage newtenantPackage = makeTenantPackage(p, param.get("isProbation").toString(), "0", startDate, endDate, tenantId,
								param.get("userId").toString());
						// 实例信息入库
						em.persist(newtenantPackage);
						//迁移项目
						orderProjectTenantExampleRelWiResources.updateTenantPackageIdByTenantPackageId(newtenantPackage.getTenantPackageId(),
								Integer.parseInt(param.get("userId").toString()), new Date(), Integer.parseInt(param.get("oldTenantPackageId").toString()));
					}else {
						return "4";
					}
					
					return "0";
				} else {
					return "3";// 原套餐发生改变
				}
			} else {
				return "3";
			}
		}
	}

	/***
	 * 
	 * @param p
	 * @param isProbation
	 *            是否试用
	 * @param payType
	 *            付费 按月 还是按年
	 * @param startDate
	 *            开始时间
	 * @param endDate
	 *            结束时间
	 * @return
	 * @throws Exception 
	 */
	public TenantPackage makeTenantPackage(Packages p, String isProbation, String payType, Date startDate, Date endDate,
			String tenantId, String userId) throws Exception {

		TenantPackage tenantPackage = new TenantPackage();
		tenantPackage.setTenantPackageId(masterSequenceUtil.getCommonSeq());
		tenantPackage.setAppType(p.getAppType());
		tenantPackage.setCreateBy(StringUtils.toInteger(userId));
		tenantPackage.setCreateDate(new Date());
		tenantPackage.setDelFlag("0");
		tenantPackage.setUpdateBy(StringUtils.toInteger(userId));
		tenantPackage.setUpdateDate(new Date());
		tenantPackage.setIsProbation(isProbation);
		tenantPackage.setPackageEndDate(endDate);
		tenantPackage.setPackageStartDate(startDate);
		tenantPackage.setTenantId(Integer.parseInt(tenantId));
		tenantPackage.setPackageId(p.getPackageId());
		tenantPackage.setPayType(payType);
		tenantPackage.setPackageLevel(p.getPackageLevel());

		List<TenantPackageElementsRel> relList = new ArrayList<TenantPackageElementsRel>();
		for (PackageElementsRel r : p.getPackageElementsRelList()) {
			TenantPackageElementsRel rel = new TenantPackageElementsRel();
			rel.setExamEleRelId(masterSequenceUtil.getCommonSeq());
			rel.setCreateBy(StringUtils.toInteger(userId));
			rel.setCreateDate(new Date());
			rel.setDelFlag("0");
			rel.setUpdateBy(StringUtils.toInteger(userId));

			rel.setUpdateDate(new Date());
			rel.setElements(r.getElements());
			rel.setPackageId(p.getPackageId());
			rel.setTenantPackage(tenantPackage);
			rel.setUsedMin(r.getUsedMin());
			rel.setUsedMax(r.getUsedMax());
			rel.setValidFlag(r.getValidFlag());
			rel.setValidType(r.getValidType());
			rel.setElementValue(r.getElementValue());
			rel.setElementType(r.getElementType());
			rel.setUnit(r.getUnit());
			relList.add(rel);
		}

		tenantPackage.setTenantPackageElementsRelList(relList);

		return tenantPackage;
	}

	/**
	 * 查询套餐个人信息
	 * 
	 * @param packageId
	 * @return
	 */
	public PackagePersonalDto getPackagePersonalInfoByPackageId(String packageId) {
		// 所有的套餐信息 包含各个应用的
		Packages p = packageResources.getPackageInfoByPackageId(Integer.parseInt(packageId));
		PackagePersonalDto pd = new PackagePersonalDto();
		pd.setPackageId(p.getPackageId()+"");
		pd.setPackageName(p.getPackageName());
		pd.setPackageLevel(p.getPackageLevel());
		return pd;
	}

	/***
	 * 查询某个套餐规格
	 * 
	 * @param packageId
	 * @return
	 */
	public PackagesDto getPackageInfoByPackageId(String packageId) {
		// 所有的套餐信息 包含各个应用的
		Packages p = packageResources.getPackageInfoByPackageId(Integer.parseInt(packageId));
		if (null == p) {
			return null;
		}
		PackagesDto pd = new PackagesDto();
		pd.setPackageId(p.getPackageId()+"");
		pd.setPackageName(p.getPackageName());
		pd.setPackageOldPriceMonth(p.getPackageOldPriceMonth());
		pd.setPackageOldPriceYear(p.getPackageOldPriceYear());
		pd.setPackagePriceMontn(p.getPackagePriceMontn());
		pd.setPackagePriceYear(p.getPackagePriceYear());
		pd.setAppType(p.getAppType());
		pd.setDelFlag(p.getDelFlag());
		pd.setIsProbation(p.getIsProbation());
		List<String> engineType = new ArrayList<String>();// 监控范围
		if (p.getPackageElementsRelList().size() > 0) {
			pd.setScope(getScope(p.getPackageElementsRelList()));
		}
		return pd;
	}

	/***
	 * 查询所有套餐
	 * 
	 * @param isShow
	 *            是否显示订购
	 * @return
	 * @throws Exception
	 */
	public List<PackagesDto> getPackageList(String isShow) throws Exception {
		// 所有的套餐信息 包含各个应用的
		List<Packages> packagesList = packageResources.getPackageList();// 所有的套餐规格
		/**
		 * 此用户订购的实例信息
		 */
		// 将seo下的套餐，orm下的套餐 进行合并
		List<TenantPackage> tmpl = new ArrayList<TenantPackage>();
		/**
		 * 转成map packageId --
		 */
		Map<String, TenantPackage> tmplMap = new HashMap<String, TenantPackage>();
		int seolevel = 0;// seo等于
		int ormlevel = 0;// orm等级
		if ("1".equals(isShow)) {// 需要判断是不是订购
			String userId = UserUtils.getUser() == null ? "2" : UserUtils.getUser().getUserId();
			String seoTenantId = userService.isPrimary(userId, "0");// seo 应用
			String ormTenantId = userService.isPrimary(userId, "1");// Orm 应用
			List<TenantPackage> seoTenantPackageList = new ArrayList<TenantPackage>();// seo下的套餐
			List<TenantPackage> ormTenantPackageList = new ArrayList<TenantPackage>();// orm下的套餐

			if (null != seoTenantId) {
				seoTenantPackageList = getPackageExampleListByTenantId(seoTenantId);
			}
			if (null != ormTenantId) {
				ormTenantPackageList = getPackageExampleListByTenantId(ormTenantId);
			}
			if (seoTenantPackageList.size() > 0) {
				seolevel = Integer.parseInt(seoTenantPackageList.get(0).getPackageLevel());
			}

			if (ormTenantPackageList.size() > 0) {
				ormlevel = Integer.parseInt(ormTenantPackageList.get(0).getPackageLevel());
			}

			tmpl.addAll(seoTenantPackageList);
			tmpl.addAll(ormTenantPackageList);
			if (null != tmpl && tmpl.size() > 0) {
				for (TenantPackage t : tmpl) {

					tmplMap.put(t.getPackageId()+"", t);
				}
			}

		}
		List<PackagesDto> list = new ArrayList<>();
		if (null != packagesList && packagesList.size() > 0) {
			for (Packages p : packagesList) {
				PackagesDto pd = new PackagesDto();
				pd.setPackageId(p.getPackageId()+"");
				pd.setPackageName(p.getPackageName());
				pd.setPackageOldPriceMonth(p.getPackageOldPriceMonth());
				pd.setPackageOldPriceYear(p.getPackageOldPriceYear());
				pd.setPackagePriceMontn(p.getPackagePriceMontn());
				pd.setPackagePriceYear(p.getPackagePriceYear());
				pd.setAppType(p.getAppType());
				pd.setDelFlag(p.getDelFlag());

				List<String> engineType = new ArrayList<String>();// 监控范围
				if (p.getPackageElementsRelList().size() > 0) {
					pd.setScope(getScope(p.getPackageElementsRelList()));
				}
				pd.setMontnType("2");
				pd.setYearType("2");
				int level = Integer.parseInt(p.getPackageLevel());
				if (null != tmplMap.get(p.getPackageId())) {
					TenantPackage t = tmplMap.get(p.getPackageId());
					if (null != t) {
						if ("0".equals(t.getIsProbation())) {// 试用
							pd.setOrderType("1");
						} else if ("1".equals(t.getIsProbation())) {
							pd.setOrderType("2");
						} else {
							pd.setOrderType("0");
						}

						if ("0".equals(t.getPayType())) {// 月
							pd.setMontnType("1");// 显示续费
							pd.setYearType("2");// 显示升级
						} else if ("1".equals(t.getPayType())) {// 年
							pd.setMontnType("-1");// 不显示
							pd.setYearType("1");// 显示续费
						}
					}
					level = Integer.parseInt(tmplMap.get(p.getPackageId()).getPackageLevel());
				}

				if ("0".equals(p.getAppType())) {// seo

					if (seolevel < level) {// 不能升级
						pd.setMontnType("-1");
						pd.setYearType("-1");

					} else {

					}

				} else {
					if (ormlevel < level) {// 不能升级
						pd.setMontnType("-1");
						pd.setYearType("-1");
					} else {

					}
				}

				list.add(pd);
			}
		}
		return list;
	}

	public List<Map<String, Object>> getScope(List<PackageElementsRel> list) {

		if (null == list || list.size() <= 0) {
			return null;
		}
		List<Map<String, Object>> l = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> engineType = new ArrayList<Map<String, Object>>();// 监控范围
		for (PackageElementsRel rel : list) {
			if (rel.getElementType().equals("0")) {
				if ("1".equals(rel.getValidType())) {// 取最大值
					Map<String, Object> m = new HashMap<String, Object>();
					m.put("value", rel.getUsedMax());//
					m.put("elementName", rel.getElements().getElementName());
					m.put("elementId", rel.getElements().getElementId());// 12
					m.put("unit", rel.getUnit());// 单位
					m.put("type", "1");// 等于1 直接显示结果
					l.add(m);
				}
				if ("2".equals(rel.getValidType())) {// 布尔
					Map<String, Object> m = new HashMap<String, Object>();
					m.put("value", rel.getValidFlag());//
					m.put("elementName", rel.getElements().getElementName());
					m.put("elementId", rel.getElements().getElementId());// 12
					m.put("unit", "");// 单位
					m.put("type", "2");// 显示 布尔
					l.add(m);

				}
				if ("0".equals(rel.getValidType())) {// 文字
					Map<String, Object> m = new HashMap<String, Object>();
					m.put("value", rel.getElementValue());//
					m.put("elementName", rel.getElements().getElementName());
					m.put("elementId", rel.getElements().getElementId());// 12
					m.put("unit", "");// 单位
					m.put("type", "0");
					l.add(m);
				}
			} else if (rel.getElementType().equals("1")) {// 监控
				Map<String, Object> m = new HashMap<String, Object>();
				m.put("elementName", rel.getElements().getElementName());
				m.put("elementId", rel.getElements().getElementId());// 12
				engineType.add(m);
			}

		}
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("value", engineType);// 12
		m.put("elementName", "监控范围");
		m.put("elementId", "##engineType");// 12
		m.put("type", "1");// 监控范围
		l.add(m);
		return l;
	}

	/*
	 * **********上方是应用服务私有方法不对别的模块和别的应用直接提供提供***********
	 */

	/*
	 * ********************此处之下是service服务层调用的公共方法，若要修改，请慎重操作********************
	 * *
	 */
	@Override
	public TenantPackage getTenantPackageInfo(String tenantId) {
		Date nowDate = new Date();
		TenantPackage t = tenantPackageWiResources.getTenantPackageInfo(Integer.parseInt(tenantId), nowDate);
		return t;
	}

	/***
	 * wi 根据租户查询所有的套餐实例的规格
	 * 
	 * @param id
	 * @return
	 */
	public List<TenantPackage> getPackageExampleListByTenantId(@Param("tenantId") String tenantId) {
		List<TenantPackage> tenantPackageList = tenantPackageWiResources.getPackageExampleListByTenantId(Integer.parseInt(tenantId),
				new Date());
		if (null != tenantPackageList && tenantPackageList.size() > 0) {
			for (TenantPackage t : tenantPackageList) {
				List<TenantPackageElementsRel> list = new ArrayList<TenantPackageElementsRel>();
				if (null != t) {
					for (TenantPackageElementsRel rel : t.getTenantPackageElementsRelList()) {
						if ("0".equals(rel.getDelFlag())) {
							list.add(rel);
						}
					}
				}
				t.setTenantPackageElementsRelList(list);
			}
		}

		return tenantPackageList;

	}

	@Override
	public Packages getPackageInfoByPackageId(String packageId, String appType) {
		Packages p = packageResources.getPackageInfoByPackageId(Integer.parseInt(packageId), appType);
		return p;
	}

	@Override
	public void savePackageInstance(Packages pack, String tenantId) throws Exception {
		String userId = UserUtils.getUser().getUserId();
		TenantPackage tenantPackage = makeTenantPackage(pack, "1", pack.getAppType(), new Date(),
				DateUtils.addDays(new Date(), 30), tenantId, userId);
		// 实例信息入库
		em.persist(tenantPackage);
	}

	@Override
	public List<PackageDTO> getPackageList() throws Exception {
		List<PackageDTO> dtoList = new ArrayList<PackageDTO>();
		List<Packages> packageList = packageResources.findAll();
		for (Packages packages : packageList) {
			PackageDTO packageDTO = new PackageDTO();
			if (packages != null) {
				packageDTO.setPackageId(packages.getPackageId() + "");
				packageDTO.setPackageName(packages.getPackageName());
				List<PackageElementsRel> rels = packages.getPackageElementsRelList();
				List<Map<String, Object>> specifications = this.getPackageSpecifications(rels);
				PackageScopeDto scopeDto = null;
				List<PackageScopeDto> scope = new ArrayList<PackageScopeDto>();
				for (Map<String, Object> map : specifications) {
					scopeDto = new PackageScopeDto();
					if (null != map && map.containsKey("elementId")) {
						scopeDto.setElementId(StringUtils.toInteger(map.get("elementId")));
						scopeDto.setElementName(map.get("elementName") + "");
						scopeDto.setType(map.get("type") + "");
						scopeDto.setUnit(map.get("unit") + "");
						scopeDto.setValue(map.get("value"));
						scope.add(scopeDto);
					}
					packageDTO.setScope(scope);
				}
				dtoList.add(packageDTO);
			}
		}
		return dtoList;
	}

	@Override
	public List<Map<String, Object>> getPackageSpecifications(List<PackageElementsRel> rels) throws Exception {
		if (ListUntils.isNull(rels)) {
			throw new BusinessException(prop.getProcessStatus("PACKAGE_ELE_REL_NULL"));
		}
		List<Map<String, Object>> l = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> engineType = new ArrayList<Map<String, Object>>();// 监控范围
		for (PackageElementsRel rel : rels) {
			if (rel.getDelFlag().equals("0")) {
				if (rel.getElementType().equals("0")) {
					if ("1".equals(rel.getValidType())) {// 取最大值
						Map<String, Object> m = new HashMap<String, Object>();
						m.put("value", rel.getUsedMax());//
						m.put("elementName", rel.getElements().getElementName());
						m.put("elementId", rel.getElements().getElementId());// 12
						m.put("unit", rel.getUnit());// 单位
						m.put("type", "1");// 等于1 直接显示结果
						l.add(m);
					}
					if ("2".equals(rel.getValidType())) {// 布尔
						Map<String, Object> m = new HashMap<String, Object>();
						m.put("value", rel.getValidFlag());//
						m.put("elementName", rel.getElements().getElementName());
						m.put("elementId", rel.getElements().getElementId());// 12
						m.put("unit", "");// 单位
						m.put("type", "2");// 显示 布尔
						l.add(m);

					}
					if ("0".equals(rel.getValidType())) {// 文字
						Map<String, Object> m = new HashMap<String, Object>();
						m.put("value", rel.getElementValue());//
						m.put("elementName", rel.getElements().getElementName());
						m.put("elementId", rel.getElements().getElementId());// 12
						m.put("unit", "");// 单位
						m.put("type", "0");
						l.add(m);
					}
				} else if (rel.getElementType().equals("1")) {// 监控
					Map<String, Object> m = new HashMap<String, Object>();
					m.put("elementName", rel.getElements().getElementName());
					m.put("elementId", rel.getElements().getElementId());// 12
					engineType.add(m);
				}
			}
		}
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("value", engineType);// 12
		m.put("elementName", "监控范围");
		m.put("elementId", "##engineType");// 12
		m.put("type", "0");// 监控范围
		l.add(m);
		if (ListUntils.isNull(l)) {
			throw new BusinessException(prop.getProcessStatus("PACKAGE_ELE_REL_NULL"));
		}
		return l;
	}

	@Override
	public List<ManageTenantPackageDto> getTenantPackageList(List<String> tenantIds) throws Exception {
		List<Integer> idlist = new ArrayList<Integer>();
		for(String id:tenantIds){
			idlist.add(Integer.parseInt(id));
		}
		List<ManageTenantPackageDto> list = tenantPackageWiResources.getTenantPackageList(idlist, new Date());
		if (!ListUntils.isNull(list)) {
			List<PackageDTO> PackageDTOs = this.getPackageList();
			if (!ListUntils.isNull(PackageDTOs)) {
				for (ManageTenantPackageDto dto : list) {
					for (PackageDTO packageDTO : PackageDTOs) {
						if (dto.getPackageId().equals(packageDTO.getPackageId())) {
							dto.setPackageName(packageDTO.getPackageName());
						}
					}
				}
			} else {
				return null;
			}
		} else {
			return null;
		}
		return list;
	}

	@Override
	public void updateTenantPackagePackageEndDate(String tenantPackageId, String tenantId, Date packageEndDate,
			String userId) throws Exception {
		TenantPackage tenantPackage = tenantPackageWiResources.getTenantpackageByTenantpackageId(Integer.parseInt(tenantPackageId));
		if (null != tenantPackage) {
			if (tenantPackage.getDelFlag().equals("1")) {// 已删除
				throw new BusinessException(prop.getProcessStatus("OLDPACKAGE_NOT_EXIST"));
			}
		} else {
			throw new BusinessException(prop.getProcessStatus("OLDPACKAGE_NOT_EXIST"));
		}

		tenantPackageWiResources.updateTenantPackageByTenantPackageId(packageEndDate, "0", Integer.parseInt(userId), new Date(),
				Integer.parseInt(tenantPackageId));
	}

}
