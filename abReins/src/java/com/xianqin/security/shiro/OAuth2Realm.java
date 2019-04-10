package com.xianqin.security.shiro;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import com.xianqin.security.service.OAuthService;

public class OAuth2Realm extends AuthorizingRealm
{

    @Autowired
    private OAuthService oAuthService;

    @Override
    public boolean supports(AuthenticationToken token)
    {
        return token instanceof OAuth2Token;//表示此Realm只支持OAuth2Token类型
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals)
    {
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        return authorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException
    {
        OAuth2Token oAuth2Token = (OAuth2Token) token;

        String username = extractUsername(oAuth2Token.getOauthToken()); // 提取用户名
        if (null != username)
        {
            oAuth2Token.setPrincipal(username);
            SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(username, oAuth2Token.getOauthToken(), getName());
            return authenticationInfo;
        }
        else
        {
            return null;
        }
    }

    private String extractUsername(String accessToken)
    {
        String username = oAuthService.getUsernameByAccessToken(accessToken);
        return username;
    }
}
