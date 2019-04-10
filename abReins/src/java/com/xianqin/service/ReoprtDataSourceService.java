package com.xianqin.service;

import java.util.List;
import java.util.Map;

/**
 * 报表数据源服务接口
 * 该接口对应了所有报表服务中的数据源生成
 * @author thinkpad
 *
 */
public interface ReoprtDataSourceService {
	public List<?> getListByReportCode(String reportCode,Map<String,?> parmsMap)throws Exception;
}
