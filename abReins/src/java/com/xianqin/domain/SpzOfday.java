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
 * 数据库表SPZ_OFDAY与业务对象售票站日统计映射类
 * 对象说明:售票站日统计表
 * 系统自动生成
 */
@Entity
@Table(name = "SPZ_OFDAY")
public class SpzOfday implements Serializable{
	private static final long serialVersionUID = 1L;
	
	public static final String TABLE_NAME = "SPZ_OFDAY";
	//列名与属性名的映射
	public final static String _id="id";
	public final static String _spzId="spzId";
	public final static String _income="income";
	public final static String _peopleCount="peopleCount";
	public final static String _dataDate="dataDate";
	public final static String _createDate="createDate";
	
	  /**
	   * 映射列名称:ID
	   * <p>列说明:ID
	   */ 
	   private String id;
	  /**
	   * 映射列名称:SPZ_ID
	   * <p>列说明:售票站
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
	   * 映射列名称:DATA_DATE
	   * <p>列说明:数据日期
	   */ 
	   private Date dataDate;
	  /**
	   * 映射列名称:CREATE_DATE
	   * <p>列说明:创建时间
	   */ 
	   private Date createDate;
	
	
	  /**
	   * 获取数据库表:SPZ_OFDAY.ID列值
	   * <p>列说明:ID
	   */ 
	   @Id
	@Column(name = "ID", length = 32)
	   public String getId(){
	     return this.id;
	   }
	   
	  /**
	   * 设置数据库表:SPZ_OFDAY.ID列值
	   * <p>列说明:ID
	   */ 
	   public void setId(String id){
	     this.id = id;
	   }
	
	  /**
	   * 获取数据库表:SPZ_OFDAY.SPZ_ID列值
	   * <p>列说明:售票站
	   */ 
	   	@Column(name = "SPZ_ID")
	   public Long getSpzId(){
	     return this.spzId;
	   }
	   
	  /**
	   * 设置数据库表:SPZ_OFDAY.SPZ_ID列值
	   * <p>列说明:售票站
	   */ 
	   public void setSpzId(Long spzId){
	     this.spzId = spzId;
	   }
	
	  /**
	   * 获取数据库表:SPZ_OFDAY.INCOME列值
	   * <p>列说明:票款
	   */ 
	   	@Column(name = "INCOME")
	   public Double getIncome(){
	     return this.income;
	   }
	   
	  /**
	   * 设置数据库表:SPZ_OFDAY.INCOME列值
	   * <p>列说明:票款
	   */ 
	   public void setIncome(Double income){
	     this.income = income;
	   }
	
	  /**
	   * 获取数据库表:SPZ_OFDAY.PEOPLE_COUNT列值
	   * <p>列说明:售票人数
	   */ 
	   	@Column(name = "PEOPLE_COUNT")
	   public Long getPeopleCount(){
	     return this.peopleCount;
	   }
	   
	  /**
	   * 设置数据库表:SPZ_OFDAY.PEOPLE_COUNT列值
	   * <p>列说明:售票人数
	   */ 
	   public void setPeopleCount(Long peopleCount){
	     this.peopleCount = peopleCount;
	   }
	
	  /**
	   * 获取数据库表:SPZ_OFDAY.DATA_DATE列值
	   * <p>列说明:数据日期
	   */ 
	   	@Temporal(TemporalType.DATE)
	@Column(name = "DATA_DATE", length = 7)
	   public Date getDataDate(){
	     return this.dataDate;
	   }
	   
	  /**
	   * 设置数据库表:SPZ_OFDAY.DATA_DATE列值
	   * <p>列说明:数据日期
	   */ 
	   public void setDataDate(Date dataDate){
	     this.dataDate = dataDate;
	   }
	
	  /**
	   * 获取数据库表:SPZ_OFDAY.CREATE_DATE列值
	   * <p>列说明:创建时间
	   */ 
	   	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATE_DATE", length = 19)
	   public Date getCreateDate(){
	     return this.createDate;
	   }
	   
	  /**
	   * 设置数据库表:SPZ_OFDAY.CREATE_DATE列值
	   * <p>列说明:创建时间
	   */ 
	   public void setCreateDate(Date createDate){
	     this.createDate = createDate;
	   }
}