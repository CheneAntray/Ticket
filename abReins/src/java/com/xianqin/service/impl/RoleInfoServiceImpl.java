package com.xianqin.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.base.ReturnMap;
import com.base.utils.LogUtils;
import com.xianqin.common.Page;
import com.xianqin.common.QueryRule;
import com.xianqin.dao.RoleInfoDao;
import com.xianqin.domain.MenuInfo;
import com.xianqin.domain.ResourceInfo;
import com.xianqin.domain.RoleInfo;
import com.xianqin.domain.RoleResourceRel;
import com.xianqin.domain.UrlInfo;
import com.xianqin.security.service.AccountService;
import com.xianqin.service.MenuInfoService;
import com.xianqin.service.ResourceInfoService;
import com.xianqin.service.RoleInfoService;
import com.xianqin.service.RoleResourceRelService;
import com.xianqin.service.UrlInfoService;
import com.xianqin.view.roleinfo.TreeView;

@Service("roleInfoService")
public class RoleInfoServiceImpl implements RoleInfoService {

	@Autowired
	private AccountService accountService;
	
	@Resource
	private RoleInfoDao roleInfoDao;

	@Resource
	private MenuInfoService menuInfoService;

	@Resource
	private UrlInfoService urlInfoService;

	@Resource
	private RoleResourceRelService roleResourceRelService;

	@Resource
	private ResourceInfoService resourceInfoService;

	@SuppressWarnings("unused")
	private static Logger logger = LogUtils.getConsoleLogIns();
	
	public Subject subject=null;

	@SuppressWarnings("unchecked")
	@Override
	public ReturnMap queryAuthorityByRoleId(String roleId) throws Exception {
		// TODO Auto-generated method stub
		ReturnMap ret = new ReturnMap();
		// 菜单list集合
		List<MenuInfo> menuInfoList = null;
		// 权限list集合
		List<UrlInfo> urlInfosList = null;
		// 资源信息list集合
		List<ResourceInfo> resourceInfoList = null;
		// 角色资源表list集合
		List<RoleResourceRel> roleResourceRelList = null;
		// 菜单信息map集合
		Map<String, MenuInfo> menuInfoMap = null;
		// 权限信息map集合
		Map<String, UrlInfo> urlInfoMap = null;
		// 资源信息map集合
		Map<String, ResourceInfo> resourceInfoMap = null;
		// 树形控件map集合
		Map<Integer, TreeView> treeMap = new HashMap<Integer, TreeView>();
		// 树形控件对象
		TreeView treeView = null;
		// 资源信息对象
		ResourceInfo resourceInfoIns = null;
		// 初始化查询
		QueryRule queryRule = QueryRule.getInstance();
		// 获取菜单信息
		ret = menuInfoService.getMenuInfo(queryRule);
		menuInfoList = ret.getListContext();
		menuInfoMap = new HashMap<String, MenuInfo>(menuInfoList.size());
		for (MenuInfo menuInfo : menuInfoList) {
			menuInfoMap.put(menuInfo.getId(), menuInfo);
			// 将菜单持久化数据转化为视图层树形控件初始化数据
			// 此处树形控件初始化数据将作为控件一级节点存在，所以pid不传则构造出的树形控件初始化数据条目pid等于0
			treeView = new TreeView(menuInfo.getId(), menuInfo.getMenuName(), null);
			treeMap.put(treeView.getId(), treeView);
		}
		// 获取权限信息
		ret = urlInfoService.queryUrlInfo(queryRule);
		urlInfosList = ret.getListContext();
		urlInfoMap = new HashMap<String, UrlInfo>(urlInfosList.size());
		for (UrlInfo urlInfo : urlInfosList) {
			urlInfoMap.put(urlInfo.getId(), urlInfo);
			// 将菜单持久化数据转化为试图层树形控件初始化数据
			// 此处树形控件初始化数据将作为控件一级节点存在，所以pid不传则构造出的树形控件初始化数据条目pid等于0
			// 由于现在未知角色资源关联关系信息，所以暂时将urlInfo持久化对象直接作为一级节点，待资源关联关系信息查明后进行赋值
			treeView = new TreeView(urlInfo.getId(), urlInfo.getUrlName(), null);
			treeMap.put(treeView.getId(), treeView);
		}
		// 获取资源信息
		ret = resourceInfoService.queryResourceList(queryRule);
		resourceInfoList = ret.getListContext();
		resourceInfoMap = new HashMap<String, ResourceInfo>(resourceInfoList.size());
		for (ResourceInfo resourceInfo : resourceInfoList) {
			resourceInfoMap.put(resourceInfo.getId(), resourceInfo);
			treeView = treeMap.get(Integer.valueOf(resourceInfo.getResourceId()));
			if (resourceInfo.getParentMenu() == null || resourceInfo.getParentMenu().equals("")) {
				treeView.setOpen(true);
				treeView.setpId(0);
			} else {
				treeView.setpId(resourceInfo.getParentMenu());
			}
			treeMap.put(treeView.getId(), treeView);
		}
		// 根据角色ID查询角色资源关联表信息
		ret = roleResourceRelService.getRoleResourceRelIdByRoleId(roleId);
		roleResourceRelList = ret.getListContext();
		for (RoleResourceRel roleResourceRel : roleResourceRelList) {
			// 获取该角色已有的资源权限，然后在树形控件中查找已有的资源信息
			resourceInfoIns = resourceInfoMap.get(roleResourceRel.getResourceId());
			treeView = treeMap.get(Integer.valueOf(resourceInfoIns.getResourceId()));
			// 然后把该角色已有的资源信息的check更改为ture
			treeView.setChecked(true);
		}
		List<TreeView> treeViewList = new ArrayList<TreeView>(treeMap.size());
		for (Map.Entry<Integer, TreeView> entry : treeMap.entrySet()) {
			treeViewList.add(entry.getValue());
		}
		ret.setSucc();
		ret.setListContext(treeViewList);
		return ret;
	}

	@Override
	public ReturnMap queryRoleInfo(QueryRule queryRule) throws Exception {
		// TODO Auto-generated method stub
		ReturnMap returnMap = new ReturnMap();
		List<RoleInfo> list = roleInfoDao.getRoleInfoByCondition(queryRule);
		returnMap.setSucc();
		returnMap.setObjContext(list);
		returnMap.setMsg("查询角色信息成功");
		return returnMap;
	}

	@Override
	public ReturnMap saveRoleInfo(RoleInfo roleInfo) throws Exception {
		// TODO Auto-generated method stub
		ReturnMap returnMap = new ReturnMap();
		RoleInfo roleInfo2 = null;
		subject=SecurityUtils.getSubject();
		if (queryRoleInfoIsExist(roleInfo.getRoleName())) {
			roleInfo.setCreateOper(accountService.getUserIdBySubjec(subject));
			roleInfo2 = roleInfoDao.saveRoleInfo(roleInfo);
			returnMap.setSucc();
			returnMap.setObjContext(roleInfo2);
			returnMap.setMsg("添加角色信息成功");
		} else {
			returnMap.setMsg("您填写的角色名称已经存在，请您重新填写");
		}
		return returnMap;
	}

	@Override
	public boolean queryRoleInfoIsExist(String roleName) throws Exception {
		// TODO Auto-generated method stub
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addEqual(RoleInfo._roleName, roleName);
		RoleInfo roleInfo = roleInfoDao.getRoleInfoByRoleName(queryRule);
		if (roleInfo != null && roleInfo.getRoleName().equals(roleName)) {
			return false;
		}
		return true;
	}

	@Override
	public ReturnMap getRoleInfoByRoleName(String roleName) throws Exception {
		// TODO Auto-generated method stub
		ReturnMap returnMap = new ReturnMap();
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addEqual(RoleInfo._roleName, roleName);
		RoleInfo roleInfo = (RoleInfo) roleInfoDao.getRoleInfoByRoleName(queryRule);
		returnMap.setSucc();
		returnMap.setObjContext(roleInfo);
		returnMap.setMsg("查询角色信息成功");
		return returnMap;
	}

	@Override
	public ReturnMap getRoleInfoByRoleId(String roleid) throws Exception {
		// TODO Auto-generated method stub
		ReturnMap returnMap = new ReturnMap();
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addEqual(RoleInfo._id, roleid);
		RoleInfo roleInfo = (RoleInfo) roleInfoDao.getRoleInfoByRoleName(queryRule);
		returnMap.setSucc();
		returnMap.setObjContext(roleInfo);
		returnMap.setMsg("查询角色信息成功");
		return returnMap;
	}

	@Override
	public ReturnMap queryRoleInfoByPage(QueryRule queryRule, Integer pageIndex, Integer pageSize) throws Exception {
		// TODO Auto-generated method stub
		ReturnMap returnMap = new ReturnMap();
		Page<RoleInfo> page = roleInfoDao.queryRoleInfoByPage(queryRule, pageIndex, pageSize);
		returnMap.setSucc();
		returnMap.setObjContext(page);
		returnMap.setMsg("分页查询角色信息成功");
		return returnMap;
	}

	@Override
	public ReturnMap editRoleInfo(RoleInfo roleInfo) throws Exception {
		// TODO Auto-generated method stub
		ReturnMap returnMap = new ReturnMap();
		QueryRule queryRule=QueryRule.getInstance();
		queryRule.addEqual(RoleInfo._id, roleInfo.getId());
		RoleInfo roleInfoIns=roleInfoDao.getRoleInfoByRoleName(queryRule);
		if (roleInfoIns!=null) {
			subject=SecurityUtils.getSubject();
			roleInfo.setUpdateOper(accountService.getUserIdBySubjec(subject));
			roleInfoIns.setRoleName(roleInfo.getRoleName());
			roleInfoIns.setUpdateTime(roleInfo.getUpdateTime());
			roleInfoIns.setUpdateOper(roleInfo.getUpdateOper());
			roleInfoDao.updateRoleInfo(roleInfoIns);
			returnMap.setSucc();
			returnMap.setMsg("修改角色信息成功");
		}
		
		return returnMap;
	}

	@Override
	public List<RoleInfo> queryRoleInfoToReport(QueryRule queryRule) throws Exception {
		// TODO Auto-generated method stub
		List<RoleInfo> list = roleInfoDao.getRoleInfoByCondition(queryRule);
		return list;
	}
}
