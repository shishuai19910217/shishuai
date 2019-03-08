/**
 * 
 */
package com.ido85.frame.common.restful;


/**
 * 服务器相应状态
 * @author rongxj
 *
 */
public class ResponseStatus {

	private int code = 200;
	private String message = "OK";
	
	public ResponseStatus(int code, String message){
		this.code = code;
		this.message = message;
	}
	
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	/**
	 * 200:ok
	 */
	public final static ResponseStatus HTTP_OK = new ResponseStatus(200, "OK");
	/**
	 * 201:Created
	 */
	public final static ResponseStatus HTTP_CREATED = new ResponseStatus(201, "Created");
	/**
	 * 202:Acceted
	 */
	public final static ResponseStatus HTTP_ACCEPTED = new ResponseStatus(202, "Accepted");
	/**
	 * 203:Non-Authoritative Information
	 */
	public final static ResponseStatus HTTP_NON_AUTH= new ResponseStatus(203, " Non-Authoritative Information");
	/**
	 * 204:No Content
	 */
	public final static ResponseStatus HTTP_NO_CONTENT= new ResponseStatus(204, " No Content");
	/**
	 * 205:Reset Content
	 */
	public final static ResponseStatus HTTP_RESET_CONTENT= new ResponseStatus(205, "Reset Content");
	/**
	 * 206:Partial Content
	 */
	public final static ResponseStatus HTTP_PARTIAL_CONTENT= new ResponseStatus(206, "Partial Content");
	/**
	 * 207:Multi-Status
	 */
	public final static ResponseStatus HTTP_MULTI_STATUS= new ResponseStatus(207, "Multi-Status");
	
	/**
	 * 错误的请求
	 * 400:Bad Request
	 */
	public final static ResponseStatus HTTP_BAD_REQUEST= new ResponseStatus(400, "Bad Request");
	/**
	 * 身份验证错误<br/>
	 * 401:Unauthorized
	 */
	public final static ResponseStatus HTTP_UNAUTHORIZED= new ResponseStatus(401, "Unauthorized");
	/**
	 * 服务器拒绝请求<br/>
	 * 402: Payment Required
	 */
	public final static ResponseStatus HTTP_PAYMENT_REQUIRED= new ResponseStatus(402, "Payment Required");
	/**
	 * 无效的json<br/>
	 * 402: Unprocessable Entity
	 */
	public final static ResponseStatus HTTP_UNPROCESSABLE_ENTITY= new ResponseStatus(422, "Unprocessable Entity");
	/**
	 * 请求过时<br/>
	 * 408： Request Timeout
	 */
	public final static ResponseStatus HTTP_REQUEST_TIMEOUT= new ResponseStatus(408, "Request Timeout");
	/**
	 * 服务器错误<br/>
	 * 500: 
	 */
	public final static ResponseStatus HTTP_SERVER_ERROR= new ResponseStatus(500, "Internal Server Error");
	
	
}
