package com.xianqin.service;

public interface ZdOfmonthService {
	
	/**
	 * 根据站段ID和月份查询收入
	 * @param zdId
	 * @param year
	 * @return
	 */
	Double getIncomeByZdIdAndMonth(Long zdId,String month)throws Exception;
	
	/**
	 * 根据站段ID和月份查询人数
	 * @param zdId
	 * @param year
	 * @return
	 */
	Long getPeopleCountByZdIdAndMonth(Long zdId,String month)throws Exception;

}
