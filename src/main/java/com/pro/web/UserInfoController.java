package com.pro.web;

import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.subject.Subject;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.pro.entity.PageInfo;
import com.pro.entity.UserInfo;
import com.pro.service.UserInfoService;
import com.pro.util.BaseUtils;

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
    public String list(Model model,HttpServletRequest request,PageInfo pageInfo) {
    	Map<String, String> query=BaseUtils.getRequestParam(request, "query_");
        model.addAttribute("users", userInfoService.findAll(query, pageInfo));
        model.addAllAttributes(query);
        return "user/list";
    }
    /**
     * 添加用户
     */
    @RequestMapping("addUserInfo")
	public String add(Model model, String username, String password,RedirectAttributes attr) {
    	//获取添加用户的管理员
		Subject subject = SecurityUtils.getSubject();
		UserInfo user=(UserInfo) subject.getPrincipal();
		
//		userInfoService.addUser(username,password,user);


		attr.addFlashAttribute("username", username);
		
		return "redirect:/userInfo/userList";
	}

    /**
     * 修改用户信息
     */
	@RequestMapping("update")
	public String delete(Model model, Integer id, String name, String password) {
		UserInfo user=userInfoService.findById(id);
		if(user != null) {
			user.setName(name);
			user.setSalt(new SecureRandomNumberGenerator().nextBytes().toString());
			user.setPassword(new SimpleHash("md5",password,user.getCredentialsSalt(),2).toString());
		}
		userInfoService.saveUserInfo(user);
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
