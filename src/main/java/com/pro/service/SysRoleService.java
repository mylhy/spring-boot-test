package com.pro.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.pro.dao.SysRoleDao;
import com.pro.dao.UserInfoDao;
import com.pro.entity.SysRole;

@Service
public class SysRoleService{

	@Resource
	private UserInfoDao userInfoDao;
	@Resource
	private SysRoleDao sysRoleDao;
	
	/**
	 * 查询所有角色
	 */
	public List<SysRole> findAll(){
		return sysRoleDao.findAll();
	}
	
	/**
	 * 保存角色
	 */
	public SysRole saveSysRole(SysRole sysRole) {
		return sysRoleDao.save(sysRole);
	}
	
	public List<SysRole> findByUserRole(Integer uid) {

		List<SysRole> roleList=sysRoleDao.findByUserInfosUid(uid);
		return roleList;
	}

}