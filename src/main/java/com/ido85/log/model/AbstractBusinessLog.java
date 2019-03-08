package com.ido85.log.model;


import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

/**
 * User: zjzhai Date: 12/5/13 Time: 4:54 PM
 */
@MappedSuperclass
@DiscriminatorColumn(discriminatorType = DiscriminatorType.STRING, columnDefinition="LOG_CATEGORY")
public abstract class AbstractBusinessLog implements Entity{

	private static final long serialVersionUID = -3614081808804897797L;


	@Id
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
	@Column(name = "ID")
	private String id;

	@Version
	@Column(name = "VERSION")
	private int version;

	@Column(name = "LOG_CATEGORY")
	private String category;

	@Column(name = "LOG_CONTENT")
	private String log;

	/**
	 * 获得实体的标识
	 * 
	 * @return 实体的标识
	 */
	public String getId() {
		return id;
	}

	/**
	 * 设置实体的标识
	 * 
	 * @param id 要设置的实体标识
	 */
	public void setId(String id) {
		this.id = id;
	}

	public String getLog() {
		return log;
	}

	public void setLog(String log) {
		this.log = log;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	/**
	 * 获得实体的版本号。持久化框架以此实现乐观锁。
	 * 
	 * @return 实体的版本号
	 */
	public int getVersion() {
		return version;
	}

	/**
	 * 设置实体的版本号。持久化框架以此实现乐观锁。
	 * 
	 * @param version 要设置的版本号
	 */
	public void setVersion(int version) {
		this.version = version;
	}

	

//	public boolean existed(String propertyName, Object propertyValue) {
//		List<?> entities = getRepository().createCriteriaQuery(getClass()).eq(propertyName, propertyValue).list();
//		return !(entities.isEmpty());
//	}

	

	@Override
	public abstract int hashCode();

	@Override
	public abstract boolean equals(Object other);

	@Override
	public abstract String toString();
}
