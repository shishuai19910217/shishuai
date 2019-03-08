package com.ido85.master.user.resources;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ido85.master.user.domain.EmailLog;



public interface EmailLogResources extends JpaRepository<EmailLog, String> {
	/**
	 * 根据用户的邮箱和日志id来获取日志信息
	 * @param email
	 * @param emailLogId
	 * @return
	 */
	@Query("SELECT t FROM EmailLog t where t.emailLogId = :emailLogId and t.email = :email and t.delFlag = '0'")
	List<EmailLog> getEmailLogInfo(@Param("email")String email, @Param("emailLogId")String emailLogId);
	
	
	
	
	
}
