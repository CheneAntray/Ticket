package com.xianqin.service;

public interface SpzOfMonthService {
	
	/**
	 * 根据售票站ID和月份查询收入
	 * @param spzId
	 * @param month
	 * @return
	 */
	Double getIncomeBySpzIdAndMonth(Long spzId,String month) throws Exception;
	
	/**
	 * 根据售票站ID和年份查询人数
	 * @param spzId
	 * @param month
	 * @return
	 */
	Long getPeopleCountBySpzIdAndMonth(Long spzId,String month) throws Exception;

}
