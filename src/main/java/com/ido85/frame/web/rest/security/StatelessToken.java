package com.ido85.frame.web.rest.security;

import org.apache.shiro.authc.AuthenticationToken;


/**
 * rest 令牌
 */
public class StatelessToken implements AuthenticationToken {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String accessKeyID;
    private String strToSign;
    private String clientSignature;

    public StatelessToken(String accessKeyID,  String strToSign, String clientSignature) {
        this.accessKeyID = accessKeyID;
        this.strToSign = strToSign;
        this.clientSignature = clientSignature;
    }

    public String getAccessKeyID() {
        return accessKeyID;
    }

    public void setAccessKeyID(String accessKeyID) {
        this.accessKeyID = accessKeyID;
    }

    public  String getStrToSign() {
        return strToSign;
    }

    public void setStrToSign(String strToSign) {
        this.strToSign = strToSign;
    }

    public String getClientSignature() {
        return clientSignature;
    }

    public void setClientSignature(String clientSignature) {
        this.clientSignature = clientSignature;
    }

    @Override
    public Object getPrincipal() {
       return accessKeyID;
    }

    @Override
    public Object getCredentials() {
        return clientSignature;
    }
}
