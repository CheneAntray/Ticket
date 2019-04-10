package com.xianqin.dao;

import java.util.Date;
import java.util.List;
import com.xianqin.common.Page;
import com.xianqin.common.QueryRule;
import com.xianqin.domain.SpzOfday;

/**
 * 售票站日统计对象数据访问层接口定义
 * 该接口定义了售票站日统计对象常用的数据访问层方法
 * @author xianqin-atuoBuilder
 * @@version 1.0
 */
public interface SpzOfdayDao {
	/**
	 * 将bean实例保存到数据库中
	 * @param SpzOfday bean实例，一般为游离态对象
	 * @throws Exception
	 */
	void saveSpzOfday(SpzOfday spzOfday);
	
	/**
	 * 依据bean实例的属性修改数据库行对象
	 * @param spzOfday bean持久化对象实例
	 * @throws Exception
	 */
	void updateSpzOfday(SpzOfday spzOfday);
	
	/**
	 * 查询符合条件的售票站日统计持久化对象实例列表
	 * 使用Page对象实例封装后返回，该对象具有分页查询属性
	 * @param QueryRule 查询条件对象实例
	 * @param pageIndex 当前数据分页的页码
	 * @param pageSize 每页显示条数
	 * @throws Exception
	 * @return 翻页对象实例
	 */
	Page<SpzOfday> querySpzOfdayByPage(QueryRule queryRule,Integer pageIndex,Integer pageSize);

    /**
	 * 根据售票站日统计持久化对象主键字段值删除持久化对象
	 * 此方法为物理删除,使用时请注意
	 * @param id 售票站日统计持久化对象主键字段值
	 * @throws Exception
	 */
	void deleteSpzOfdayById(String id);
	
	/**
	 * 查询符合条件的售票站日统计持久化对象实例
	 * @param queryRule 查询对象实例
	 * @throws Exception
	 * @return 售票站日统计持久化对象实例
	 */
	SpzOfday getSpzOfdayByCondition(QueryRule queryRule);
	
	/**
	 * 根据售票站ID和月份查询总票款
	 * @return
	 */
	double getSumIncome(Long ticketStationId, String startDate,String endDate);
	
	/**
	 * 根据站段ID和月份查询售票人数
	 * @return
	 */
	Long getSumPeopleCount(Long ticketStationId, String startDate,String endDate);
	
	/**
	 * 根据站段ID查询收入和售票人数
	 * @param stationSectionId
	 * @param dataDate
	 * @return
	 */
	List<Object[]> getIncomPeopleCountByZdId(String stationSectionId,String dataDate);
	
	/**
	 * 根据日期判断是否存在数据
	 * @return
	 */
	Integer getIsNullByDataDate(String startDate,String endDate);
	
	/**
	 * 根据日期判断是否存在数据
	 * @return
	 */
	Integer getIsNullByDataDateOfDay(String dataDate);
	
	/**
	 * 根据数据日期删除售票站日统计信息
	 * @param dataDate
	 */
	void deleteSpzOfdayByDataDate(Date dataDate);
	
	/**
	 * 根据售票站ID和年月查询收入
	 * @param spzId
	 * @param month
	 * @return
	 */
	Double getSumIncomeBySpzIdAndDate(Long spzId,Date date);
	
	/**
	 * 根据售票站ID和年月查询人数
	 * @param spzId
	 * @param month
	 * @return
	 */
	Long getSumPeopleCountBySpzIdAndDate(Long spzId,Date date);

}