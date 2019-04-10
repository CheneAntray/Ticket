package com.xianqin.dao;

import java.util.List;

import com.xianqin.common.Page;

import com.xianqin.common.QueryRule;
import com.xianqin.domain.ZdOfYear;

/**
 * 站段年统计对象数据访问层接口定义
 * 该接口定义了站段年统计对象常用的数据访问层方法
 * @author xianqin-atuoBuilder
 * @@version 1.0
 */
public interface ZdOfyearDao {
	/**
	 * 将bean实例保存到数据库中
	 * @param ZdOfyear bean实例，一般为游离态对象
	 * @throws Exception
	 */
	void saveZdOfyear(ZdOfYear zdOfyear);
	
	/**
	 * 依据bean实例的属性修改数据库行对象
	 * @param zdOfyear bean持久化对象实例
	 * @throws Exception
	 */
	void updateZdOfyear(ZdOfYear zdOfyear);
	
	/**
	 * 查询符合条件的站段年统计持久化对象实例列表
	 * 使用Page对象实例封装后返回，该对象具有分页查询属性
	 * @param QueryRule 查询条件对象实例
	 * @param pageIndex 当前数据分页的页码
	 * @param pageSize 每页显示条数
	 * @throws Exception
	 * @return 翻页对象实例
	 */
	Page<ZdOfYear> queryZdOfyearByPage(QueryRule queryRule,Integer pageIndex,Integer pageSize);

    /**
	 * 根据站段年统计持久化对象主键字段值删除持久化对象
	 * 此方法为物理删除,使用时请注意
	 * @param id 站段年统计持久化对象主键字段值
	 * @throws Exception
	 */
	void deleteZdOfyearById(String id);
	
	/**
	 * 查询符合条件的站段年统计持久化对象实例
	 * @param queryRule 查询对象实例
	 * @throws Exception
	 * @return 站段年统计持久化对象实例
	 */
	ZdOfYear getZdOfyearByCondition(QueryRule queryRule);
	
	/**
	 * 根据年份删除站段年统计信息
	 * @param year
	 */
	void deleteZdOfyearByYear(Long year);
	
	/**
	 * 获取所有的年份
	 * @return
	 */
	List<Long> getYearList();
	
	/**
	 * 根据站段ID和年份查询收入
	 * @param zdId
	 * @param year
	 * @return
	 */
	Double getIncomeByZdIdAndYear(Long zdId,Long year);
	
	/**
	 * 根据站段ID和年份查询人数
	 * @param zdId
	 * @param year
	 * @return
	 */
	Long getPeopleCountByZdIdAndYear(Long zdId,Long year);
	
	/**
	 * 根据站段ID查询全部收入
	 * @param spzId
	 * @param year
	 * @return
	 */
	Double getSumIncomeByZdId(Long zdId);
	
	/**
	 * 根据站段ID查询全部人数
	 * @param spzId
	 * @param year
	 * @return
	 */
	Long getSumPeopleCountByZdId(Long zdId);

}