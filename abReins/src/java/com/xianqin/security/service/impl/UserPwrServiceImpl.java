package com.xianqin.security.service.impl;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

import com.xianqin.security.service.UserPwrService;

@Service
public class UserPwrServiceImpl implements UserPwrService {

    private Cache cache;


    @Autowired
    public UserPwrServiceImpl(CacheManager cacheManager) {
        this.cache = cacheManager.getCache("user-pwr");
    }

//    user-pwr
    @Override
    public void addUserPwrByUserName(String userName,Set<String> urlSet){
    	cache.put(userName, urlSet);
    }

    /**
     * 校验用户是否具有该URL的使用权限
     * @param userName
     * @param url
     * @return
     */
    @SuppressWarnings("unchecked")
	@Override
    public boolean isPwr(String userName,String url){
    	Set<String> cachePwd = null;
    	if(cache.get(userName)!=null){
    		cachePwd = (Set<String>)cache.get(userName).get();
        	if(cachePwd!=null && !cachePwd.isEmpty()){
        		if(cachePwd.contains(url)){
        			return true;
        		}else{
        			return false;
        		}
        	}else{
        		return false;
        	}
    	}else{
    		return false;
    	}
    	
    }
    

    /**
     * 获取到期时间
     */
    @Override
    public long getExpireIn() {
        return 3600L;
    }
}
