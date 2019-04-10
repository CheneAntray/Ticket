package com.xianqin.dao;

import com.xianqin.common.Page;

import com.xianqin.common.QueryRule;
import com.xianqin.domain.TempIncome;

/**
 * 收入信息临时表对象数据访问层接口定义
 * 该接口定义了收入信息临时表对象常用的数据访问层方法
 * @author xianqin-atuoBuilder
 * @@version 1.0
 */
public interface TempIncomeDao {
	/**
	 * 将bean实例保存到数据库中
	 * @param TempIncome bean实例，一般为游离态对象
	 * @throws Exception
	 */
	void saveTempIncome(TempIncome tempIncome);
	
	/**
	 * 依据bean实例的属性修改数据库行对象
	 * @param tempIncome bean持久化对象实例
	 * @throws Exception
	 */
	void updateTempIncome(TempIncome tempIncome);
	
	/**
	 * 查询符合条件的收入信息临时表持久化对象实例列表
	 * 使用Page对象实例封装后返回，该对象具有分页查询属性
	 * @param QueryRule 查询条件对象实例
	 * @param pageIndex 当前数据分页的页码
	 * @param pageSize 每页显示条数
	 * @throws Exception
	 * @return 翻页对象实例
	 */
	Page<TempIncome> queryTempIncomeByPage(QueryRule queryRule,Integer pageIndex,Integer pageSize);

    /**
	 * 根据收入信息临时表持久化对象主键字段值删除持久化对象
	 * 此方法为物理删除,使用时请注意
	 * @param id 收入信息临时表持久化对象主键字段值
	 * @throws Exception
	 */
	void deleteTempIncomeById(String id);
	
	/**
	 * 查询符合条件的收入信息临时表持久化对象实例
	 * @param queryRule 查询对象实例
	 * @throws Exception
	 * @return 收入信息临时表持久化对象实例
	 */
	TempIncome getTempIncomeByCondition(QueryRule queryRule);
	
	/**
	 * 使用TRUNCATE删除所有数据
	 */
	void truncateAllData();
	
	/**
	 * 查询总票款
	 * @return
	 */
	Double getSumIncome();
	
	/**
	 * 查询售票人数
	 * @return
	 */
	Long getSumPeopleCount();
	

}