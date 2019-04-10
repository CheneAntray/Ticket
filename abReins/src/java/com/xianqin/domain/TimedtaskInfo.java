package com.xianqin.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.xianqin.view.TimedTask.TimedTaskView;
/**
 * 数据库表TIMEDTASK_INFO与业务对象定时任务表映射类
 * 对象说明:定时任务表
 * 系统自动生成
 */
@Entity
@Table(name = "TIMEDTASK_INFO")
public class TimedtaskInfo implements Serializable{
	private static final long serialVersionUID = 1L;
	
	public static final String TABLE_NAME = "TIMEDTASK_INFO";
	//列名与属性名的映射
	public final static String _id="id";
	public final static String _timedtaskType="timedtaskType";
	public final static String _timedtaskDate="timedtaskDate";
	
	  /**
	   * 映射列名称:ID
	   * <p>列说明:ID
	   */ 
	   private String id;
	  /**
	   * 映射列名称:TIMEDTASK_TYPE
	   * <p>列说明:定时任务类型
	   */ 
	   private Long timedtaskType;
	  /**
	   * 映射列名称:TIMEDTASK_DATE
	   * <p>列说明:定时任务时间
	   */ 
	   private Date timedtaskDate;
	
	
	  /**
	   * 获取数据库表:TIMEDTASK_INFO.ID列值
	   * <p>列说明:ID
	   */ 
	   @Id
	@Column(name = "ID", length = 32)
	   public String getId(){
	     return this.id;
	   }
	   
	  /**
	   * 设置数据库表:TIMEDTASK_INFO.ID列值
	   * <p>列说明:ID
	   */ 
	   public void setId(String id){
	     this.id = id;
	   }
	
	  /**
	   * 获取数据库表:TIMEDTASK_INFO.TIMEDTASK_TYPE列值
	   * <p>列说明:定时任务类型
	   */ 
	   	@Column(name = "TIMEDTASK_TYPE")
	   public Long getTimedtaskType(){
	     return this.timedtaskType;
	   }
	   
	  /**
	   * 设置数据库表:TIMEDTASK_INFO.TIMEDTASK_TYPE列值
	   * <p>列说明:定时任务类型
	   */ 
	   public void setTimedtaskType(Long timedtaskType){
	     this.timedtaskType = timedtaskType;
	   }
	
	  /**
	   * 获取数据库表:TIMEDTASK_INFO.TIMEDTASK_DATE列值
	   * <p>列说明:定时任务时间
	   */ 
	   	@Temporal(TemporalType.DATE)
	@Column(name = "TIMEDTASK_DATE", length = 7)
	   public Date getTimedtaskDate(){
	     return this.timedtaskDate;
	   }
	   
	  /**
	   * 设置数据库表:TIMEDTASK_INFO.TIMEDTASK_DATE列值
	   * <p>列说明:定时任务时间
	   */ 
	   public void setTimedtaskDate(Date timedtaskDate){
	     this.timedtaskDate = timedtaskDate;
	   }
	   
		/**
		 * 将视图对象与业务对象具有相同属性名称的属性值进行拷贝，将视图对象属性值复制给业务对象实例
		 * @param timedTaskView 数据源对象实例
		 * @param timedtaskInfo 数据目标对象实例
		 * @return 具有源对象属性值的目标对象实例
		 */
		 public static TimedtaskInfo processTimedtaskInfoToTimedTaskView(TimedtaskInfo timedtaskInfo,TimedTaskView timedTaskView){
			timedTaskView.setId(timedtaskInfo.getId());
			timedTaskView.setTimedtaskDate(timedtaskInfo.getTimedtaskDate());
			timedTaskView.setTimedtaskType(timedtaskInfo.getTimedtaskType());
			return timedTaskView;
		}
}