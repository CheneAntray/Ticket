package com.xianqin.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.base.ReturnMap;
import com.xianqin.common.QueryRule;
import com.xianqin.dao.RoleResourceRelDao;
import com.xianqin.domain.RoleResourceRel;
import com.xianqin.service.ResourceInfoService;
import com.xianqin.service.RoleResourceRelService;
@Service("roleResourceRelService")
public class RoleResourceRelServiceImpl implements RoleResourceRelService{

	@Resource
	private RoleResourceRelDao roleResourceRelDao;
	
	@Resource
	private ResourceInfoService resourceInfoservice;
	
	@Override
	public ReturnMap saveRoleResourceRel(RoleResourceRel roleResourceRel) throws Exception {
		// TODO Auto-generated method stub
		ReturnMap ret=new ReturnMap();
		roleResourceRelDao.saveRoleResourceRel(roleResourceRel);
		ret.setSucc();
		ret.setMsg("添加角色资源关联表成功");
		return ret;
	}
	
	@Override
	public ReturnMap getRoleResourceRelIdByRoleId(String roleId) throws Exception {
		// TODO Auto-generated method stub
		ReturnMap returnMap=new ReturnMap();
		QueryRule queryRule=QueryRule.getInstance();
		queryRule.addEqual(RoleResourceRel._roleId, roleId);
		List<RoleResourceRel> list=roleResourceRelDao.getRoleResourceRelListByCondition(queryRule);
		returnMap.setSucc();
		returnMap.setListContext(list);
		returnMap.setMsg("查询角色资源表ID成功");
		return returnMap;
	}

	@Override
	public ReturnMap deleteRoleResourceRelByRoleId(String roleId) throws Exception {
		// TODO Auto-generated method stub
		ReturnMap returnMap=new ReturnMap();
		roleResourceRelDao.deleteRoleResourceByRoleId(roleId);
		returnMap.setSucc();
		returnMap.setMsg("删除角色资源关联表信息成功");
		return returnMap;
	}

}
