package com.pro.web;

import java.util.List;

import javax.annotation.Resource;

import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pro.entity.UserInfo;
import com.pro.service.UserInfoService;

@Controller
@RequestMapping("userInfo")
public class UserInfoController {

    @Resource
    UserInfoService userInfoService;

    /**
     * 用户注册
     * @param model
     * @return
     */
    @RequestMapping("register")
    public String register(Model model,String username,String password) {
    	userInfoService.register(username,password);
        return "user/list";
    }
    /**
     * 用户列表
     * @param model
     * @return
     */
    @RequestMapping("userList")
    public String list(Model model) {
        List<UserInfo> users=userInfoService.findAll();
        model.addAttribute("users", users);
        return "user/list";
    }
    /**
     * 添加用户
     */
    @RequestMapping("addUserInfo")
	public String add(Model model, String name, String password) {
		userInfoService.register(name,password);
		return "redirect:/userInfo/userList";
	}
    
    /**
     * 删除用户
     */
	@RequestMapping("delete")
	public String delete(Model model, Integer id) {
		userInfoService.delete(id);
		return "redirect:/userInfo/userList";
	}

}
