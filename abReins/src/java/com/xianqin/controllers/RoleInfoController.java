package com.xianqin.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.base.ApplicationDefined;
import com.base.BaseController;
import com.base.ResponseData;
import com.base.ReturnMap;
import com.base.ServiceRespond;
import com.base.ServiceRespondData;
import com.xianqin.common.Page;
import com.xianqin.common.QueryRule;
import com.xianqin.domain.ResourceInfo;
import com.xianqin.domain.RoleInfo;
import com.xianqin.domain.RoleResourceRel;
import com.xianqin.domain.UserInfo;
import com.xianqin.domain.UserRoleRel;
import com.xianqin.service.ResourceInfoService;
import com.xianqin.service.RoleInfoService;
import com.xianqin.service.RoleResourceRelService;
import com.xianqin.service.UserInfoService;
import com.xianqin.service.UserRoleRelService;
import com.xianqin.view.roleinfo.RoleView;

@RequestMapping("/role")
@RestController
public class RoleInfoController extends BaseController {

	// 角色
	@Autowired
	private RoleInfoService roleInfoService;
	// 角色资源关联业务层
	@Autowired
	private RoleResourceRelService roleResourceRElService;
	// 资源业务层
	@Autowired
	private ResourceInfoService resourceInfoService;
	// 用户角色关联业务层
	@Autowired
	private UserRoleRelService userRoleRelService;
	@Autowired
	private UserInfoService userInfoService;

	/**
	 * 分页获取角色信息
	 * 
	 * @param roleView
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getRoleByPage", method = RequestMethod.POST)
	public @ResponseBody ResponseData queryRoleInfoByPage(@RequestBody RoleView roleView) throws Exception {
		ServiceRespond res = null;
		ResponseData rpt = null;
		ReturnMap ret = null;
		QueryRule queryRule = null;
		try {
			queryRule = QueryRule.getInstance();
			if (roleView.getRoleName() != null && !roleView.getRoleName().equals("")) {
				queryRule.addEqual(RoleInfo._roleName, roleView.getRoleName());
			}
			ret = roleInfoService.queryRoleInfoByPage(queryRule, roleView.getPage(), roleView.getRows());
			res = new ServiceRespond();
			if (ret.isSucc()) {
				@SuppressWarnings("unchecked")
				Page<RoleInfo> page = (Page<RoleInfo>) ret.getObjContext();
				List<RoleInfo> roleInfos = page.getResult();
				List<UserInfo> userInfos=userInfoService.getUserInfoToReport(QueryRule.getInstance());
				List<RoleView> roleViews = new ArrayList<RoleView>(roleInfos.size());
				for (RoleInfo roleInfo : roleInfos) {
					RoleView roleView2 = new RoleView();
					roleView2 = RoleView.processRoleInfoToRoleView(roleView2, roleInfo);
					for (UserInfo userInfo : userInfos) {
						if (roleInfo.getCreateOper() != null) {
							if (roleInfo.getCreateOper().equals(userInfo.getId())) {
								roleView2.setCreateOper(userInfo.getName());
							}
						}
						if (roleInfo.getUpdateOper() != null) {
							if (roleInfo.getUpdateOper().equals(userInfo.getId())) {
								roleView2.setUpdateOper(userInfo.getName());
							}
						}
					}
					roleViews.add(roleView2);
				}
				Page<RoleView> page2 = new Page<RoleView>(page.getStart(), page.getTotalCount(), page.getPageSize(), roleViews);
				rpt = ResponseData.ok();
				rpt.putDataValue("page", page2);
			} else {
				res.setCode(ApplicationDefined.PROCESS_CODE_FAIL);
				res.setMsg("分页查询角色信息失败");
			}
		} catch (Exception e) {
			// TODO: handle exception
			res = new ServiceRespond();
			res.setCode(ApplicationDefined.PROCESS_CODE_ERROR);
			res.setMsg("分页查询角色异常");
			logger.error("分页查询角色异常", e);
		}
		return rpt;
	}

	/**
	 * 保存角色信息
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/saveRole", method = RequestMethod.POST)
	public @ResponseBody ServiceRespond saveRoleInfo(@RequestBody RoleView roleView) throws Exception {
		ServiceRespond res = null;
		ReturnMap ret = null;
		RoleResourceRel roleResourceRel = null;
		RoleInfo roleInfo = new RoleInfo();
		roleInfo = RoleView.processRoleViewToRoleInfo(roleView, roleInfo);
		try {
			ret = roleInfoService.saveRoleInfo(roleInfo);
			res = new ServiceRespond();
			if (ret.isSucc()) {
				roleInfo = (RoleInfo) ret.getObjContext();
				res = new ServiceRespond();
				try {
					roleResourceRel = new RoleResourceRel();
					String[] ary = roleView.getResourceId().split(",");
					for (String resourceId : ary) {
						ResourceInfo resourceInfo = new ResourceInfo();
						ret = resourceInfoService.getResourceIdByResourceId(resourceId);
						resourceInfo = (ResourceInfo) ret.getObjContext();
						roleResourceRel.setResourceId(resourceInfo.getId());
						roleResourceRel.setRoleId(roleInfo.getId());
						ret = roleResourceRElService.saveRoleResourceRel(roleResourceRel);
					}

				} catch (Exception e) {
					// TODO: handle exception
					res = new ServiceRespond();
					res.setCode(ApplicationDefined.PROCESS_CODE_ERROR);
					res.setMsg("添加角色资源表失败");
					logger.error("添加角色资源表失败异常", e);
				}
			} else {
				res.setCode(ApplicationDefined.PROCESS_CODE_FAIL);
			}
			res.setMsg(ret.getMsg());
		} catch (Exception e) {
			// TODO: handle exception
			res = new ServiceRespond();
			res.setCode(ApplicationDefined.PROCESS_CODE_ERROR);
			res.setMsg("添加角色异常");
			logger.error("添加角色发生异常", e);
		}
		return res;
	}

	/**
	 * 获取角色
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getRole", method = RequestMethod.POST)
	public @ResponseBody ServiceRespond getRoleInfo(@RequestBody RoleView roleView) throws Exception {
		ServiceRespond res = null;
		ReturnMap ret = null;
		QueryRule queryRule = null;
		try {
			queryRule = QueryRule.getInstance();
			ret = roleInfoService.queryRoleInfo(queryRule);
			res = new ServiceRespond();
			if (ret.isSucc()) {
				@SuppressWarnings("unchecked")
				List<RoleInfo> roleInfos = (List<RoleInfo>) ret.getObjContext();
				List<RoleInfo> roleViews = new ArrayList<RoleInfo>(roleInfos.size());
				for (RoleInfo roleInfo : roleInfos) {
					RoleView roleView2 = new RoleView();
					roleView2 = RoleInfo.processRoleInfoToRoleView(roleView2, roleInfo);
					roleViews.add(roleView2);
				}
				ServiceRespondData data = new ServiceRespondData(roleViews);
				res.setMsg(ret.getMsg());
				res.setData(data);
			} else {
				res.setCode(ApplicationDefined.PROCESS_CODE_FAIL);
				res.setMsg("查询角色信息失败");
			}
		} catch (Exception e) {
			// TODO: handle exception
			res.setCode(ApplicationDefined.PROCESS_CODE_ERROR);
			res.setMsg("查询角色异常");
			logger.error("查询角色发生异常", e);
		}
		return res;
	}

	/**
	 * 根据id获取角色信息
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getRoleById", method = RequestMethod.POST)
	public @ResponseBody ServiceRespond getRoleById(@RequestBody RoleView roleView) throws Exception {
		ServiceRespond res = null;
		ReturnMap ret = null;
		try {
			ret = roleInfoService.getRoleInfoByRoleId(roleView.getId());
			List<UserInfo> userInfos = userInfoService.getUserInfoToReport(QueryRule.getInstance());
			res = new ServiceRespond();
			if (ret.isSucc()) {
				RoleInfo roleInfo = (RoleInfo) ret.getObjContext();
				RoleView roleView2 = new RoleView();
				roleView2 = RoleInfo.processRoleInfoToRoleView(roleView2, roleInfo);
				for (UserInfo userInfo2 : userInfos) {
					if (roleView2.getCreateOper() != null) {
						if (roleView2.getCreateOper().equals(userInfo2.getId())) {
							roleView2.setCreateOper(userInfo2.getName());
						}
					}
					if (roleView2.getUpdateOper() != null) {
						if (roleView2.getUpdateOper().equals(userInfo2.getId())) {
							roleView2.setUpdateOper(userInfo2.getName());
						}
					}
				}
				ServiceRespondData data = new ServiceRespondData(roleView2);
				res.setMsg(ret.getMsg());
				res.setData(data);
			} else {
				res.setCode(ApplicationDefined.PROCESS_CODE_FAIL);
				res.setMsg("角色信息查询失败");
				return res;
			}
			ret = roleInfoService.queryAuthorityByRoleId(roleView.getId());
			if (ret.isSucc()) {
				res.getData().setList(ret.getListContext());
			}
		} catch (Exception e) {
			// TODO: handle exception
			res = new ServiceRespond();
			res.setCode(ApplicationDefined.PROCESS_CODE_ERROR);
			res.setMsg("角色信息查询异常");
			logger.error("角色信息查询异常", e);
		}
		return res;
	}

	/**
	 * 根据用户id获取角色
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getRoleByUserId", method = RequestMethod.POST)
	public @ResponseBody ServiceRespond getRoleInfoByUserId(@RequestBody RoleView roleView) throws Exception {
		ServiceRespond res = null;
		ReturnMap ret = null;
		List<RoleInfo> roleInfos = new ArrayList<RoleInfo>();
		try {
			ret = userRoleRelService.queryUserRoleRelByUserId(roleView.getId());
			res = new ServiceRespond();
			if (ret.isSucc()) {
				@SuppressWarnings("unchecked")
				List<UserRoleRel> userRoleRels = ret.getListContext();
				for (UserRoleRel ure : userRoleRels) {
					ret = roleInfoService.getRoleInfoByRoleId(ure.getRoleId());
					RoleInfo roleInfo = new RoleInfo();
					roleInfo = (RoleInfo) ret.getObjContext();
					roleInfos.add(roleInfo);
				}
				List<RoleInfo> roleViews = new ArrayList<RoleInfo>(roleInfos.size());
				for (RoleInfo roleInfo : roleInfos) {
					RoleView roleView2 = new RoleView();
					roleView2 = RoleInfo.processRoleInfoToRoleView(roleView2, roleInfo);
					roleViews.add(roleView2);
				}
				ServiceRespondData data = new ServiceRespondData(roleViews);
				res.setMsg(ret.getMsg());
				res.setData(data);
			} else {
				res.setCode(ApplicationDefined.PROCESS_CODE_FAIL);
				res.setMsg("查询角色信息失败");
			}
		} catch (Exception e) {
			// TODO: handle exception
			res.setCode(ApplicationDefined.PROCESS_CODE_ERROR);
			res.setMsg("查询角色异常");
			logger.error("查询角色发生异常", e);
		}
		return res;
	}

	/**
	 * 添加用户时加载权限树形控件信息
	 * 
	 * @param roleView
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/showTreeView", method = RequestMethod.POST)
	public @ResponseBody ServiceRespond getUrlAndMenuByRoleId(@RequestBody RoleView roleView) throws Exception {
		ServiceRespond res = null;
		ReturnMap ret = null;
		try {
			ret = roleInfoService.queryAuthorityByRoleId(roleView.getId());
			res = new ServiceRespond();
			if (ret.isSucc()) {
				ServiceRespondData data = new ServiceRespondData(ret.getListContext());
				res.setMsg(ret.getMsg());
				res.setData(data);
			} else {
				res.setCode(ApplicationDefined.PROCESS_CODE_FAIL);
				res.setMsg("获取权限失败");

			}
		} catch (Exception e) {
			// TODO: handle exception
			res.setCode(ApplicationDefined.PROCESS_CODE_ERROR);
			res.setMsg("获取权限失败");
			logger.error("获取权限失败异常", e);
		}
		return res;
	}

	@RequestMapping(value = "/editRoleById", method = RequestMethod.POST)
	public @ResponseBody ServiceRespond editRoleInfoByRoleId(@RequestBody RoleView roleView) throws Exception {
		ServiceRespond res = null;
		ReturnMap ret = null;
		RoleInfo roleInfo = new RoleInfo();
		RoleResourceRel roleResourceRel = null;
		roleInfo = RoleView.processRoleViewToRoleInfo(roleView, roleInfo);
		try {
			ret = roleInfoService.editRoleInfo(roleInfo);
			res = new ServiceRespond();
			if (ret.isSucc()) {
				ret = roleResourceRElService.deleteRoleResourceRelByRoleId(roleInfo.getId());
				res = new ServiceRespond();
				if (ret.isSucc()) {
					roleResourceRel = new RoleResourceRel();
					String[] ary = roleView.getResourceId().split(",");
					if (roleView.getResourceId() != null && !"".equals(roleView.getResourceId())) {
						for (String resourceId : ary) {
							ResourceInfo resourceInfo = new ResourceInfo();
							ret = resourceInfoService.getResourceIdByResourceId(resourceId);
							resourceInfo = (ResourceInfo) ret.getObjContext();
							roleResourceRel.setResourceId(resourceInfo.getId());
							roleResourceRel.setRoleId(roleInfo.getId());
							ret = roleResourceRElService.saveRoleResourceRel(roleResourceRel);
						}
					}
				} else {
					res.setCode(ApplicationDefined.PROCESS_CODE_FAIL);
					res.setMsg("删除角色资源关联表失败");
				}
			} else {
				res.setCode(ApplicationDefined.PROCESS_CODE_FAIL);
				res.setMsg("修改角色信息失败");
			}
		} catch (Exception e) {
			// TODO: handle exception
			res.setCode(ApplicationDefined.PROCESS_CODE_ERROR);
			res.setMsg("修改角色信息异常");
			logger.error("修改角色信息异常", e);
		}
		return res;
	}
}
