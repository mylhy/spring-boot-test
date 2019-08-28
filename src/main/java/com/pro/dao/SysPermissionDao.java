package com.pro.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pro.entity.SysPermission;

public interface SysPermissionDao extends JpaRepository<SysPermission,Integer> {

}