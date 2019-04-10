package com.xianqin.service;

import java.util.List;

public interface ZdOfyearService {
	
	List<Long> getYearList() throws Exception;
	
	/**
	 * 根据站段ID和年份查询收入
	 * @param zdId
	 * @param year
	 * @return
	 */
	Double getIncomeByZdIdAndYear(Long zdId,Long year)throws Exception;
	
	/**
	 * 根据站段ID和年份查询人数
	 * @param zdId
	 * @param year
	 * @return
	 */
	Long getPeopleCountByZdIdAndYear(Long zdId,Long year)throws Exception;
	
	/**
	 * 根据站段ID和拆分日期查询全部收入
	 * @param zdId
	 * @return
	 * @throws Exception
	 */
	Double getSumIncomeByZdId(Long zdId,String date) throws Exception;
	
	/**
	 * 根据站段ID和拆分日期查询全部人数
	 * @param zdId
	 * @return
	 * @throws Exception
	 */
	Long getSumPeopleCountByZdId(Long zdId,String date) throws Exception;

}
