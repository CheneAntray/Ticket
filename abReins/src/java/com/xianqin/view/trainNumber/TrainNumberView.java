package com.xianqin.view.trainNumber;

import java.io.Serializable;

import com.xianqin.domain.TrainNumberInfo;

public class TrainNumberView implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * ID
	 */

	private Long id;

	/**
	 * 车次编号
	 */

	private String trainNo;

	/**
	 * 起始站ID
	 */

	private Long startStationId;

	/**
	 * 起始站名称
	 */
	private String startStationName;

	/**
	 * 终到站ID
	 */
	private Long endStationId;

	/**
	 * 终到站名称
	 */

	private String endStationName;

	/**
	 * 担当企业ID
	 */
	private Long underEnterId;

	/**
	 * 担当企业名称
	 */

	private String uepName;

	/**
	 * 头次
	 */

	private String titleNo;

	/**
	 * 当前页码
	 */
	private Integer page;
	/**
	 * 每页显示的数量
	 */
	private Integer rows;

	/**
	 * 车次方向
	 */
	private Long directionId;

	/**
	 * 车次Id数组
	 */
	private Long[] trainNumberIds;

	/**
	 * 没有车次的头次查询条件
	 */
	private String notInTitleNo;

	/**
	 * 没有车次的其起始站查询条件
	 */
	private Long notInStartStation;

	/**
	 * 没有车次的终到站查询条件
	 */
	private Long notInEndStation;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTrainNo() {
		return trainNo;
	}

	public void setTrainNo(String trainNo) {
		this.trainNo = trainNo;
	}

	public Long getStartStationId() {
		return startStationId;
	}

	public void setStartStationId(Long startStationId) {
		this.startStationId = startStationId;
	}

	public String getStartStationName() {
		return startStationName;
	}

	public void setStartStationName(String startStationName) {
		this.startStationName = startStationName;
	}

	public Long getEndStationId() {
		return endStationId;
	}

	public void setEndStationId(Long endStationId) {
		this.endStationId = endStationId;
	}

	public String getEndStationName() {
		return endStationName;
	}

	public void setEndStationName(String endStationName) {
		this.endStationName = endStationName;
	}

	public String getUepName() {
		return uepName;
	}

	public void setUepName(String uepName) {
		this.uepName = uepName;
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

	public String getTitleNo() {
		return titleNo;
	}

	public void setTitleNo(String titleNo) {
		this.titleNo = titleNo;
	}

	public Long getDirectionId() {
		return directionId;
	}

	public void setDirectionId(Long directionId) {
		this.directionId = directionId;
	}

	public Long[] getTrainNumberIds() {
		return trainNumberIds;
	}

	public void setTrainNumberIds(Long[] trainNumberIds) {
		this.trainNumberIds = trainNumberIds;
	}

	public String getNotInTitleNo() {
		return notInTitleNo;
	}

	public void setNotInTitleNo(String notInTitleNo) {
		this.notInTitleNo = notInTitleNo;
	}

	public Long getNotInStartStation() {
		return notInStartStation;
	}

	public void setNotInStartStation(Long notInStartStation) {
		this.notInStartStation = notInStartStation;
	}

	public Long getNotInEndStation() {
		return notInEndStation;
	}

	public void setNotInEndStation(Long notInEndStation) {
		this.notInEndStation = notInEndStation;
	}

	public Long getUnderEnterId() {
		return underEnterId;
	}

	public void setUnderEnterId(Long underEnterId) {
		this.underEnterId = underEnterId;
	}

	/**
	 * 将视图对象与业务对象具有相同属性名称的属性值进行拷贝，将视图对象属性值复制给业务对象实例
	 * 
	 * @param TrainNumberView
	 *            数据源对象实例
	 * @param TrainNumberInfo
	 *            数据目标对象实例
	 * @return 具有源对象属性值的目标对象实例
	 */
	public static TrainNumberInfo processTrainNumberViewToTrainNumberInfo(TrainNumberView trainNumberView,
			TrainNumberInfo trainNumberInfo) {
		trainNumberInfo.setId(trainNumberView.getId());
		trainNumberInfo.setStartStationId(trainNumberView.getStartStationId());
		trainNumberInfo.setDirectionId(trainNumberView.getDirectionId());
		trainNumberInfo.setEndStationId(trainNumberView.getEndStationId());
		trainNumberInfo.setTrainNo(trainNumberView.getTrainNo());
		trainNumberInfo.setUnderEnterId(trainNumberView.getUnderEnterId());
		return trainNumberInfo;
	}

}
