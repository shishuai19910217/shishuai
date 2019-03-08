package com.ido85.log.model;

import com.ido85.frame.spring.InstanceFactory;
import com.ido85.frame.web.rest.utils.RestUserUtils;
import com.ido85.log.BusinessLog;
import com.ido85.log.resources.DefaultLogRepository;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import static com.ido85.log.ContextKeyConstant.*;

/**
 * User: zjzhai Date: 12/12/13 Time: 11:23 AM
 */
@javax.persistence.Entity
@DiscriminatorValue(value = "DEFAULT")
@Table(name = "KL_BUSSINESSLOGS")
public class DefaultBusinessLog extends AbstractBusinessLog {

	private static final long serialVersionUID = -6898675233659096041L;

	@Column(name = "USERNAME")
	private String user;

	@Column(name = "IP")
	private String ip;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "RECORD_TIME")
	private Date time;

	@Transient
	private Map<String, Object> context;

	public synchronized static DefaultBusinessLog createBy(BusinessLog businessLog) {
		DefaultBusinessLog myBusinessLog = new DefaultBusinessLog();
		Map<String, Object> context = businessLog.getContext();

		if (context.get(BUSINESS_OPERATION_USER) != null) {
			myBusinessLog.setUser(RestUserUtils.getUserInfo().getUserId().toString());
		}

		if (context.get(BUSINESS_OPERATION_TIME) != null) {
			myBusinessLog.setTime((Date) context.get(BUSINESS_OPERATION_TIME));
		}

		if (context.get(BUSINESS_OPERATION_IP) != null) {
			myBusinessLog.setIp((String) context.get(BUSINESS_OPERATION_IP));
		}
		myBusinessLog.setUser(RestUserUtils.getUserInfo().getUserId().toString());
		myBusinessLog.setLog(businessLog.getLog());
		myBusinessLog.setCategory(businessLog.getCategory());

		context = Collections.unmodifiableMap(context);

		return myBusinessLog;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof DefaultBusinessLog))
			return false;

		DefaultBusinessLog that = (DefaultBusinessLog) o;

		if (ip != null ? !ip.equals(that.ip) : that.ip != null)
			return false;
		if (time != null ? !time.equals(that.time) : that.time != null)
			return false;
		if (user != null ? !user.equals(that.user) : that.user != null)
			return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = user != null ? user.hashCode() : 0;
		result = 31 * result + (ip != null ? ip.hashCode() : 0);
		result = 31 * result + (time != null ? time.hashCode() : 0);
		return result;
	}

	@Override
	public String toString() {
		return "DefaultBusinessLog{" + "user='" + user + '\'' + ", ip='" + ip + '\'' + ", time=" + time + ", log="
				+ getLog() + ", context=" + context + '}';
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}
	
	
	
	private static DefaultLogRepository repository;

	public static DefaultLogRepository getRepository() {
		if (repository == null) {
			repository = InstanceFactory.getInstance(DefaultLogRepository.class);
		}
		return repository;
	}


	public void save() {
		getRepository().save(this);
	}

	public void remove() {
		getRepository().save(this);
	}

	public static boolean exists(String id) {
		return getRepository().exists(id);
	}

	public static DefaultBusinessLog get(String id) {
		return getRepository().getOne(id);
	}


	public static DefaultBusinessLog load(String id) {
		return getRepository().getOne(id);
	}

	public static List<DefaultBusinessLog> findAll() {
		return getRepository().findAll();
	}
}
