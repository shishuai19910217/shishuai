package com.ido85.master.user.application;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.ido85.master.user.domain.EmailLog;
import com.ido85.master.user.domain.Profession;
import com.ido85.master.user.domain.ProfessionInfo;
import com.ido85.master.user.domain.Tenant;
import com.ido85.master.user.domain.TenantApp;
import com.ido85.master.user.domain.TenantRelation;
import com.ido85.master.user.domain.User;
import com.ido85.master.user.domain.Vocation;
import com.ido85.master.user.dto.InUserInfoDto;


public interface UserApplication {
	/**
	 * 根据用户id，获取用户信息
	 * @param userId
	 * @return
	 */
	User getUserInfoById(String userId);
	/**
	 * 根据用户username或mobile或email，获取用户信息
	 * @param account
	 * @return
	 */
	User getUserInfoByAccount(String account);
	/**
	 * 用户普通注册
	 * @param request
	 * @param user
	 * @return
	 */
	int register(HttpServletRequest request, User user);
	/**
	 * 校验用户名是否存在，存在返回true
	 * @param request
	 * @param username
	 * @return
	 */
	boolean checkUsername(String username);
	/**
	 * 校验用户邮箱是否存在，存在返回true
	 * @param email
	 * @return
	 */
	int checkEmail(String email);
	/**
	 * 校验用户手机号码是否存在，存在返回true
	 * @param mobile
	 * @return
	 */
	boolean checkMobile(String mobile);
	/**
	 * 根据用户手机号码获取用户信息
	 * @param mobile
	 * @return
	 */
	User getUserInfoByMobile(String mobile);
	/**
	 * 根据用户邮箱获取用户信息
	 * @param email
	 * @return
	 */
	User getUserInfoByEmail(String email);
	/**
	 * 根据用户id获取用户和租户的所有关系数据
	 * @param userId
	 * @return
	 */
	List<TenantRelation> getTenantRelationInfosByUserId(String userId);
	
	/**
	 * 根据行业id获取行业信息
	 * @param userId
	 * @return
	 */
	Profession getProfessionById(String professionId);
	/**
	 * 根据用户id获取用户的行业信息
	 * @param userId
	 * @return
	 */
	ProfessionInfo getProfessionInfoByUserId(String userId);
	/**
	 * 根据职业id获取职业信息
	 * @param userId
	 * @return
	 */
	Vocation getVocationInfoById(String vocationId);
	/**
	 * 修改用户信息，传入哪些字段就修改那些字段
	 * @param in
	 * @return
	 */
	boolean updateUserInfo(InUserInfoDto in) throws Exception;
	/***
	 * 生成租户信息
	 * @param userId
	 * @param appType
	 * @return 返回租户id
	 */
	String saveTenant(String userId ,String appType);
	
	/**
	 * 用户登录后对套餐进行试用
	 * @param userId
	 * @param tenantId
	 * @param packageId
	 * @param appType
	 * @return
	 */
	public boolean trialPackage(String userId, String tenantId, String packageId, String appType);
	/**
	 * 记录发送邮件信息
	 * @param user
	 * @param emailType
	 * @return
	 */
	String saveEmailLog(User user, String emailType) throws Exception;
	/**
	 * 修改发送邮件信息记录
	 * @param emailLog
	 * @return
	 */
	boolean updateEmailLogInfo(EmailLog value, Map<String, Object> params)throws Exception;
	/**
	 * 校验邮箱链接
	 * @param email
	 * @param emailLogId
	 * @return
	 * @throws Exception
	 */
	EmailLog getEmailLogInfo(String email, String emailLogId)throws Exception;
	/**
	 * 修改用户密码,并且将邮箱链接置为已经失效
	 * @param userInfo
	 * @param emailLogId
	 * @return
	 * @throws Exception
	 */
	boolean updatePwdByEmail(InUserInfoDto userInfo, String emailLogId)throws Exception;
	/**
	 * 修改用户邮箱验证状态，并且将邮箱链接置为已经失效
	 * @param userInfo
	 * @param emailLogId
	 * @return
	 * @throws Exception
	 */
	boolean verfyEmail(InUserInfoDto userInfo, String emailLogId)throws Exception;
	/**
	 * **********************此处之下是service调用的公共方法***********************
	 */
	
	/**
	 * 保存租户信息
	 * @param tenant
	 */
	Tenant saveTenant(Tenant tenant);
	
	/**
	 * 保存租户和应用关系表信息
	 * @param tenantApp
	 */
	TenantApp saveTenantApp(TenantApp tenantApp);
	
	/**
	 * 保存租户和用户关系表信息
	 * @param tenantRel
	 */
	TenantRelation saveTenantRel(TenantRelation tenantRel);
	/**
	 * 根据用户和租户关系id，获取用户和租户关系详细信息
	 * @param id
	 * @return
	 */
	TenantRelation getTenantRelationInfo(String id);
	/**
	 * 创建租户库
	 * @param tenantId 
	 * @throws Exception 
	 **/
	void createTenantDB(Integer tenantId) throws Exception;
	
}
