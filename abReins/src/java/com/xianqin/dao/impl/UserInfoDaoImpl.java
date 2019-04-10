package com.xianqin.dao.impl;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.base.utils.UUIDGenerator;
import com.xianqin.common.Page;
import com.xianqin.common.QueryRule;
import com.xianqin.dao.UserInfoDao;
import com.xianqin.domain.UserInfo;
/**
 * 用户基本信息对象数据访问层接口实现类
 * 该接口实现了userInfoDao接口常用的数据访问层方法
 * @author xianqin-atuoBuilder
 * @@version 1.0
 */
 
@Repository("userInfoDao")
public class UserInfoDaoImpl extends CommonDaoImpl<UserInfo, String> implements UserInfoDao{
	@Override
	public void saveUserInfo(UserInfo userInfo) {
		userInfo.setId(UUIDGenerator.getUUID()); //生成主键
		userInfo.setCreateTime(new Date());    //创建时间
		this.save(userInfo);
	}

	@Override
	public void updateUserInfo(UserInfo userInfo) {
		userInfo.setUpdateTime(new Date());
		this.update(userInfo);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Page<UserInfo> queryUserInfoByPage(QueryRule queryRule, Integer pageIndex, Integer pageSize) {
		queryRule.addDescOrder("createTime");
		return  this.find(queryRule, pageIndex, pageSize);
	}

    @Override
	public void deleteUserInfoById(String userInfoId) {
		String hql="DELETE FROM UserInfo WHERE ID=?";
		this.getSessionFactory().getCurrentSession().createQuery(hql)
		.setParameter(0, userInfoId).executeUpdate();
	}
	
	@Override
	public UserInfo getUserInfoByCondition(QueryRule queryRule) {
		List<UserInfo> userInfos=this.find(queryRule);
		UserInfo userInfo=null;
		if (userInfos!=null && !userInfos.isEmpty()) {
			userInfo=userInfos.get(0);
		}
		return userInfo;
	}

	@Override
	public List<UserInfo> getUserInfoAll(QueryRule queryRule) {
		// TODO Auto-generated method stub
		List<UserInfo> userInfos=this.find(queryRule);
		return userInfos;
	}
}
