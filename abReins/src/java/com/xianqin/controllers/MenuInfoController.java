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
import com.xianqin.common.QueryRule;
import com.xianqin.domain.MenuInfo;
import com.xianqin.service.MenuInfoService;
import com.xianqin.view.menuinfo.MenuView;
@RequestMapping("/menu")
@RestController
public class MenuInfoController extends BaseController{

	@Autowired
	private MenuInfoService menuInfoService;
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/getMenu",method=RequestMethod.POST)
	public @ResponseBody ServiceRespond getMenuInfo(@RequestBody MenuView menuView) throws Exception{
		ServiceRespond res=null;
		ReturnMap ret=null;
		QueryRule queryRule=null;
		//接收查询出的菜单数据
		List<MenuInfo> menuInfos=null;
		try {
			queryRule=QueryRule.getInstance();
			//查询菜单信息
			ret=menuInfoService.getMenuInfo(queryRule);
			res=new ServiceRespond();
			if (ret.isSucc()) {
				//取出菜单信息放入菜单集合中
				menuInfos=ret.getListContext();
				List<MenuInfo> menuViews=new ArrayList<MenuInfo>(menuInfos.size());
				for (MenuInfo menuInfo : menuInfos) {
					MenuView menuView2=new MenuView();
					menuView2=MenuInfo.processMenuInfoToUrlView(menuInfo, menuView2);
					menuViews.add(menuView2);
				}
				ServiceRespondData data = new ServiceRespondData(menuViews);
				res.setMsg(ret.getMsg());
				res.setData(data);
			}else {
				res.setCode(ApplicationDefined.PROCESS_CODE_FAIL);
				res.setMsg("查询菜单信息失败");
			}
		} catch (Exception e) {
			// TODO: handle exception
			res.setCode(ApplicationDefined.PROCESS_CODE_ERROR);
			res.setMsg("查询菜单异常");
			logger.error("查询菜单信息发生异常", e);
		}
		return res;
	}
}
