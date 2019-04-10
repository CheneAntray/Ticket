package com.xianqin.security.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Service;

import com.xianqin.common.QueryRule;
import com.xianqin.dao.ResourceInfoDao;
import com.xianqin.dao.RoleResourceRelDao;
import com.xianqin.dao.UrlInfoDao;
import com.xianqin.dao.UserInfoDao;
import com.xianqin.dao.UserRoleRelDao;
import com.xianqin.domain.ResourceInfo;
import com.xianqin.domain.RoleResourceRel;
import com.xianqin.domain.UrlInfo;
import com.xianqin.domain.UserInfo;
import com.xianqin.domain.UserRoleRel;
import com.xianqin.security.service.AccountService;

/**
 * 系统用户服务实现类
 * 该类实现了系统用户服务接口
 * @author xianqin-bill
 *
 */
@Service("accountService")
public class AccountServiceImpl implements AccountService {
	
	@Resource
	private UserInfoDao userInfoDao;
	
	@Resource
	private UserRoleRelDao userRoleRelDao;
	
	@Resource
	private RoleResourceRelDao roleResourceRelDao;
	
	@Resource
	private UrlInfoDao urlInfoDao;
	
	@Resource
	private ResourceInfoDao resourceInfoDao;
	
	
	@Override
	public UserInfo getUserInfoByUserName(String userName) throws Exception {
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addEqual(UserInfo._account, userName);
		return userInfoDao.getUserInfoByCondition(queryRule);
	}

	@Override
	public List<UrlInfo> getPermissionsByUserName(String userName) throws Exception {
		QueryRule queryRule = null;
		UserInfo userInfo = null;
		List<UserRoleRel> roleRelList = null;
		List<String> roleIdList = null;
		List<RoleResourceRel> roleResourceRelList = null;
		List<String> resourceIdList = null;
		List<ResourceInfo> resourceInfos=null;
		//根据用户名查询用户信息
		userInfo = getUserInfoByUserName(userName);
		queryRule = QueryRule.getInstance();
		queryRule.addEqual(UserRoleRel._userId,userInfo.getId());
		if(userInfo!=null){
			//查询用户所属的角色信息 由于一个用户可能具有多个权限，所以，这里使用list实例
			roleRelList = userRoleRelDao.qeuryListByQueryRule(queryRule);
			if(roleRelList!=null && roleRelList.size()>0){
				roleIdList = getRoleIdByUserRoleRelList(roleRelList);
				//查询所属角色权限 在角色与权限关联关系中查询
				queryRule = QueryRule.getInstance();
				queryRule.addIn(RoleResourceRel._roleId, roleIdList);
				roleResourceRelList = roleResourceRelDao.getRoleResourceRelListByCondition(queryRule);
				if(roleResourceRelList!=null && roleResourceRelList.size()>0){
					//将角色与权限关联信息对象持久化实例队列转换为权限ID队列，方便dao查询
					resourceIdList = getFunctionIdRoleResourceRelList(roleResourceRelList);
					if(resourceIdList!=null && resourceIdList.size()>0){
						queryRule = QueryRule.getInstance();
						queryRule.addEqual(ResourceInfo._resourceType, 2);
						queryRule.addIn(ResourceInfo._id,resourceIdList );
						resourceInfos=resourceInfoDao.getResourceInfoListByCondition(queryRule);
						List<String> resourceIds=new ArrayList<String>(resourceInfos.size());
						for (ResourceInfo resources : resourceInfos) {
							resourceIds.add(resources.getResourceId());
						}
						queryRule=QueryRule.getInstance();
						queryRule.addIn(UrlInfo._id,resourceIds);
						return urlInfoDao.getUrlInfoListByCondition(queryRule);
					}
				}
			}
		}
		return null;
	}
	
	@Override
	public Boolean checkLogin(String userName,String password)throws Exception {
		UserInfo userInfo = getUserInfoByUserName(userName);
		if (userInfo != null) {
			if(userInfo.getPassword().equals(password)){
				return true;
			}else{
				return false;
			}
		}else{
			return false;
		}
	}
	
	/**
	 * 将UserRoleRel持久化关系队列，转换为只包含角色对象ID的队列
	 * @param userRoleRelList UserRoleRel持久化对象实例队列
	 * @return
	 */
	private List<String> getRoleIdByUserRoleRelList(List<UserRoleRel> userRoleRelList){
		List<String> returnList = new ArrayList<String>(userRoleRelList.size());
		for(UserRoleRel userRoleRel:userRoleRelList){
			returnList.add(userRoleRel.getRoleId());
		}
		return returnList;
	}
	
	/**
	 * 将RoleFunctionRel持久化关系队列，转换为只包含权限定义持久化对象ID的队列
	 * @param roleFunctionRelList RoleFunctionRel持久化对象实例队列
	 * @return
	 */
	private List<String> getFunctionIdRoleResourceRelList(List<RoleResourceRel> roleResourceRel){
		List<String> returnList = new ArrayList<String>(roleResourceRel.size());
		for(RoleResourceRel roleResource:roleResourceRel){
			returnList.add(roleResource.getResourceId());
		}
		return returnList;
	}
	
	@Override
	public String getUserIdBySubjec(Subject subject) throws Exception{
		String account = (String)subject.getPrincipal();
		UserInfo userInfo= getUserInfoByUserName(account);
		if (userInfo!=null) {
			return userInfo.getId();
		}
		return null;
	}
}
