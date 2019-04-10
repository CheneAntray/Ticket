package com.xianqin.dao;

import java.util.List;

import com.xianqin.common.Page;
import com.xianqin.common.QueryRule;
import com.xianqin.domain.TrainDirection;
/**
 * 方向基本信息对象数据访问层接口定义
 * 该接口定义了用户基本信息对象常用的数据访问层方法
 * @author xianqin-atuoBuilder
 * @@version 1.0
 */

public interface TrainDirectionDao {
	/**
	 * 将bean实例保存到数据库中
	 * @param TicketStationInfo bean实例，一般为游离态对象
	 * @throws Exception
	 */
	void saveTrainDirectionInfo(TrainDirection trainDirection);
	
	/**
	 * 依据bean实例的属性修改数据库行对象
	 * @param ticketStationInfo bean持久化对象实例
	 * @throws Exception
	 */
	void updateTrainDirectionInfo(TrainDirection trainDirection);
	
	/**
	 * 查询符合条件的售票站信息持久化对象实例列表
	 * 使用Page对象实例封装后返回，该对象具有分页查询属性
	 * @param QueryRule 查询条件对象实例
	 * @param pageIndex 当前数据分页的页码
	 * @param pageSize 每页显示条数
	 * @throws Exception
	 * @return 翻页对象实例
	 */
	Page<TrainDirection> queryTrainDirectionInfo(QueryRule queryRule,Integer pageIndex,Integer pageSize);

    /**
	 * 根据售票站信息持久化对象主键字段值删除持久化对象
	 * 此方法为物理删除,使用时请注意
	 * @param id 售票站信息持久化对象主键字段值
	 * @throws Exception
	 */
	void deleteTrainDirectionInfo(String id);
	
	/**
	 * 查询符合条件的售票站信息持久化对象实例
	 * @param queryRule 查询对象实例
	 * @throws Exception
	 * @return 售票站信息持久化对象实例
	 */
	TrainDirection getTrainDirectionInfoByCondition(QueryRule queryRule);
	
	/**
	 * 查询符合条件的售票站信息持久化对象集合
	 * @param queryRule 查询对象实例
	 * @throws Exception
	 * @return 售票站信息持久化对象集合
	 */
	List<TrainDirection> getTrainDirectionInfoListByCondition(QueryRule queryRule);

}
