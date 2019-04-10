package com.xianqin.dao.impl;

import java.util.Date;
import java.util.List;
import org.springframework.stereotype.Repository;

import com.base.utils.UUIDGenerator;
import com.xianqin.common.Page;
import com.xianqin.common.QueryRule;
import com.xianqin.dao.RoleInfoDao;
import com.xianqin.domain.RoleInfo;
/**
 * 角色基本信息对象数据访问层接口实现类
 * 该接口实现了roleInfoDao接口常用的数据访问层方法
 * @author xianqin-atuoBuilder
 * @@version 1.0
 */
 
@Repository("roleInfoDao")
public class RoleInfoDaoImpl extends CommonDaoImpl<RoleInfo, String> implements RoleInfoDao{
	@Override
	public RoleInfo saveRoleInfo(RoleInfo roleInfo) {
		roleInfo.setId(UUIDGenerator.getUUID()); //生成主键
		roleInfo.setCreateTime(new Date());    //创建时间
		this.save(roleInfo);
		return roleInfo;
	}

	@Override
	public void updateRoleInfo(RoleInfo roleInfo) {
		roleInfo.setUpdateTime(new Date());
		this.update(roleInfo);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Page<RoleInfo> queryRoleInfoByPage(QueryRule queryRule, Integer pageIndex, Integer pageSize) {
		queryRule.addDescOrder("createTime");
		return  this.find(queryRule, pageIndex, pageSize);
	}

    @Override
	public void deleteRoleInfoById(String roleInfoId) {
		String hql="DELETE FROM RoleInfo WHERE ID=?";
		this.getSessionFactory().getCurrentSession().createQuery(hql)
		.setParameter(0, roleInfoId).executeUpdate();
	}
	
	@Override
	public List<RoleInfo> getRoleInfoByCondition(QueryRule queryRule) {
		List<RoleInfo> roleInfo=this.find(queryRule);
		return roleInfo;
	}

	@Override
	public RoleInfo getRoleInfoByRoleName(QueryRule queryRule) {
		// TODO Auto-generated method stub
		List<RoleInfo> roleInfos=this.find(queryRule);
		RoleInfo roleInfo=null;
		if (roleInfos!=null&&!roleInfos.isEmpty()) {
			roleInfo=roleInfos.get(0);
		}
		return roleInfo;
	}
}
