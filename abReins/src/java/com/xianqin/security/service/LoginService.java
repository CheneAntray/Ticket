package com.xianqin.security.service;

import java.util.List;

import com.xianqin.domain.MenuInfo;
import com.xianqin.domain.UserInfo;
import com.xianqin.security.view.MenuVO;

public interface LoginService {

    public List<String> queryRolesByUserId(String id);
	
	public List<String> queryMenuIdListByRoleList(List<String> roleIdList);
	
	public UserInfo queryUserByAccount(String account);

	public List<MenuVO> queryMenuListByIdList(List<String> menuIdList);
    
	public List<MenuInfo> queryMenuListByAccount(String account);
    
}
