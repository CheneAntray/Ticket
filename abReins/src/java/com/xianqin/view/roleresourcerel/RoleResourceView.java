package com.xianqin.view.roleresourcerel;

import java.io.Serializable;

import com.xianqin.domain.RoleResourceRel;

public class RoleResourceView extends RoleResourceRel implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 将视图对象与业务对象具有相同属性名称的属性值进行拷贝，将视图对象属性值复制给业务对象实例
	 * @param roleResourceView 数据源对象实例
	 * @param roleResourceRel 数据目标对象实例
	 * @return 具有源对象属性值的目标对象实例
	 */
	 public static RoleResourceRel processRoleResourceViewToRoleResourceRel(RoleResourceView roleResourceView,RoleResourceRel roleResourceRel){
		roleResourceRel.setResourceId(roleResourceView.getResourceId());
		roleResourceRel.setRoleId(roleResourceView.getRoleId());
		roleResourceRel.setId(roleResourceView.getId());
		return roleResourceRel;
	}
}
