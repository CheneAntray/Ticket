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
import com.base.ReturnMap;
import com.base.ServiceRespond;
import com.base.ServiceRespondData;
import com.xianqin.domain.UserRoleRel;
import com.xianqin.service.UserRoleRelService;
import com.xianqin.view.userrolerel.UserRoleRelView;

@RequestMapping("/userrole")
@RestController
public class UserRoleRelController extends BaseController {

	@Autowired
	private UserRoleRelService userRoleRelService;
	/**
	 * 查询用户拥有的角色
	 * @param userRoleRelView
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getRoleId", method = RequestMethod.POST)
	public @ResponseBody ServiceRespond getRoleIdByUserId(@RequestBody UserRoleRelView userRoleRelView) throws Exception {
		ServiceRespond res = null;
		ReturnMap ret = null;
		try {
			ret = userRoleRelService.queryUserRoleRelByUserId(userRoleRelView.getUserId());
			res = new ServiceRespond();
			if (ret.isSucc()) {
				@SuppressWarnings("unchecked")
				List<UserRoleRel> list = ret.getListContext();
				List<UserRoleRel> userRoleRels = new ArrayList<UserRoleRel>(list.size());
				for (UserRoleRel userRoleRel : list) {
					UserRoleRelView userRoleRelView2=new UserRoleRelView();
					userRoleRelView2=UserRoleRel.processUserRoleRelToUserRoleRelView(userRoleRel, userRoleRelView2);
					userRoleRels.add(userRoleRelView2);
					}
				ServiceRespondData data=new ServiceRespondData(userRoleRels);
				res.setMsg(ret.getMsg());
				res.setData(data);
			}else{
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

}
