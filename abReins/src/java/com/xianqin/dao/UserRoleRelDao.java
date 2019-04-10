package com.xianqin.dao;

import java.util.List;

import com.xianqin.common.QueryRule;
import com.xianqin.domain.UserRoleRel;

public interface UserRoleRelDao {
	/**
	 * 删除角色时
	 * 根据角色Id删除用户角色关联
	 * @param roleId  角色Id
	 */
	void deleteUserRoleRelByRoleId(String roleId);
	
	/**
	 * 当添加用户时
	 * 插入一条数据用于用户与角色进行关联
	 * @param userRoleRel  持久化对象
	 */
	void saveUserRoleRel(UserRoleRel userRoleRel);
	/**
	 * 修改用户或删除用户时
	 * 根据用户Id删除用户角色关联
	 * @param userId
	 */
	void deleteUserRoleRelByUserId(String userId);
	
	/**
	 * 根据条件查询用户角色关联信息
	 * @param queryRule
	 * @return
	 */
	UserRoleRel getUserRoleRelByCondition(QueryRule queryRule);
	
	/**
	 * 根据条件查询用户与角色的关联信息队列
	 * @param queryRule 查询对象实例
	 * @return
	 */
	List<UserRoleRel> qeuryListByQueryRule(QueryRule queryRule);
	

}
