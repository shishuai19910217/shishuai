package com.ido85.frame.web.rest.security;

public enum TrustAccessKeyEnum {
	/**
	 * wi系统调用orm系统所使用的ACCESS_KEY_ID枚举
	 */
	ORM_ACCESS_KEY_ID("PKOWYJI1WFLJTSPZP9K2"),
	/**
	 * orm系统调用wi系统所使用的ACCESS_KEY_ID枚举
	 */
	WI_ACCESS_KEY_ID("3DV4E8SRNZTJ5FP1XSPS"),
	/**
	 * wi系统调用orm系统所使用的SECURITY_KEY枚举
	 */
	ORM_SECURITY_KEY("C2B8E5CBE2A80744/9C7203C27CC98553DCB6AC3DF50A0726F7E21361"),
	/**
	 * orm系统调用wi系统所使用的SECURITY_KEY枚举
	 */
	WI_SECURITY_KEY("DCE9F6A6E084F816/437B3224EC1A3EA799727FAF44D8EAAC703ADF49");
	
	
	
	private String code;
	
	TrustAccessKeyEnum(String value){
		this.code = value;
	}
	
	@Override
	public String toString() {
		return code;
	}
	
	public String getCode(){
		return code;
	}
}
