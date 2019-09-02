package com.pro.web;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.pro.entity.UserInfo;
import com.pro.service.UserInfoService;

@Controller
@RequestMapping("")
public class RegisterController {
	

    @Resource
	private UserInfoService userInfoService;
	
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String login(Model model, String username, String password) {
		String msg="注册失败";
		UserInfo userInfo=userInfoService.register(username, password);
		if(userInfo!=null) {
			msg="注册成功";
			return "login";
		}
		model.addAttribute("msg", msg);
		return "/register";
	}
}
