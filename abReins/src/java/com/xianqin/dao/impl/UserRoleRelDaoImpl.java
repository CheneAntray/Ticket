package com.xianqin.dao.impl;

import java.util.List;


import org.springframework.stereotype.Repository;

import com.base.utils.UUIDGenerator;
import com.xianqin.common.QueryRule;
import com.xianqin.dao.UserRoleRelDao;
import com.xianqin.domain.UserRoleRel;
@Repository("userRoleRelDao")
public class UserRoleRelDaoImpl extends CommonDaoImpl<UserRoleRel, String> implements UserRoleRelDao {
	@Override
	public void deleteUserRoleRelByRoleId(String roleId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void saveUserRoleRel(UserRoleRel userRoleRel) {
		// TODO Auto-generated method stub
		userRoleRel.setId(UUIDGenerator.getUUID());
		this.save(userRoleRel);
		
	}

	@Override
	public void deleteUserRoleRelByUserId(String userId) {
		// TODO Auto-generated method stub
		String hql="DELETE FROM "+UserRoleRel.TABLE_NAME+" WHERE USER_ID=:userId";
		this.getSessionFactory().getCurrentSession().createSQLQuery(hql)
		.setParameter("userId",userId).executeUpdate();
	}

	@Override
	public UserRoleRel getUserRoleRelByCondition(QueryRule queryRule) {
		// TODO Auto-generated method stub
		List<UserRoleRel> userRoleRels=this.find(queryRule);
		UserRoleRel userRoleRel=null;
		if (userRoleRels!=null && !userRoleRels.isEmpty()) {
			userRoleRel=userRoleRels.get(0);
		}
		return userRoleRel;
	}

	@Override
	public List<UserRoleRel> qeuryListByQueryRule(QueryRule queryRule) {
		return super.find(queryRule);
	}
	
	

}
