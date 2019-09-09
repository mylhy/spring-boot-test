package com.pro.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.stereotype.Service;

import com.pro.dao.SysRoleDao;
import com.pro.dao.UserInfoDao;
import com.pro.entity.SysRole;
import com.pro.entity.UserInfo;

@Service
public class UserInfoService{
    @Resource
    private UserInfoDao userInfoDao;

    @Resource
    private SysRoleDao sysRoleDao;
    
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
     * 保存用户信息
     */
    public void saveUserInfo(UserInfo userInfo) {
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

	public void delete(Integer id) {
		userInfoDao.deleteById(id);
	}

	public UserInfo findById(Integer uid) {
		return userInfoDao.getOne(uid);
	}
	
	/**
	 * 保存用户角色信息
	 */
	public UserInfo setRoles(Integer uid,Integer[] rids) {
		//查user
		UserInfo user=this.findById(uid);
		if(user==null) {
			return null;
		}
		List<SysRole> rList=new ArrayList<SysRole>();
		if(rids!=null && rids.length>0) {

			for(Integer rid : rids){  
	            SysRole role = sysRoleDao.getOne(rid);
	            rList.add(role);
	        }  
		}
		user.setRoleList(rList);
		this.saveUserInfo(user);
		return user;
	}
}