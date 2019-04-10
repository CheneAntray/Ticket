package com.xianqin.domain;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.xianqin.view.userrolerel.UserRoleRelView;
/**
 * 数据库表USER_ROLE_REL与业务对象角色与用户关联信息映射类
 * 对象说明:
 * 系统自动生成
 */
@Entity
@Table(name = "USER_ROLE_REL")
public class UserRoleRel implements Serializable{
	private static final long serialVersionUID = 1L;
	
	public static final String TABLE_NAME = "USER_ROLE_REL";
	//列名与属性名的映射
	public final static String _id="id";
	public final static String _userId="userId";
	public final static String _roleId="roleId";
	
	  /**
	   * 映射列名称:ID
	   * <p>列说明:ID
	   */ 
	   private String id;
	  /**
	   * 映射列名称:USER_ID
	   * <p>列说明:用户ID
	   */ 
	   private String userId;
	  /**
	   * 映射列名称:ROLE_ID
	   * <p>列说明:角色ID
	   */ 
	   private String roleId;
	
	
	  /**
	   * 获取数据库表:USER_ROLE_REL.ID列值
	   * <p>列说明:ID
	   */ 
	   @Id
	@Column(name = "ID", length = 32)
	   public String getId(){
	     return this.id;
	   }
	   
	  /**
	   * 设置数据库表:USER_ROLE_REL.ID列值
	   * <p>列说明:ID
	   */ 
	   public void setId(String id){
	     this.id = id;
	   }
	
	  /**
	   * 获取数据库表:USER_ROLE_REL.USER_ID列值
	   * <p>列说明:用户ID
	   */ 
	   	@Column(name = "USER_ID", length = 32)
	   public String getUserId(){
	     return this.userId;
	   }
	   
	  /**
	   * 设置数据库表:USER_ROLE_REL.USER_ID列值
	   * <p>列说明:用户ID
	   */ 
	   public void setUserId(String userId){
	     this.userId = userId;
	   }
	
	  /**
	   * 获取数据库表:USER_ROLE_REL.ROLE_ID列值
	   * <p>列说明:角色ID
	   */ 
	   	@Column(name = "ROLE_ID", length = 32)
	   public String getRoleId(){
	     return this.roleId;
	   }
	   
	  /**
	   * 设置数据库表:USER_ROLE_REL.ROLE_ID列值
	   * <p>列说明:角色ID
	   */ 
	   public void setRoleId(String roleId){
	     this.roleId = roleId;
	   }
		/**
		 * 将视图对象与业务对象具有相同属性名称的属性值进行拷贝，将视图对象属性值复制给业务对象实例
		 * @param userRoleRel 数据源对象实例
		 * @param userRoleRelView 数据目标对象实例
		 * @return 具有源对象属性值的目标对象实例
		 */
		 public static UserRoleRelView processUserRoleRelToUserRoleRelView(UserRoleRel userRoleRel,UserRoleRelView userRoleRelView){
			userRoleRelView.setId(userRoleRel.getId());
			userRoleRelView.setRoleId(userRoleRel.getRoleId());
			userRoleRelView.setUserId(userRoleRel.getUserId());
			return userRoleRelView;
		}

}