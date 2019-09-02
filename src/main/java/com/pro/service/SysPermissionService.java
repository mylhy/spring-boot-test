package com.pro.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pro.dao.SysPermissionDao;
import com.pro.dao.UserInfoDao;
import com.pro.entity.SysPermission;
import com.pro.entity.SysRole;
import com.pro.entity.UserInfo;

@Service
public class SysPermissionService{

	@Autowired
	private SysPermissionDao sysPermissionDao;
	@Autowired
	private UserInfoDao UserInfoDao;

	public Set<String> listSysPermissions(String userName) {
		Set<String> result = new HashSet<>();
		
		UserInfo userInfo=UserInfoDao.findByUsername(userName);
		
		List<SysRole> SysRoles = userInfo.getRoleList();

		for (SysRole sysRole : SysRoles) {
			for (SysPermission sysPermission : sysRole.getPermissions()) {
				result.add(sysPermission.getName());
			}
		}

		

		return result;
	}

	
	public void add(SysPermission u) {
		sysPermissionDao.save(u);
	}

	
	public void delete(Integer id) {
		sysPermissionDao.deleteById(id);
	}

	public void update(SysPermission u) {
		sysPermissionDao.save(u);
	}

	
	public SysPermission get(Integer id) {
		return sysPermissionDao.getOne(id);
	}

	
	public List<SysPermission> list() {
		return sysPermissionDao.findAll();

	}

	public boolean needInterceptor(String requestURI) {
		List<SysPermission> ps = list();
		for (SysPermission p : ps) {
			if (p.getUrl().equals(requestURI))
				return true;
		}
		return false;
	}

	/**
	 * 根据用户获取用户可以进的Url
	 * @param userName
	 * @return
	 */
	public Set<String> listSysPermissionURLs(String userName) {
		Set<String> result = new HashSet<>();

		UserInfo userInfo=UserInfoDao.findByUsername(userName);
		List<SysRole> SysRoles = userInfo.getRoleList();

		for (SysRole sysRole : SysRoles) {
			for (SysPermission sysPermission : sysRole.getPermissions()) {
				result.add(sysPermission.getUrl());
			}
		}

		

		return result;
	}

}