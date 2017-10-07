package cn.parker.ssm.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

//登录拦截器
@Controller
public class LoginController {

	@RequestMapping("/login")
	public String login(HttpSession session,String username,String password)throws Exception {
		
		//调用service进行登录认证
		//...
		
		//在session中保存用户信息
		session.setAttribute("username", username);
		
		return "redirect:/items/queryItems.action";
		
	}
	
	@RequestMapping("/logout")
	public String logout(HttpSession session)throws Exception {
		
		//调用service进行登录认证
		//...
		
		//在session中数据过期
		session.invalidate();
		
		return "redirect:/items/queryItems.action";
		
	}
}
