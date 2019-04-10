package com.xianqin.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.xianqin.view.roleinfo.RoleView;
/**
 * 数据库表ROLE_INFO与业务对象角色基本信息映射类
 * 对象说明:
 * 系统自动生成
 */
@Entity
@Table(name = "ROLE_INFO")
public class RoleInfo implements Serializable{
	private static final long serialVersionUID = 1L;
	
	public static final String TABLE_NAME = "ROLE_INFO";
	//列名与属性名的映射
	public final static String _id="id";
	public final static String _roleName="roleName";
	public final static String _createTime="createTime";
	public final static String _createOper="createOper";
	public final static String _updateTime="updateTime";
	public final static String _updateOper="updateOper";
	
	  /**
	   * 映射列名称:ID
	   * <p>列说明:角色ID
	   */ 
	   private String id;
	  /**
	   * 映射列名称:ROLE_NAME
	   * <p>列说明:角色名称
	   */ 
	   private String roleName;
	  /**
	   * 映射列名称:CREATE_TIME
	   * <p>列说明:创建时间
	   */ 
	   private Date createTime;
	  /**
	   * 映射列名称:CREATE_OPER
	   * <p>列说明:创建者
	   */ 
	   private String createOper;
	  /**
	   * 映射列名称:UPDATE_TIME
	   * <p>列说明:修改时间
	   */ 
	   private Date updateTime;
	  /**
	   * 映射列名称:UPDATE_OPER
	   * <p>列说明:修改者
	   */ 
	   private String updateOper;
	
	
	  /**
	   * 获取数据库表:ROLE_INFO.ID列值
	   * <p>列说明:角色ID
	   */ 
	   @Id
	@Column(name = "ID", length = 32)
	   public String getId(){
	     return this.id;
	   }
	   
	  /**
	   * 设置数据库表:ROLE_INFO.ID列值
	   * <p>列说明:角色ID
	   */ 
	   public void setId(String id){
	     this.id = id;
	   }
	
	  /**
	   * 获取数据库表:ROLE_INFO.ROLE_NAME列值
	   * <p>列说明:角色名称
	   */ 
	   	@Column(name = "ROLE_NAME", length = 20)
	   public String getRoleName(){
	     return this.roleName;
	   }
	   
	  /**
	   * 设置数据库表:ROLE_INFO.ROLE_NAME列值
	   * <p>列说明:角色名称
	   */ 
	   public void setRoleName(String roleName){
	     this.roleName = roleName;
	   }
	
	  /**
	   * 获取数据库表:ROLE_INFO.CREATE_TIME列值
	   * <p>列说明:创建时间
	   */ 
	   	@Temporal(TemporalType.DATE)
	@Column(name = "CREATE_TIME", length = 7)
	   public Date getCreateTime(){
	     return this.createTime;
	   }
	   
	  /**
	   * 设置数据库表:ROLE_INFO.CREATE_TIME列值
	   * <p>列说明:创建时间
	   */ 
	   public void setCreateTime(Date createTime){
	     this.createTime = createTime;
	   }
	
	  /**
	   * 获取数据库表:ROLE_INFO.CREATE_OPER列值
	   * <p>列说明:创建者
	   */ 
	   	@Column(name = "CREATE_OPER", length = 32)
	   public String getCreateOper(){
	     return this.createOper;
	   }
	   
	  /**
	   * 设置数据库表:ROLE_INFO.CREATE_OPER列值
	   * <p>列说明:创建者
	   */ 
	   public void setCreateOper(String createOper){
	     this.createOper = createOper;
	   }
	
	  /**
	   * 获取数据库表:ROLE_INFO.UPDATE_TIME列值
	   * <p>列说明:修改时间
	   */ 
	   	@Temporal(TemporalType.DATE)
	@Column(name = "UPDATE_TIME", length = 7)
	   public Date getUpdateTime(){
	     return this.updateTime;
	   }
	   
	  /**
	   * 设置数据库表:ROLE_INFO.UPDATE_TIME列值
	   * <p>列说明:修改时间
	   */ 
	   public void setUpdateTime(Date updateTime){
	     this.updateTime = updateTime;
	   }
	
	  /**
	   * 获取数据库表:ROLE_INFO.UPDATE_OPER列值
	   * <p>列说明:修改者
	   */ 
	   	@Column(name = "UPDATE_OPER", length = 32)
	   public String getUpdateOper(){
	     return this.updateOper;
	   }
	   
	  /**
	   * 设置数据库表:ROLE_INFO.UPDATE_OPER列值
	   * <p>列说明:修改者
	   */ 
	   public void setUpdateOper(String updateOper){
	     this.updateOper = updateOper;
	   }
	   /**
			 * 将业务对象与视图对象具有相同属性名称的属性值进行拷贝，将业务对象实例属性值复制给视图对象
			 * @param roleInfo 数据源对象实例
			 * @param roleView 数据目标对象实例
			 * @return 具有源对象属性值的目标对象实例
			 */
			 public static RoleView processRoleInfoToRoleView(RoleView roleView,RoleInfo roleInfo){
				roleView.setCreateOper(roleInfo.getCreateOper());
				roleView.setCreateTime(roleInfo.getCreateTime());
				roleView.setId(roleInfo.getId());
				roleView.setRoleName(roleInfo.getRoleName());
				roleView.setUpdateTime(roleInfo.getUpdateTime());
				roleView.setUpdateOper(roleInfo.getUpdateOper());
				return roleView;
			}
}