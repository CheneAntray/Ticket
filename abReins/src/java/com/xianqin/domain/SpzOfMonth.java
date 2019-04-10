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
 * 数据库表SPZ_OFMONTH与业务对象售票站月统计映射类
 * 对象说明:
 * 系统自动生成
 */
@Entity
@Table(name = "SPZ_OFMONTH")
public class SpzOfMonth implements Serializable{
	private static final long serialVersionUID = 1L;
	
	public static final String TABLE_NAME = "SPZ_OFMONTH";
	//列名与属性名的映射
	public final static String _id="id";
	public final static String _spzId="spzId";
	public final static String _income="income";
	public final static String _peopleCount="peopleCount";
	public final static String _month="month";
	public final static String _createDate="createDate";
	
	  /**
	   * 映射列名称:ID
	   * <p>列说明:ID
	   */ 
	   private String id;
	  /**
	   * 映射列名称:SPD_ID
	   * <p>列说明: 售票站ID
	   */ 
	   private Long spzId;
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
	   * 映射列名称:MONTH
	   * <p>列说明:月份
	   */ 
	   private String month;
	  /**
	   * 映射列名称:CREATE_TIME
	   * <p>列说明:创建时间
	   */ 
	   private Date createDate;
	
	
	  /**
	   * 获取数据库表:SPZ_OFMONTH.ID列值
	   * <p>列说明:ID
	   */ 
	   @Id
	@Column(name = "ID", length = 32)
	   public String getId(){
	     return this.id;
	   }
	   
	  /**
	   * 设置数据库表:SPZ_OFMONTH.ID列值
	   * <p>列说明:ID
	   */ 
	   public void setId(String id){
	     this.id = id;
	   }
	
	  /**
	   * 获取数据库表:SPZ_OFMONTH.SPD_ID列值
	   * <p>列说明: 售票站ID
	   */ 
	   	@Column(name = "SPZ_ID")
	   public Long getSpzId(){
	     return this.spzId;
	   }
	   
	  /**
	   * 设置数据库表:SPZ_OFMONTH.SPD_ID列值
	   * <p>列说明: 售票站ID
	   */ 
	   public void setSpzId(Long spzId){
	     this.spzId = spzId;
	   }
	
	  /**
	   * 获取数据库表:SPZ_OFMONTH.INCOME列值
	   * <p>列说明:票款
	   */ 
	   	@Column(name = "INCOME")
	   public Double getIncome(){
	     return this.income;
	   }
	   
	  /**
	   * 设置数据库表:SPZ_OFMONTH.INCOME列值
	   * <p>列说明:票款
	   */ 
	   public void setIncome(Double income){
	     this.income = income;
	   }
	
	  /**
	   * 获取数据库表:SPZ_OFMONTH.PEOPLE_COUNT列值
	   * <p>列说明:售票人数
	   */ 
	   	@Column(name = "PEOPLE_COUNT")
	   public Long getPeopleCount(){
	     return this.peopleCount;
	   }
	   
	  /**
	   * 设置数据库表:SPZ_OFMONTH.PEOPLE_COUNT列值
	   * <p>列说明:售票人数
	   */ 
	   public void setPeopleCount(Long peopleCount){
	     this.peopleCount = peopleCount;
	   }
	
	  /**
	   * 获取数据库表:SPZ_OFMONTH.MONTH列值
	   * <p>列说明:月份
	   */ 
	   	@Column(name = "MONTH",length = 20)
	   public String getMonth(){
	     return this.month;
	   }
	   
	  /**
	   * 设置数据库表:SPZ_OFMONTH.MONTH列值
	   * <p>列说明:月份
	   */ 
	   public void setMonth(String month){
	     this.month = month;
	   }
	
	  /**
	   * 获取数据库表:SPZ_OFMONTH.CREATE_TIME列值
	   * <p>列说明:创建时间
	   */ 
	   	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATE_DATE", length = 19)
	   public Date getCreateDate(){
	     return this.createDate;
	   }
	   
	  /**
	   * 设置数据库表:SPZ_OFMONTH.CREATE_TIME列值
	   * <p>列说明:创建时间
	   */ 
	   public void setCreateDate(Date createDate){
	     this.createDate = createDate;
	   }
}