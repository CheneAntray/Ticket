package com.xianqin.controllers;

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
import com.xianqin.domain.TrainDirection;
import com.xianqin.service.TrainDirectionService;
import com.xianqin.view.direction.TrainDirectionView;

@RequestMapping(value = "/direction")
@RestController
public class TrainDirectionController extends BaseController {

	@Autowired
	private TrainDirectionService trainDirectionService;

	@RequestMapping(value = "/getDirectionByPage", method = RequestMethod.POST)
	public @ResponseBody ResponseData getDirectionByPage(@RequestBody TrainDirectionView trainDirectionView)
			throws Exception {
		ServiceRespond res = null;
		ResponseData rpd = null;// 接收的
		try {
			QueryRule queryRule = QueryRule.getInstance();// 初始化查询参数
			if (trainDirectionView.getDirectionName() != null && !trainDirectionView.getDirectionName().equals("")) {
				queryRule.addLike(TrainDirection._directionName, "%" + trainDirectionView.getDirectionName() + "%");// 设置参数
			}
			// 获取的集合放入到ret中 因为service中的这个方法的返回值是ReturnMap，所以用他接收
			ReturnMap ret = trainDirectionService.queryTrainDirectionByPage(queryRule, trainDirectionView.getPage(),
					trainDirectionView.getRows());
			res = new ServiceRespond();
			if (ret.isSucc()) {
				@SuppressWarnings("unchecked")
				Page<TrainDirection> page = (Page<TrainDirection>) ret.getObjContext();// 把ret中接收的值取出来放到page中
				rpd = ResponseData.ok();
				rpd.putDataValue("page", page);// 把page方法该Controller的返回参数ResponseData中
												// 返回给前台
			} else {
				res.setCode(ApplicationDefined.PROCESS_CODE_FAIL);// 异常处理
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

	@RequestMapping(value = "/saveDirection", method = RequestMethod.POST)
	public @ResponseBody ServiceRespond saveRoleInfo(@RequestBody TrainDirectionView trainDirectionView)
			throws Exception {
		ServiceRespond res = null;
		ReturnMap ret = null;
		TrainDirection trainDirection=new TrainDirection();
		trainDirection=TrainDirectionView.processTrainDirectionViewToTrainDirection(trainDirectionView, trainDirection);
		try {
			ret = trainDirectionService.saveTrainDirection(trainDirection);
			res = new ServiceRespond();
			if (!ret.isSucc()) {
				res.setCode(ApplicationDefined.PROCESS_CODE_FAIL);
			}
			res.setMsg(ret.getMsg());
		} catch (Exception e) {
			// TODO: handle exception
			res = new ServiceRespond();
			res.setCode(ApplicationDefined.PROCESS_CODE_ERROR);
			res.setMsg("添加方向异常");
			logger.error("添加方向信息发生异常", e);
		}
		return res;
	}
	
	@RequestMapping(value="/getDirectionById", method = RequestMethod.POST)
	public @ResponseBody ServiceRespond getDirectionById(@RequestBody TrainDirectionView trainDirectionView) throws Exception{
		ServiceRespond res=null;
		ReturnMap ret=null;
		try {
			ret=trainDirectionService.getDirectionById(trainDirectionView.getId());
			res = new ServiceRespond();
			if (!ret.isSucc()) {
				res.setCode(ApplicationDefined.PROCESS_CODE_FAIL);
				res.setMsg("方向信息查询失败");
			}
			TrainDirection trainDirection=(TrainDirection) ret.getObjContext();
			TrainDirectionView trainDirectionView2=TrainDirection.processTrainDirectionToTrainDirectionView(trainDirection, trainDirectionView);
			ServiceRespondData data=new ServiceRespondData(trainDirectionView2);
			res.setData(data);
			res.setMsg(ret.getMsg());
		} catch (Exception e) {
			// TODO: handle exception
			res = new ServiceRespond();
			res.setCode(ApplicationDefined.PROCESS_CODE_ERROR);
			res.setMsg("查询方向异常");
			logger.error("查询方向信息发生异常", e);
		}
		return res;
	}
	@RequestMapping(value="/editDirectionById", method = RequestMethod.POST)
	public @ResponseBody ServiceRespond editDirectionById(@RequestBody TrainDirectionView trainDirectionView) throws Exception{
		ServiceRespond res = null;
		ReturnMap ret = null;
		TrainDirection trainDirection=new TrainDirection();
		trainDirection=TrainDirectionView.processTrainDirectionViewToTrainDirection(trainDirectionView, trainDirection);
		try {
			ret = trainDirectionService.editDirectionById(trainDirection);
			res = new ServiceRespond();
			if (!ret.isSucc()) {
				res.setCode(ApplicationDefined.PROCESS_CODE_FAIL);
				res.setMsg("方向信息修改失败");
			}
			res.setMsg(ret.getMsg());
		} catch (Exception e) {
			// TODO: handle exception
			res.setCode(ApplicationDefined.PROCESS_CODE_ERROR);
			res.setMsg("修改方向异常");
			logger.error("修改方向信息发生异常", e);
		}
		return res;
	}
}
