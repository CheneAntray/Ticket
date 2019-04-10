package com.xianqin.dao.impl;

import java.util.Date;
import java.util.List;
import org.springframework.stereotype.Repository;

import com.base.utils.UUIDGenerator;
import com.xianqin.common.Page;
import com.xianqin.common.QueryRule;
import com.xianqin.dao.UrlInfoDao;
import com.xianqin.domain.UrlInfo;
/**
 * 路径信息对象数据访问层接口实现类
 * 该接口实现了urlInfoDao接口常用的数据访问层方法
 * @author xianqin-atuoBuilder
 * @@version 1.0
 */
 
@Repository("urlInfoDao")
public class UrlInfoDaoImpl extends CommonDaoImpl<UrlInfo, String> implements UrlInfoDao{
	@Override
	public void saveUrlInfo(UrlInfo urlInfo) {
		urlInfo.setId(UUIDGenerator.getUUID()); //生成主键
		urlInfo.setCreateTime(new Date());    //创建时间
		this.save(urlInfo);
	}

	@Override
	public void updateUrlInfo(UrlInfo urlInfo) {
		urlInfo.setUpdateTime(new Date());
		this.update(urlInfo);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Page<UrlInfo> queryUrlInfoByPage(QueryRule queryRule, Integer pageIndex, Integer pageSize) {
		queryRule.addDescOrder("createTime");
		return  this.find(queryRule, pageIndex, pageSize);
	}

    @Override
	public void deleteUrlInfoById(String urlInfoId) {
		String hql="DELETE FROM UrlInfo WHERE ID=?";
		this.getSessionFactory().getCurrentSession().createQuery(hql)
		.setParameter(0, urlInfoId).executeUpdate();
	}
	
	@Override
	public UrlInfo getUrlInfoByCondition(QueryRule queryRule) {
		List<UrlInfo> urlInfos=this.find(queryRule);
		UrlInfo urlInfo=null;
		if (urlInfos!=null && !urlInfos.isEmpty()) {
			urlInfo=urlInfos.get(0);
		}
		return urlInfo;
	}

	@Override
	public List<UrlInfo> getUrlInfoListByCondition(QueryRule queryRule) {
		// TODO Auto-generated method stub
		return this.find(queryRule);
	}
}
