package com.xianqin.security.shiro;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.shiro.web.filter.AccessControlFilter;


/**
 * 东城社管项目身份验证过滤器
 * @author xianqin-bill
 *
 */
public class VisualizationAuthenticationFilter extends AccessControlFilter {

	/**
	 * 是否允许访问系统资源
	 * 
	 */
	@Override
	protected boolean isAccessAllowed(ServletRequest arg0, ServletResponse arg1, Object arg2) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected boolean onAccessDenied(ServletRequest arg0, ServletResponse arg1) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

}
