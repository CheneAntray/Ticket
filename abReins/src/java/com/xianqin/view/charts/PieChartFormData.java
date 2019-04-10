package com.xianqin.view.charts;

import java.io.Serializable;

public class PieChartFormData implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 站段ID
	 */
	private Long stationSectionId;
	

	/**
	 * 单选按钮值
	 */
	private Integer radio;
	

	/**
	 * 起始年
	 */
	private String startdate;
	
	/**
	 * 终止年
	 */
	private String enddate;
	
	/**
	 * 起始月
	 */
	private String startmonth;
	
	/**
	 * 终止月
	 */
	private String endmonth;

	public Long getStationSectionId() {
		return stationSectionId;
	}

	public void setStationSectionId(Long stationSectionId) {
		this.stationSectionId = stationSectionId;
	}

	public Integer getRadio() {
		return radio;
	}

	public void setRadio(Integer radio) {
		this.radio = radio;
	}

	public String getStartdate() {
		return startdate;
	}

	public void setStartdate(String startdate) {
		this.startdate = startdate;
	}

	public String getEnddate() {
		return enddate;
	}

	public void setEnddate(String enddate) {
		this.enddate = enddate;
	}

	public String getStartmonth() {
		return startmonth;
	}

	public void setStartmonth(String startmonth) {
		this.startmonth = startmonth;
	}

	public String getEndmonth() {
		return endmonth;
	}

	public void setEndmonth(String endmonth) {
		this.endmonth = endmonth;
	}
	
	
	
	

}
