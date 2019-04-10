package com.xianqin.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class TrainNumberReport implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// 车辆号码
	private String trainNo;
	//报表名称
	private String reportName;
	// 车站收入
	private double income;
	// 车站人数
	private Long peopleCount;
	// 起始站id
	private String startStationId;
	// 终点站id
	private String endStationId;
	// 担当企业id
	private String underEnterId;
	// 起始日期
	private Date startDate;
	// 结束日期
	private Date endDate;
	// 售票率
	private double ticketRate;
	
	//总收入
	private double incomeSum;
	//总人数
	private int peopleCountSum;
	//总售票率
	private BigDecimal ticketRateSum;

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

	public double getTicketRate() {
		return ticketRate;
	}

	public void setTicketRate(double ticketRate) {
		this.ticketRate = ticketRate;
	}

	public String getTrainNo() {
		return trainNo;
	}

	public void setTrainNo(String trainNo) {
		this.trainNo = trainNo;
	}

	public double getIncome() {
		return income;
	}

	public void setIncome(double income) {
		this.income = income;
	}

	public Long getPeopleCount() {
		return peopleCount;
	}

	public void setPeopleCount(Long peopleCount) {
		this.peopleCount = peopleCount;
	}

	public String getStartStationId() {
		return startStationId;
	}

	public void setStartStationId(String startStationId) {
		this.startStationId = startStationId;
	}

	public String getEndStationId() {
		return endStationId;
	}

	public void setEndStationId(String endStationId) {
		this.endStationId = endStationId;
	}

	public String getUnderEnterId() {
		return underEnterId;
	}

	public void setUnderEnterId(String underEnterId) {
		this.underEnterId = underEnterId;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getReportName() {
		return reportName;
	}

	public void setReportName(String reportName) {
		this.reportName = reportName;
	}
}
