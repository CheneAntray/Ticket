package com.xianqin.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.base.ReturnMap;
import com.xianqin.common.QueryRule;
import com.xianqin.dao.MenuInfoDao;
import com.xianqin.domain.MenuInfo;
import com.xianqin.service.MenuInfoService;
@Service("menuServiceImpl")
public class MenuInfoServiceImpl implements MenuInfoService{

	@Resource
	private MenuInfoDao menuInfoDao;
	
	/**
	 * 查询菜单信息
	 */
	@Override
	public ReturnMap getMenuInfo(QueryRule queryRule) throws Exception {
		// TODO Auto-generated method stub
		ReturnMap returnMap=new ReturnMap();
		List<MenuInfo> menuList=menuInfoDao.getMenuInfoListByCondition(queryRule);
		returnMap.setSucc();
		returnMap.setListContext(menuList);
		returnMap.setMsg("查询菜单路径成功");
		return returnMap;
	}

}
