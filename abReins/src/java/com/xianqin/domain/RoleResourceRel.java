package com.xianqin.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.xianqin.view.roleresourcerel.RoleResourceView;
/**
 * 数据库表ROLE_RESOURCE_REL与业务对象角色资源关联信息映射类
 * 对象说明:
 * 系统自动生成
 */
@Entity
@Table(name = "ROLE_RESOURCE_REL")
public class RoleResourceRel implements Serializable{
	private static final long serialVersionUID = 1L;
	
	public static final String TABLE_NAME = "ROLE_RESOURCE_REL";
	//列名与属性名的映射
	public final static String _id="id";
	public final static String _roleId="roleId";
	public final static String _resourceId="resourceId";
	
	  /**
	   * 映射列名称:ID
	   * <p>列说明:ID
	   */ 
	   private String id;
	  /**
	   * 映射列名称:ROLE_ID
	   * <p>列说明:用户ID
	   */ 
	   private String roleId;
	  /**
	   * 映射列名称:RESOURCE_ID
	   * <p>列说明:角色ID
	   */ 
	   private String resourceId;
	
	
	  /**
	   * 获取数据库表:ROLE_RESOURCE_REL.ID列值
	   * <p>列说明:ID
	   */ 
	   @Id
	@Column(name = "ID", length = 32)
	   public String getId(){
	     return this.id;
	   }
	   
	  /**
	   * 设置数据库表:ROLE_RESOURCE_REL.ID列值
	   * <p>列说明:ID
	   */ 
	   public void setId(String id){
	     this.id = id;
	   }
	
	  /**
	   * 获取数据库表:ROLE_RESOURCE_REL.ROLE_ID列值
	   * <p>列说明:用户ID
	   */ 
	   	@Column(name = "ROLE_ID", length = 32)
	   public String getRoleId(){
	     return this.roleId;
	   }
	   
	  /**
	   * 设置数据库表:ROLE_RESOURCE_REL.ROLE_ID列值
	   * <p>列说明:用户ID
	   */ 
	   public void setRoleId(String roleId){
	     this.roleId = roleId;
	   }
	
	  /**
	   * 获取数据库表:ROLE_RESOURCE_REL.RESOURCE_ID列值
	   * <p>列说明:角色ID
	   */ 
	   	@Column(name = "RESOURCE_ID", length = 32)
	   public String getResourceId(){
	     return this.resourceId;
	   }
	   
	  /**
	   * 设置数据库表:ROLE_RESOURCE_REL.RESOURCE_ID列值
	   * <p>列说明:角色ID
	   */ 
	   public void setResourceId(String resourceId){
	     this.resourceId = resourceId;
	   }
	   /**
		 * 将视图对象与业务对象具有相同属性名称的属性值进行拷贝，将视图对象属性值复制给业务对象实例
		 * @param roleResourceRel 数据源对象实例
		 * @param roleResourceView 数据目标对象实例
		 * @return 具有源对象属性值的目标对象实例
		 */
		 public static RoleResourceView processRoleResourceRelToRoleResourceView(RoleResourceRel roleResourceRel,RoleResourceView roleResourceView){
			 roleResourceView.setId(roleResourceRel.getId());
			 roleResourceView.setResourceId(roleResourceRel.getResourceId());
			 roleResourceView.setRoleId(roleResourceRel.getRoleId());
			return roleResourceView;
		}
}