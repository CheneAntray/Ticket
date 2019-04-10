package com.xianqin.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.xianqin.view.user.UserView;
/**
 * 数据库表USER_INFO与业务对象用户基本信息映射类
 * 对象说明:
 * 系统自动生成
 */
@Entity
@Table(name = "USER_INFO")
public class UserInfo implements Serializable{
	private static final long serialVersionUID = 1L;
	
	public static final String TABLE_NAME = "USER_INFO";
	//列名与属性名的映射
	public final static String _id="id";
	public final static String _account="account";
	public final static String _password="password";
	public final static String _name="name";
	public final static String _createTime="createTime";
	public final static String _createOper="createOper";
	public final static String _updateTime="updateTime";
	public final static String _updateOper="updateOper";
	
	  /**
	   * 映射列名称:ID
	   * <p>列说明:用户ID
	   */ 
	   private String id;
	  /**
	   * 映射列名称:ACCOUNT
	   * <p>列说明:账号
	   */ 
	   private String account;
	  /**
	   * 映射列名称:PASSWORD
	   * <p>列说明:密码
	   */ 
	   private String password;
	  /**
	   * 映射列名称:NAME
	   * <p>列说明:姓名
	   */ 
	   private String name;
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
	   * 获取数据库表:USER_INFO.ID列值
	   * <p>列说明:用户ID
	   */ 
	   @Id
	@Column(name = "ID", length = 32)
	   public String getId(){
	     return this.id;
	   }
	   
	  /**
	   * 设置数据库表:USER_INFO.ID列值
	   * <p>列说明:用户ID
	   */ 
	   public void setId(String id){
	     this.id = id;
	   }
	
	  /**
	   * 获取数据库表:USER_INFO.ACCOUNT列值
	   * <p>列说明:账号
	   */ 
	   	@Column(name = "ACCOUNT", length = 30)
	   public String getAccount(){
	     return this.account;
	   }
	   
	  /**
	   * 设置数据库表:USER_INFO.ACCOUNT列值
	   * <p>列说明:账号
	   */ 
	   public void setAccount(String account){
	     this.account = account;
	   }
	
	  /**
	   * 获取数据库表:USER_INFO.PASSWORD列值
	   * <p>列说明:密码
	   */ 
	   	@Column(name = "PASSWORD", length = 30)
	   public String getPassword(){
	     return this.password;
	   }
	   
	  /**
	   * 设置数据库表:USER_INFO.PASSWORD列值
	   * <p>列说明:密码
	   */ 
	   public void setPassword(String password){
	     this.password = password;
	   }
	
	  /**
	   * 获取数据库表:USER_INFO.NAME列值
	   * <p>列说明:姓名
	   */ 
	   	@Column(name = "NAME", length = 10)
	   public String getName(){
	     return this.name;
	   }
	   
	  /**
	   * 设置数据库表:USER_INFO.NAME列值
	   * <p>列说明:姓名
	   */ 
	   public void setName(String name){
	     this.name = name;
	   }
	
	  /**
	   * 获取数据库表:USER_INFO.CREATE_TIME列值
	   * <p>列说明:创建时间
	   */ 
	   	@Temporal(TemporalType.DATE)
	@Column(name = "CREATE_TIME", length = 7)
	   public Date getCreateTime(){
	     return this.createTime;
	   }
	   
	  /**
	   * 设置数据库表:USER_INFO.CREATE_TIME列值
	   * <p>列说明:创建时间
	   */ 
	   public void setCreateTime(Date createTime){
	     this.createTime = createTime;
	   }
	
	  /**
	   * 获取数据库表:USER_INFO.CREATE_OPER列值
	   * <p>列说明:创建者
	   */ 
	   	@Column(name = "CREATE_OPER", length = 32)
	   public String getCreateOper(){
	     return this.createOper;
	   }
	   
	  /**
	   * 设置数据库表:USER_INFO.CREATE_OPER列值
	   * <p>列说明:创建者
	   */ 
	   public void setCreateOper(String createOper){
	     this.createOper = createOper;
	   }
	
	  /**
	   * 获取数据库表:USER_INFO.UPDATE_TIME列值
	   * <p>列说明:修改时间
	   */ 
	   	@Temporal(TemporalType.DATE)
	@Column(name = "UPDATE_TIME", length = 7)
	   public Date getUpdateTime(){
	     return this.updateTime;
	   }
	   
	  /**
	   * 设置数据库表:USER_INFO.UPDATE_TIME列值
	   * <p>列说明:修改时间
	   */ 
	   public void setUpdateTime(Date updateTime){
	     this.updateTime = updateTime;
	   }
	
	  /**
	   * 获取数据库表:USER_INFO.UPDATE_OPER列值
	   * <p>列说明:修改者
	   */ 
	   	@Column(name = "UPDATE_OPER", length = 32)
	   public String getUpdateOper(){
	     return this.updateOper;
	   }
	   
	  /**
	   * 设置数据库表:USER_INFO.UPDATE_OPER列值
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
		 public static UserView processRoleInfoToUserView(UserView userView,UserInfo userInfo){
			 userView.setCreateOper(userInfo.getCreateOper());
			 userView.setCreateTime(userInfo.getCreateTime());
			 userView.setId(userInfo.getId());
			 userView.setName(userInfo.getName());
			 userView.setPassword(userInfo.getPassword());
			 userView.setAccount(userInfo.getAccount());
			 userView.setUpdateTime(userInfo.getUpdateTime());
			 userView.setUpdateOper(userInfo.getUpdateOper());
			return userView;
		}
}