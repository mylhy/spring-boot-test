package com.pro.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

//专门用于显示页面的控制器
@Controller
@RequestMapping("")
public class PageController {

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login() {
		return "login";
	}
	
	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public String register() {
		return "register";
	}
	
	@RequestMapping("/index")
	public String index() {
		return "index";
	}
	@RequestMapping("/403")
	public String noPerms() {
		return "403";
	}

	@RequestMapping("deleteOrder")
	public String deleteOrder() {
		return "deleteOrder";
	}

	@RequestMapping("deleteProduct")
	public String deleteProduct() {
		return "deleteProduct";
	}

	@RequestMapping("listProduct")
	public String listProduct() {
		return "listProduct";
	}


}
