package com.xianqin.view.TimedTask;

import java.io.Serializable;

import com.xianqin.domain.TimedtaskInfo;

public class TimedTaskView extends TimedtaskInfo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	//月日期id
	private String monthId;
	//月日期
	private String monthDate;
	//年日期
	private String yearDate;
	//年日期id
	private String yearId;
	


	public String getMonthId() {
		return monthId;
	}



	public void setMonthId(String monthId) {
		this.monthId = monthId;
	}



	public String getYearId() {
		return yearId;
	}



	public void setYearId(String yearId) {
		this.yearId = yearId;
	}



	public String getMonthDate() {
		return monthDate;
	}



	public void setMonthDate(String monthDate) {
		this.monthDate = monthDate;
	}



	public String getYearDate() {
		return yearDate;
	}



	public void setYearDate(String yearDate) {
		this.yearDate = yearDate;
	}


	/**
	 * 将视图对象与业务对象具有相同属性名称的属性值进行拷贝，将视图对象属性值复制给业务对象实例
	 * @param timedtaskInfo 数据源对象实例
	 * @param timedTaskView 数据目标对象实例
	 * @return 具有源对象属性值的目标对象实例
	 */
	 public static TimedTaskView processTimedtaskInfoToTimedTaskView(TimedtaskInfo timedtaskInfo,TimedTaskView timedTaskView){
		 timedtaskInfo.setId(timedTaskView.getId());
		 timedtaskInfo.setTimedtaskDate(timedTaskView.getTimedtaskDate());
		 timedtaskInfo.setTimedtaskType(timedTaskView.getTimedtaskType());
		return timedTaskView;
	}
}
