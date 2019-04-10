package com.xianqin.security.shiro;

import java.util.List;

import org.apache.log4j.Logger;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import com.base.utils.LogUtils;
import com.xianqin.domain.UrlInfo;
import com.xianqin.domain.UserInfo;
import com.xianqin.security.service.AccountService;

/**
 * 社管可视化项目权限域实现类
 * @author xianqin-bill
 *
 */
public class VisualizationRealm extends AuthorizingRealm {

	private Logger logger = LogUtils.getConsoleLogIns();
	@Autowired
	private AccountService accountService;
	/*** 
     * 获取授权信息 
     */  
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
		String username = null;
		List<UrlInfo> pers = null; 
        username = (String) principalCollection.fromRealm(getName()).iterator().next();
        if (username != null) {  
            try {
				pers = accountService.getPermissionsByUserName(username);
			} catch (Exception e) {
				logger.error("权限校验出现异常:",e);
				return null;
			}  
            if (pers != null && !pers.isEmpty()) {  
                SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();  
                for (UrlInfo urlInfo : pers) {  
                    //将权限资源添加到用户信息中  
                    info.addStringPermission(urlInfo.getUrlPath());  
                }  
                return info;  
            }  
        }  
        return null;  
	}

	/*** 
     * 获取认证信息 
     */  
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
		logger.debug("安全域对象被加载，开始执行身份验证");
		UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;  
        // 通过表单接收的用户名  
        String username = token.getUsername();  
        if (username != null && !"".equals(username)) {  
        	UserInfo user;
			try {
				user = accountService.getUserInfoByUserName(username);
			} catch (Exception e) {
				logger.error("用户身份校验出现异常:",e);
				return null;
			}  
            if (user != null) {  
                return new SimpleAuthenticationInfo(user.getAccount(), user.getPassword(), getName());  
            }else{
            	 throw new UnknownAccountException(); //如果用户名错误
            }
        }  
        return null;  
	}

}
