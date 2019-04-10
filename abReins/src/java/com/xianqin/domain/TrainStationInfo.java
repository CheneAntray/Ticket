package com.xianqin.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
/**
 * 数据库表TRAIN_STATION_INFO与业务对象车次到发站信息映射类
 * 对象说明:
 * 系统自动生成
 */
@Entity
@Table(name = "TRAIN_STATION_INFO")
public class TrainStationInfo implements Serializable{
	private static final long serialVersionUID = 1L;
	
	public static final String TABLE_NAME = "TRAIN_STATION_INFO";
	//列名与属性名的映射
	public final static String _id="id";
	public final static String _name="name";
	
	  /**
	   * 映射列名称:ID
	   * <p>列说明:
	   */ 
	   private Long id;
	  /**
	   * 映射列名称:NAME
	   * <p>列说明:
	   */ 
	   private String name;
	
	
	  /**
	   * 获取数据库表:TRAIN_STATION_INFO.ID列值
	   * <p>列说明:
	   */ 
	   @Id
	   @GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "ID")
	   public Long getId(){
	     return this.id;
	   }
	   
	  /**
	   * 设置数据库表:TRAIN_STATION_INFO.ID列值
	   * <p>列说明:
	   */ 
	   public void setId(Long id){
	     this.id = id;
	   }
	
	  /**
	   * 获取数据库表:TRAIN_STATION_INFO.NAME列值
	   * <p>列说明:
	   */ 
	   	@Column(name = "NAME", length = 20)
	   public String getName(){
	     return this.name;
	   }
	   
	  /**
	   * 设置数据库表:TRAIN_STATION_INFO.NAME列值
	   * <p>列说明:
	   */ 
	   public void setName(String name){
	     this.name = name;
	   }
}