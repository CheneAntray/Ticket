package com.xianqin.dao;


import com.xianqin.common.Page;

import com.xianqin.common.QueryRule;
import com.xianqin.domain.ZdOfMonth;

/**
 * 站段月统计对象数据访问层接口定义
 * 该接口定义了站段月统计对象常用的数据访问层方法
 * @author xianqin-atuoBuilder
 * @@version 1.0
 */
public interface ZdOfmonthDao {
	/**
	 * 将bean实例保存到数据库中
	 * @param ZdOfmonth bean实例，一般为游离态对象
	 * @throws Exception
	 */
	void saveZdOfmonth(ZdOfMonth zdOfmonth);
	
	/**
	 * 依据bean实例的属性修改数据库行对象
	 * @param zdOfmonth bean持久化对象实例
	 * @throws Exception
	 */
	void updateZdOfmonth(ZdOfMonth zdOfmonth);
	
	/**
	 * 查询符合条件的站段月统计持久化对象实例列表
	 * 使用Page对象实例封装后返回，该对象具有分页查询属性
	 * @param QueryRule 查询条件对象实例
	 * @param pageIndex 当前数据分页的页码
	 * @param pageSize 每页显示条数
	 * @throws Exception
	 * @return 翻页对象实例
	 */
	Page<ZdOfMonth> queryZdOfmonthByPage(QueryRule queryRule,Integer pageIndex,Integer pageSize);

    /**
	 * 根据站段月统计持久化对象主键字段值删除持久化对象
	 * 此方法为物理删除,使用时请注意
	 * @param id 站段月统计持久化对象主键字段值
	 * @throws Exception
	 */
	void deleteZdOfmonthById(String id);
	
	/**
	 * 查询符合条件的站段月统计持久化对象实例
	 * @param queryRule 查询对象实例
	 * @throws Exception
	 * @return 站段月统计持久化对象实例
	 */
	ZdOfMonth getZdOfmonthByCondition(QueryRule queryRule);
	
	/**
	 * 根据站段ID和年份查询总票款
	 * @return
	 */
	double getSumIncome(String stationSectionId,String year);
	
	/**
	 * 根据站段ID和年份查询售票人数
	 * @return
	 */
	Long getSumPeopleCount(String stationSectionId,String year);
	
	/**
	 * 根据日期判断是否存在数据
	 * @return
	 */
	Integer getIsNullByDataDate(String year);
	
	
	/**
	 * 根据月份删除站段月统计信息
	 * @param month
	 */
	void deleteZdOfmonthByMonth(String month);
	
	/**
	 * 根据站段ID和月份查询收入
	 * @param zdId
	 * @param year
	 * @return
	 */
	Double getIncomeByZdIdAndMonth(Long zdId,String month);
	
	/**
	 * 根据站段ID和月份查询人数
	 * @param zdId
	 * @param year
	 * @return
	 */
	Long getPeopleCountByZdIdAndMonth(Long zdId,String month);
	
	/**
	 * 根据站段ID和年份查询收入
	 * @param zdId
	 * @param month
	 * @return
	 */
	Double getSumIncomeByzdIdAndYear(Long zdId,String year);
	
	/**
	 * 根据站段ID和年份查询人数
	 * @param spzId
	 * @param month
	 * @return
	 */
	Long getSumPeopleCountByzdIdAndYear(Long zdId,String year);

}