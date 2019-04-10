package com.xianqin.service;

import java.util.Date;

import com.base.ReturnMap;

public interface TimedTaskService {
	
	/**
	 * 执行定时任务
	 */
	void doTimedTask();
	
	/**
	 * 查询所有定时日期
	 * @return
	 * @throws Exception
	 */
	ReturnMap queryTimedTaskInfo() throws Exception;
	
	/**
	 * 修改日期信息
	 * @param timedtaskInfo
	 * @return
	 * @throws Exception
	 */
	ReturnMap editTimedTaskInfo(String dataId,Date date) throws Exception;
	
	/**
	 * 补跑售票站车次日统计
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	ReturnMap runUpByTicketTrainnUmberOfMonth(Date startDate,Date endDate) throws Exception;
	
	/**
	 * 补跑售票站车次月统计
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	ReturnMap runUpByTicketTrainnUmberOfYear(Date startDate,Date endDate) throws Exception;
	
	/**
	 * 补跑售票站日统计
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	ReturnMap runUpByTicketStationOfDay(Date startDate,Date endDate) throws Exception;
	
	/**
	 * 补跑售票站月统计
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	ReturnMap runUpByTicketStationOfMonth(Date startDate,Date endDate) throws Exception;
	
	/**
	 * 补跑售票站年统计
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	ReturnMap runUpByTicketStationOfYear(Date startDate,Date endDate) throws Exception;
	
	/**
	 * 补跑站段日统计
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	ReturnMap runUpByStationSectionOfDay(Date startDate,Date endDate) throws Exception;
	
	/**
	 * 补跑站段月统计
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	ReturnMap runUpByStationSectionOfMonth(Date startDate,Date endDate) throws Exception;
	
	/**
	 * 补跑站段年统计
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	ReturnMap runUpByStationSectionOfYear(Date startDate,Date endDate) throws Exception;
	
}
