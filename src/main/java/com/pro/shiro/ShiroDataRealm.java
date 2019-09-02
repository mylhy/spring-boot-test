package com.pro.shiro;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import com.pro.entity.SysPermission;
import com.pro.entity.SysRole;
import com.pro.entity.UserInfo;
import com.pro.service.UserInfoService;

public class ShiroDataRealm extends AuthorizingRealm {

    @Autowired
    private UserInfoService userInfoService;

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
		System.out.println("权限配置-->");
		// 能进入到这里，表示账号已经通过验证了
		String userName = (String) principalCollection.getPrimaryPrincipal();
		// 通过service获取角色和权限
		UserInfo userInfo=userInfoService.findByUsername(userName);

		// 授权对象
		SimpleAuthorizationInfo s = new SimpleAuthorizationInfo();
		
		for(SysRole role:userInfo.getRoleList()){
            s.addRole(role.getRole());
            for(SysPermission p:role.getPermissions()){
                s.addStringPermission(p.getPermission());
            }
        }
		return s;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		System.out.println("-->doGetAuthenticationInfo");
        //获取用户的输入的账号.
        String username = (String)token.getPrincipal();
        if(username == null){
            return null;
        }
        UserInfo userInfo = userInfoService.findByUsername(username);
        if(userInfo == null){
            return null;
        }
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                userInfo, //用户名
                userInfo.getPassword(), //密码
                ByteSource.Util.bytes(userInfo.getCredentialsSalt()),//salt=username+salt
                getName()  //realm name
        );
        return authenticationInfo;
	}

}