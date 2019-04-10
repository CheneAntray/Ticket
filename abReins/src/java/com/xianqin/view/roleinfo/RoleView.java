package com.xianqin.view.roleinfo;

import java.io.Serializable;
import java.util.List;

import com.xianqin.domain.MenuInfo;
import com.xianqin.domain.RoleInfo;
import com.xianqin.domain.UrlInfo;

public class RoleView extends RoleInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String urlId;
	private String menuId;
	private Integer page;
	private Integer rows;
	private List<MenuInfo> menuInfos;
	private List<UrlInfo> urlInfos;
	private String resourceId;

	public String getResourceId() {
		return resourceId;
	}

	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}

	public List<MenuInfo> getMenuInfos() {
		return menuInfos;
	}

	public void setMenuInfos(List<MenuInfo> menuInfos) {
		this.menuInfos = menuInfos;
	}

	public List<UrlInfo> getUrlInfos() {
		return urlInfos;
	}

	public void setUrlInfos(List<UrlInfo> urlInfos) {
		this.urlInfos = urlInfos;
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

	public String getUrlId() {
		return urlId;
	}

	public void setUrlId(String urlId) {
		this.urlId = urlId;
	}

	public String getMenuId() {
		return menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	/**
	 * 将视图对象与业务对象具有相同属性名称的属性值进行拷贝，将视图对象属性值复制给业务对象实例
	 * 
	 * @param roleView
	 *            数据源对象实例
	 * @param roleInfo
	 *            数据目标对象实例
	 * @return 具有源对象属性值的目标对象实例
	 */
	public static RoleInfo processRoleViewToRoleInfo(RoleView roleView, RoleInfo roleInfo) {
		roleInfo.setCreateOper(roleView.getCreateOper());
		roleInfo.setCreateTime(roleView.getCreateTime());
		roleInfo.setRoleName(roleView.getRoleName());
		roleInfo.setUpdateTime(roleView.getUpdateTime());
		roleInfo.setId(roleView.getId());
		roleInfo.setUpdateOper(roleView.getUpdateOper());
		return roleInfo;
	}
}
