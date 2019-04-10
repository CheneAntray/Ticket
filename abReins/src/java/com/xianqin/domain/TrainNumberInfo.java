package com.xianqin.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
/**
 * 数据库表TRAIN_NUMBER_INFO与业务对象车次信息映射类
 * 对象说明:
 * 系统自动生成
 */
@Entity
@Table(name = "TRAIN_NUMBER_INFO")
public class TrainNumberInfo implements Serializable{
	private static final long serialVersionUID = 1L;
	
	public static final String TABLE_NAME = "TRAIN_NUMBER_INFO";
	//列名与属性名的映射
	public final static String _id="id";
	public final static String _trainNo="trainNo";
	public final static String _startStationId="startStationId";
	public final static String _endStationId="endStationId";
	public final static String _underEnterId="underEnterId";
	public final static String _directionId="directionId";
	
	  /**
	   * 映射列名称:ID
	   * <p>列说明:车次ID
	   */ 
	   private Long id;
	  /**
	   * 映射列名称:TRAIN_NO
	   * <p>列说明:车次编号
	   */ 
	   private String trainNo;
	  /**
	   * 映射列名称:START_STATION_ID
	   * <p>列说明:始发站
	   */ 
	   private Long startStationId;
	  /**
	   * 映射列名称:END_STATION_ID
	   * <p>列说明:终点站
	   */ 
	   private Long endStationId;
	  /**
	   * 映射列名称:UNDER_ENTER_ID
	   * <p>列说明:担当企业ID
	   */ 
	   private Long underEnterId;
	   /**
		   * 映射列名称:DIRECTION_ID
		   * <p>列说明:方向ID
		   */ 
	   private Long directionId;
	
	
	  /**
	   * 获取数据库表:TRAIN_NUMBER_INFO.ID列值
	   * <p>列说明:车次ID
	   */ 
	   @Id
	   @GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "ID")
	   public Long getId(){
	     return this.id;
	   }
	   
	  /**
	   * 设置数据库表:TRAIN_NUMBER_INFO.ID列值
	   * <p>列说明:车次ID
	   */ 
	   public void setId(Long id){
	     this.id = id;
	   }
	
	  /**
	   * 获取数据库表:TRAIN_NUMBER_INFO.TRAIN_NO列值
	   * <p>列说明:车次编号
	   */ 
	   	@Column(name = "TRAIN_NO", length = 5)
	   public String getTrainNo(){
	     return this.trainNo;
	   }
	   
	  /**
	   * 设置数据库表:TRAIN_NUMBER_INFO.TRAIN_NO列值
	   * <p>列说明:车次编号
	   */ 
	   public void setTrainNo(String trainNo){
	     this.trainNo = trainNo;
	   }
	
	  /**
	   * 获取数据库表:TRAIN_NUMBER_INFO.START_STATION_ID列值
	   * <p>列说明:始发站
	   */ 
	   	@Column(name = "START_STATION_ID")
	   public Long getStartStationId(){
	     return this.startStationId;
	   }
	   
	  /**
	   * 设置数据库表:TRAIN_NUMBER_INFO.START_STATION_ID列值
	   * <p>列说明:始发站
	   */ 
	   public void setStartStationId(Long startStationId){
	     this.startStationId = startStationId;
	   }
	
	  /**
	   * 获取数据库表:TRAIN_NUMBER_INFO.END_STATION_ID列值
	   * <p>列说明:终点站
	   */ 
	   	@Column(name = "END_STATION_ID")
	   public Long getEndStationId(){
	     return this.endStationId;
	   }
	   
	  /**
	   * 设置数据库表:TRAIN_NUMBER_INFO.END_STATION_ID列值
	   * <p>列说明:终点站
	   */ 
	   public void setEndStationId(Long endStationId){
	     this.endStationId = endStationId;
	   }
	
	  /**
	   * 获取数据库表:TRAIN_NUMBER_INFO.UNDER_ENTER_ID列值
	   * <p>列说明:担当企业ID
	   */ 
	   	@Column(name = "UNDER_ENTER_ID")
	   public Long getUnderEnterId(){
	     return this.underEnterId;
	   }
	   
	  /**
	   * 设置数据库表:TRAIN_NUMBER_INFO.UNDER_ENTER_ID列值
	   * <p>列说明:担当企业ID
	   */ 
	   public void setUnderEnterId(Long underEnterId){
	     this.underEnterId = underEnterId;
	   }
	   
	   
	   /**
		   * 获取数据库表:TRAIN_NUMBER_INFO.DIRECTION_ID列值
		   * <p>列说明:车次方向ID
		   */ 
		   	@Column(name = "DIRECTION_ID")
		   public Long getDirectionId(){
		     return this.directionId;
		   }
		   
		  /**
		   * 设置数据库表:TRAIN_NUMBER_INFO.DIRECTION_ID列值
		   * <p>列说明:车次方向ID
		   */ 
		   public void setDirectionId(Long directionId){
		     this.directionId = directionId;
		   }
}