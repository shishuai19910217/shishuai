package com.ido85.frame.web.rest.security;


import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.stereotype.Service;

/**
 * 
 */
@Service
public class StatelessRealm extends AuthorizingRealm {
	
	
    @Override
    public boolean supports(AuthenticationToken token) {
        //仅支持StatelessToken类型的Token
        return token instanceof StatelessToken;
    }
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        //根据用户名查找角色，请根据需求实现
    	StatelessPrincipal principal = (StatelessPrincipal)principals.oneByType(StatelessPrincipal.class);
    	if(principal == null) return null;
    	return null;
    	
//    	User user = getSystemService().getUser(principal.getAccessKeyID());
//		if (user != null) {
//			SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
//			List<Menu> list = UserUtils.getRestMenuList(user);
//			for (Menu menu : list){
//				if (StringUtils.isNotBlank(menu.getPermission())){
//					// 添加基于Permission的权限信息
//					for (String permission : StringUtils.split(menu.getPermission(),",")){
//						info.addStringPermission(permission);
//					}
//				}
//			}
//			// 添加用户权限
////			info.addStringPermission("user");
//			// 添加用户角色信息
//			for (Role role : user.getRoleList()){
//				info.addRole(role.getEnname());
//			}
//			// 更新登录IP和时间
//			getSystemService().updateUserLoginInfo(user, Servlets.getRequest());
//			// 记录登录日志
//			LogUtils.saveLog(Servlets.getRequest(), "rest登录");
//			return info;
//		}else{
//			return null;
//		}
//        SimpleAuthorizationInfo authorizationInfo =  new SimpleAuthorizationInfo();
//        authorizationInfo.addRole("admin");
//        return authorizationInfo;
    }
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
//        StatelessToken statelessToken = (StatelessToken) token;
//        String keyId = statelessToken.getAccessKeyID();
        //在服务器端生成客户端参数消息摘要        
        /*String serverDigest = Encodes.encodeUrlSafeBase64(Cryptos.hmacSha1(statelessToken.getStrToSign().getBytes(Charset.forName("UTF-8")), getSecurityKey(statelessToken.getAccessKeyID()))).replaceAll(" ", "");*/
        
        
//        String serverDigest = HmacSHA256Utils.digest( getSecurityKey(statelessToken.getAccessKeyID()), statelessToken.getStrToSign());
//		//然后进行客户端消息摘要和服务器端消息摘要的匹配
//        return new SimpleAuthenticationInfo(
//                new StatelessPrincipal(keyId),
//                serverDigest,
//                getName());
        return null;
    }
    
    @Override
    public String getName() {
    	return "restRealm";
    }
}
