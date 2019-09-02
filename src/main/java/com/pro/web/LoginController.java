package com.pro.web;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("")
public class LoginController {
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(Model model, String userName, String password) {
		Subject subject = SecurityUtils.getSubject();
		UsernamePasswordToken token = new UsernamePasswordToken(userName, password);
		try {
			subject.login(token);
			Session session = subject.getSession();
			session.setAttribute("subject", subject);
			session.setAttribute("userName", userName);
			return "redirect:index";

		} catch (AuthenticationException e) {
			String msg="";
			if (UnknownAccountException.class.getName().equals(e.getClass().getName())) {
                msg = "账号不存在";
            } else if (IncorrectCredentialsException.class.getName().equals(e.getClass().getName())) {
                msg = "密码不正确";
            } else if ("kaptchaValidateFailed".equals(e.getClass().getName())) {
                msg = "验证码错误";
            } else {
                msg = "else >> "+e;
                e.printStackTrace();
            }
			model.addAttribute("error", msg);
			model.addAttribute("userName", userName);
			model.addAttribute("password", password);
			return "login";
		}
	}	
}
