package com.xianqin.service;

import com.base.ReturnMap;
import com.xianqin.common.QueryRule;
import com.xianqin.domain.TicketStationInfo;

public interface TicketStationInfoService {
	
	/**
	 * 将bean实例保存到数据库中
	 * @param TicketStationInfo bean实例，一般为游离态对象
	 * @throws Exception
	 */
	ReturnMap saveTicketStationInfo(TicketStationInfo ticketStationInfo) throws Exception;
	
	/**
	 * 依据bean实例的属性修改数据库行对象
	 * @param ticketStationInfo bean持久化对象实例
	 * @throws Exception
	 */
	ReturnMap updateTicketStationInfo(TicketStationInfo ticketStationInfo) throws Exception;
	
	/**
	 * 查询符合条件的售票站信息持久化对象实例列表
	 * 使用Page对象实例封装后返回，该对象具有分页查询属性
	 * @param QueryRule 查询条件对象实例
	 * @param pageIndex 当前数据分页的页码
	 * @param pageSize 每页显示条数
	 * @throws Exception
	 * @return 翻页对象实例
	 */
	ReturnMap queryTicketStationInfoByPage(QueryRule queryRule,Integer pageIndex,Integer pageSize) throws Exception;

    /**
	 * 根据售票站信息持久化对象主键字段值删除持久化对象
	 * 此方法为物理删除,使用时请注意
	 * @param id 售票站信息持久化对象主键字段值
	 * @throws Exception
	 */
	ReturnMap deleteTicketStationInfoById(String id) throws Exception;
	
	/**
	 * 查询符合条件的售票站信息持久化对象实例
	 * @param queryRule 查询对象实例
	 * @throws Exception
	 * @return 售票站信息持久化对象实例
	 */
	TicketStationInfo getTicketStationInfoByCondition(QueryRule queryRule) throws Exception;

}
