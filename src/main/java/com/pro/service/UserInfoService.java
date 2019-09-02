package com.pro.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

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
    	for (UserInfo userInfo : userList) {
			userInfo.setPassword(userInfo.getPassword());
		}
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
}