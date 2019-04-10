package com.xianqin.security.shiro;


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
import com.xianqin.domain.UserInfo;
import com.xianqin.security.service.AccountService;

/**
 * 权限框架中的用户域定义类
 * 该类定义了用户在系统中(域)的权限范围
 * <p>User: xianqin-bill
 * <p>Date: 2017-07-07
 * <p>Version: 1.0
 */
public class UserRealm extends AuthorizingRealm
{
    @Autowired
    private AccountService accountService;

    @Override
    public boolean supports(AuthenticationToken token)
    {
        //仅支持UsernamePasswordToken 类型的Token
        return token instanceof UsernamePasswordToken;
    }

    /**
     * 理解权限验证
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals)
    {
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        //        authorizationInfo.setRoles(userService.findRoles(username));
        //        authorizationInfo.setStringPermissions(userService.findPermissions(username));
        return authorizationInfo;
    }

    
    /**
     * 登录验证
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException
    {
    	UserInfo user = null;
        String account = (String) token.getPrincipal();
		try {
			user = accountService.getUserInfoByUserName(account);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new UnknownAccountException();//也抛出账号未找到异常
		}

        if (user == null)
        {
            throw new UnknownAccountException();//没找到帐号
        }

        //交给AuthenticatingRealm使用CredentialsMatcher进行密码匹配，如果觉得人家的不好可以自定义实现
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(user.getAccount(), //用户名
                user.getPassword(), //密码
                getName() //realm name -域名
        );
        return authenticationInfo;
    }

    @Override
    public void clearCachedAuthorizationInfo(PrincipalCollection principals)
    {
        super.clearCachedAuthorizationInfo(principals);
    }

    @Override
    public void clearCachedAuthenticationInfo(PrincipalCollection principals)
    {
        super.clearCachedAuthenticationInfo(principals);
    }

    @Override
    public void clearCache(PrincipalCollection principals)
    {
        super.clearCache(principals);
    }

    public void clearAllCachedAuthorizationInfo()
    {
        getAuthorizationCache().clear();
    }

    public void clearAllCachedAuthenticationInfo()
    {
        getAuthenticationCache().clear();
    }

    public void clearAllCache()
    {
        clearAllCachedAuthenticationInfo();
        clearAllCachedAuthorizationInfo();
    }

}
