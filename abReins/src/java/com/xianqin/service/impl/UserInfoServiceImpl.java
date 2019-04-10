package com.xianqin.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.base.ReturnMap;
import com.xianqin.common.Page;
import com.xianqin.common.QueryRule;
import com.xianqin.dao.UserInfoDao;
import com.xianqin.domain.UserInfo;
import com.xianqin.security.service.AccountService;
import com.xianqin.service.UserInfoService;

@Service("userInfoService")
public class UserInfoServiceImpl implements UserInfoService {
	/**
	 * 用户数据访问层接口
	 */
	@Resource
	private UserInfoDao userInfoDao;
	
	@Autowired
	private AccountService accountService;
	
	public Subject subject=null;
	/**
	 * 用户登录
	 */
	@Override
	public ReturnMap doLogin(String userAccount,String password) throws Exception {
		// TODO Auto-generated method stub
		ReturnMap returnMap = new ReturnMap();
		QueryRule queryRule=QueryRule.getInstance();
		queryRule.addEqual(UserInfo._account, userAccount);
		UserInfo userInfo=userInfoDao.getUserInfoByCondition(queryRule);
		if (userInfo!=null) {
			if (password.equals(userInfo.getPassword())) {
				returnMap.setSucc();
				returnMap.setObjContext(userInfo);
				returnMap.setMsg("登录成功");
			}
			else {
				returnMap.setMsg("您输入的账号密码不匹配，请重新输入");
			}
		}else {
			returnMap.setMsg("您输入的账号不存在，请您重新输入");
		}
		return returnMap;
	}
	/**
	 * 添加用户
	 */
	@Override
	public ReturnMap saveUserInfo(UserInfo userInfo) throws Exception{
		// TODO Auto-generated method stub
		ReturnMap returnMap=new ReturnMap();
		subject=SecurityUtils.getSubject();
		if (queryUserByAccount(null, userInfo.getAccount())) {
			//保存用户信息
			userInfo.setCreateOper(accountService.getUserIdBySubjec(subject));//获取当前用户的信息
			userInfoDao.saveUserInfo(userInfo);
			returnMap.setSucc();
			returnMap.setObjContext(userInfo);
			returnMap.setMsg("用户添加成功");
		}else{
			returnMap.setMsg("您填写的用户账号已经存在，请您重新填写");
		}
		return returnMap;
	}
	/**
	 * 查询用户（是否存在重复帐号）
	 */
	@Override
	public boolean queryUserByAccount(String userId, String account) throws Exception{
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addEqual(UserInfo._account, account);
		UserInfo userInfo = userInfoDao.getUserInfoByCondition(queryRule);
		if (userInfo != null && userInfo.getAccount().equals(account)) {
			return false;
		}
		return true;
	}
	/**
	 * 根据用户帐号查询用户id
	 */
	@Override
	public ReturnMap getUserIdByAccount(String account) throws Exception {
		// TODO Auto-generated method stub
		ReturnMap returnMap=new ReturnMap();
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addEqual(UserInfo._account, account);
		UserInfo userInfo = userInfoDao.getUserInfoByCondition(queryRule);
		returnMap.setSucc();
		returnMap.setObjContext(userInfo);
		returnMap.setMsg("查询用户id成功");
		return returnMap;
	}
	/**
	 * 分页查询用户
	 */
	@Override
	public ReturnMap queryUserByPage(QueryRule queryRule, Integer pageIndex, Integer pageSize) throws Exception {
		// TODO Auto-generated method stub
		ReturnMap returnMap=new ReturnMap();
		Page<UserInfo> page=userInfoDao.queryUserInfoByPage(queryRule, pageIndex, pageSize);
		returnMap.setSucc();
		returnMap.setObjContext(page);
		returnMap.setMsg("分页查询用户成功");
		return returnMap;
	}
	/**
	 * 根据id查询用户
	 */
	@Override
	public ReturnMap queryUserById(String userid) throws Exception {
		// TODO Auto-generated method stub
		ReturnMap returnMap=new ReturnMap();
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addEqual(UserInfo._id, userid);
		UserInfo userInfo = userInfoDao.getUserInfoByCondition(queryRule);
		returnMap.setSucc();
		returnMap.setObjContext(userInfo);
		returnMap.setMsg("查询用户id成功");
		return returnMap;
	}
	/**
	 * 修改用户信息
	 */
	@Override
	public ReturnMap editUserInfo(UserInfo userInfo) throws Exception {
		// TODO Auto-generated method stub
		ReturnMap returnMap=new ReturnMap();
		QueryRule queryRule=QueryRule.getInstance();
		queryRule.addEqual(UserInfo._id, userInfo.getId());
		UserInfo userInfoIns=userInfoDao.getUserInfoByCondition(queryRule);
		if (userInfoIns!=null) {
			subject=SecurityUtils.getSubject();
			userInfoIns.setUpdateOper(accountService.getUserIdBySubjec(subject));//获取修改者ID
			userInfoIns.setAccount(userInfo.getAccount());
			userInfoIns.setPassword(userInfo.getPassword());
			userInfoIns.setName(userInfo.getName());
			userInfoDao.updateUserInfo(userInfoIns);
			returnMap.setSucc();
			returnMap.setMsg("用户修改成功");
		}else{
			returnMap.setFail();
			returnMap.setMsg("没有查询到该用户");
		}
		
		return returnMap;
	}
	@Override
	public List<UserInfo> getUserInfoToReport(QueryRule queryRule) throws Exception {
		// TODO Auto-generated method stub
		List<UserInfo> list=userInfoDao.getUserInfoAll(queryRule);
		return list;
	}

}
