package com.xianqin.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.xianqin.view.direction.TrainDirectionView;
/**
 * 数据库表TRAIN_DIRECTION与业务对象"车次方向信息"映射类
 * 对象说明:
 * 系统自动生成
 */
@Entity
@Table(name = "TRAIN_DIRECTION")
public class TrainDirection implements Serializable{
	private static final long serialVersionUID = 1L;
	
	public static final String TABLE_NAME = "TRAIN_DIRECTION";
	//列名与属性名的映射
	public final static String _id="id";
	public final static String _directionName="directionName";
	
	  /**
	   * 映射列名称:ID
	   * <p>列说明:车次方向ID
	   */ 
	   private Long id;
	  /**
	   * 映射列名称:DIRECTION_NAME
	   * <p>列说明:方向名称
	   */ 
	   private String directionName;
	
	
	  /**
	   * 获取数据库表:TRAIN_DIRECTION.ID列值
	   * <p>列说明:车次方向ID
	   */ 
	   @Id
	   @GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "ID")
	   public Long getId(){
	     return this.id;
	   }
	   
	  /**
	   * 设置数据库表:TRAIN_DIRECTION.ID列值
	   * <p>列说明:车次方向ID
	   */ 
	   public void setId(Long id){
	     this.id = id;
	   }
	
	  /**
	   * 获取数据库表:TRAIN_DIRECTION.DIRECTION_NAME列值
	   * <p>列说明:方向名称
	   */ 
	   @Column(name = "DIRECTION_NAME")
	   public String getDirectionName(){
	     return this.directionName;
	   }
	   
	  /**
	   * 设置数据库表:TRAIN_DIRECTION.DIRECTION_NAME列值
	   * <p>列说明:方向名称
	   */ 
	   public void setDirectionName(String directionName){
	     this.directionName = directionName;
	   }
	   
	   /**
		 * 将视图对象与业务对象具有相同属性名称的属性值进行拷贝，将视图对象属性值复制给业务对象实例
		 * @param trainDirection 数据源对象实例
		 * @param trainDirectionView 数据目标对象实例
		 * @return 具有源对象属性值的目标对象实例
		 */
		 public static TrainDirectionView processTrainDirectionToTrainDirectionView(TrainDirection trainDirection,TrainDirectionView trainDirectionView){
			
			 trainDirectionView.setId(trainDirection.getId());
			 trainDirectionView.setDirectionName(trainDirection.getDirectionName());
			 return trainDirectionView;
		}
}