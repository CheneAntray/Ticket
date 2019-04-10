package com.xianqin.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.base.ReturnMap;
import com.xianqin.common.QueryRule;
import com.xianqin.dao.UrlInfoDao;
import com.xianqin.domain.UrlInfo;
import com.xianqin.service.UrlInfoService;
@Service("urlInfoService")
public class UrlInfoServiceImpl implements UrlInfoService{

	@Resource
	private UrlInfoDao urlInfoDao;
	
	/**
	 * 查询路径信息
	 */
	@Override
	public ReturnMap queryUrlInfo(QueryRule queryRule) throws Exception {
		// TODO Auto-generated method stub
		ReturnMap returnMap=new ReturnMap();
		List<UrlInfo> urlList=urlInfoDao.getUrlInfoListByCondition(queryRule);
		returnMap.setSucc();
		returnMap.setListContext(urlList);
		returnMap.setMsg("查询路径信息成功");
		return returnMap;
	}

	@Override
	public ReturnMap getUrlByResourceId(String resourceId) throws Exception {
		// TODO Auto-generated method stub
		ReturnMap returnMap=new ReturnMap();
		QueryRule queryRule=QueryRule.getInstance();
		queryRule.addEqual(UrlInfo._id, resourceId);
		List<UrlInfo> list=urlInfoDao.getUrlInfoListByCondition(queryRule);
		returnMap.setSucc();
		returnMap.setListContext(list);
		returnMap.setMsg("查询路径信息成功");
		return returnMap;
	}

}
