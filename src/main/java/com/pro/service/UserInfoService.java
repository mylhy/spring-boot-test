package com.pro.service;

import java.util.List;

import javax.annotation.Resource;

import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.stereotype.Service;

import com.mysql.fabric.xmlrpc.base.Data;
import com.pro.dao.UserInfoDao;
import com.pro.entity.UserInfo;

@Service
public class UserInfoService{
    @Resource
    private UserInfoDao userInfoDao;
    
    /**
     * 查询所有用户
     */
    public List<UserInfo> findAll(){
    	List<UserInfo> userList=userInfoDao.findAll();
    	return userInfoDao.findAll();
    }
    
    public UserInfo findByUsername(String username) {
        return userInfoDao.findByUsername(username);
    }
    
    /**
     * 添加用户,默认不分配权限
     */
    public void addUserInfo(UserInfo userInfo) {
    	userInfoDao.save(userInfo);
    }

	public UserInfo register(String username, String password) {
		UserInfo userInfo=new UserInfo();
		userInfo.setName("用户_"+System.currentTimeMillis());
		userInfo.setSalt(new SecureRandomNumberGenerator().nextBytes().toString());
		userInfo.setUsername(username);
		userInfo.setPassword(new SimpleHash("md5",password,userInfo.getCredentialsSalt(),2).toString());
		userInfoDao.save(userInfo);
		return userInfo;
	}
}