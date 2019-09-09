package com.pro.web;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pro.entity.SysPermission;
import com.pro.entity.SysRole;
import com.pro.service.SysPermissionService;
import com.pro.service.SysRoleService;
import com.pro.service.UserInfoService;

/**
 *  权限管理管理
 * @author Administrator
 *
 */
@Controller
@RequestMapping("config")
public class AuthorityConfigAction {
	@Resource
	private UserInfoService userInfoService;
	
	@Resource
	private SysRoleService sysRoleService;

	@Resource
	private SysPermissionService sysPermissionService;
	

    /**
     * 为用户分配角色
     */
	@RequestMapping("setUserRoles")
	public String assignRoles(Model model,Integer uid,Integer[] rid) {
		userInfoService.setRoles(uid, rid);
		return "redirect:/userInfo/userList";
	}
	
	
	/**
	 * 获取用户角色
	 */
	@ResponseBody
	@RequestMapping("getUserRoles")
	public List<SysRole> getUserRoles(Integer uid) {
		List<SysRole> resultMap = new ArrayList<SysRole>();
		//用户角色
		List<SysRole> urList=sysRoleService.findByUserRole(uid);
		//所有角色
		List<SysRole> rList=sysRoleService.findAll();
		
		for (SysRole obj : rList) {
			for (SysRole ur : urList) {
				if(ur.getId().equals(obj.getId())) {
					obj.setIsSelect(1);
				}
			}
			obj.setUserInfos(null);
			obj.setPermissions(null);
		}
		
		resultMap.addAll(rList);
		
		return resultMap;
	}
	
	/**
	 * 查询角色
	 * @param model
	 * @return
	 */
	@RequestMapping("getRoleList")
	public String findRoleList(Model model){
		List<SysRole> rList=sysRoleService.findAll();
		model.addAttribute("roles", rList);
		return "/role/list";
	}
	/**
	 * 添加角色
	 * @param role
	 * @return
	 */
	public String addRole(SysRole role) {
		sysRoleService.saveSysRole(role);
		return "redirect:/config/roleList";
	}
	
	/**
	 * 修改角色
	 * @param role
	 * @return
	 */
	public String updateRole(SysRole role) {
		sysRoleService.saveSysRole(role);
		return "redirect:/config/roleList";
	}
	
	/**
	 * 查询权限
	 */
	@RequestMapping("getRoleList")
	public String findAll(Model model){
		List<SysPermission> rList=sysPermissionService.findAll();
		model.addAttribute("roles", rList);
		return "/role/list";
	}
	/**
	 * 添加权限
	 * @param role
	 * @return
	 */
	public String addPsermission(SysPermission permission) {
		sysPermissionService.add(permission);
		return "redirect:/config/getRoleList";
	}
	/**
	 * 修改权限
	 * @param role
	 * @return
	 */
	public String updatePsermission(SysPermission permission) {
		sysPermissionService.update(permission);
		return "redirect:/config/getRoleList";
	}

}