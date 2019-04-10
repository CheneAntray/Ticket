package com.xianqin.service;

import java.util.List;

import com.base.ReturnMap;
import com.xianqin.common.QueryRule;
import com.xianqin.domain.RoleInfo;

public interface RoleInfoService {
	/**
	 * 添加角色信息
	 * @param roleInfo
	 * @return
	 * @throws Exception
	 */
	ReturnMap saveRoleInfo(RoleInfo roleInfo) throws Exception;
	/**
	 * 查询角色信息
	 * @return
	 * @throws Exception
	 */
	ReturnMap queryRoleInfo(QueryRule queryRule) throws Exception;
	/**
	 * 添加角色时验证该角色是否存在
	 * @return
	 * @throws Exception
	 */
	boolean queryRoleInfoIsExist(String roleName) throws Exception;
	
	/**
	 * 根据角色名称查询角色信息
	 * @param queryRule
	 * @return
	 * @throws Exception
	 */
	ReturnMap getRoleInfoByRoleName(String roleName) throws Exception;
	/**
	 * 根据id查询角色信息
	 * @param roleid
	 * @return
	 * @throws Exception
	 */
	ReturnMap getRoleInfoByRoleId(String roleid) throws Exception;
	/**
	 * 分页查询角色信息
	 * @param queryRule
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	ReturnMap queryRoleInfoByPage(QueryRule queryRule, Integer pageIndex, Integer pageSize) throws Exception;
	/**
	 * 根据该角色拥有的所有权限
	 * @param roleId
	 * @return
	 * @throws Exception
	 */
	ReturnMap queryAuthorityByRoleId(String roleId) throws Exception;
	/**
	 * 修改角色信息
	 * @param roleInfo
	 * @return
	 * @throws Exception
	 */
	ReturnMap editRoleInfo(RoleInfo roleInfo) throws Exception;
	/**
	 * 查询角色信息用于填充报表模版
	 * @param queryRule
	 * @return
	 * @throws Exception
	 */
	List<RoleInfo> queryRoleInfoToReport(QueryRule queryRule) throws Exception;
}
