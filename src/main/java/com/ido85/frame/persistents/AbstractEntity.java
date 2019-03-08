/**
 * 
 */
package com.ido85.frame.persistents;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;

/**
 * 实体基础类
 * @author rongxj
 *
 */
@MappedSuperclass
public abstract class AbstractEntity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3936007981895623310L;
	
	private String id;
	
	
	@PrePersist
	private void prePersist(){
		this.id = UUID.randomUUID().toString();
	}

	@Id
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}