package com.xianqin.service;


import com.base.ReturnMap;
import com.xianqin.common.QueryRule;
import com.xianqin.domain.TrainDirection;

public interface TrainDirectionService {
	/**
	 * 分页查询方向信息
	 * @param queryRule
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	ReturnMap queryTrainDirectionByPage(QueryRule queryRule, Integer pageIndex, Integer pageSize) throws Exception;

	/**
	 * 保存车次方向信息
	 * @param trainDirection
	 * @return
	 * @throws Exception
	 */
	ReturnMap saveTrainDirection(TrainDirection trainDirection) throws Exception;
	
	/**
	 * 根据车次方向id获取方向信息
	 * @param id
	 * @return
	 * @throws Exception
	 */
	ReturnMap getDirectionById(Long id) throws Exception;
	
	/**
	 * 根据车次方向id修改车次方向信息
	 * @param id
	 * @return
	 * @throws Exception
	 */
	ReturnMap editDirectionById(TrainDirection trainDirection) throws Exception;
	/**
	 * 判断车次方向是否存在重复的值
	 * @param name
	 * @return
	 * @throws Exception
	 */
	boolean queryDirectionIfExist(String name) throws Exception;
	
	/**
	 * 查询全部车次方向信息绑定下拉框
	 * @param queryRule
	 * @return
	 * @throws Exception
	 */
	ReturnMap queryDirectionSelect(QueryRule queryRule) throws Exception;
}
