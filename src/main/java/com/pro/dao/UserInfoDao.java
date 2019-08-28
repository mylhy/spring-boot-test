package com.pro.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pro.entity.UserInfo;

public interface UserInfoDao extends JpaRepository<UserInfo,Integer> {
    /**通过username查找用户信息;*/
    public UserInfo findByUsername(String username);
}