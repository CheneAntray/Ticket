package com.xianqin.security.service;

import java.util.Set;

/**
 * 用户权限缓存
 * @author xianqin-bill
 *
 */
public interface UserPwrService {

	/**
	 * 获取到期时间
	 * 返回的数值单位为秒
	 * @return
	 */
	long getExpireIn();

	/**
	 * 校验用户是否具有该URL的权限
	 * @param userName 系统用户登录名
	 * @param url 请求的url
	 * @return 是否具有该权限
	 */
	boolean isPwr(String userName, String url);

	/**
	 * 将用户权限加入到权限缓存
	 * @param userName
	 * @param urlSet
	 */
	void addUserPwrByUserName(String userName, Set<String> urlSet);
	
}
