package com.xianqin.service;

import com.base.ReturnMap;
import com.xianqin.domain.UserRoleRel;

public interface UserRoleRelService {
	/**
	 * 添加用户和角色关联关系
	 * @param userRoleRel
	 * @return
	 * @throws Exception
	 */
	ReturnMap saveUserRoleRel(UserRoleRel userRoleRel) throws Exception;
	/**
	 * 根据用户帐号查询角色信息
	 * @param userid
	 * @return
	 * @throws Exception
	 */
	ReturnMap queryUserRoleRelByUserId(String userid) throws Exception;
	/**
	 * 根据用户id删除角色关联表信息
	 * @param userid
	 * @return
	 * @throws Exception
	 */
	ReturnMap deleteUserRoleRelByUserId(String userid) throws Exception;
}
