package com.xianqin.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.xianqin.view.menuinfo.MenuView;
/**
 * 数据库表MENU_INFO与业务对象菜单信息映射类
 * 对象说明:
 * 系统自动生成
 */
@Entity
@Table(name = "MENU_INFO")
public class MenuInfo implements Serializable{
	private static final long serialVersionUID = 1L;
	
	public static final String TABLE_NAME = "MENU_INFO";
	//列名与属性名的映射
	public final static String _id="id";
	public final static String _menuName="menuName";
	public final static String _appId="appId";
	public final static String _menuUrl="menuUrl";
	public final static String _icon="icon";
	public final static String _ordernum="ordernum";
	public final static String _isdefault="isdefault";
	public final static String _description="description";
	public final static String _isleaf="isleaf";
	public final static String _menulv="menulv";
	public final static String _parentid="parentid";
	public final static String _title="title";
	public final static String _activeflag="activeflag";
	public final static String _createtime="createtime";
	public final static String _createOper="createOper";
	public final static String _updateTime="updateTime";
	public final static String _updateOper="updateOper";
	
	  /**
	   * 映射列名称:ID
	   * <p>列说明:功能ID
	   */ 
	   private String id;
	  /**
	   * 映射列名称:MENU_NAME
	   * <p>列说明:功能名称
	   */ 
	   private String menuName;
	  /**
	   * 映射列名称:APP_ID
	   * <p>列说明:
	   */ 
	   private String appId;
	  /**
	   * 映射列名称:MENU_URL
	   * <p>列说明:
	   */ 
	   private String menuUrl;
	  /**
	   * 映射列名称:ICON
	   * <p>列说明:
	   */ 
	   private String icon;
	  /**
	   * 映射列名称:ORDERNUM
	   * <p>列说明:
	   */ 
	   private Long ordernum;
	  /**
	   * 映射列名称:ISDEFAULT
	   * <p>列说明:
	   */ 
	   private String isdefault;
	  /**
	   * 映射列名称:DESCRIPTION
	   * <p>列说明:
	   */ 
	   private String description;
	  /**
	   * 映射列名称:ISLEAF
	   * <p>列说明:
	   */ 
	   private Long isleaf;
	  /**
	   * 映射列名称:MENULV
	   * <p>列说明:
	   */ 
	   private Long menulv;
	  /**
	   * 映射列名称:PARENTID
	   * <p>列说明:
	   */ 
	   private String parentid;
	  /**
	   * 映射列名称:TITLE
	   * <p>列说明:
	   */ 
	   private String title;
	  /**
	   * 映射列名称:ACTIVEFLAG
	   * <p>列说明:
	   */ 
	   private Long activeflag;
	  /**
	   * 映射列名称:CREATETIME
	   * <p>列说明:创建时间
	   */ 
	   private Date createtime;
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
	   * 获取数据库表:MENU_INFO.ID列值
	   * <p>列说明:功能ID
	   */ 
	   @Id
	@Column(name = "ID", length = 32)
	   public String getId(){
	     return this.id;
	   }
	   
	  /**
	   * 设置数据库表:MENU_INFO.ID列值
	   * <p>列说明:功能ID
	   */ 
	   public void setId(String id){
	     this.id = id;
	   }
	
	  /**
	   * 获取数据库表:MENU_INFO.MENU_NAME列值
	   * <p>列说明:功能名称
	   */ 
	   	@Column(name = "MENU_NAME", length = 20)
	   public String getMenuName(){
	     return this.menuName;
	   }
	   
	  /**
	   * 设置数据库表:MENU_INFO.MENU_NAME列值
	   * <p>列说明:功能名称
	   */ 
	   public void setMenuName(String menuName){
	     this.menuName = menuName;
	   }
	
	  /**
	   * 获取数据库表:MENU_INFO.APP_ID列值
	   * <p>列说明:
	   */ 
	   	@Column(name = "APP_ID", length = 20)
	   public String getAppId(){
	     return this.appId;
	   }
	   
	  /**
	   * 设置数据库表:MENU_INFO.APP_ID列值
	   * <p>列说明:
	   */ 
	   public void setAppId(String appId){
	     this.appId = appId;
	   }
	
	  /**
	   * 获取数据库表:MENU_INFO.MENU_URL列值
	   * <p>列说明:
	   */ 
	   	@Column(name = "MENU_URL", length = 100)
	   public String getMenuUrl(){
	     return this.menuUrl;
	   }
	   
	  /**
	   * 设置数据库表:MENU_INFO.MENU_URL列值
	   * <p>列说明:
	   */ 
	   public void setMenuUrl(String menuUrl){
	     this.menuUrl = menuUrl;
	   }
	
	  /**
	   * 获取数据库表:MENU_INFO.ICON列值
	   * <p>列说明:
	   */ 
	   	@Column(name = "ICON", length = 30)
	   public String getIcon(){
	     return this.icon;
	   }
	   
	  /**
	   * 设置数据库表:MENU_INFO.ICON列值
	   * <p>列说明:
	   */ 
	   public void setIcon(String icon){
	     this.icon = icon;
	   }
	
	  /**
	   * 获取数据库表:MENU_INFO.ORDERNUM列值
	   * <p>列说明:
	   */ 
	   	@Column(name = "ORDERNUM")
	   public Long getOrdernum(){
	     return this.ordernum;
	   }
	   
	  /**
	   * 设置数据库表:MENU_INFO.ORDERNUM列值
	   * <p>列说明:
	   */ 
	   public void setOrdernum(Long ordernum){
	     this.ordernum = ordernum;
	   }
	
	  /**
	   * 获取数据库表:MENU_INFO.ISDEFAULT列值
	   * <p>列说明:
	   */ 
	   	@Column(name = "ISDEFAULT", length = 10)
	   public String getIsdefault(){
	     return this.isdefault;
	   }
	   
	  /**
	   * 设置数据库表:MENU_INFO.ISDEFAULT列值
	   * <p>列说明:
	   */ 
	   public void setIsdefault(String isdefault){
	     this.isdefault = isdefault;
	   }
	
	  /**
	   * 获取数据库表:MENU_INFO.DESCRIPTION列值
	   * <p>列说明:
	   */ 
	   	@Column(name = "DESCRIPTION", length = 20)
	   public String getDescription(){
	     return this.description;
	   }
	   
	  /**
	   * 设置数据库表:MENU_INFO.DESCRIPTION列值
	   * <p>列说明:
	   */ 
	   public void setDescription(String description){
	     this.description = description;
	   }
	
	  /**
	   * 获取数据库表:MENU_INFO.ISLEAF列值
	   * <p>列说明:
	   */ 
	   	@Column(name = "ISLEAF")
	   public Long getIsleaf(){
	     return this.isleaf;
	   }
	   
	  /**
	   * 设置数据库表:MENU_INFO.ISLEAF列值
	   * <p>列说明:
	   */ 
	   public void setIsleaf(Long isleaf){
	     this.isleaf = isleaf;
	   }
	
	  /**
	   * 获取数据库表:MENU_INFO.MENULV列值
	   * <p>列说明:
	   */ 
	   	@Column(name = "MENULV")
	   public Long getMenulv(){
	     return this.menulv;
	   }
	   
	  /**
	   * 设置数据库表:MENU_INFO.MENULV列值
	   * <p>列说明:
	   */ 
	   public void setMenulv(Long menulv){
	     this.menulv = menulv;
	   }
	
	  /**
	   * 获取数据库表:MENU_INFO.PARENTID列值
	   * <p>列说明:
	   */ 
	   	@Column(name = "PARENTID", length = 32)
	   public String getParentid(){
	     return this.parentid;
	   }
	   
	  /**
	   * 设置数据库表:MENU_INFO.PARENTID列值
	   * <p>列说明:
	   */ 
	   public void setParentid(String parentid){
	     this.parentid = parentid;
	   }
	
	  /**
	   * 获取数据库表:MENU_INFO.TITLE列值
	   * <p>列说明:
	   */ 
	   	@Column(name = "TITLE", length = 20)
	   public String getTitle(){
	     return this.title;
	   }
	   
	  /**
	   * 设置数据库表:MENU_INFO.TITLE列值
	   * <p>列说明:
	   */ 
	   public void setTitle(String title){
	     this.title = title;
	   }
	
	  /**
	   * 获取数据库表:MENU_INFO.ACTIVEFLAG列值
	   * <p>列说明:
	   */ 
	   	@Column(name = "ACTIVEFLAG")
	   public Long getActiveflag(){
	     return this.activeflag;
	   }
	   
	  /**
	   * 设置数据库表:MENU_INFO.ACTIVEFLAG列值
	   * <p>列说明:
	   */ 
	   public void setActiveflag(Long activeflag){
	     this.activeflag = activeflag;
	   }
	
	  /**
	   * 获取数据库表:MENU_INFO.CREATETIME列值
	   * <p>列说明:创建时间
	   */ 
	   	@Temporal(TemporalType.DATE)
	@Column(name = "CREATETIME", length = 7)
	   public Date getCreatetime(){
	     return this.createtime;
	   }
	   
	  /**
	   * 设置数据库表:MENU_INFO.CREATETIME列值
	   * <p>列说明:创建时间
	   */ 
	   public void setCreatetime(Date createtime){
	     this.createtime = createtime;
	   }
	
	  /**
	   * 获取数据库表:MENU_INFO.CREATE_OPER列值
	   * <p>列说明:创建者
	   */ 
	   	@Column(name = "CREATE_OPER", length = 32)
	   public String getCreateOper(){
	     return this.createOper;
	   }
	   
	  /**
	   * 设置数据库表:MENU_INFO.CREATE_OPER列值
	   * <p>列说明:创建者
	   */ 
	   public void setCreateOper(String createOper){
	     this.createOper = createOper;
	   }
	
	  /**
	   * 获取数据库表:MENU_INFO.UPDATE_TIME列值
	   * <p>列说明:修改时间
	   */ 
	   	@Temporal(TemporalType.DATE)
	@Column(name = "UPDATE_TIME", length = 7)
	   public Date getUpdateTime(){
	     return this.updateTime;
	   }
	   
	  /**
	   * 设置数据库表:MENU_INFO.UPDATE_TIME列值
	   * <p>列说明:修改时间
	   */ 
	   public void setUpdateTime(Date updateTime){
	     this.updateTime = updateTime;
	   }
	
	  /**
	   * 获取数据库表:MENU_INFO.UPDATE_OPER列值
	   * <p>列说明:修改者
	   */ 
	   	@Column(name = "UPDATE_OPER", length = 32)
	   public String getUpdateOper(){
	     return this.updateOper;
	   }
	   
	  /**
	   * 设置数据库表:MENU_INFO.UPDATE_OPER列值
	   * <p>列说明:修改者
	   */ 
	   public void setUpdateOper(String updateOper){
	     this.updateOper = updateOper;
	   }
	   /* 将视图对象与业务对象具有相同属性名称的属性值进行拷贝，将视图对象属性值复制给业务对象实例
		 * @param urlInfo 数据源对象实例
		 * @param urlView 数据目标对象实例
		 * @return 具有源对象属性值的目标对象实例
		 */
		 public static MenuView processMenuInfoToUrlView(MenuInfo menuInfo,MenuView menuView){
			 menuView.setId(menuInfo.getId());
			 menuView.setMenuName(menuInfo.getMenuName());
			 menuView.setMenuUrl(menuInfo.getMenuUrl());
			 menuView.setCreatetime(menuInfo.getCreatetime());
			 menuView.setCreateOper(menuInfo.getCreateOper());
			 menuView.setUpdateTime(menuInfo.getUpdateTime());
			 menuView.setUpdateOper(menuInfo.getUpdateOper());
			return menuView;
		}
}