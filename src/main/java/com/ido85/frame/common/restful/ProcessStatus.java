/**
 * 
 */
package com.ido85.frame.common.restful;

import java.io.Serializable;

/**
 * 处理结果返回状态
 * @author rongxj
 *
 */
public class ProcessStatus implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8397325882760657573L;

	private int retCode;
	
	private String retMsg;
	
	public ProcessStatus(){
		
	}
	
	public ProcessStatus(int retCode, String retMsg){
		this.retCode = retCode;
		this.retMsg = retMsg;
	}

	/**
	 * 返回处理正常或错误编码
	 * @return int
	 */
	public int getRetCode() {
		return retCode;
	}

	public void setRetCode(int retCode) {
		this.retCode = retCode;
	}

	/**
	 * 处理结果描述
	 * @return String
	 */
	public String getRetMsg() {
		return retMsg;
	}

	public void setRetMsg(String retMsg) {
		this.retMsg = retMsg;
	}
	
	@Override
	public String toString(){
		return String.format("process status {retCode: %s, retMsg: %s}", retCode, retMsg);
	}
	
	public final static ProcessStatus COMMON_SUCCESS = new ProcessStatus(0, "处理成功");
	public final static ProcessStatus COMMON_ERROR = new ProcessStatus(-1, "服务器内部错误");
	
	public final static ProcessStatus LOGIN_ERROR = new ProcessStatus(1000, "用户名或密码错误");
	public final static ProcessStatus TOKEN_INVALID = new ProcessStatus(1001, "用户Token失效");
	public final static ProcessStatus UNLOGIN_ERROR = new ProcessStatus(1002, "用户未登录");
	public final static ProcessStatus ILLEGAL_REQUEST = new ProcessStatus(1003, "非法请求");
	public final static ProcessStatus REQUEST_TIMEOUT = new ProcessStatus(1004, "请求超时");
	public final static ProcessStatus MISSING_DEVICE_INFO = new ProcessStatus(1005, "缺失登录设备信息");
	public final static ProcessStatus AUTH_ERROR = new ProcessStatus(1006, "身份认证已过期");

	public static final ProcessStatus AUTHLOGIN_ERROR = new ProcessStatus(1007, "该授权未绑定账号");
}
