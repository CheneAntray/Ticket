package com.xianqin.service;

import java.util.List;

public interface SpzOfyearService {
	/**
	 * 获取所有的年份
	 * @return
	 */
	List<Long> getYearList() throws Exception;
	
	/**
	 * 根据售票站ID和年份查询收入
	 * @param spzId
	 * @param year
	 * @return
	 */
	Double getIncomeBySpzIdAndYear(Long spzId,Long year) throws Exception;
	
	/**
	 * 根据售票站ID和年份查询人数
	 * @param spzId
	 * @param year
	 * @return
	 */
	Long getPeopleCountBySpzIdAndYear(Long spzId,Long year) throws Exception;
	
	/**
	 * 根据售票站ID和拆分日期查询全部收入
	 * @param map
	 * @return
	 * @throws Exception
	 */
	Double getSumIncomeBySpzId(Long ticketStationId,String date) throws Exception;
	
	/**
	 * 根据售票站ID和拆分日期查询全部人数
	 * @param map
	 * @return
	 * @throws Exception
	 */
	Long getSumPeopleCountBySpzId(Long ticketStationId,String date) throws Exception;

}
