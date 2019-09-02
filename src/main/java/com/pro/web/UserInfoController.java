package com.pro.web;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pro.entity.UserInfo;
import com.pro.service.UserInfoService;

@Controller
@RequestMapping("user")
public class UserInfoController {

    @Resource
    UserInfoService userInfoService;
    
    
    @RequestMapping("/list")
    public String list(Model model) {
        List<UserInfo> users=userInfoService.findAll();
        model.addAttribute("users", users);
        return "user/list";
    }

    @RequestMapping("/toAdd")
    public String toAdd() {
        return "user/userAdd";
    }

    @RequestMapping("/add")
    public String add(UserInfo UserInfo) {
    	userInfoService.addUserInfo(UserInfo);
        return "redirect:/list";
    }

}
