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
 * 数据库表INCOME_INFO与业务对象收入信息映射类
 * 对象说明:
 * 系统自动生成
 */
@Entity
@Table(name = "INCOME_INFO")
public class IncomeInfo implements Serializable{
	private static final long serialVersionUID = 1L;
	
	public static final String TABLE_NAME = "INCOME_INFO";
	//列名与属性名的映射
	public final static String _id="id";
	public final static String _createDate="createDate";
	public final static String _dataDate="dataDate";
	public final static String _income="income";
	public final static String _peopleCount="peopleCount";
	public final static String _ticketStationId="ticketStationId";
	public final static String _trainNumberId="trainNumberId";
	
	  /**
	   * 映射列名称:ID
	   * <p>列说明:
	   */ 
	   private String id;
	  /**
	   * 映射列名称:CREATE_DATE
	   * <p>列说明:
	   */ 
	   private Date createDate;
	  /**
	   * 映射列名称:DATA_DATE
	   * <p>列说明:
	   */ 
	   private Date dataDate;
	  /**
	   * 映射列名称:INCOME
	   * <p>列说明:
	   */ 
	   private Double income;
	  /**
	   * 映射列名称:PEOPLE_COUNT
	   * <p>列说明:
	   */ 
	   private Long peopleCount;
	  /**
	   * 映射列名称:TICKET_STATION_ID
	   * <p>列说明:
	   */ 
	   private Long ticketStationId;
	  /**
	   * 映射列名称:TRAIN_NUMBER_ID
	   * <p>列说明:
	   */ 
	   private Long trainNumberId;
	
	
	  /**
	   * 获取数据库表:INCOME_INFO.ID列值
	   * <p>列说明:
	   */ 
	   @Id
	@Column(name = "ID", length = 32)
	   public String getId(){
	     return this.id;
	   }
	   
	  /**
	   * 设置数据库表:INCOME_INFO.ID列值
	   * <p>列说明:
	   */ 
	   public void setId(String id){
	     this.id = id;
	   }
	
	  /**
	   * 获取数据库表:INCOME_INFO.CREATE_DATE列值
	   * <p>列说明:
	   */ 
	   	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATE_DATE", length = 19)
	   public Date getCreateDate(){
	     return this.createDate;
	   }
	   
	  /**
	   * 设置数据库表:INCOME_INFO.CREATE_DATE列值
	   * <p>列说明:
	   */ 
	   public void setCreateDate(Date createDate){
	     this.createDate = createDate;
	   }
	
	  /**
	   * 获取数据库表:INCOME_INFO.DATA_DATE列值
	   * <p>列说明:
	   */ 
	   	@Temporal(TemporalType.DATE)
	@Column(name = "DATA_DATE", length = 7)
	   public Date getDataDate(){
	     return this.dataDate;
	   }
	   
	  /**
	   * 设置数据库表:INCOME_INFO.DATA_DATE列值
	   * <p>列说明:
	   */ 
	   public void setDataDate(Date dataDate){
	     this.dataDate = dataDate;
	   }
	
	  /**
	   * 获取数据库表:INCOME_INFO.INCOME列值
	   * <p>列说明:
	   */ 
	   	@Column(name = "INCOME")
	   public Double getIncome(){
	     return this.income;
	   }
	   
	  /**
	   * 设置数据库表:INCOME_INFO.INCOME列值
	   * <p>列说明:
	   */ 
	   public void setIncome(Double income){
	     this.income = income;
	   }
	
	  /**
	   * 获取数据库表:INCOME_INFO.PEOPLE_COUNT列值
	   * <p>列说明:
	   */ 
	   	@Column(name = "PEOPLE_COUNT")
	   public Long getPeopleCount(){
	     return this.peopleCount;
	   }
	   
	  /**
	   * 设置数据库表:INCOME_INFO.PEOPLE_COUNT列值
	   * <p>列说明:
	   */ 
	   public void setPeopleCount(Long peopleCount){
	     this.peopleCount = peopleCount;
	   }
	
	  /**
	   * 获取数据库表:INCOME_INFO.TICKET_STATION_ID列值
	   * <p>列说明:
	   */ 
	   	@Column(name = "TICKET_STATION_ID")
	   public Long getTicketStationId(){
	     return this.ticketStationId;
	   }
	   
	  /**
	   * 设置数据库表:INCOME_INFO.TICKET_STATION_ID列值
	   * <p>列说明:
	   */ 
	   public void setTicketStationId(Long ticketStationId){
	     this.ticketStationId = ticketStationId;
	   }
	
	  /**
	   * 获取数据库表:INCOME_INFO.TRAIN_NUMBER_ID列值
	   * <p>列说明:
	   */ 
	   	@Column(name = "TRAIN_NUMBER_ID")
	   public Long getTrainNumberId(){
	     return this.trainNumberId;
	   }
	   
	  /**
	   * 设置数据库表:INCOME_INFO.TRAIN_NUMBER_ID列值
	   * <p>列说明:
	   */ 
	   public void setTrainNumberId(Long trainNumberId){
	     this.trainNumberId = trainNumberId;
	   }
}