package com.xianqin.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.base.ReturnMap;
import com.xianqin.common.QueryRule;
import com.xianqin.dao.ReportDefinedDao;
import com.xianqin.domain.ReportDefined;
import com.xianqin.service.ReportDefinedService;

@Service(value="reportDefinedService")
public class ReportDefinedServiceImpl implements ReportDefinedService{

	@Resource
	private ReportDefinedDao reportDefinedDao;
	
	@Override
	public ReturnMap queryReportDefinedByReportCode(String reportCode) throws Exception {
		// TODO Auto-generated method stub
		ReturnMap returnMap=new ReturnMap();
		QueryRule queryRule=QueryRule.getInstance();
		queryRule.addEqual(ReportDefined._reportCode, reportCode);
		ReportDefined reportDefined=reportDefinedDao.getReportDefinedByCondition(queryRule);
		returnMap.setSucc();
		returnMap.setObjContext(reportDefined);
		returnMap.setMsg("根据报表编号查询报表信息成功");
		return returnMap;
	}

	@Override
	public ReturnMap queryReportListByReportCode(QueryRule queryRule) throws Exception {
		// TODO Auto-generated method stub
		ReturnMap returnMap=new ReturnMap();
		List<ReportDefined> list=reportDefinedDao.getReportDefinedByList(queryRule);
		returnMap.setSucc();
		returnMap.setListContext(list);
		returnMap.setMsg("查询报表信息成功");
		return returnMap;
	}

}
