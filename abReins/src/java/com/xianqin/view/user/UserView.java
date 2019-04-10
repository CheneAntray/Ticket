package com.xianqin.view.user;

import java.io.Serializable;

import com.xianqin.domain.UserInfo;

public class UserView extends UserInfo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 483768572884228310L;
	private String[] unitIds;
	private String[] roleIds;
	private String roleid;
	private Integer page;
	private Integer rows;
	
	public String getRoleid() {
		return roleid;
	}
	public void setRoleid(String roleid) {
		this.roleid = roleid;
	}
	public Integer getPage() {
		return page;
	}
	public void setPage(Integer page) {
		this.page = page;
	}
	public Integer getRows() {
		return rows;
	}
	public void setRows(Integer rows) {
		this.rows = rows;
	}
	public String[] getUnitIds() {
		return unitIds;
	}
	public void setUnitIds(String[] unitIds) {
		this.unitIds = unitIds;
	}
	public String[] getRoleIds() {
		return roleIds;
	}
	public void setRoleIds(String[] roleIds) {
		this.roleIds = roleIds;
	}
	
	/**
	 * 将视图对象与业务对象具有相同属性名称的属性值进行拷贝，将视图对象属性值复制给业务对象实例
	 * @param userView 数据源对象实例
	 * @param userInfo 数据目标对象实例
	 * @return 具有源对象属性值的目标对象实例
	 */
	 public static UserInfo processUserViewToUserInfo(UserView userView,UserInfo userInfo){
		userInfo.setId(userView.getId());
		userInfo.setUpdateTime(userView.getUpdateTime());
		userInfo.setUpdateOper(userView.getUpdateOper());
		userInfo.setCreateOper(userView.getCreateOper());
		userInfo.setPassword(userView.getPassword());
		userInfo.setCreateTime(userView.getCreateTime());
		userInfo.setName(userView.getName());
		userInfo.setAccount(userView.getAccount());
		return userInfo;
	}
	
}
