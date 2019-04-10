package com.xianqin.security.controllers;

import java.io.File;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.oltu.oauth2.as.issuer.MD5Generator;
import org.apache.oltu.oauth2.as.issuer.OAuthIssuer;
import org.apache.oltu.oauth2.as.issuer.OAuthIssuerImpl;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.base.ApplicationDefined;
import com.base.ServiceRespond;
import com.base.ServiceRespondData;
import com.base.confread.SystemSetting;
import com.xianqin.domain.MenuInfo;
import com.xianqin.domain.NfAuthorityRel;
import com.xianqin.domain.UrlInfo;
import com.xianqin.security.service.AccountService;
import com.xianqin.security.service.LoginService;
import com.xianqin.security.service.OAuthService;
import com.xianqin.security.service.UserPwrService;
import com.xianqin.view.user.LoginVo;
import com.xianqin.view.user.UserView;

/**
 * 登录控制器 该控制器负责用户登录校验以及用户登录后的一些关联服务的控制器层服务提供
 * 
 * @author xianqin-bill
 *
 */
@Controller
public class AuthorizeController {

	/**
	 * 用户认证服务接口
	 */
	@Autowired
	private OAuthService oAuthService;

	/**
	 * 登录接口
	 */
	@Autowired
	private LoginService loginService;

	/**
	 * 系统公用配置文件
	 */
	@Autowired
	private SystemSetting systemSetting;

	@Autowired
	private AccountService accountService;

	@Autowired
	private UserPwrService userPwrService;
	
	Subject subject = SecurityUtils.getSubject();

	/**
	 * 用户登录请求
	 * 
	 * @param request
	 * @return
	 * @throws URISyntaxException
	 * @throws OAuthSystemException
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public @ResponseBody ServiceRespond token(@RequestBody UserView userView)
			throws URISyntaxException, OAuthSystemException {
		List<UrlInfo> functionList = null;
		ServiceRespond respond = null;
		ServiceRespondData data = null;
		LoginVo loginVo = null;
		respond = new ServiceRespond();
		data = new ServiceRespondData();
		// 校验用户名密码是否正确
		UsernamePasswordToken token = new UsernamePasswordToken(userView.getAccount(), userView.getPassword());
		try {
			subject.login(token);
		} catch (Exception e) {
			respond.setCode(ApplicationDefined.USER_CHECK_FAIL);
			respond.setMsg("用户身份验证错误");
			return respond;
		}
		// 生成Access Token
		OAuthIssuer oauthIssuerImpl = new OAuthIssuerImpl(new MD5Generator());
		final String accessToken = oauthIssuerImpl.accessToken();
		oAuthService.addAccessToken(accessToken, userView.getAccount());
		// 查询用户权限
		try {
			functionList = accountService.getPermissionsByUserName(userView.getAccount());
		} catch (Exception e) {
			// 此处异常暂时不做处理，应该为系统bug 处理方式待定
		}
		// 将用户权限进行缓存
		userPwrService.addUserPwrByUserName(userView.getAccount(), processFunctionListToSetUrl(functionList));
		// 获取登录用户Id
		String userId=null;
		try {
			userId=accountService.getUserIdBySubjec(subject);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//不做异常处理
		}
		// 生成OAuth响应
		respond.setCode(ApplicationDefined.PROCESS_CODE_SUCCESS);
		loginVo = new LoginVo();
		loginVo.setAccess_token(accessToken);
		loginVo.setExpires_in(oAuthService.getExpireIn());
		loginVo.setUserId(userId);
		data.setObj(loginVo);
		respond.setData(data);
		respond.setMsg("用户身份成功");
		return respond;
	}

	@RequestMapping(value = "/menu", method = RequestMethod.POST)
	public @ResponseBody ServiceRespond getMenuList() {
		ServiceRespond res=new ServiceRespond();
		try {
			String account = (String) subject.getPrincipal();
			List<MenuInfo> list=loginService.queryMenuListByAccount(account);
			res.setData(new ServiceRespondData(list));
		} catch (Exception e) {
			e.getStackTrace();
			// TODO: handle exception
		}
		return res;
	}

	@RequestMapping(value = "/hello/", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<List<NfAuthorityRel>> testJson() {
		List<NfAuthorityRel> list = new ArrayList<NfAuthorityRel>(1);
		NfAuthorityRel obj = new NfAuthorityRel();
		obj.setAuthorityType("1");
		obj.setCircumstanceId("2");
		obj.setCircumstanceType("3");
		obj.setDescription("4");
		list.add(obj);
		return new ResponseEntity<List<NfAuthorityRel>>(list, HttpStatus.OK);
	}

	@RequestMapping(value = "/upload/", method = RequestMethod.POST)
	public @ResponseBody ServiceRespond upload(@RequestParam(value = "file", required = false) MultipartFile file,
			HttpServletRequest request, ModelMap model) {
		ServiceRespond respond = new ServiceRespond();
		String fileName = file.getOriginalFilename();
		File targetFile = new File(systemSetting.UPLOAD_FILE_TEMP_PATH, fileName);
		if (!targetFile.exists()) {
			targetFile.mkdirs();
		}
		// 保存
		try {
			file.transferTo(targetFile);
			respond.setMsg("文件上传成功");
		} catch (Exception e) {
			e.printStackTrace();
			respond.setCode(ApplicationDefined.PROCESS_CODE_FAIL);
			respond.setMsg("文件上传失败:" + e.getMessage());
		}
		return respond;
	}

	private Set<String> processFunctionListToSetUrl(List<UrlInfo> functionList) {
		Set<String> urlSet = null;
		if (functionList != null && !functionList.isEmpty()) {
			urlSet = new LinkedHashSet<String>(functionList.size());
			for (UrlInfo functionInfo : functionList) {
				urlSet.add(functionInfo.getUrlPath());
			}
		}
		return urlSet;
	}
}
