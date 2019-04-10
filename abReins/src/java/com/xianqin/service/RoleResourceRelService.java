package com.xianqin.service;

import com.base.ReturnMap;
import com.xianqin.domain.RoleResourceRel;

public interface RoleResourceRelService {

	/**
	 * 添加角色资源关联表
	 * @param queryRule
	 * @return
	 * @throws Exception
	 */
	ReturnMap saveRoleResourceRel(RoleResourceRel roleResourceRel) throws Exception;
	/**
	 * 根据角色id获取角色资源关联表id
	 * @param roleId
	 * @return
	 * @throws Exception
	 */
	ReturnMap getRoleResourceRelIdByRoleId(String roleId) throws Exception;
	/**
	 * 根据角色id删除角色资源关联表
	 * @param roleId
	 * @return
	 * @throws Exception
	 */
	ReturnMap deleteRoleResourceRelByRoleId(String roleId) throws Exception;
}
