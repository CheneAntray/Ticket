package com.xianqin.security.shiro;

import java.util.Map;

public class StatelessToken {
	private String username;  
    private Map<String, ?> params;  
    private String clientDigest;  
    
    /**
     * 获取请求参数
     * @return
     */
    public Map<String, ?> getParams() {
		return params;
	}
    
    /**
     * 设置请求参数
     * @param params
     */
	public void setParams(Map<String, ?> params) {
		this.params = params;
	}
	
	/**
	 * 获取用户名
	 * @return
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * 设置用户名
	 * @param username
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * 获取客户端认证证书
	 * @return
	 */
	public String getClientDigest() {
		return clientDigest;
	}

	/**
	 * 设置用户端认证证书
	 * @param clientDigest
	 */
	public void setClientDigest(String clientDigest) {
		this.clientDigest = clientDigest;
	}

	//省略部分代码
	/**
	 * 获取主要认证信息
	 * @return
	 */
    public Object getPrincipal() {  return username;} 
    
    /**
     * 获取认证证书
     * @return
     */
    public Object getCredentials() {  return clientDigest;}  
}
