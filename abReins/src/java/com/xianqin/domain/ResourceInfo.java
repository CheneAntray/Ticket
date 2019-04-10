package com.xianqin.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 数据库表RESOURCE_INFO与业务对象资源信息映射类 对象说明: 系统自动生成
 */
@Entity
@Table(name = "RESOURCE_INFO")
public class ResourceInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	public static final String TABLE_NAME = "RESOURCE_INFO";
	// 列名与属性名的映射
	public final static String _id = "id";
	public final static String _resourceId = "resourceId";
	public final static String _resourceType = "resourceType";
	public final static String _parentMenu = "parentMenu";

	/**
	 * 映射列名称:ID
	 * <p>
	 * 列说明:ID
	 */
	private String id;
	/**
	 * 映射列名称:RESOURCE_ID
	 * <p>
	 * 列说明:角色ID
	 */
	private String resourceId;
	/**
	 * 映射列名称:RESOURCE_TYPE
	 * <p>
	 * 列说明:功能ID
	 */
	private Integer resourceType;
	/**
	 * 映射列名称:parentMenu
	 * <p>
	 * 列说明:功能ID
	 */
	private Integer parentMenu;

	/**
	 * 获取数据库表:RESOURCE_INFO.ID列值
	 * <p>
	 * 列说明:ID
	 */
	@Id
	@Column(name = "ID", length = 32)
	public String getId() {
		return this.id;
	}

	/**
	 * 设置数据库表:RESOURCE_INFO.ID列值
	 * <p>
	 * 列说明:ID
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * 获取数据库表:RESOURCE_INFO.RESOURCE_ID列值
	 * <p>
	 * 列说明:角色ID
	 */
	@Column(name = "RESOURCE_ID", length = 32)
	public String getResourceId() {
		return this.resourceId;
	}

	/**
	 * 设置数据库表:RESOURCE_INFO.RESOURCE_ID列值
	 * <p>
	 * 列说明:角色ID
	 */
	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}

	/**
	 * 获取数据库表:RESOURCE_INFO.RESOURCE_TYPE列值
	 * <p>
	 * 列说明:功能ID
	 */
	@Column(name = "RESOURCE_TYPE")
	public Integer getResourceType() {
		return this.resourceType;
	}

	/**
	 * 设置数据库表:RESOURCE_INFO.RESOURCE_TYPE列值
	 * <p>
	 * 列说明:功能ID
	 */
	public void setResourceType(Integer resourceType) {
		this.resourceType = resourceType;
	}
	/**
	 * 获取数据库表:RESOURCE_INFO.PARENT_MENU列值
	 * <p>
	 * 列说明:功能ID
	 */
	@Column(name = "PARENT_MENU")
	public Integer getParentMenu() {
		return this.parentMenu;
	}
	/**
	 * 设置数据库表:RESOURCE_INFO.PARENT_MENU列值
	 * <p>
	 * 列说明:功能ID
	 */
	public void setParentMenu(Integer parentMenu) {
		this.parentMenu = parentMenu;
	}
}