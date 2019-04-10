package com.xianqin.view.direction;

import java.io.Serializable;

import com.xianqin.domain.TrainDirection;

public class TrainDirectionView extends TrainDirection implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer page;
	private Integer rows;

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

	/**
	 * 将视图对象与业务对象具有相同属性名称的属性值进行拷贝，将视图对象属性值复制给业务对象实例
	 * 
	 * @param trainDirectionView
	 *            数据源对象实例
	 * @param trainDirection
	 *            数据目标对象实例
	 * @return 具有源对象属性值的目标对象实例
	 */
	public static TrainDirection processTrainDirectionViewToTrainDirection(TrainDirectionView trainDirectionView,
			TrainDirection trainDirection) {
		trainDirection.setId(trainDirectionView.getId());
		trainDirection.setDirectionName(trainDirectionView.getDirectionName());
		return trainDirection;
	}

}
