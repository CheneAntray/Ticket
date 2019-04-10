package com.xianqin.dao.impl;

import java.util.List;
import org.springframework.stereotype.Repository;
import com.xianqin.common.Page;
import com.xianqin.common.QueryRule;
import com.xianqin.dao.UndertakeEnterpriseInfoDao;
import com.xianqin.domain.UndertakeEnterpriseInfo;
/**
 * 担当企业信息对象数据访问层接口实现类
 * 该接口实现了undertakeEnterpriseInfoDao接口常用的数据访问层方法
 * @author xianqin-atuoBuilder
 * @@version 1.0
 */
 
@Repository("undertakeEnterpriseInfoDao")
public class UndertakeEnterpriseInfoDaoImpl extends CommonDaoImpl<UndertakeEnterpriseInfo, String> implements UndertakeEnterpriseInfoDao{
	@Override
	public void saveUndertakeEnterpriseInfo(UndertakeEnterpriseInfo undertakeEnterpriseInfo) {
		this.save(undertakeEnterpriseInfo);
	}

	@Override
	public void updateUndertakeEnterpriseInfo(UndertakeEnterpriseInfo undertakeEnterpriseInfo) {
		this.update(undertakeEnterpriseInfo);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Page<UndertakeEnterpriseInfo> queryUndertakeEnterpriseInfoByPage(QueryRule queryRule, Integer pageIndex, Integer pageSize) {
		queryRule.addDescOrder("createTime");
		return  this.find(queryRule, pageIndex, pageSize);
	}

    @Override
	public void deleteUndertakeEnterpriseInfoById(String undertakeEnterpriseInfoId) {
		String hql="DELETE FROM UndertakeEnterpriseInfo WHERE ID=?";
		this.getSessionFactory().getCurrentSession().createQuery(hql)
		.setParameter(0, undertakeEnterpriseInfoId).executeUpdate();
	}
	
	@Override
	public UndertakeEnterpriseInfo getUndertakeEnterpriseInfoByCondition(QueryRule queryRule) {
		List<UndertakeEnterpriseInfo> undertakeEnterpriseInfos=this.find(queryRule);
		UndertakeEnterpriseInfo undertakeEnterpriseInfo=null;
		if (undertakeEnterpriseInfos!=null && !undertakeEnterpriseInfos.isEmpty()) {
			undertakeEnterpriseInfo=undertakeEnterpriseInfos.get(0);
		}
		return undertakeEnterpriseInfo;
	}

	@Override
	public List<UndertakeEnterpriseInfo> getUndertakeEnterpriseInfoListByCondition(QueryRule queryRule) {
		// TODO Auto-generated method stub
		return this.find(queryRule);
	}
}
