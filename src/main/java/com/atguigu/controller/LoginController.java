package com.atguigu.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.atguigu.bean.T_MALL_USER_ACCOUNT;
import com.atguigu.service.LoginService;

@Controller
public class LoginController {
	@Autowired
	LoginService loginService;

	@RequestMapping("login")
	public String login(HttpServletResponse response, HttpServletRequest request, HttpSession session,
			T_MALL_USER_ACCOUNT user) throws UnsupportedEncodingException {

		// 用户登录接口
		T_MALL_USER_ACCOUNT loginUser = loginService.login(user);

		if (loginUser == null) {

			return "redirect:/login.do";
		} else {

			session.setAttribute("user", loginUser);
			// 存储用户个性化信息,向客户端用cookie提供一些个性化的信息
			Cookie cookie = new Cookie("yh_mch", URLEncoder.encode(loginUser.getYh_mch(), "UTF-8"));

			cookie.setPath("/");
			cookie.setMaxAge(60*60*24);
			
			Cookie cookie2 = new Cookie("yh_nch", URLEncoder.encode("巧克力", "UTF-8"));

			cookie2.setPath("/");
			cookie2.setMaxAge(60*60*24);

			response.addCookie(cookie);
			response.addCookie(cookie2);
			
		}

		return "redirect:/index.do";

	}

}
