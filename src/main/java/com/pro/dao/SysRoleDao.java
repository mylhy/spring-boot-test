package com.pro.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pro.entity.SysRole;

public interface SysRoleDao extends JpaRepository<SysRole,Integer> {

	List<SysRole> findByUserInfosUid(Integer uid);

}