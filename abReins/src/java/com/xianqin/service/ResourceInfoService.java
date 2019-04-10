package com.xianqin.service;

import com.base.ReturnMap;
import com.xianqin.common.QueryRule;

public interface ResourceInfoService {
	/**
	 * 根据ResourceId获取资源表Id
	 * @param resourceid
	 * @return
	 * @throws Exception
	 */
	ReturnMap getIdByResourceId(String resourceid) throws Exception;
	/**
	 * 根据资源类型查询资源信息表
	 * @param reosurceType
	 * @return
	 * @throws Exception
	 */
	ReturnMap queryListByResourceType(Integer reosurceType) throws Exception;
	/**
	 * 根据条件获取资源
	 * @param queryRule
	 * @return
	 * @throws Exception
	 */
	ReturnMap queryResourceList(QueryRule queryRule) throws Exception;
	/**
	 * 根据资源id获取资源表id
	 * @param resourceId
	 * @return
	 * @throws Exception
	 */
	ReturnMap getResourceIdByResourceId(String resourceId) throws Exception;
}
