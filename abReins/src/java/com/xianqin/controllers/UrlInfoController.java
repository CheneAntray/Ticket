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
import com.xianqin.domain.UrlInfo;
import com.xianqin.service.UrlInfoService;
import com.xianqin.view.urlinfo.UrlView;

@RequestMapping("/url")
@RestController
public class UrlInfoController extends BaseController{
	
	@Autowired
	private UrlInfoService urlInfoService;
	
	/**
	 * 查询路径信息
	 * @param urlView
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/getUrl",method=RequestMethod.POST)
	public @ResponseBody ServiceRespond queryUrlInfo(@RequestBody UrlView urlView) throws Exception{
		ServiceRespond res=null;
		try {
			QueryRule queryRule=QueryRule.getInstance();
			ReturnMap ret=urlInfoService.queryUrlInfo(queryRule);
			res=new ServiceRespond();
			if (ret.isSucc()) {
				@SuppressWarnings("unchecked")
				List<UrlInfo> urlList=(List<UrlInfo>) ret.getObjContext();
				List<UrlInfo> urlView1=new ArrayList<UrlInfo>(urlList.size());
				for (UrlInfo urlInfo : urlList) {
					UrlView urlView2=new UrlView();
					urlView2=UrlInfo.processUrlInfoToUrlView(urlInfo, urlView2);
					urlView1.add(urlView2);
				}
				ServiceRespondData data=new ServiceRespondData(urlView1);
				res.setMsg(res.getMsg());
				res.setData(data);
			}else{
				res.setCode(ApplicationDefined.PROCESS_CODE_FAIL);
				res.setMsg("查询角色信息失败");
			}
		} catch (Exception e) {
			// TODO: handle exception
			res.setCode(ApplicationDefined.PROCESS_CODE_ERROR);
			res.setMsg("查询路径信息异常");
			logger.error("查询路径信息发生异常", e);
		}
		return res;
	}
}





















