package com.xianqin.dao;

import java.util.List;

import com.xianqin.common.Page;

import com.xianqin.common.QueryRule;
import com.xianqin.domain.SpzOfYear;

/**
 * 售票站年统计对象数据访问层接口定义
 * 该接口定义了售票站年统计对象常用的数据访问层方法
 * @author xianqin-atuoBuilder
 * @@version 1.0
 */
public interface SpzOfyearDao {
	/**
	 * 将bean实例保存到数据库中
	 * @param SpzOfyear bean实例，一般为游离态对象
	 * @throws Exception
	 */
	void saveSpzOfyear(SpzOfYear spzOfyear);
	
	/**
	 * 依据bean实例的属性修改数据库行对象
	 * @param spzOfyear bean持久化对象实例
	 * @throws Exception
	 */
	void updateSpzOfyear(SpzOfYear spzOfyear);
	
	/**
	 * 查询符合条件的售票站年统计持久化对象实例列表
	 * 使用Page对象实例封装后返回，该对象具有分页查询属性
	 * @param QueryRule 查询条件对象实例
	 * @param pageIndex 当前数据分页的页码
	 * @param pageSize 每页显示条数
	 * @throws Exception
	 * @return 翻页对象实例
	 */
	Page<SpzOfYear> querySpzOfyearByPage(QueryRule queryRule,Integer pageIndex,Integer pageSize);

    /**
	 * 根据售票站年统计持久化对象主键字段值删除持久化对象
	 * 此方法为物理删除,使用时请注意
	 * @param id 售票站年统计持久化对象主键字段值
	 * @throws Exception
	 */
	void deleteSpzOfyearById(String id);
	
	/**
	 * 查询符合条件的售票站年统计持久化对象实例
	 * @param queryRule 查询对象实例
	 * @throws Exception
	 * @return 售票站年统计持久化对象实例
	 */
	SpzOfYear getSpzOfyearByCondition(QueryRule queryRule);
	
	/**
	 * 根据数据年份删除售票站年统计信息
	 * @param dataDate
	 */
	void deleteSpzOfyearByYear(Long year);
	
	/**
	 * 获取所有的年份
	 * @return
	 */
	List<Long> getYearList();
	
	/**
	 * 根据售票站ID和年份查询收入
	 * @param spzId
	 * @param year
	 * @return
	 */
	Double getIncomeBySpzIdAndYear(Long spzId,Long year);
	
	/**
	 * 根据售票站ID和年份查询人数
	 * @param spzId
	 * @param year
	 * @return
	 */
	Long getPeopleCountBySpzIdAndYear(Long spzId,Long year);
	
	/**
	 * 根据售票站ID查询全部收入
	 * @param spzId
	 * @param year
	 * @return
	 */
	Double getSumIncomeBySpzId(Long spzId);
	
	/**
	 * 根据售票站ID查询全部人数
	 * @param spzId
	 * @param year
	 * @return
	 */
	Long getSumPeopleCountBySpzId(Long spzId);

}