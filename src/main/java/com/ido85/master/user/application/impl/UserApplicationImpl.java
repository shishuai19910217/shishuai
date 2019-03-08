package com.ido85.master.user.application.impl;

import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;
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
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.apache.shiro.SecurityUtils;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;

import com.ido85.config.properties.FileMapingProperties;
import com.ido85.config.properties.WiConstantsProperties;
import com.ido85.frame.common.jdbc.ScriptRunner;
import com.ido85.frame.common.utils.StringUtils;
import com.ido85.frame.multitenant.SchemaBasedMultiTenantConnectionProvider;
import com.ido85.frame.spring.InstanceFactory;
import com.ido85.frame.web.encrypt.EncryptService;
import com.ido85.frame.web.rest.security.StatelessPrincipal;
import com.ido85.master.user.application.UserApplication;
import com.ido85.master.user.domain.EmailLog;
import com.ido85.master.user.domain.Profession;
import com.ido85.master.user.domain.ProfessionInfo;
import com.ido85.master.user.domain.Tenant;
import com.ido85.master.user.domain.TenantApp;
import com.ido85.master.user.domain.TenantRelation;
import com.ido85.master.user.domain.User;
import com.ido85.master.user.domain.Vocation;
import com.ido85.master.user.dto.InUserInfoDto;
import com.ido85.master.user.resources.EmailLogResources;
import com.ido85.master.user.resources.ProfessionInfoResources;
import com.ido85.master.user.resources.ProfessionResources;
import com.ido85.master.user.resources.TenantAppResources;
import com.ido85.master.user.resources.TenantRelationResources;
import com.ido85.master.user.resources.TenantResources;
import com.ido85.master.user.resources.UserResources;
import com.ido85.master.user.resources.VocationResources;
import com.ido85.master.user.util.UserUtils;
import com.ido85.seo.common.BaseApplication;
import com.ido85.services.packages.PackageApi;

@Named
public class UserApplicationImpl extends BaseApplication implements UserApplication {
	@Inject
	private UserResources userResources;
	@Inject
	private TenantRelationResources tenantRelRes;
	@Inject
	private TenantAppResources tenantAppRes;
	@Inject
	private TenantResources tenantResources;
	@Inject
	private ProfessionInfoResources professionInfoRes;
	@Inject
	private VocationResources vocationRes;
	@Inject
	private ProfessionResources professionRes;
	@PersistenceContext(unitName="system")
	private EntityManager em;
	@Inject
	private PackageApi packageService;
	@Inject
	private WiConstantsProperties constants;
	@Inject
	private EmailLogResources emailResources;
	@Inject
	private SchemaBasedMultiTenantConnectionProvider schemaBasedMultiTenantConnectionProvider;
	@Inject
	private FileMapingProperties fileMaping;
	public User getUserInfoById(String userId){
		return null;
//		return userResources.getUserInfo(userId);
	}

	@Override
	@Transactional
	public int register(HttpServletRequest request, User user) {
		// TODO 试用注册插入套餐实例表，同时创建租户表、用户租户关系、租户应用关系表、套餐实例表、套餐实例与元素的关系表
		int res = -1;
		if(null != user){
			if(null != user.getUsername() && !"".equals(user.getUsername())
					&& !checkUsername(user.getUsername())){
				if(null != user.getEmail() && !"".equals(user.getEmail())
					&& checkEmail(user.getEmail()) <= 0){
					if(null != user.getMobile() && !"".equals(user.getMobile())
						&& !checkMobile(user.getMobile())){
						user.setPassword(InstanceFactory.getInstance(EncryptService.class).encryptPassword(user.getPassword(), user.getSalt()));
						user.setDelTlag("0");
						user.setCreateDate(new Date());
						user.setPhoto(constants.getValue("defaultPhoto") + "");
//						user = userResources.save(user);
						res = 0;
					}else
						res = 1;//用户手机已经存在
				}else
					res = 2;//用户email已经存在
			}else
				res = 3;//用户名已经存在
		}
		return res;
	}

	@Override
	public boolean checkUsername(String username) {
		boolean res = false;
//		User user = userResources.checkUsername(username);
//		if(null != user && !"".equals(user) && username.equals(user.getUsername())){
//			res = true;
//		}
		return res;
	}

	@Override
	public int checkEmail(String email) {
		int res = 0;
//		User user = userResources.checkEmail(email);
//		if(null != user && !"".equals(user) && email.equals(user.getEmail())){
//			res = 1;
//			if("1".equals(user.getEmailType()))
//				res = 2;
//		}
		return res;
	}

	@Override
	public boolean checkMobile(String mobile) {
		boolean res = false;
//		User user = userResources.checkMobile(mobile);
//		if(null != user && !"".equals(user) && mobile.equals(user.getMobile())){
//			res = true;
//		}
		return res;
	}


	@Override
	public ProfessionInfo getProfessionInfoByUserId(String userId) {
		// 根据用户id获取用户的行业信息
		List<ProfessionInfo> list = professionInfoRes.getProfessionInfo(userId);
		if(null != list && list.size() > 0){
			return list.get(0);
		}
		return null;
	}
	@Override
	public Profession getProfessionById(String professionId) {
		return professionRes.getProfessionInfoById(professionId);
	}

	@Override
	public Vocation getVocationInfoById(String vocationId) {
		return vocationRes.getVocationInfoById(vocationId);
	}
	/***
	 * 生成租户信息
	 * @param userId
	 * @param appType
	 * @return 返回租户id
	 */
	@Transactional
	public String saveTenant(String userId ,String appType){
		User user = getUserInfoById(userId);
		Tenant tenant = null;
		int flag = 0;
		String tenantId = "";
		
		if(null != user && !"".equals(user) && !StringUtils.isNull(appType)){
			List<TenantRelation> relations = getTenantRelationInfosByUserId(userId);
			if(null != relations){
				TenantRelation tempRelation = null;
				for (int i = 0, len = relations.size(); i < len; i++) {
					tempRelation = relations.get(i);
					if(appType.equals(tempRelation.getAppType()) && "1".equals(tempRelation.getRelationFlag())){
						return null;
					}else if(!appType.equals(tempRelation.getAppType()) && "1".equals(tempRelation.getRelationFlag())){
						flag = 1;
						tenantId = tempRelation.getTenantId();
					}
				}
			}
			
			if(flag == 0){
				tenant = new Tenant();
				tenant.setDelFalg("0");
				tenant.setTenantName(user.getUsername());
				tenant = saveTenant(tenant);
				
				tenantId = tenant.getTenantId();
				tenant = null;
			}
			
			TenantRelation tenantRelation = new TenantRelation();
			tenantRelation.setDelFlag("0");
			tenantRelation.setTenantId(tenantId);
			tenantRelation.setRelationFlag("1");
			tenantRelation.setUserId(user.getUserId());
			tenantRelation.setAppType(appType);
			
			saveTenantRel(tenantRelation);
			
			TenantApp tenantApp = new TenantApp();
			tenantApp.setAppType(appType);
			tenantApp.setDelFlag("0");
			tenantApp.setTenantId(tenantId);
			
			saveTenantApp(tenantApp);
		}
		
		return tenantId;
	}
	

	@Override
	@Transactional
	public boolean trialPackage(String userId, String tenantId, String packageId, String appType) {
		boolean res = false;
		try {
			if(null == tenantId || "".equals(tenantId)){
				tenantId = this.saveTenant(UserUtils.getUser().getUserId(), appType);
				StatelessPrincipal principal = (StatelessPrincipal)SecurityUtils.getSubject().getPrincipal();
				if (null!=principal) {
					principal.setTenantID(tenantId);
				}
			}
			res = packageService.savePackageInstance(tenantId,packageId);
			if (null == tenantId || "".equals(tenantId) || !res) {
				throw new RuntimeException();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return res;
		}
		return res;
	}
	
	@Override
	public User getUserInfoByMobile(String mobile) {
		return null;
//		return userResources.checkMobile(mobile);
	}
	@Override
	public User getUserInfoByEmail(String email) {
		return null;
//		return userResources.checkEmail(email);
	}

	@Override
	@Transactional
	public String saveEmailLog(User user, String emailType) throws Exception {
		String res = null;
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("email", user.getEmail());
		params.put("emailType", emailType);
		params.put("flag", "0");
		params.put("dateFlag", "1");
		
		EmailLog emailLog = new EmailLog();
		emailLog.setDelFlag("1");
		
		//将原来有效的所有邮箱链接置为失效
		if(this.updateEmailLogInfo(emailLog, params)){
			emailLog.setCreateDate(new Date());
			emailLog.setDelFlag("0");
			emailLog.setEmail(user.getEmail());
			emailLog.setEmailType(emailType);
			emailLog.setFlag("0");
			emailLog.setDelFlag("0");
			emailLog.setUserId(user.getUserId());
			emailLog.setUsername(user.getUsername());
			entity.persist(emailLog);
			res = emailLog.getEmailLogId();;
		}
		
		return res;
	}
	
	@Override
	@Transactional
	public boolean updateEmailLogInfo(EmailLog value, Map<String, Object> params) throws Exception {
		boolean res = false;
		if(null == value || "".equals(value) || null == params ||
				"".equals(params) || params.size() <= 0){
			return res;
		}
		
		StringBuffer sql = new StringBuffer(" update EmailLog t SET t.rsvFiled1 = '' ");
		
		if(null != value.getDelFlag() && !"".equals(value.getDelFlag())){
			sql.append(" ,t.delFlag = :delFlag ");
			params.put("delFlag", value.getDelFlag());
		}
		if(null != value.getFlag() && !"".equals(value.getFlag())){
			sql.append(" ,t.flag = :vflag ");
			params.put("vflag", value.getFlag());
		}
		
		sql.append(" where 1 = 1 ");
		if(null != params && params.containsKey("uuid")){
			sql.append(" and t.emailLogId = :uuid ");
		}
		if(null != params && params.containsKey("email")){
			sql.append(" and t.email = :email ");
		}
		if(null != params && params.containsKey("userId")){
			sql.append(" and t.userId = :userId ");
		}
		if(null != params && params.containsKey("emailType")){
			sql.append(" and t.emailType = :emailType ");
		}
		if(null != params && params.containsKey("dateFlag")){
			params.remove("dateFlag");
			sql.append(" and t.createDate <= :createDate ");
			params.put("createDate", new Date());
		}
		if(null != params && params.containsKey("flag")){
			sql.append(" and t.flag = :flag ");
		}
		
		return this.updateEntitySys(sql.toString(), params);
	}
	@Override
	public EmailLog getEmailLogInfo(String email, String emailLogId)throws Exception{
		List<EmailLog> list = emailResources.getEmailLogInfo(email, emailLogId);
		if(null != list && list.size() > 0){
			return list.get(0);
		}
		return null;
	}
	@Override
	@Transactional
	public boolean updatePwdByEmail(InUserInfoDto userInfo, String emailLogId)throws Exception{
		boolean res = false;
		if(this.updateUserInfo(userInfo)){
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("uuid", emailLogId);
			
			EmailLog emailLog = new EmailLog();
			emailLog.setFlag("1");
			
			//将原来有效的所有邮箱链接置为失效
			if(this.updateEmailLogInfo(emailLog, params)){
				res = true;
			}
		}
		return res;
	}
	@Override
	@Transactional
	public boolean verfyEmail(InUserInfoDto userInfo, String emailLogId)throws Exception{
		boolean res = false;
		if(this.updateUserInfo(userInfo)){
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("uuid", emailLogId);
			
			EmailLog emailLog = new EmailLog();
			emailLog.setFlag("1");
			
			//将原来有效的所有邮箱链接置为失效
			if(this.updateEmailLogInfo(emailLog, params)){
				res = true;
			}
		}
		return res;
	}
	/**
	 * **********************此处之下是service调用的公共方法***********************
	 */
	@Override
	public List<TenantRelation> getTenantRelationInfosByUserId(String userId) {
		return tenantRelRes.getTenantRelationInfoByUserId(userId);
	}
	

	@Override
	public Tenant saveTenant(Tenant tenant) {
		return tenantResources.save(tenant);
	}

	@Override
	public TenantApp saveTenantApp(TenantApp tenantApp) {
		return tenantAppRes.save(tenantApp);
	}

	@Override
	public TenantRelation saveTenantRel(TenantRelation tenantRel) {
		return tenantRelRes.save(tenantRel);
	}

	@Override
	public TenantRelation getTenantRelationInfo(String id) {
		return tenantRelRes.getTenantRelationInfo(id);
	}

	@Override
	@Transactional
	public boolean updateUserInfo(InUserInfoDto in) throws Exception {
		boolean res = false;
		if(null == in || "".equals(in)){
			return res;
		}
		
		/**
		 * 修改用户信息
		 */
		StringBuffer sql = new StringBuffer(" update User t SET t.updateDate = :updateDate ");
		
		Map<String, Object> map = new HashMap<String, Object>();
		if(null != in.getEmail() && !"".equals(in.getEmail())){
			sql.append(" ,t.email = :email ");
			map.put("email", in.getEmail());
		}
		if(null != in.getMobile() && !"".equals(in.getMobile())){
			sql.append(" ,t.mobile = :mobile ");
			map.put("mobile", in.getMobile());
		}
		if(null != in.getName() && !"".equals(in.getName())){
			sql.append(" ,t.name = :name ");
			map.put("name", in.getName());
		}
		if(null != in.getPassword() && !"".equals(in.getPassword())){
			sql.append(" ,t.password = :password ");
			map.put("password", InstanceFactory.getInstance(EncryptService.class).encryptPassword(in.getPassword(), in.getSalt()));
		}
		if(null != in.getPhoto() && !"".equals(in.getPhoto())){
			sql.append(" ,t.photo = :photo ");
			map.put("photo", in.getPhoto());
		}
		if(null != in.getUsername() && !"".equals(in.getUsername())){
			sql.append(" ,t.username = :username ");
			map.put("username", in.getUsername());
		}
		if(null != in.getEmailType() && !"".equals(in.getEmailType())){
			sql.append(" ,t.emailType = :emailType ");
			map.put("emailType", in.getEmailType());
		}
		sql.append("WHERE t.userId = :userId and t.delFlag = '0' ");
		
		Query query = entity.createQuery(sql.toString(), User.class);
		query.setParameter("updateDate", new Date());
		query.setParameter("userId", in.getUserId());
		
		if(map.size() > 0){
			Set<String> set = map.keySet();
				for (String str : set) {
					query.setParameter(str, map.get(str));
				}
		}
		res = query.executeUpdate() > 0?true:false;
		
		return res;
	}

	@Override
	public User getUserInfoByAccount(String account) {
//		return userResources.getUserByAccount(account);
		return null;
	}

	@Override
	public void createTenantDB(Integer tenantId) throws Exception {
		Connection conn = schemaBasedMultiTenantConnectionProvider.getConnection("seo_nodes_"+tenantId);
		ScriptRunner run = new ScriptRunner(conn, false, true);
		FileReader reader = new FileReader(fileMaping.getSqlPath()+"init_table.sql");
		run.runScript(reader);
		if(reader != null){
			reader.close();
		}
		if(conn != null){
			conn.close();
		}
	}


	

	
	
}
