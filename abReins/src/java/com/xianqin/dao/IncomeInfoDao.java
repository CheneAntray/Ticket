package com.xianqin.dao;

import java.util.Date;
import java.util.List;

import com.xianqin.common.Page;

import com.xianqin.common.QueryRule;
import com.xianqin.domain.IncomeInfo;

/**
 * 收入信息对象数据访问层接口定义
 * 该接口定义了收入信息对象常用的数据访问层方法
 * @author xianqin-atuoBuilder
 * @@version 1.0
 */
public interface IncomeInfoDao {
	/**
	 * 将bean实例保存到数据库中
	 * @param IncomeInfo bean实例，一般为游离态对象
	 * @throws Exception
	 */
	void saveIncomeInfo(IncomeInfo incomeInfo);
	
	/**
	 * 依据bean实例的属性修改数据库行对象
	 * @param incomeInfo bean持久化对象实例
	 * @throws Exception
	 */
	void updateIncomeInfo(IncomeInfo incomeInfo);
	
	/**
	 * 查询符合条件的收入信息持久化对象实例列表
	 * 使用Page对象实例封装后返回，该对象具有分页查询属性
	 * @param QueryRule 查询条件对象实例
	 * @param pageIndex 当前数据分页的页码
	 * @param pageSize 每页显示条数
	 * @throws Exception
	 * @return 翻页对象实例
	 */
	Page<IncomeInfo> queryIncomeInfoByPage(QueryRule queryRule,Integer pageIndex,Integer pageSize);

    /**
	 * 根据收入信息持久化对象主键字段值删除持久化对象
	 * 此方法为物理删除,使用时请注意
	 * @param id 收入信息持久化对象主键字段值
	 * @throws Exception
	 */
	void deleteIncomeInfoById(String id);
	
	/**
	 * 查询符合条件的收入信息持久化对象实例
	 * @param queryRule 查询对象实例
	 * @throws Exception
	 * @return 收入信息持久化对象实例
	 */
	IncomeInfo getIncomeInfoByCondition(QueryRule queryRule);
	
	/**
	 * 查询符合条件的收入信息持久化对象实例集合
	 * @param queryRule 查询对象实例集合
	 * @throws Exception
	 * @return 收入信息持久化对象实例集合
	 */
	List<IncomeInfo> getIncomeInfoListByCondition(QueryRule queryRule);
	
	/**
	 * 根据售票站Id和时间查询售票站总收入
	 * @return
	 */
	double getSumIncomeByTicketStationIdAndDate(Long ticketStationId,String startDate,String endDate);
	
	
	/**
	 * 根据售票站Id和时间查询售票站总售票人数
	 * @return
	 */
	Long getSumPeopleCountByTicketStationIdAndDate(Long ticketStationId,String startDate,String endDate);
	
	/**
	 * 根据站段Id查询将数据复制给收入临时表
	 * @param stationSection
	 * @param dataDate
	 */
	void queryIncomByZdIdForTempIncome(Long stationSection,String dataDate);
	
	/**
	 * 根据售票站Id查询将数据复制给收入临时表
	 * @param ticketStationId
	 * @param dataDate
	 */
	void queryIncomBySpzIdForTempIncome(Long ticketStationId,String dataDate);
	
	/**
	 * 根据车次Id查询将数据复制给收入临时表
	 * @param ticketStationId
	 * @param dataDate
	 */
	void queryIncomByTrainNumberIdForTempIncome(Long trainNumberId,String dataDate);
	/**
	 * 跟据售票站、车次分组查询收入和售票人数
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	List<Object[]> getIncomePeopleCountGroouByTicketStationTrainnumber(String startDate,String endDate);
	/**
	 * 根据日期 车次id获取车次日收入 人数
	 * @param year
	 * @param trainId
	 * @return
	 */
	List<Object[]> getIncomePeopleCountGroouByHql(String day,String trainNumberId);
	
	/**
	 * 根据日期判断是否存在数据
	 * @return
	 */
	Integer getIsNullByDataDate(String dataDate);
	

}