package com.xianqin.dao;

import java.util.List;

import com.xianqin.common.Page;

import com.xianqin.common.QueryRule;
import com.xianqin.domain.TicketstationTrainnumberOfmonth;

/**
 * 售票站车次月统计信息表对象数据访问层接口定义
 * 该接口定义了售票站车次月统计信息表对象常用的数据访问层方法
 * @author xianqin-atuoBuilder
 * @@version 1.0
 */
public interface TicketstationTrainnumberOfmonthDao {
	/**
	 * 将bean实例保存到数据库中
	 * @param TicketstationTrainnumberOfmonth bean实例，一般为游离态对象
	 * @throws Exception
	 */
	void saveTicketstationTrainnumberOfmonth(TicketstationTrainnumberOfmonth ticketstationTrainnumberOfmonth);
	
	/**
	 * 依据bean实例的属性修改数据库行对象
	 * @param ticketstationTrainnumberOfmonth bean持久化对象实例
	 * @throws Exception
	 */
	void updateTicketstationTrainnumberOfmonth(TicketstationTrainnumberOfmonth ticketstationTrainnumberOfmonth);
	
	/**
	 * 查询符合条件的售票站车次月统计信息表持久化对象实例列表
	 * 使用Page对象实例封装后返回，该对象具有分页查询属性
	 * @param QueryRule 查询条件对象实例
	 * @param pageIndex 当前数据分页的页码
	 * @param pageSize 每页显示条数
	 * @throws Exception
	 * @return 翻页对象实例
	 */
	Page<TicketstationTrainnumberOfmonth> queryTicketstationTrainnumberOfmonthByPage(QueryRule queryRule,Integer pageIndex,Integer pageSize);

    /**
	 * 根据售票站车次月统计信息表持久化对象主键字段值删除持久化对象
	 * 此方法为物理删除,使用时请注意
	 * @param id 售票站车次月统计信息表持久化对象主键字段值
	 * @throws Exception
	 */
	void deleteTicketstationTrainnumberOfmonthById(String id);
	
	/**
	 * 查询符合条件的售票站车次月统计信息表持久化对象实例
	 * @param queryRule 查询对象实例
	 * @throws Exception
	 * @return 售票站车次月统计信息表持久化对象实例
	 */
	TicketstationTrainnumberOfmonth getTicketstationTrainnumberOfmonthByCondition(QueryRule queryRule);
	
	/**
	 * 跟据售票站、车次分组查询收入和售票人数
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	List<Object[]> getIncomePeopleCountGroouByTicketStationTrainnumber(String year);
	/**
	 * 跟据日期 车次id查询 车次收入 人数
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	List<Object[]> getIncomePeopleCountGroouByHql(String year,String trainId);
	
	/**
	 * 根据时间删除售票站车次月统计信息
	 * @param month
	 */
	void deleteTicketTrainnumberOfmonthByMonth(String month);

}