package com.xianqin.controllers;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import com.xianqin.domain.TimedtaskInfo;
import com.xianqin.service.TimedTaskService;
import com.xianqin.view.TimedTask.RunUpView;
import com.xianqin.view.TimedTask.TimedTaskView;

@RequestMapping("/timedtask")
@RestController
public class TimedTaskController extends BaseController {

	@Autowired
	private TimedTaskService timedTaskService;

	// 查询定时日期
	@RequestMapping(value = "/querytimedtask", method = RequestMethod.POST)
	public @ResponseBody ServiceRespond queryTimedTaskInfo(@RequestBody TimedTaskView taskView) throws Exception {
		ServiceRespond res = null;
		ReturnMap ret = null;
		try {
			ret = timedTaskService.queryTimedTaskInfo();
			res = new ServiceRespond();
			if (ret.isSucc()) {
				@SuppressWarnings("unchecked")
				List<TimedtaskInfo> timedtaskInfos = ret.getListContext();
				List<TimedtaskInfo> timedtaskInfoIns = new ArrayList<TimedtaskInfo>(timedtaskInfos.size());
				for (TimedtaskInfo timedtaskInfo : timedtaskInfos) {
					TimedTaskView taskView2 = new TimedTaskView();
					taskView2 = (TimedTaskView) TimedtaskInfo.processTimedtaskInfoToTimedTaskView(timedtaskInfo,
							taskView2);
					timedtaskInfoIns.add(taskView2);
				}
				ServiceRespondData data = new ServiceRespondData(timedtaskInfoIns);
				res.setMsg(ret.getMsg());
				res.setData(data);
			} else {
				res.setMsg("查询计时日期失败");
				res.setCode(ApplicationDefined.PROCESS_CODE_FAIL);
			}
		} catch (Exception e) {
			// TODO: handle exception
			res.setCode(ApplicationDefined.PROCESS_CODE_ERROR);
			res.setMsg("查询定时日期异常");
			logger.error("查询定时日期异常", e);
		}
		return res;
	}

	// 修改定时日期
	@RequestMapping(value = "/edittimedtask", method = RequestMethod.POST)
	public @ResponseBody ServiceRespond editTimedTaskInfo(@RequestBody TimedTaskView taskView) throws Exception {
		ServiceRespond res = null;
		ReturnMap ret = null;
		try {
			SimpleDateFormat date = new SimpleDateFormat("yyyy-mm-dd");
			ret = timedTaskService.editTimedTaskInfo(taskView.getMonthId(), date.parse(taskView.getMonthDate()));
			res = new ServiceRespond();
			if (ret.isSucc()) {
				ret = timedTaskService.editTimedTaskInfo(taskView.getYearId(), date.parse(taskView.getYearDate()));
				if (ret.isSucc()) {
					res.setMsg(ret.getMsg());
				} else {
					res.setMsg("修改年日期失败");
					res.setCode(ApplicationDefined.PROCESS_CODE_FAIL);
				}
			} else {
				res.setMsg("修改月日期失败");
				res.setCode(ApplicationDefined.PROCESS_CODE_FAIL);
			}
		} catch (Exception e) {
			// TODO: handle exception
			res.setCode(ApplicationDefined.PROCESS_CODE_ERROR);
			res.setMsg("修改定时日期异常");
			logger.error("修改定时日期异常", e);
		}
		return res;
	}

	// 售票站车次月统计
	@RequestMapping(value = "/ticketTrainNoOfMonth", method = RequestMethod.POST)
	public @ResponseBody ServiceRespond ticketTrainNoOfMonth(@RequestBody RunUpView runUpView) throws Exception {
		ServiceRespond res = null;
		ReturnMap ret = null;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
			Date startDate = null;
			Date endDate = null;
			if (runUpView.getTicketTrainMonthOfStartDate() != null
					&& !runUpView.getTicketTrainMonthOfStartDate().equals("")) {
				startDate = sdf.parse(runUpView.getTicketTrainMonthOfStartDate());
			}
			if (runUpView.getTicketTrainMonthOfEndDate() != null
					&& !runUpView.getTicketTrainMonthOfEndDate().equals("")) {
				endDate = sdf.parse(runUpView.getTicketTrainMonthOfEndDate());
			}
			res = new ServiceRespond();
			ret = timedTaskService.runUpByTicketTrainnUmberOfMonth(startDate, endDate);
			if (ret.isSucc()) {
				res.setMsg(ret.getMsg());
			} else {
				res.setCode(ApplicationDefined.PROCESS_CODE_FAIL);
				res.setMsg("售票站车次月统计补跑失败");
			}
		} catch (Exception e) {
			// TODO: handle exception
			res = new ServiceRespond();
			res.setCode(ApplicationDefined.PROCESS_CODE_ERROR);
			res.setMsg("售票站车次月统计补跑异常，请联系系统管理员");
			logger.error("售票站车次月统计补跑异常", e);
		}
		return res;
	}

	// 售票站车次年统计
	@RequestMapping(value = "/ticketTrainNoOfYear", method = RequestMethod.POST)
	public @ResponseBody ServiceRespond ticketTrainNoOfYear(@RequestBody RunUpView runUpView) throws Exception {
		ServiceRespond res = null;
		ReturnMap ret = null;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
			Date startDate = null;
			Date endDate = null;
			if (runUpView.getTicketTrainYearOfStartDate() != null
					&& !runUpView.getTicketTrainYearOfStartDate().equals("")) {
				startDate = sdf.parse(runUpView.getTicketTrainYearOfStartDate());
			}
			if (runUpView.getTicketTrainYearOfEndDate() != null
					&& !runUpView.getTicketTrainYearOfEndDate().equals("")) {
				endDate = sdf.parse(runUpView.getTicketTrainYearOfEndDate());
			}
			res = new ServiceRespond();
			ret = timedTaskService.runUpByTicketTrainnUmberOfYear(startDate, endDate);
			if (ret.isSucc()) {
				res.setMsg(ret.getMsg());
			} else {
				res.setCode(ApplicationDefined.PROCESS_CODE_FAIL);
				res.setMsg("售票站车次年统计补跑失败");
			}
		} catch (Exception e) {
			// TODO: handle exception
			res = new ServiceRespond();
			res.setCode(ApplicationDefined.PROCESS_CODE_ERROR);
			res.setMsg("售票站车次年统计补跑异常，请联系系统管理员");
			logger.error("售票站车次年统计补跑异常", e);
		}
		return res;
	}

	// 售票站日统计
	@RequestMapping(value = "/ticketOfDay", method = RequestMethod.POST)
	public @ResponseBody ServiceRespond ticketOfDay(@RequestBody RunUpView runUpView) throws Exception {
		ServiceRespond res = null;
		ReturnMap ret = null;
		try {
			res = new ServiceRespond();
			ret = timedTaskService.runUpByTicketStationOfDay(runUpView.getTicketDayOfStartDate(),
					runUpView.getTicketDayOfEndDate());
			if (ret.isSucc()) {
				res.setMsg(ret.getMsg());
			} else {
				res.setCode(ApplicationDefined.PROCESS_CODE_FAIL);
				res.setMsg("售票站日统计补跑失败");
			}
		} catch (Exception e) {
			// TODO: handle exception
			res = new ServiceRespond();
			res.setCode(ApplicationDefined.PROCESS_CODE_ERROR);
			res.setMsg("售票站日统计补跑异常，请联系系统管理员");
			logger.error("售票站日统计补跑异常", e);
		}
		return res;
	}

	// 售票站月统计
	@RequestMapping(value = "/ticketOfMonth", method = RequestMethod.POST)
	public @ResponseBody ServiceRespond ticketOfMonth(@RequestBody RunUpView runUpView) throws Exception {
		ServiceRespond res = null;
		ReturnMap ret = null;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
			Date startDate = null;
			Date endDate = null;
			if (runUpView.getTicketMonthOfStartDate() != null && !runUpView.getTicketMonthOfStartDate().equals("")) {
				startDate = sdf.parse(runUpView.getTicketMonthOfStartDate());
			}
			if (runUpView.getTicketMonthOfEndDate() != null && !runUpView.getTicketMonthOfEndDate().equals("")) {
				endDate = sdf.parse(runUpView.getTicketMonthOfEndDate());
			}
			res = new ServiceRespond();
			ret = timedTaskService.runUpByTicketStationOfMonth(startDate, endDate);
			if (ret.isSucc()) {
				res.setMsg(ret.getMsg());
			} else {
				res.setCode(ApplicationDefined.PROCESS_CODE_FAIL);
				res.setMsg("售票站月统计补跑失败");
			}
		} catch (Exception e) {
			// TODO: handle exception
			res = new ServiceRespond();
			res.setCode(ApplicationDefined.PROCESS_CODE_ERROR);
			res.setMsg("售票站月统计补跑异常，请联系系统管理员");
			logger.error("售票站月统计补跑异常", e);
		}
		return res;
	}

	// 售票站年统计
	@RequestMapping(value = "/ticketOfYear", method = RequestMethod.POST)
	public @ResponseBody ServiceRespond ticketOfYear(@RequestBody RunUpView runUpView) throws Exception {
		ServiceRespond res = null;
		ReturnMap ret = null;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
			Date startDate = null;
			Date endDate = null;
			if (runUpView.getTicketYearOfStartDate() != null && !runUpView.getTicketYearOfStartDate().equals("")) {
				startDate = sdf.parse(runUpView.getTicketYearOfStartDate());
			}
			if (runUpView.getTicketYearOfEndDate() != null && !runUpView.getTicketYearOfEndDate().equals("")) {
				endDate = sdf.parse(runUpView.getTicketYearOfEndDate());
			}
			res = new ServiceRespond();
			ret = timedTaskService.runUpByTicketStationOfYear(startDate, endDate);
			if (ret.isSucc()) {
				res.setMsg(ret.getMsg());
			} else {
				res.setCode(ApplicationDefined.PROCESS_CODE_FAIL);
				res.setMsg("售票站年统计补跑失败");
			}
		} catch (Exception e) {
			// TODO: handle exception
			res = new ServiceRespond();
			res.setCode(ApplicationDefined.PROCESS_CODE_ERROR);
			res.setMsg("售票站年统计补跑异常，请联系系统管理员");
			logger.error("售票站年统计补跑异常", e);
		}
		return res;
	}

	// 站段日统计
	@RequestMapping(value = "/sectionOfDay", method = RequestMethod.POST)
	public @ResponseBody ServiceRespond sectionOfDay(@RequestBody RunUpView runUpView) throws Exception {
		ServiceRespond res = null;
		ReturnMap ret = null;
		try {
			res = new ServiceRespond();
			ret = timedTaskService.runUpByStationSectionOfDay(runUpView.getSectionDayOfStartDate(),
					runUpView.getSectionDayOfEndDate());
			if (ret.isSucc()) {
				res.setMsg(ret.getMsg());
			} else {
				res.setCode(ApplicationDefined.PROCESS_CODE_FAIL);
				res.setMsg("站段日统计补跑失败");
			}
		} catch (Exception e) {
			// TODO: handle exception
			res = new ServiceRespond();
			res.setCode(ApplicationDefined.PROCESS_CODE_ERROR);
			res.setMsg("站段日统计补跑异常，请联系系统管理员");
			logger.error("站段日统计补跑异常", e);
		}
		return res;
	}

	// 站段月统计
	@RequestMapping(value = "/sectionOfMonth", method = RequestMethod.POST)
	public @ResponseBody ServiceRespond sectionOfMonth(@RequestBody RunUpView runUpView) throws Exception {
		ServiceRespond res = null;
		ReturnMap ret = null;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
			Date startDate = null;
			Date endDate = null;
			if (runUpView.getSectiontMonthOfStartDate() != null
					&& !runUpView.getSectiontMonthOfStartDate().equals("")) {
				startDate = sdf.parse(runUpView.getSectiontMonthOfStartDate());
			}
			if (runUpView.getSectionMonthOfEndDate() != null && !runUpView.getSectionMonthOfEndDate().equals("")) {
				endDate = sdf.parse(runUpView.getSectionMonthOfEndDate());
			}
			res = new ServiceRespond();
			ret = timedTaskService.runUpByStationSectionOfMonth(startDate, endDate);
			if (ret.isSucc()) {
				res.setMsg(ret.getMsg());
			} else {
				res.setCode(ApplicationDefined.PROCESS_CODE_FAIL);
				res.setMsg("站段月统计补跑失败");
			}
		} catch (Exception e) {
			// TODO: handle exception
			res = new ServiceRespond();
			res.setCode(ApplicationDefined.PROCESS_CODE_ERROR);
			res.setMsg("站段月统计补跑异常，请联系系统管理员");
			logger.error("站段月统计补跑异常", e);
		}
		return res;
	}

	// 站段年统计
	@RequestMapping(value = "/sectionOfYear", method = RequestMethod.POST)
	public @ResponseBody ServiceRespond sectionOfYear(@RequestBody RunUpView runUpView) throws Exception {
		ServiceRespond res = null;
		ReturnMap ret = null;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
			Date startDate = null;
			Date endDate = null;
			if (runUpView.getSectionYearOfStartDate() != null && !runUpView.getSectionYearOfStartDate().equals("")) {
				startDate = sdf.parse(runUpView.getSectionYearOfStartDate());
			}
			if (runUpView.getSectionYearOfEndDate() != null && !runUpView.getSectionYearOfEndDate().equals("")) {
				endDate = sdf.parse(runUpView.getSectionYearOfEndDate());
			}
			res = new ServiceRespond();
			ret = timedTaskService.runUpByStationSectionOfYear(startDate, endDate);
			if (ret.isSucc()) {
				res.setMsg(ret.getMsg());
			} else {
				res.setCode(ApplicationDefined.PROCESS_CODE_FAIL);
				res.setMsg("售票站年统计补跑失败");
			}
		} catch (Exception e) {
			// TODO: handle exception
			res = new ServiceRespond();
			res.setCode(ApplicationDefined.PROCESS_CODE_ERROR);
			res.setMsg("售票站年统计补跑异常，请联系系统管理员");
			logger.error("售票站年统计补跑异常", e);
		}
		return res;
	}

}
