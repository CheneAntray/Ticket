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
import com.xianqin.domain.UserInfo;
import com.xianqin.domain.UserRoleRel;
import com.xianqin.service.UserInfoService;
import com.xianqin.service.UserRoleRelService;
import com.xianqin.view.user.UserView;

@RequestMapping(value = "/user")
@RestController
public class UserInfoController extends BaseController {
	/**
	 * 用户逻辑层接口
	 */
	@Autowired
	public UserInfoService userInfoService;
	/**
	 * 角色关联逻辑层接口
	 */
	@Autowired
	public UserRoleRelService userRoleRelService;

	@RequestMapping(value = "/queryuser", method = RequestMethod.POST)
	public @ResponseBody ResponseData queryUser(@RequestBody UserView userView) throws Exception {
		ServiceRespond res = null;
		ResponseData rpd = null;
		try {
			QueryRule queryRule = QueryRule.getInstance();// 初始化查询参数
			if (userView.getAccount() != null && !userView.getAccount().equals("")) {
				queryRule.addLike(UserInfo._account, "%" + userView.getAccount() + "%");// 设置参数
			}
			if (userView.getName() != null && !userView.getName().equals("")) {
				queryRule.addLike(UserInfo._name, "%" + userView.getName() + "%");
			}
			// 获取的集合放入到ret中 因为service中的这个方法的返回值是ReturnMap，所以用他接收
			ReturnMap ret = userInfoService.queryUserByPage(queryRule, userView.getPage(), userView.getRows());
			res = new ServiceRespond();
			if (ret.isSucc()) {
				@SuppressWarnings("unchecked")
				Page<UserInfo> page = (Page<UserInfo>) ret.getObjContext();// 把ret中接收的值取出来放到page中
				List<UserInfo> userInfos = page.getResult();
				List<UserView> userViews = new ArrayList<UserView>(userInfos.size());
				for (UserInfo userInfo : userInfos) {
					UserView userView2 = new UserView();
					userView2 = UserView.processRoleInfoToUserView(userView2, userInfo);
					for (UserInfo users : userInfos) {
						if (userInfo.getCreateOper() != null) {
							if (userInfo.getCreateOper().equals(users.getId())) {
								userView2.setCreateOper(users.getName());
							}
						}
						if (userInfo.getUpdateOper() != null) {
							if (userInfo.getUpdateOper().equals(users.getId())) {
								userView2.setUpdateOper(users.getName());
							}
						} 
					}
					userViews.add(userView2);
				}
				Page<UserView> page2 = new Page<UserView>(page.getStart(), page.getTotalCount(), page.getPageSize(), userViews);
				rpd = ResponseData.ok();
				rpd.putDataValue("page", page2);// 把page方法该Controller的返回参数ResponseData中
			} else {
				res.setCode(ApplicationDefined.PROCESS_CODE_FAIL);// 错误处理
				res.setMsg("查询用户信息失败");
			}
		} catch (Exception e) {
			res = new ServiceRespond();
			res.setCode(ApplicationDefined.PROCESS_CODE_ERROR);// 异常处理
			res.setMsg("查询用户异常");
			logger.error("查询用户发生异常", e);
		}
		return rpd;// 返回参数 由前台解析
	}

	@RequestMapping(value = "/queryuserbyid", method = RequestMethod.POST)
	public @ResponseBody ServiceRespond querUserById(@RequestBody UserView userView) throws Exception {
		ServiceRespond res = null;
		try {
			ReturnMap ret = userInfoService.queryUserById(userView.getId());
			List<UserInfo> userInfos = userInfoService.getUserInfoToReport(QueryRule.getInstance());
			res = new ServiceRespond();
			if (ret.isSucc()) {
				UserInfo userInfo = (UserInfo) ret.getObjContext();
				UserView userView2 = UserInfo.processRoleInfoToUserView(userView, userInfo);
				for (UserInfo userInfo2 : userInfos) {
					if (userView2.getCreateOper() != null) {
						if (userView2.getCreateOper().equals(userInfo2.getId())) {
							userView2.setCreateOper(userInfo2.getName());
						}
					}
					if (userView2.getUpdateOper() != null) {
						if (userView2.getUpdateOper().equals(userInfo2.getId())) {
							userView2.setUpdateOper(userInfo2.getName());
						}
					}
				}
				ServiceRespondData data = new ServiceRespondData(userView2);
				res.setMsg(ret.getMsg());
				res.setData(data);
			} else {
				res.setCode(ApplicationDefined.PROCESS_CODE_FAIL);
				res.setMsg("根据id查询用户信息失败");
			}
		} catch (Exception e) {
			// TODO: handle exception
			res.setCode(ApplicationDefined.PROCESS_CODE_ERROR);
			res.setMsg("根据id查询用户信息失败");
			logger.error("根据id查询用户信息异常", e);
		}
		return res;
	}

	/**
	 * 添加用户
	 * 
	 * @param userView
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/addUser", method = RequestMethod.POST)
	public @ResponseBody ServiceRespond saveUserInfo(@RequestBody UserView userView) throws Exception {
		ServiceRespond res = null;
		ReturnMap ret = null;
		// 将视图对象转换为实体对象
		UserInfo userInfo = new UserInfo();
		userInfo = UserView.processUserViewToUserInfo(userView, userInfo);
		try {
			ret = userInfoService.saveUserInfo(userInfo);
			res = new ServiceRespond();
			if (!ret.isSucc()) {
				res.setCode(ApplicationDefined.PROCESS_CODE_FAIL);
			} else {
				res.setData(new ServiceRespondData(ret.getObjContext()));
				// 查询用户id
				ret = userInfoService.getUserIdByAccount(userInfo.getAccount());
				UserInfo userInfo2 = new UserInfo();
				userInfo2 = (UserInfo) ret.getObjContext();
				res = new ServiceRespond();
				if (ret.isSucc()) {
					try {
						// 获取用户角色并循环保存到集合中
						UserRoleRel userRoleRel = new UserRoleRel();
						String[] ary = userView.getRoleid().split(",");
						for (String resourceId : ary) {
							userRoleRel.setUserId(userInfo2.getId());
							userRoleRel.setRoleId(resourceId);
							ret = userRoleRelService.saveUserRoleRel(userRoleRel);
						}
					} catch (Exception e) {
						// TODO: handle exception
						res = new ServiceRespond();
						res.setCode(ApplicationDefined.PROCESS_CODE_ERROR);
						res.setMsg("添加用户角色关联表异常");
						logger.error("添加用户角色关联表异常", e);
					}
				} else {
					res.setCode(ApplicationDefined.PROCESS_CODE_FAIL);
				}
			}
			res.setMsg(ret.getMsg());
		} catch (Exception e) {
			// TODO: handle exception
			res = new ServiceRespond();
			res.setCode(ApplicationDefined.PROCESS_CODE_ERROR);
			res.setMsg("添加用户异常");
			logger.error("添加用户发生异常", e);
		}
		return res;
	}

	@RequestMapping(value = "/editUser", method = RequestMethod.POST)
	public @ResponseBody ServiceRespond editUserInfo(@RequestBody UserView userView) throws Exception {
		ServiceRespond res = null;
		ReturnMap ret = null;
		UserInfo userInfo = new UserInfo();
		userInfo = UserView.processUserViewToUserInfo(userView, userInfo);
		try {
			ret = userInfoService.editUserInfo(userInfo);
			res = new ServiceRespond();
			if (ret.isSucc()) {
				try {
					ret = userRoleRelService.deleteUserRoleRelByUserId(userInfo.getId());
					res = new ServiceRespond();
					if (ret.isSucc()) {
						try {
							// 获取用户角色并循环保存到集合中
							UserRoleRel userRoleRel = new UserRoleRel();
							String[] ary = userView.getRoleid().split(",");
							for (String roleid : ary) {
								userRoleRel.setUserId(userInfo.getId());
								userRoleRel.setRoleId(roleid);
								ret = userRoleRelService.saveUserRoleRel(userRoleRel);
							}
						} catch (Exception e) {
							// TODO: handle exception
							res = new ServiceRespond();
							res.setCode(ApplicationDefined.PROCESS_CODE_ERROR);
							res.setMsg("添加用户角色关联表异常");
							logger.error("添加用户角色关联表异常", e);
						}
					} else {
						res.setCode(ApplicationDefined.PROCESS_CODE_FAIL);
					}
				} catch (Exception e) {
					// TODO: handle exception
					res = new ServiceRespond();
					res.setCode(ApplicationDefined.PROCESS_CODE_ERROR);
					res.setMsg("删除用户角色关联表异常");
					logger.error("删除用户角色关联表异常", e);
				}
			} else {
				res.setCode(ApplicationDefined.PROCESS_CODE_FAIL);
			}
		} catch (Exception e) {
			// TODO: handle exception
			res = new ServiceRespond();
			res.setCode(ApplicationDefined.PROCESS_CODE_ERROR);
			res.setMsg("修改用户信息表异常");
			logger.error("修改用户信息表异常", e);
		}
		return res;
	}
}
