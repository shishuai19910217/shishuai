package com.ido85.master.user.resources;

import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ido85.master.user.domain.Tenant;



public interface UserResources extends JpaRepository<Tenant, String> {
//	/**
//	 * 根据用户id获取用户信息
//	 * @param userId
//	 * @return
//	 */
//	@Query("SELECT t FROM User t where t.userId = :userId")
//	User getUserInfo(@Param("userId")String id);
//	
//	/**
//	 * 根据accesskeyid获取用户信息
//	 * @param accessKeyID
//	 * @return
//	 */
//	@Query("SELECT u from User u where u.accessKeyID = :keyId")
//	User getUserByUserAccessID(@Param("keyId") String accessKeyID);
//	
//	/**
//	 * 更新用户	
//	 * @param accessKeyID
//	 * @param deviceID
//	 * @return
//	 */
//	@Transactional
//	@Modifying
//	@Query("update User u set u.loginDate = :loginDate where u.accessKeyID = :keyID")
//	int updateUserLoginDate(@Param("keyID")String accessKeyID, @Param("loginDate")Date loginDate);
//	
//	/**
//	 * 根据用户名获取用户信息
//	 * @param account
//	 * @return
//	 */
//	@Query("select u from User u where u.username = :account or u.email = :account or u.mobile = :account")
//	User getUserByAccount(@Param("account") String account);
//	
//	/**
//	 * 更新当前用户securitykey
//	 * @param accessKeyID
//	 * @param securityKey
//	 * @return
//	 */
//	@Transactional
//	@Modifying
//	@Query("update User u set u.securityKey = :securityKey where u.accessKeyID = :keyID")
//	int updateCurrentUserSecurityKey(@Param("keyID")String accessKeyID, @Param("securityKey")String securityKey);
//	
//	/**
//	 * 查询用户名是否存在
//	 * @param username
//	 * @return
//	 */
//	@Query("select u from User u where u.username = :username and u.delFlag = '0'")
//	User checkUsername(@Param("username")String username);
//	
//	/**
//	 * 查询用户手机号是否存在
//	 * @param mobile
//	 * @return
//	 */
//	@Query("select u from User u where u.mobile = :mobile and u.delFlag = '0'")
//	User checkMobile(@Param("mobile")String mobile);
//	
//	/**
//	 * 查询用户邮箱是否存在
//	 * @param email
//	 * @return
//	 */
//	@Query("select u from User u where u.email = :email and u.delFlag = '0'")
//	User checkEmail(@Param("email")String email);
//	
//	/**
//	 * 新增用户
//	 * @param user
//	 * @return
//	 */
//	@Transactional
//	@Modifying
//	@Query("select u from User u where u.email = :user")
//	int insertUser(@Param("user")User user);
//	
//	
	
	
}
