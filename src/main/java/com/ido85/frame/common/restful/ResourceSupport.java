/**
 * 
 */
package com.ido85.frame.common.restful;

import java.util.Calendar;
import java.util.Date;

import org.springframework.util.Assert;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ido85.frame.common.mapper.JsonMapper;
import com.ido85.frame.common.serializer.ISO8601DateSerializer;

/**
 * 封装了资源请求处理结果<br/>
 * 默认 COMMON_SUCCESS<br/>
 *  result:{retCode:0, retMsg:"已处理"}
 * @author rongxj
 *
 */
@JsonPropertyOrder
public class ResourceSupport implements Identifiable<ProcessStatus>{

	private ProcessStatus result;
	private ResponseStatus responseStatus;
	@JsonSerialize(using=ISO8601DateSerializer.class)
	private Date datetime = Calendar.getInstance().getTime();
	
	public ResourceSupport(){
		result = ProcessStatus.COMMON_SUCCESS;
		responseStatus = ResponseStatus.HTTP_OK;
	}
	
	@JsonIgnore
	@Override
	public ProcessStatus getId() {
		// TODO Auto-generated method stub
		return result;
	}

	@JsonProperty("result")
	public ProcessStatus getResult() {
		return result;
	}
	
	public void setResult(ProcessStatus result) {
		this.result = result;
	}
	
	@JsonUnwrapped
	public ResponseStatus getResponseStatus() {
		return responseStatus;
	}
	
	public ResourceSupport setResponseStatus(ResponseStatus responseStatus) {
		Assert.notNull(responseStatus, "服务器相应状态不能为null");
		this.responseStatus = responseStatus;
		return this;
	}
	
	public void setDatetime(Date datetime) {
		this.datetime = datetime;
	}
	
	@JsonSerialize(using=ISO8601DateSerializer.class)
	public Date getDatetime() {
		return datetime;
	}
	
	@JsonIgnore
	public String toJsonString(){
		return JsonMapper.toJsonString(this);
	}
}
