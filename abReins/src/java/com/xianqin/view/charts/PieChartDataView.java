package com.xianqin.view.charts;

import java.io.Serializable;

public class PieChartDataView implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Object value;
	
	private String color;
	
	private String label;
	
	

    

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object object) {
		this.value = object;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}
	

}
