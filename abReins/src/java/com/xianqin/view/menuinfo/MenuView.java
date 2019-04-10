package com.xianqin.view.menuinfo;

import java.io.Serializable;

import com.xianqin.domain.MenuInfo;

public class MenuView extends MenuInfo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 将视图对象与业务对象具有相同属性名称的属性值进行拷贝，将视图对象属性值复制给业务对象实例
	 * @param menuView 数据源对象实例
	 * @param menuInfo 数据目标对象实例
	 * @return 具有源对象属性值的目标对象实例
	 */
	 public static MenuInfo processMenuViewToMenuInfo(MenuView menuView,MenuInfo menuInfo){
		menuInfo.setCreateOper(menuView.getCreateOper());
		//menuInfo.setCreateTime(menuView.getCreateTime());
		menuInfo.setMenuUrl(menuView.getMenuUrl());
		menuInfo.setMenuName(menuView.getMenuName());
		menuInfo.setUpdateTime(menuView.getUpdateTime());
		menuInfo.setId(menuView.getId());
		menuInfo.setUpdateOper(menuView.getUpdateOper());
		return menuInfo;
	}

}
