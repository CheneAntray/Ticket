package com.xianqin.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.xianqin.view.urlinfo.UrlView;

/**
 * 数据库表URL_INFO与业务对象路径信息映射类
 * 对象说明:
 * 系统自动生成
 */
@Entity
@Table(name = "URL_INFO")
public class UrlInfo implements Serializable{
	private static final long serialVersionUID = 1L;
	
	public static final String TABLE_NAME = "URL_INFO";
	//列名与属性名的映射
	public final static String _id="id";
	public final static String _urlName="urlName";
	public final static String _urlPath="urlPath";
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
	   * 映射列名称:URL_NAME
	   * <p>列说明:角色名称
	   */ 
	   private String urlName;
	  /**
	   * 映射列名称:URL_PATH
	   * <p>列说明:
	   */ 
	   private String urlPath;
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
	   * 获取数据库表:URL_INFO.ID列值
	   * <p>列说明:角色ID
	   */ 
	   @Id
	@Column(name = "ID", length = 32)
	   public String getId(){
	     return this.id;
	   }
	   
	  /**
	   * 设置数据库表:URL_INFO.ID列值
	   * <p>列说明:角色ID
	   */ 
	   public void setId(String id){
	     this.id = id;
	   }
	
	  /**
	   * 获取数据库表:URL_INFO.URL_NAME列值
	   * <p>列说明:角色名称
	   */ 
	   	@Column(name = "URL_NAME", length = 200)
	   public String getUrlName(){
	     return this.urlName;
	   }
	   
	  /**
	   * 设置数据库表:URL_INFO.URL_NAME列值
	   * <p>列说明:角色名称
	   */ 
	   public void setUrlName(String urlName){
	     this.urlName = urlName;
	   }
	
	  /**
	   * 获取数据库表:URL_INFO.URL_PATH列值
	   * <p>列说明:
	   */ 
	   	@Column(name = "URL_PATH", length = 200)
	   public String getUrlPath(){
	     return this.urlPath;
	   }
	   
	  /**
	   * 设置数据库表:URL_INFO.URL_PATH列值
	   * <p>列说明:
	   */ 
	   public void setUrlPath(String urlPath){
	     this.urlPath = urlPath;
	   }
	
	  /**
	   * 获取数据库表:URL_INFO.CREATE_TIME列值
	   * <p>列说明:创建时间
	   */ 
	   	@Temporal(TemporalType.DATE)
	@Column(name = "CREATE_TIME", length = 7)
	   public Date getCreateTime(){
	     return this.createTime;
	   }
	   
	  /**
	   * 设置数据库表:URL_INFO.CREATE_TIME列值
	   * <p>列说明:创建时间
	   */ 
	   public void setCreateTime(Date createTime){
	     this.createTime = createTime;
	   }
	
	  /**
	   * 获取数据库表:URL_INFO.CREATE_OPER列值
	   * <p>列说明:创建者
	   */ 
	   	@Column(name = "CREATE_OPER", length = 32)
	   public String getCreateOper(){
	     return this.createOper;
	   }
	   
	  /**
	   * 设置数据库表:URL_INFO.CREATE_OPER列值
	   * <p>列说明:创建者
	   */ 
	   public void setCreateOper(String createOper){
	     this.createOper = createOper;
	   }
	
	  /**
	   * 获取数据库表:URL_INFO.UPDATE_TIME列值
	   * <p>列说明:修改时间
	   */ 
	   	@Temporal(TemporalType.DATE)
	@Column(name = "UPDATE_TIME", length = 7)
	   public Date getUpdateTime(){
	     return this.updateTime;
	   }
	   
	  /**
	   * 设置数据库表:URL_INFO.UPDATE_TIME列值
	   * <p>列说明:修改时间
	   */ 
	   public void setUpdateTime(Date updateTime){
	     this.updateTime = updateTime;
	   }
	
	  /**
	   * 获取数据库表:URL_INFO.UPDATE_OPER列值
	   * <p>列说明:修改者
	   */ 
	   	@Column(name = "UPDATE_OPER", length = 32)
	   public String getUpdateOper(){
	     return this.updateOper;
	   }
	   
	  /**
	   * 设置数据库表:URL_INFO.UPDATE_OPER列值
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
		 public static UrlView processUrlInfoToUrlView(UrlInfo urlInfo,UrlView urlView){
			 urlView.setId(urlInfo.getId());
			 urlView.setUrlName(urlInfo.getUrlName());
			 urlView.setUrlPath(urlInfo.getUrlPath());
			 urlView.setCreateTime(urlInfo.getCreateTime());
			 urlView.setCreateOper(urlInfo.getCreateOper());
			 urlView.setUpdateTime(urlInfo.getUpdateTime());
			 urlView.setUpdateOper(urlInfo.getUpdateOper());
			return urlView;
		}
	   
}