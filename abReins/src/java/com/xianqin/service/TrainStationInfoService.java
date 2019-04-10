package com.xianqin.service;

import com.base.ReturnMap;
import com.xianqin.common.QueryRule;
import com.xianqin.domain.TrainStationInfo;

public interface TrainStationInfoService {
	
	/**
	 * 将bean实例保存到数据库中
	 * @param TrainStationInfo bean实例，一般为游离态对象
	 * @throws Exception
	 */
	ReturnMap saveTrainStationInfo(TrainStationInfo trainStationInfo) throws Exception;
	
	/**
	 * 依据bean实例的属性修改数据库行对象
	 * @param trainStationInfo bean持久化对象实例
	 * @throws Exception
	 */
	ReturnMap updateTrainStationInfo(TrainStationInfo trainStationInfo) throws Exception;
	
	/**
	 * 查询符合条件的车次到始站信息持久化对象实例列表
	 * 使用Page对象实例封装后返回，该对象具有分页查询属性
	 * @param QueryRule 查询条件对象实例
	 * @param pageIndex 当前数据分页的页码
	 * @param pageSize 每页显示条数
	 * @throws Exception
	 * @return 翻页对象实例
	 */
	ReturnMap queryTrainStationInfoByPage(QueryRule queryRule,Integer pageIndex,Integer pageSize) throws Exception;

    /**
	 * 根据车次到始站信息持久化对象主键字段值删除持久化对象
	 * 此方法为物理删除,使用时请注意
	 * @param id 车次到始站信息持久化对象主键字段值
	 * @throws Exception
	 */
	ReturnMap deleteTrainStationInfoById(String id) throws Exception;
	
	/**
	 * 查询符合条件的车次到始站信息持久化对象实例
	 * @param queryRule 查询对象实例
	 * @throws Exception
	 * @return 车次到始站信息持久化对象实例
	 */
	TrainStationInfo getTrainStationInfoByCondition(QueryRule queryRule) throws Exception;

}
