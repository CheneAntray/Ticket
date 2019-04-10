package com.xianqin.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.base.ReturnMap;
import com.xianqin.common.QueryRule;
import com.xianqin.dao.UserRoleRelDao;
import com.xianqin.domain.UserRoleRel;
import com.xianqin.service.UserRoleRelService;

@Service(value="userRoleService")
public class UserRoleRelServiceImpl implements UserRoleRelService{

	@Resource
	private UserRoleRelDao userRoleRelDao;
	
	/**
	 * 添加用户与角色关联关系
	 */
	@Override
	public ReturnMap saveUserRoleRel(UserRoleRel userRoleRel) throws Exception {
		// TODO Auto-generated method stub
		ReturnMap returnMap=new ReturnMap();
		userRoleRelDao.saveUserRoleRel(userRoleRel);
		returnMap.setSucc();
		returnMap.setMsg("添加用户与角色关联关系成功");
		return returnMap;
	}

	@Override
	public ReturnMap queryUserRoleRelByUserId(String userid) throws Exception {
		// TODO Auto-generated method stub
		ReturnMap returnMap=new ReturnMap();
		QueryRule queryRule=QueryRule.getInstance();
		queryRule.addEqual(UserRoleRel._userId, userid);
		List<UserRoleRel> list=userRoleRelDao.qeuryListByQueryRule(queryRule);
		returnMap.setSucc();
		returnMap.setMsg("查询用户关联角色信息成功");
		returnMap.setListContext(list);
		return returnMap;
	}

	@Override
	public ReturnMap deleteUserRoleRelByUserId(String userid) throws Exception {
		// TODO Auto-generated method stub
		ReturnMap returnMap=new ReturnMap();
		userRoleRelDao.deleteUserRoleRelByUserId(userid);
		returnMap.setSucc();
		returnMap.setMsg("删除用户角色关联表成功");
		return returnMap;
	}

}
