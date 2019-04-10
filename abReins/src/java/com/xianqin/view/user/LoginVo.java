package com.xianqin.view.user;

import java.io.Serializable;

public class LoginVo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3501335363388650210L;
	
	/**
	 * 访问令牌
	 */
	private String access_token;
	
	/**
	 * 令牌有效期
	 */
	private Long expires_in;
	
	/**
	 * 用户Id
	 */
	private String userId;

	
	/**
	 * 回去放心令牌
	 * @return
	 */
	public String getAccess_token() {
		return access_token;
	}

	/**
	 * 设置访问令牌
	 * @param access_token 权限框架提供的访问令牌
	 */
	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}

	
	/**
	 * 获取令牌有效期 单位秒
	 * @return
	 */
	public Long getExpires_in() {
		return expires_in;
	}

	
	/**
	 * 设置令牌有效期,单位秒
	 * @param expires_in
	 */
	public void setExpires_in(Long expires_in) {
		this.expires_in = expires_in;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	
	
	

}
