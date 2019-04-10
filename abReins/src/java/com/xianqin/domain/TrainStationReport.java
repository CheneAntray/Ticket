package com.xianqin.domain;

import java.math.BigDecimal;

public class TrainStationReport {
	//车站id
	private String stationId;
	//报表名称
	private String reportName;
	//售票站id
	private Long ticketId;
	//售票站名称
	private String ticketName;
	// 站段名称
	private String stationName;
	// 收入
	private double income;
	// 人数
	private int peopleCount;
	// 售票率
	private BigDecimal ticketRate;

	// 总收入
	private double incomeSum;
	// 总人数
	private int peopleCountSum;
	// 总售票率
	private BigDecimal ticketRateSum;

	//开始时间
	private String startDate;
	//结束时间
	private String endDate;
	
	public double getIncomeSum() {
		return incomeSum;
	}

	public void setIncomeSum(double incomeSum) {
		this.incomeSum = incomeSum;
	}

	public int getPeopleCountSum() {
		return peopleCountSum;
	}

	public void setPeopleCountSum(int peopleCountSum) {
		this.peopleCountSum = peopleCountSum;
	}

	public BigDecimal getTicketRateSum() {
		return ticketRateSum;
	}

	public void setTicketRateSum(BigDecimal ticketRateSum) {
		this.ticketRateSum = ticketRateSum;
	}

	public String getStationName() {
		return stationName;
	}

	public void setStationName(String stationName) {
		this.stationName = stationName;
	}

	public double getIncome() {
		return income;
	}

	public void setIncome(double income) {
		this.income = income;
	}

	public int getPeopleCount() {
		return peopleCount;
	}

	public void setPeopleCount(int peopleCount) {
		this.peopleCount = peopleCount;
	}

	public String getStationId() {
		return stationId;
	}

	public void setStationId(String stationId) {
		this.stationId = stationId;
	}

	public Long getTicketId() {
		return ticketId;
	}

	public void setTicketId(Long ticketId) {
		this.ticketId = ticketId;
	}
	
	public String getTicketName() {
		return ticketName;
	}

	public void setTicketName(String ticketName) {
		this.ticketName = ticketName;
	}

	public BigDecimal getTicketRate() {
		return ticketRate;
	}

	public void setTicketRate(BigDecimal ticketRate) {
		this.ticketRate = ticketRate;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getReportName() {
		return reportName;
	}

	public void setReportName(String reportName) {
		this.reportName = reportName;
	}
}
