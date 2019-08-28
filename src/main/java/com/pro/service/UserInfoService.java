package com.pro.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.pro.dao.UserInfoDao;
import com.pro.entity.UserInfo;

@Service
public class UserInfoService{
    @Resource
    private UserInfoDao userInfoDao;
    
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