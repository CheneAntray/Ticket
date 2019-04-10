package com.xianqin.service;

import com.base.ReturnMap;
import com.xianqin.common.QueryRule;

public interface UrlInfoService {
	/**
	 * 查询路径信息
	 * @param queryRule
	 * @return
	 * @throws Exception
	 */
	ReturnMap queryUrlInfo(QueryRule queryRule) throws Exception;
	/**
	 * 根据resourceid获取url信息
	 * @param resourceId
	 * @return
	 * @throws Exception
	 */
	ReturnMap getUrlByResourceId(String resourceId) throws Exception;
}
