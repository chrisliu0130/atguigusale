package com.atguigu.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;

import com.atguigu.bean.T_MALL_SHOPPINGCAR;
import com.atguigu.bean.T_MALL_USER_ACCOUNT;
import com.atguigu.server.LoginServer;
import com.atguigu.service.CartService;
import com.atguigu.util.MyJsonUtil;
import com.atguigu.util.MyPropertyUtil;
import com.atguigu.util.MyWsFactoryBean;

@Controller
public class LoginController {
	/*@Autowired   //被远程登录代替
	LoginService loginService;*/

	@Autowired
	CartService cartService;

	@RequestMapping("login")
	public String login(@CookieValue(value = "list_cart_cookie", required = false) String list_cart_cookie,
			HttpServletResponse response, HttpServletRequest request, HttpSession session, T_MALL_USER_ACCOUNT user)
			throws UnsupportedEncodingException {

		T_MALL_USER_ACCOUNT loginUser = new T_MALL_USER_ACCOUNT();// loginService.login(user);

		// 用户登录接口,远程接口
		String url = MyPropertyUtil.getProperty("ws.properties", "login_url");

		LoginServer myWs = MyWsFactoryBean.getMyWs(url, LoginServer.class);

		String loginJson = myWs.login(user);
		loginUser = MyJsonUtil.json_to_object(loginJson, T_MALL_USER_ACCOUNT.class);

		if (loginUser == null) {
			return "redirect:/goto_login.do";
		} else {
			// 用户登录成功

			session.setAttribute("user", loginUser);
			// 存储用户个性化信息,向客户端用cookie提供一些个性化的信息
			Cookie cookie = new Cookie("yh_mch", URLEncoder.encode(loginUser.getYh_mch(), "UTF-8"));

			cookie.setPath("/");
			cookie.setMaxAge(60 * 60 * 24);

			Cookie cookie2 = new Cookie("yh_nch", URLEncoder.encode("巧克力", "UTF-8"));

			cookie2.setPath("/");
			cookie2.setMaxAge(60 * 60 * 24);

			response.addCookie(cookie);
			response.addCookie(cookie2);

			// 同步购物车数据
			combine_cart(list_cart_cookie, response, session, loginUser);
		}

		return "redirect:/index.do";

	}

	private void combine_cart(String list_cart_cookie, HttpServletResponse response, HttpSession session,
			T_MALL_USER_ACCOUNT loginUser) {

		// 通用的list_cart
		List<T_MALL_SHOPPINGCAR> list_cart = new ArrayList<>();

		// 判断cookie中是否有数据
		if (StringUtils.isBlank(list_cart_cookie)) {
			// cookie中没有数据，啥也不用干吧。。。
		} else {

			// cookie中有数据，再判断db中是否有数据 ,t_mall_shoppingcar(优化：判断数据库中是否有相同的数据)
			list_cart = MyJsonUtil.json_to_list(list_cart_cookie, T_MALL_SHOPPINGCAR.class);

			List<T_MALL_SHOPPINGCAR> list_cart_db = cartService.get_list_cart_by_user(loginUser);

			for (int i = 0; i < list_cart.size(); i++) {
				// 为了方便循环的格式。。。
				T_MALL_SHOPPINGCAR cart = list_cart.get(i);
				cart.setYh_id(loginUser.getId());

				// 判断这件商品在数据库中是否已存在
				boolean b = cartService.if_cart_exists(cart);

				if (b) {
					// 更新
					for (int j = 0; j < list_cart_db.size(); j++) {
						// 这里为啥用sku_id做判断呢？
						if (cart.getSku_id() == list_cart_db.get(j).getSku_id()) {

							// 修改数量
							cart.setTjshl(list_cart.get(i).getTjshl() + list_cart_db.get(j).getTjshl());
							// 修改总价
							cart.setHj(list_cart.get(i).getSku_jg() * cart.getTjshl());

							// 老车,操作DB
							cartService.update_cart(cart);
						}
					}
				} else {
					// 添加
					cartService.add_cart(cart);
				}
			}

			/*
			 * if (list_cart_db == null || list_cart_db.size() == 0) { //
			 * 将cookie中的数据同步到数据库中，但是cookie中的数据并没有用户的id，需要添加 for (int i = 0; i <
			 * list_cart.size(); i++) { list_cart.get(i).setYh_id(loginUser.getId());
			 * cartService.add_cart(list_cart.get(i)); } } else { // 判断cookie中的数据与db中是否重复
			 * for (int i = 0; i < list_cart.size(); i++) { boolean b =
			 * if_new_cart(list_cart_db, list_cart.get(i));
			 * 
			 * if (b) { // 不重复，添加 list_cart.get(i).setYh_id(loginUser.getId());
			 * cartService.add_cart(list_cart.get(i)); } else { // 重复，更新
			 * 
			 * for (int j = 0; j < list_cart_db.size(); j++) { // 这里为啥用sku_id做判断呢？ //
			 * 同步session if (list_cart.get(i).getSku_id() ==
			 * list_cart_db.get(j).getSku_id()) { // 修改数量
			 * list_cart.get(i).setTjshl(list_cart.get(i).getTjshl() +
			 * list_cart_db.get(j).getTjshl()); // 修改总价
			 * list_cart.get(i).setHj(list_cart.get(i).getSku_jg() *
			 * list_cart.get(i).getTjshl());
			 * 
			 * // 老车,操作DB cartService.update_cart(list_cart.get(i)); }
			 * 
			 * } } }
			 * 
			 */
		}

		// 同步session，清空cookie
		session.setAttribute("list_cart_session", cartService.get_list_cart_by_user(loginUser));
		response.addCookie(new Cookie("list_cart_cookie", ""));
	}

	private boolean if_new_cart(List<T_MALL_SHOPPINGCAR> list_cart, T_MALL_SHOPPINGCAR cart) {
		boolean b = true;
		for (int i = 0; i < list_cart.size(); i++) {
			if (list_cart.get(i).getSku_id() == cart.getSku_id()) {
				b = false;
			}
		}

		return b;
	}

}
