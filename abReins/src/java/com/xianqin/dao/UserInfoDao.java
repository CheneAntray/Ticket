package com.xianqin.dao;

import java.util.List;

import com.xianqin.common.Page;

import com.xianqin.common.QueryRule;
import com.xianqin.domain.UserInfo;

/**
 * 用户基本信息对象数据访问层接口定义
 * 该接口定义了用户基本信息对象常用的数据访问层方法
 * @author xianqin-atuoBuilder
 * @@version 1.0
 */
public interface UserInfoDao {
	/**
	 * 将bean实例保存到数据库中
	 * @param UserInfo bean实例，一般为游离态对象
	 * @throws Exception
	 */
	void saveUserInfo(UserInfo userInfo);
	
	/**
	 * 依据bean实例的属性修改数据库行对象
	 * @param userInfo bean持久化对象实例
	 * @throws Exception
	 */
	void updateUserInfo(UserInfo userInfo);
	
	/**
	 * 查询符合条件的用户基本信息持久化对象实例列表
	 * 使用Page对象实例封装后返回，该对象具有分页查询属性
	 * @param QueryRule 查询条件对象实例
	 * @param pageIndex 当前数据分页的页码
	 * @param pageSize 每页显示条数
	 * @throws Exception
	 * @return 翻页对象实例
	 */
	Page<UserInfo> queryUserInfoByPage(QueryRule queryRule,Integer pageIndex,Integer pageSize);

    /**
	 * 根据用户基本信息持久化对象主键字段值删除持久化对象
	 * 此方法为物理删除,使用时请注意
	 * @param id 用户基本信息持久化对象主键字段值
	 * @throws Exception
	 */
	void deleteUserInfoById(String id);
	
	/**
	 * 查询符合条件的用户基本信息持久化对象实例
	 * @param queryRule 查询对象实例
	 * @throws Exception
	 * @return 用户基本信息持久化对象实例
	 */
	UserInfo getUserInfoByCondition(QueryRule queryRule);
	/**
	 * 查询全部用户信息用作报表填充
	 * @param queryRule
	 * @return
	 */
	List<UserInfo> getUserInfoAll(QueryRule queryRule);

}