/**
 * 
 */
package com.ido85.frame.common.restful;

import java.util.HashMap;

import javax.xml.bind.annotation.XmlAnyElement;

import org.springframework.util.Assert;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * 基本资源数据
 * @author rongxj
 *
 */
//@XmlRootElement
public class Resource<T> extends ResourceSupport{

	private T data;
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected Resource(){
		this.data = (T)new HashMap();;
	}
	
	public Resource(T data){
		this.data = data;
	}
	public Resource(T data,Exception e){
		Assert.notNull(data, "Data must not be null!");
		ProcessStatus p = new ProcessStatus(-100,e.getMessage());
		this.setResult(p);
		this.data = data;
	}
	public Resource(T data, ProcessStatus result){
		if(result.getRetCode() == -3 || result.getRetCode() == -5){
			result = ProcessStatus.COMMON_SUCCESS;
		}
		this.setResult(result);
		this.data = data;
	}
	
	public Resource(ProcessStatus result){
		if(result.getRetCode() == -3 || result.getRetCode() == -5){
			result = ProcessStatus.COMMON_SUCCESS;
		}
		this.setResult(result);
		this.data = null;
	}
	
//	@JsonUnwrapped
	@XmlAnyElement
	@JsonProperty(value="data", index = -1)
	@JsonInclude(Include.ALWAYS)
	public T getData() {
		return data;
	}
	
	@Override
	public String toString() {
		return String.format("Resource { content: %s, %s }", getData(), super.toString());
	}
	
	
	@Override
	public boolean equals(Object obj) {

		if (this == obj) {
			return true;
		}

		if (obj == null || !obj.getClass().equals(getClass())) {
			return false;
		}

		Resource<?> that = (Resource<?>) obj;

		boolean contentEqual = this.data == null ? that.data == null : this.data.equals(that.data);
		return contentEqual ? super.equals(obj) : false;
	}

	@Override
	public int hashCode() {

		int result = super.hashCode();
		result += data == null ? 0 : 17 * data.hashCode();
		return result;
	}
}