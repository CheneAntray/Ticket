package com.xianqin.service;

import java.util.List;

import com.base.ReturnMap;
import com.xianqin.common.QueryRule;
import com.xianqin.domain.UserInfo;

public interface UserInfoService {
	
	/**
	 * 根据用户账号查询用户用户对象
	 * @param userInfoId
	 * @return
	 * @throws Exception
	 */
	ReturnMap doLogin(String userAccount,String password) throws Exception;
	/**
	 * 将bean实例保存到数据库中
	 * @param UserInfo bean实例，一般为游离态对象
	 * @throws Exception
	 */
	ReturnMap saveUserInfo(UserInfo userInfo) throws Exception;
	/**
	 * 添加用户时查询帐号是否重复等
	 * @param userId
	 * @param account
	 * @return
	 * @throws Exception
	 */
	boolean queryUserByAccount(String userId, String account) throws Exception;
	/**
	 * 根据用户帐号查询用户id
	 * @param account
	 * @return
	 * @throws Exception
	 */
	ReturnMap getUserIdByAccount(String account) throws Exception;
	/**
	 * 分页查询用户信息
	 * @param queryRule
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	ReturnMap queryUserByPage(QueryRule queryRule, Integer pageIndex, Integer pageSize) throws Exception;
	/**
	 * 根据用户id查询用户
	 * @param queryRule
	 * @return
	 * @throws Exception
	 */
	ReturnMap queryUserById(String userid) throws Exception;
	/**
	 * 修改用户信息
	 * @param userInfo
	 * @return
	 * @throws Exception
	 */
	ReturnMap editUserInfo(UserInfo userInfo) throws Exception;
	/**
	 * 查询用户全部信息用户报表填充
	 * @param queryRule
	 * @return
	 * @throws Exception
	 */
	List<UserInfo> getUserInfoToReport(QueryRule queryRule) throws Exception;
}
