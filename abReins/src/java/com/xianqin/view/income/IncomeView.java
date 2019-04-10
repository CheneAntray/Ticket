package com.xianqin.view.income;

import java.io.Serializable;
import java.util.Date;

public class IncomeView implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * ID
	 */
	private String id;
	/**
	 * 站段ID
	 */
	private String stationSectionId;
	/**
	 * 站段名称
	 */
	private String stationSectionName;
	/**
	 * 售票站ID
	 */
	private String ticketStationId;
	/**
	 * 售票站名称
	 */
	private String ticketStationName;
	/**
	 * 车次编号
	 */
	private String trainNo;
	/**
	 * 车次始发站
	 */
	private String startStation;
	/**
	 * 车次终到站
	 */
	private String endStation;
    /**
     * 担当企业ID
     */
	private String uepId;
	/**
     * 担当企业名称
     */
	private String uepName;
	/**
	 * 售票时间
	 */
	private Date ticketDate;
	/**
	 * 票款
	 */
	private double income;
	/**
	 * 售票人数
	 */
	private Long peopleCount;
	/**
	 * 数据日期
	 */
	private Date dataDate;
	/**
	 * 查询起期
	 */
	private Date startdate;
	/**
	 * 查询止期
	 */
	private Date enddate;
	/**
	 * 总收入
	 */
	private double totalIncome;
	/**
	 * 总售票人数
	 */
	private Long totalPeopleCount;
	/**
	 * 当前页码
	 */
	private Integer page;
	/**
	 * 每页显示的数量
	 */
	private Integer rows;
	
	/**
	 * 售票率
	 */
	private String ticketRate;
	
	
	public String getStationSectionId() {
		return stationSectionId;
	}
	public void setStationSectionId(String stationSectionId) {
		this.stationSectionId = stationSectionId;
	}
	public String getStationSectionName() {
		return stationSectionName;
	}
	public void setStationSectionName(String stationSectionName) {
		this.stationSectionName = stationSectionName;
	}
	public String getTicketStationId() {
		return ticketStationId;
	}
	public void setTicketStationId(String ticketStationId) {
		this.ticketStationId = ticketStationId;
	}
	public String getTicketStationName() {
		return ticketStationName;
	}
	public void setTicketStationName(String ticketStationName) {
		this.ticketStationName = ticketStationName;
	}
	public String getTrainNo() {
		return trainNo;
	}
	public void setTrainNo(String trainNo) {
		this.trainNo = trainNo;
	}
	public String getStartStation() {
		return startStation;
	}
	public void setStartStation(String startStation) {
		this.startStation = startStation;
	}
	public String getEndStation() {
		return endStation;
	}
	public void setEndStation(String endStation) {
		this.endStation = endStation;
	}
	public Date getTicketDate() {
		return ticketDate;
	}
	public void setTicketDate(Date ticketDate) {
		this.ticketDate = ticketDate;
	}
	public String getUepId() {
		return uepId;
	}
	public void setUepId(String uepId) {
		this.uepId = uepId;
	}
	public String getUepName() {
		return uepName;
	}
	public void setUepName(String uepName) {
		this.uepName = uepName;
	}
	public Date getStartdate() {
		return startdate;
	}
	public void setStartdate(Date startdate) {
		this.startdate = startdate;
	}
	public Date getEnddate() {
		return enddate;
	}
	public void setEnddate(Date enddate) {
		this.enddate = enddate;
	}
	public Integer getPage() {
		return page;
	}
	public void setPage(Integer page) {
		this.page = page;
	}
	public Integer getRows() {
		return rows;
	}
	public void setRows(Integer rows) {
		this.rows = rows;
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
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Date getDataDate() {
		return dataDate;
	}
	public void setDataDate(Date dataDate) {
		this.dataDate = dataDate;
	}
	public double getTotalIncome() {
		return totalIncome;
	}
	public void setTotalIncome(double totalIncome) {
		this.totalIncome = totalIncome;
	}
	public Long getTotalPeopleCount() {
		return totalPeopleCount;
	}
	public void setTotalPeopleCount(Long totalPeopleCount) {
		this.totalPeopleCount = totalPeopleCount;
	}
	public String getTicketRate() {
		return ticketRate;
	}
	public void setTicketRate(String ticketRate) {
		this.ticketRate = ticketRate;
	}
	
	

}
