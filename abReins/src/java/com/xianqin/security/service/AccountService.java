package com.xianqin.security.service;

import java.util.List;

import org.apache.shiro.subject.Subject;
import com.xianqin.domain.UrlInfo;
import com.xianqin.domain.UserInfo;

/**
 * 系统用户以及权限服务接口
 * 该接口为系统提供了系统用户身份校验以及权限相关的服务
 * @author xianqin-bill
 *
 */
public interface AccountService {
	
	/**
	 * 根据用户名，查找用户持久化对象实例
	 * @param userName 用户名，一般为登录名
	 * @return 用户持久化对象实例，如果用户名不存在，则返回空对象
	 * @throws Exception
	 */
	public UserInfo getUserInfoByUserName(String userName)throws Exception;
	
	
	/**
	 * 根据用户名，查找用户的系统资源
	 * @param userName
	 * @return
	 * @throws Exception
	 */
	public List<UrlInfo> getPermissionsByUserName(String userName)throws Exception;
	
	
	/**
	 * 通过用户名和密码，校验用户是否为合法用户
	 * @param userName 用户名
	 * @param password 登录密码
	 * @return 是否为合法用户
	 * @throws Exception
	 */
	public Boolean checkLogin(String userName,String password)throws Exception;
    /**
     * 根据用户账号查找用户Id
     * @param subject
     * @return
     * @throws Exception
     */
	String getUserIdBySubjec(Subject subject) throws Exception;
	
}
