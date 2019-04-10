package com.xianqin.security.service.impl;

import java.util.ArrayList;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.xianqin.common.QueryRule;
import com.xianqin.dao.MenuInfoDao;
import com.xianqin.dao.ResourceInfoDao;
import com.xianqin.dao.RoleResourceRelDao;
import com.xianqin.dao.UserInfoDao;
import com.xianqin.dao.UserRoleRelDao;
import com.xianqin.domain.MenuInfo;
import com.xianqin.domain.ResourceInfo;
import com.xianqin.domain.RoleResourceRel;
import com.xianqin.domain.UserInfo;
import com.xianqin.domain.UserRoleRel;
import com.xianqin.security.service.LoginService;
import com.xianqin.security.view.MenuVO;

@Service("loginService")
public class LoginServiceImpl implements LoginService {

	@Resource
	private UserInfoDao userInfoDao;

	@Resource
	private UserRoleRelDao userRoleRelDao;

	@Resource
	private MenuInfoDao menuInfoDao;

	@Resource
	private ResourceInfoDao resourceInfoDao;
	
	@Resource
	private RoleResourceRelDao roleResourceRelDao;

	@Override
	public List<String> queryRolesByUserId(String id) {
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addEqual(UserRoleRel._userId, id);
		List<UserRoleRel> nfUserRoleRelList = userRoleRelDao.qeuryListByQueryRule(queryRule);
		List<String> roleIdList = new ArrayList<String>();
		for (UserRoleRel userRoleRel : nfUserRoleRelList) {
			roleIdList.add(userRoleRel.getRoleId());
		}
		return roleIdList;
	}

	@Override
	public List<String> queryMenuIdListByRoleList(List<String> roleIdList) {
		/*List<NfAuthorityRel> nfAuthorityRelList = nfAuthorityRelDao.queryListByRoleIds(roleIdList);
		List<String> menuIdList = new ArrayList<String>();
		for (NfAuthorityRel nfAuthorityRel : nfAuthorityRelList) {
			if (!menuIdList.contains(nfAuthorityRel.getResourceId())) {
				menuIdList.add(nfAuthorityRel.getResourceId());
			}
		}
		return menuIdList;
		*/
		return null;
	}

	@Override
	public UserInfo queryUserByAccount(String account) {
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addEqual(UserInfo._account, account);
		return userInfoDao.getUserInfoByCondition(queryRule);
	}

	@Override
	public List<MenuVO> queryMenuListByIdList(List<String> menuIdList) {
		/*List<NfMenu> nfMenuList = nfMenuDao.queryListByIdList(menuIdList);
		List<MenuVO> menuVOList = new ArrayList<MenuVO>();
		for (NfMenu nfMenu : nfMenuList) {
			MenuVO menuVO = new MenuVO();
			menuVO.setId(nfMenu.getId());
			menuVO.setIcon(nfMenu.getImage());
			menuVO.setIsDefault(nfMenu.getIsDefault());
			menuVO.setIsleaf(true);
			menuVO.setLevel("1");
			menuVO.setName(nfMenu.getName());
			menuVO.setOrderNum(nfMenu.getOrderNum());
			menuVO.setPid(nfMenu.getParentId());
			menuVO.setTitle(nfMenu.getTitle());
			menuVO.setUrl(nfMenu.getUrl());
			menuVOList.add(menuVO);
		}

		// 附带根节点菜单，如菜单不在树上，则不绑进菜单列表中
		return MenuSort.sortShowMenu(menuVOList);
		*/
		return null;
	}

	@Override
	public List<MenuInfo> queryMenuListByAccount(String account) {
		QueryRule queryRule = null;
		UserInfo userInfo = null;
		List<UserRoleRel> roleRelList = null;
		List<String> roleIdList = null;
		List<RoleResourceRel> roleResourceRelList = null;
		List<String> resourceIdList = null;
		List<ResourceInfo> resourceInfos=null;
		// 认证登录用户
		userInfo = queryUserByAccount(account);
		if (userInfo != null) {
			// 查询用户所属的角色信息 由于一个用户可能具有多个权限，所以，这里使用list实例
			queryRule = QueryRule.getInstance();
			queryRule.addEqual(UserRoleRel._userId, userInfo.getId());
			roleRelList = userRoleRelDao.qeuryListByQueryRule(queryRule);
			if (roleRelList != null && roleRelList.size() > 0) {
				roleIdList = getRoleIdByUserRoleRelList(roleRelList);
				// 查询所属角色权限 在角色与权限关联关系中查询
				queryRule = QueryRule.getInstance();
				queryRule.addIn(RoleResourceRel._roleId, roleIdList);
				roleResourceRelList = roleResourceRelDao.getRoleResourceRelListByCondition(queryRule);
				if (roleResourceRelList != null && roleResourceRelList.size() > 0) {
					// 将角色与权限关联信息对象持久化实例队列转换为权限ID队列，方便dao查询
					resourceIdList = getFunctionIdRoleResourceRelList(roleResourceRelList);
					if (resourceIdList != null && resourceIdList.size() > 0) {
						queryRule = QueryRule.getInstance();
						queryRule.addEqual(ResourceInfo._resourceType, 1);
						queryRule.addIn(ResourceInfo._id, resourceIdList);
						resourceInfos = new ArrayList<ResourceInfo>();
						resourceInfos = resourceInfoDao.getResourceInfoListByCondition(queryRule);
						List<String> resourceIds = new ArrayList<String>(resourceInfos.size());
						for (ResourceInfo resources : resourceInfos) {
							resourceIds.add(resources.getResourceId());
						}
						queryRule = QueryRule.getInstance();
						queryRule.addIn(MenuInfo._id, resourceIds);
						queryRule.addAscOrder(MenuInfo._menulv);
						return menuInfoDao.getMenuInfoListByCondition(queryRule);
					}
				}
			}
		}
		return null;
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
	
	/**
	 * 构建首页菜单
	 * @return
	 */
	public MenuInfo buildHomePage(){
        //{"id":"1","name":"首页","url":"main.html","pid":"1","icon":"icon-home","isleaf":true,"level":"1"},
        MenuInfo menuInfo = new MenuInfo();
        menuInfo.setId("0");
        menuInfo.setIcon("icon-home");
        menuInfo.setIsdefault("F");
        menuInfo.setIsleaf(new Long("0"));
        menuInfo.setMenulv(new Long("1"));
        menuInfo.setMenuName("main");
        menuInfo.setOrdernum((long)1);
        menuInfo.setAppId("0");
        menuInfo.setTitle("首页");
        menuInfo.setMenuUrl("about:blank");
        return menuInfo;
    }

}
