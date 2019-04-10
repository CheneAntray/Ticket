package com.xianqin.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.base.ReturnMap;
import com.xianqin.common.QueryRule;
import com.xianqin.dao.ResourceInfoDao;
import com.xianqin.domain.ResourceInfo;
import com.xianqin.service.ResourceInfoService;

@Service("resourceInfoService")
public class ResourceInfoServiceImpl implements ResourceInfoService{

	@Resource
	private ResourceInfoDao resourceInfoDao;
	
	@Override
	public ReturnMap getIdByResourceId(String resourceid) throws Exception {
		// TODO Auto-generated method stub
		ReturnMap returnMap=new ReturnMap();
		QueryRule queryRule=QueryRule.getInstance();
		queryRule.addEqual(ResourceInfo._id, resourceid);
		List<ResourceInfo> list=resourceInfoDao.getResourceInfoListByCondition(queryRule);
		returnMap.setSucc();
		returnMap.setListContext(list);
		returnMap.setMsg("查询资源表id成功");
		return returnMap;
	}

	@Override
	public ReturnMap queryListByResourceType(Integer reosurceType) throws Exception {
		// TODO Auto-generated method stub
		ReturnMap returnMap=new ReturnMap();
		QueryRule queryRule=QueryRule.getInstance();
		queryRule.addEqual(ResourceInfo._resourceType, reosurceType);
		List<ResourceInfo> list=resourceInfoDao.getResourceInfoListByCondition(queryRule);
		returnMap.setSucc();
		returnMap.setListContext(list);
		return returnMap;
	}

	@Override
	public ReturnMap queryResourceList(QueryRule queryRule) throws Exception {
		// TODO Auto-generated method stub
		ReturnMap returnMap=new ReturnMap();
		List<ResourceInfo> list=resourceInfoDao.getResourceInfoListByCondition(queryRule);
		returnMap.setSucc();
		returnMap.setListContext(list);
		return returnMap;
	}

	@Override
	public ReturnMap getResourceIdByResourceId(String resourceId) throws Exception {
		// TODO Auto-generated method stub
		ReturnMap returnMap=new ReturnMap();
		QueryRule queryRule=QueryRule.getInstance();
		queryRule.addEqual(ResourceInfo._resourceId, resourceId);
		ResourceInfo resourceInfo=resourceInfoDao.getResourceInfoByCondition(queryRule);
		returnMap.setSucc();
		returnMap.setObjContext(resourceInfo);
		returnMap.setMsg("获取资源表id成功");
		return returnMap;
	}
	
}
