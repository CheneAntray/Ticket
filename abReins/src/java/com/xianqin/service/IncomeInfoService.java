package com.xianqin.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.base.ReturnMap;
import com.xianqin.common.QueryRule;
import com.xianqin.domain.IncomeInfo;

public interface IncomeInfoService {
	
	/**
	 * 将bean实例保存到数据库中
	 * @param IncomeInfo bean实例，一般为游离态对象
	 * @throws Exception
	 */
	ReturnMap saveIncomeInfo(IncomeInfo incomeInfo) throws Exception;
	
	/**
	 * 依据bean实例的属性修改数据库行对象
	 * @param incomeInfo bean持久化对象实例
	 * @throws Exception
	 */
	ReturnMap updateIncomeInfo(IncomeInfo incomeInfo) throws Exception;
	
	/**
	 * 查询符合条件的收入信息持久化对象实例列表
	 * 使用Page对象实例封装后返回，该对象具有分页查询属性
	 * @param QueryRule 查询条件对象实例
	 * @param pageIndex 当前数据分页的页码
	 * @param pageSize 每页显示条数
	 * @throws Exception
	 * @return 翻页对象实例
	 */
	ReturnMap queryIncomeInfoByPage(QueryRule queryRule,Integer pageIndex,Integer pageSize) throws Exception;

    /**
	 * 根据收入信息持久化对象主键字段值删除持久化对象
	 * 此方法为物理删除,使用时请注意
	 * @param id 收入信息持久化对象主键字段值
	 * @throws Exception
	 */
	ReturnMap deleteIncomeInfoById(String id) throws Exception;
	
	/**
	 * 查询符合条件的收入信息持久化对象实例
	 * @param queryRule 查询对象实例
	 * @throws Exception
	 * @return 收入信息持久化对象实例
	 */
	IncomeInfo getIncomeInfoByCondition(QueryRule queryRule) throws Exception;
	
	/**
	 * 批量保存收入信息
	 * @param list
	 * @return
	 * @throws Exception
	 */
	Map<String, String> saveBatch(List<Map<Integer, String>> list,String dataDate) throws Exception;
	
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
	Map<String, Object> getSumIncomePeopleCountByTicketStationIdAndDate(Long ticketStationId,Date startDate,Date endDate);

}
