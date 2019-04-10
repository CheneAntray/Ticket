package com.xianqin.security.shiro;



import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;
import com.base.ServiceRespond;
import com.base.utils.LogUtils;
import com.xianqin.security.service.UserPwrService;

public class OAuth2AuthenticationFilter extends AccessControlFilter
{
	/**
	 * 日志对象初始化
	 */
	protected Logger logger = LogUtils.getConsoleLogIns();
	 @Autowired
	 private UserPwrService userPwrService;

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue)throws Exception
    {
    	String url;
    	Subject subject = null;
    	String username = null;
    	ServiceRespond respond=new ServiceRespond();
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        String token = httpRequest.getHeader(Constants.HEAD_TOKEN);
        if (StringUtils.isEmpty(token)){
            return false;
        }
        OAuth2Token oauth2Token = new OAuth2Token(token);
        try {
            //5、委托给Realm进行登录
        	subject = getSubject(request, response);
        	subject.login(oauth2Token);
            url = httpRequest.getRequestURI();
            username = (String)subject.getPrincipal();
            url=url.replaceAll("/api", "");
            if(userPwrService.isPwr(username, url)){
            	return true;
            }else{
            	httpServletResponse.setHeader("Content-Type", "application/json;charset=UTF-8");
                respond.setCode(8);
                respond.setMsg("权限校验失败");
                String result = JSON.toJSONString(respond);
                httpServletResponse.getWriter().write(result);
                request.setAttribute("processFlag", "1");
            	return false;
            }
        } catch (Exception e) {
            httpServletResponse.setHeader("Content-Type", "application/json;charset=UTF-8");
            respond.setCode(9);
            respond.setMsg("认证失败,请重新登录");
            request.setAttribute("processFlag", "1");
            String result = JSON.toJSONString(respond);
            httpServletResponse.getWriter().write(result);
            return false;
        }
    
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception
    {

//        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
//        String method = httpServletRequest.getMethod();
//        if (RequestMethod.GET.toString().equals(method))
//        {
//            return getOnAccessDenied(httpServletRequest, response);
//        }
//        else
//        {
//            return postOnAccessDenied(httpServletRequest, response);
//        }
    	if(request.getAttribute("processFlag")!=null){
    		return false;
    	}
    	ServiceRespond respond=new ServiceRespond();
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
      //没有登录 没有token
        httpServletResponse.setHeader("Content-Type", "application/json;charset=UTF-8");
        respond.setCode(9);
        respond.setMsg("需要登录后操作");
        String result = JSON.toJSONString(respond);
        httpServletResponse.getWriter().write(result);
        return false;
    }
}
