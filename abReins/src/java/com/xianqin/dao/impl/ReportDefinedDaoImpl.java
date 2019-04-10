package com.xianqin.dao.impl;


import java.util.List;
import org.springframework.stereotype.Repository;

import com.base.utils.UUIDGenerator;
import com.xianqin.common.Page;
import com.xianqin.common.QueryRule;
import com.xianqin.dao.ReportDefinedDao;
import com.xianqin.domain.ReportDefined;
/**
 * 报表定义信息对象数据访问层接口实现类
 * 该接口实现了reportDefinedDao接口常用的数据访问层方法
 * @author xianqin-atuoBuilder
 * @@version 1.0
 */
 
@Repository("reportDefinedDao")
public class ReportDefinedDaoImpl extends CommonDaoImpl<ReportDefined, String> implements ReportDefinedDao{
	@Override
	public void saveReportDefined(ReportDefined reportDefined) {
		reportDefined.setId(UUIDGenerator.getUUID()); //生成主键
		this.save(reportDefined);
	}

	@Override
	public void updateReportDefined(ReportDefined reportDefined) {
		this.update(reportDefined);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Page<ReportDefined> queryReportDefinedByPage(QueryRule queryRule, Integer pageIndex, Integer pageSize) {
		queryRule.addDescOrder("createTime");
		return  this.find(queryRule, pageIndex, pageSize);
	}

    @Override
	public void deleteReportDefinedById(String reportDefinedId) {
		String hql="DELETE FROM ReportDefined WHERE ID=?";
		this.getSessionFactory().getCurrentSession().createQuery(hql)
		.setParameter(0, reportDefinedId).executeUpdate();
	}
	
	@Override
	public ReportDefined getReportDefinedByCondition(QueryRule queryRule) {
		List<ReportDefined> reportDefineds=this.find(queryRule);
		ReportDefined reportDefined=null;
		if (reportDefineds!=null && !reportDefineds.isEmpty()) {
			reportDefined=reportDefineds.get(0);
		}
		return reportDefined;
	}

	@Override
	public List<ReportDefined>  getReportDefinedByList(QueryRule queryRule) {
		// TODO Auto-generated method stub
		List<ReportDefined> reportDefineds=this.find(queryRule);
		return reportDefineds;
	}
}
