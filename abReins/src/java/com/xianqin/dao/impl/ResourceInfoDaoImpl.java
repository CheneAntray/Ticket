package com.xianqin.dao.impl;


import java.util.List;
import org.springframework.stereotype.Repository;

import com.base.utils.UUIDGenerator;
import com.xianqin.common.Page;
import com.xianqin.common.QueryRule;
import com.xianqin.dao.ResourceInfoDao;
import com.xianqin.domain.ResourceInfo;
/**
 * 资源信息对象数据访问层接口实现类
 * 该接口实现了resourceInfoDao接口常用的数据访问层方法
 * @author xianqin-atuoBuilder
 * @@version 1.0
 */
 
@Repository("resourceInfoDao")
public class ResourceInfoDaoImpl extends CommonDaoImpl<ResourceInfo, String> implements ResourceInfoDao{
	@Override
	public void saveResourceInfo(ResourceInfo resourceInfo) {
		resourceInfo.setId(UUIDGenerator.getUUID()); //生成主键
		this.save(resourceInfo);
	}

	@Override
	public void updateResourceInfo(ResourceInfo resourceInfo) {
		this.update(resourceInfo);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Page<ResourceInfo> queryResourceInfoByPage(QueryRule queryRule, Integer pageIndex, Integer pageSize) {
		queryRule.addDescOrder("createTime");
		return  this.find(queryRule, pageIndex, pageSize);
	}

    @Override
	public void deleteResourceInfoById(String resourceInfoId) {
		String hql="DELETE FROM ResourceInfo WHERE ID=?";
		this.getSessionFactory().getCurrentSession().createQuery(hql)
		.setParameter(0, resourceInfoId).executeUpdate();
	}
	
	@Override
	public ResourceInfo getResourceInfoByCondition(QueryRule queryRule) {
		List<ResourceInfo> resourceInfos=this.find(queryRule);
		ResourceInfo resourceInfo=null;
		if (resourceInfos!=null && !resourceInfos.isEmpty()) {
			resourceInfo=resourceInfos.get(0);
		}
		return resourceInfo;
	}

	@Override
	public List<ResourceInfo> getResourceInfoListByCondition(QueryRule queryRule) {
		// TODO Auto-generated method stub
		return this.find(queryRule);
	}
}
