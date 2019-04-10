package com.xianqin.dao.impl;

import java.util.List;
import org.springframework.stereotype.Repository;

import com.base.utils.UUIDGenerator;
import com.xianqin.common.Page;
import com.xianqin.common.QueryRule;
import com.xianqin.dao.RoleResourceRelDao;
import com.xianqin.domain.RoleResourceRel;
/**
 * 角色资源关联信息对象数据访问层接口实现类
 * 该接口实现了roleResourceRelDao接口常用的数据访问层方法
 * @author xianqin-atuoBuilder
 * @@version 1.0
 */
 
@Repository("roleResourceRelDao")
public class RoleResourceRelDaoImpl extends CommonDaoImpl<RoleResourceRel, String> implements RoleResourceRelDao{
	@Override
	public void saveRoleResourceRel(RoleResourceRel roleResourceRel) {
		roleResourceRel.setId(UUIDGenerator.getUUID()); //生成主键
		this.save(roleResourceRel);
	}

	@Override
	public void updateRoleResourceRel(RoleResourceRel roleResourceRel) {
		this.update(roleResourceRel);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Page<RoleResourceRel> queryRoleResourceRelByPage(QueryRule queryRule, Integer pageIndex, Integer pageSize) {
		queryRule.addDescOrder("createTime");
		return  this.find(queryRule, pageIndex, pageSize);
	}

    @Override
	public void deleteRoleResourceRelById(String roleResourceRelId) {
		String hql="DELETE FROM RoleResourceRel WHERE ID=?";
		this.getSessionFactory().getCurrentSession().createQuery(hql)
		.setParameter(0, roleResourceRelId).executeUpdate();
	}
	
	@Override
	public RoleResourceRel getRoleResourceRelByCondition(QueryRule queryRule) {
		List<RoleResourceRel> roleResourceRels=this.find(queryRule);
		RoleResourceRel roleResourceRel=null;
		if (roleResourceRels!=null && !roleResourceRels.isEmpty()) {
			roleResourceRel=roleResourceRels.get(0);
		}
		return roleResourceRel;
	}

	@Override
	public List<RoleResourceRel> getRoleResourceRelListByCondition(QueryRule queryRule) {
		// TODO Auto-generated method stub
		return this.find(queryRule);
	}

	@Override
	public void deleteRoleResourceByRoleId(String roleid) {
		// TODO Auto-generated method stub
		String hql="DELETE FROM RoleResourceRel WHERE roleId=?";
		this.getSessionFactory().getCurrentSession().createQuery(hql)
		.setParameter(0, roleid).executeUpdate();
	}
}
