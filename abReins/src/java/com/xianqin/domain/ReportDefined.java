package com.xianqin.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
/**
 * 数据库表report_defined与业务对象报表定义信息映射类
 * 对象说明:
 * 系统自动生成
 */
@Entity
@Table(name = "REPORT_DEFINED")
public class ReportDefined implements Serializable{
	private static final long serialVersionUID = 1L;
	
	public static final String TABLE_NAME = "report_defined";
	//列名与属性名的映射
	public final static String _id="id";
	public final static String _reportCode="reportCode";
	public final static String _reportName="reportName";
	public final static String _reportFileName="reportFileName";
	
	  /**
	   * 映射列名称:ID
	   * <p>列说明:ID
	   */ 
	   private String id;
	  /**
	   * 映射列名称:REPORT_CODE
	   * <p>列说明:报表编码
	   */ 
	   private String reportCode;
	   /**
	    * 映射列名称:REPORT_NAME
	    * <p>列说明:报表名称
	    */ 
	   private String reportName;
	  /**
	   * 映射列名称:REPORT_FILE_NAME
	   * <p>列说明:报表模版文件名称
	   */ 
	   private String reportFileName;
	
	
	  /**
	   * 获取数据库表:report_defined.ID列值
	   * <p>列说明:ID
	   */ 
	   @Id
	   @Column(name = "ID", length = 32)
	   public String getId(){
	     return this.id;
	   }
	   
	  /**
	   * 设置数据库表:report_defined.ID列值
	   * <p>列说明:ID
	   */ 
	   public void setId(String id){
	     this.id = id;
	   }
	
	  /**
	   * 获取数据库表:report_defined.REPORT_CODE列值
	   * <p>列说明:报表编码
	   */ 
	   @Column(name = "REPORT_CODE", length = 32)
	   public String getReportCode(){
	     return this.reportCode;
	   }
	   
	  /**
	   * 设置数据库表:report_defined.REPORT_CODE列值
	   * <p>列说明:报表编码
	   */ 
	   public void setReportCode(String reportCode){
	     this.reportCode = reportCode;
	   }
	   /**
	    * 获取数据库表:report_defined.REPORT_NAME列值
	    * <p>列说明:报表名称
	    */ 
	   @Column(name = "REPORT_NAME", length = 32)
	   public String getReportName(){
		   return this.reportName;
	   }
	   
	   /**
	    * 设置数据库表:report_defined.REPORT_NAME列值
	    * <p>列说明:报表名称
	    */ 
	   public void setReportName(String reportName){
		   this.reportName = reportName;
	   }
	
	  /**
	   * 获取数据库表:report_defined.REPORT_FILE_NAME列值
	   * <p>列说明:报表模版文件名称
	   */ 
		@Column(name = "REPORT_FILE_NAME", length = 32)
	   public String getReportFileName(){
	     return this.reportFileName;
	   }
	   
	  /**
	   * 设置数据库表:report_defined.REPORT_FILE_NAME列值
	   * <p>列说明:报表模版文件名称
	   */ 
	   public void setReportFileName(String reportFileName){
	     this.reportFileName = reportFileName;
	   }
}