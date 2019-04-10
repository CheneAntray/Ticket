package com.xianqin.service;

import com.base.ReturnMap;
import com.xianqin.common.QueryRule;

public interface ReportDefinedService {
	/**
	 * 根据报表编号获取报表名称
	 * @param reportCode
	 * @return
	 * @throws Exception
	 */
	ReturnMap queryReportDefinedByReportCode(String reportCode) throws Exception;
	
		/**
		 * 获取报表集合
		 * @param reportCode
		 * @return
		 * @throws Exception
		 */
		ReturnMap queryReportListByReportCode(QueryRule queryRule) throws Exception;
}
