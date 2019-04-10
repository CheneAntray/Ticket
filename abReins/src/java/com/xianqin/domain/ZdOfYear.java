package com.xianqin.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
/**
 * 数据库表ZD_OFYEAR与业务对象站段年统计映射类
 * 对象说明:
 * 系统自动生成
 */
@Entity
@Table(name = "ZD_OFYEAR")
public class ZdOfYear implements Serializable{
	private static final long serialVersionUID = 1L;
	
	public static final String TABLE_NAME = "ZD_OFYEAR";
	//列名与属性名的映射
	public final static String _id="id";
	public final static String _zdId="zdId";
	public final static String _income="income";
	public final static String _peopleCount="peopleCount";
	public final static String _year="year";
	public final static String _createDate="createDate";
	
	  /**
	   * 映射列名称:ID
	   * <p>列说明:ID
	   */ 
	   private String id;
	  /**
	   * 映射列名称:ZD_ID
	   * <p>列说明:站段ID
	   */ 
	   private Long zdId;
	  /**
	   * 映射列名称:INCOME
	   * <p>列说明:票款
	   */ 
	   private Double income;
	  /**
	   * 映射列名称:PEOPLE_COUNT
	   * <p>列说明:售票人数
	   */ 
	   private Long peopleCount;
	  /**
	   * 映射列名称:YEAR
	   * <p>列说明:年份
	   */ 
	   private Long year;
	  /**
	   * 映射列名称:CREATE_DATE
	   * <p>列说明:创建时间
	   */ 
	   private Date createDate;
	
	
	  /**
	   * 获取数据库表:ZD_OFYEAR.ID列值
	   * <p>列说明:ID
	   */ 
	   @Id
	@Column(name = "ID", length = 32)
	   public String getId(){
	     return this.id;
	   }
	   
	  /**
	   * 设置数据库表:ZD_OFYEAR.ID列值
	   * <p>列说明:ID
	   */ 
	   public void setId(String id){
	     this.id = id;
	   }
	
	  /**
	   * 获取数据库表:ZD_OFYEAR.ZD_ID列值
	   * <p>列说明:站段ID
	   */ 
	   	@Column(name = "ZD_ID")
	   public Long getZdId(){
	     return this.zdId;
	   }
	   
	  /**
	   * 设置数据库表:ZD_OFYEAR.ZD_ID列值
	   * <p>列说明:站段ID
	   */ 
	   public void setZdId(Long zdId){
	     this.zdId = zdId;
	   }
	
	  /**
	   * 获取数据库表:ZD_OFYEAR.INCOME列值
	   * <p>列说明:票款
	   */ 
	   	@Column(name = "INCOME")
	   public Double getIncome(){
	     return this.income;
	   }
	   
	  /**
	   * 设置数据库表:ZD_OFYEAR.INCOME列值
	   * <p>列说明:票款
	   */ 
	   public void setIncome(Double income){
	     this.income = income;
	   }
	
	  /**
	   * 获取数据库表:ZD_OFYEAR.PEOPLE_COUNT列值
	   * <p>列说明:售票人数
	   */ 
	   	@Column(name = "PEOPLE_COUNT")
	   public Long getPeopleCount(){
	     return this.peopleCount;
	   }
	   
	  /**
	   * 设置数据库表:ZD_OFYEAR.PEOPLE_COUNT列值
	   * <p>列说明:售票人数
	   */ 
	   public void setPeopleCount(Long peopleCount){
	     this.peopleCount = peopleCount;
	   }
	
	  /**
	   * 获取数据库表:ZD_OFYEAR.YEAR列值
	   * <p>列说明:年份
	   */ 
	   	@Column(name = "YEAR")
	   public Long getYear(){
	     return this.year;
	   }
	   
	  /**
	   * 设置数据库表:ZD_OFYEAR.YEAR列值
	   * <p>列说明:年份
	   */ 
	   public void setYear(Long year){
	     this.year = year;
	   }
	
	  /**
	   * 获取数据库表:ZD_OFYEAR.CREATE_TIME列值
	   * <p>列说明:创建时间
	   */ 
	   	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATE_DATE", length = 19)
	   public Date getCreateDate(){
	     return this.createDate;
	   }
	   
	  /**
	   * 设置数据库表:ZD_OFYEAR.CREATE_TIME列值
	   * <p>列说明:创建时间
	   */ 
	   public void setCreateDate(Date createDate){
	     this.createDate = createDate;
	   }
}