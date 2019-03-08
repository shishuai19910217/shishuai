/**
 * 
 */
package com.ido85.frame.common.exceptions;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ido85.frame.common.restful.ProcessStatus;

/**
 * 业务异常基类
 * @author rongxj
 *
 */
@JsonIgnoreProperties({"stackTrace","localizedMessage"})
public class BusinessException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 445445701327085003L;

	private ProcessStatus status;
	
	public BusinessException(){
		this(-1, "服务器内部错误");
	}

	public BusinessException(String message){
		this(-1, message);
	}
	
	public BusinessException(int code, String message){
		super(message);
		status = new ProcessStatus(code, message);
	}
	
	public BusinessException(ProcessStatus status){
		super(status.getRetMsg());
		this.status = status;
	}
	/**
	 * 获取返回错误信息和编码
	 * 如果传入的status参数中的retMsg字段包含{}字符,则将其替换为errorMsg参数
	 * @param errorMsg
	 * @param status
	 * @return
	 */
	public static BusinessException getErrorMsg(String errorMsg, ProcessStatus status){
		status.setRetMsg(status.getRetMsg().replaceFirst("\\{\\}", errorMsg));
		return new BusinessException(status);
	}
	public ProcessStatus getStatus(){
		return this.status;
	}
	
	@Override
	public String toString(){
		return String.format("business status:{retCode: %s, retMsg: %s}", status.getRetCode(), status.getRetMsg());
	}
}
