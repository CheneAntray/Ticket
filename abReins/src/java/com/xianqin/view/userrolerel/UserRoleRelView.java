package com.xianqin.view.userrolerel;

import java.io.Serializable;

import com.xianqin.domain.UserRoleRel;

public class UserRoleRelView extends UserRoleRel implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 将视图对象与业务对象具有相同属性名称的属性值进行拷贝，将视图对象属性值复制给业务对象实例
	 * @param userRoleRelView 数据源对象实例
	 * @param userRoleRel 数据目标对象实例
	 * @return 具有源对象属性值的目标对象实例
	 */
	 public static UserRoleRel processUserRoleRelViewToUserRoleRel(UserRoleRelView userRoleRelView,UserRoleRel userRoleRel){
		userRoleRel.setRoleId(userRoleRelView.getRoleId());
		userRoleRel.setId(userRoleRelView.getId());
		userRoleRel.setUserId(userRoleRelView.getUserId());
		return userRoleRel;
	}
}
