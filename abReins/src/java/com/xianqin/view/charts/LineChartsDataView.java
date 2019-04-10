package com.xianqin.view.charts;

import java.io.Serializable;

public class LineChartsDataView implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String[] labels;
	
	private LineChartsDataSets[] datasets;

	public String[] getLabels() {
		return labels;
	}

	public void setLabels(String[] labels) {
		this.labels = labels;
	}

	public LineChartsDataSets[] getDatasets() {
		return datasets;
	}

	public void setDatasets(LineChartsDataSets[] datasets) {
		this.datasets = datasets;
	}
	
	
	
	
	

}
