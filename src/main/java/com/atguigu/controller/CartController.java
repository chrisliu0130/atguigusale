package com.atguigu.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.atguigu.bean.T_MALL_SHOPPINGCAR;
import com.atguigu.bean.T_MALL_USER_ACCOUNT;
import com.atguigu.service.CartService;
import com.atguigu.util.MyJsonUtil;
import com.mysql.fabric.Response;

@Controller
public class CartController {

	@Autowired
	CartService cartService;

	@RequestMapping("change_shfxz")
	public String change_shfxz(HttpServletResponse response, HttpSession session,
			@CookieValue(value = "list_cart_cookie", required = false) String list_cart_cookie, ModelMap map,
			T_MALL_SHOPPINGCAR cart) {

		List<T_MALL_SHOPPINGCAR> list_cart = new ArrayList<>();
		T_MALL_USER_ACCOUNT user = (T_MALL_USER_ACCOUNT) session.getAttribute("user");
		// 根据复选框中的状态修改购物车中的内容
		if (user == null) {
			// 修改cookie
			list_cart = MyJsonUtil.json_to_list(list_cart_cookie, T_MALL_SHOPPINGCAR.class);

		} else {
			// 修改db
			list_cart = (List<T_MALL_SHOPPINGCAR>) session.getAttribute("list_cart_session");

		}

		for (int i = 0; i < list_cart.size(); i++) {
			if (list_cart.get(i).getSku_id() == cart.getSku_id()) {
				list_cart.get(i).setShfxz(cart.getShfxz());

				if (user == null) {
					// 覆盖cookie
					Cookie cookie = new Cookie("list_cart_cookie", MyJsonUtil.list_to_json(list_cart));
					cookie.setMaxAge(60 * 60 * 24); // 过期时间
					response.addCookie(cookie);
				} else {
					cartService.update_cart(list_cart.get(i));
				}
			}
		}

		map.put("sum", get_sum(list_cart));
		map.put("list_cart", list_cart);
		return "cartListInner";
	}

	private BigDecimal get_sum(List<T_MALL_SHOPPINGCAR> list_cart) {
		BigDecimal sum = new BigDecimal("0");
		for (int i = 0; i < list_cart.size(); i++) {
			if (list_cart.get(i).getShfxz().equals("1")) {
				sum = sum.add(new BigDecimal(list_cart.get(i).getHj() + ""));
			}
		}

		return sum;
	}

	@RequestMapping("goto_list_cart")
	public String goto_list_cart(HttpSession session,
			@CookieValue(value = "list_cart_cookie", required = false) String list_cart_cookie, ModelMap map) {
		List<T_MALL_SHOPPINGCAR> list_cart = new ArrayList<>();
		T_MALL_USER_ACCOUNT user = (T_MALL_USER_ACCOUNT) session.getAttribute("user");

		// 通过cookie或者session获取购物车数据
		if (user == null) {
			// 用户未登录
			// 从cookie中获得购物车信息
			list_cart = MyJsonUtil.json_to_list(list_cart_cookie, T_MALL_SHOPPINGCAR.class);
		} else {
			// 用户已经未登录
			// 从session中获得用户数据库的信息
			list_cart = (List<T_MALL_SHOPPINGCAR>) session.getAttribute("list_cart_session");
		}

		map.put("sum", get_sum(list_cart));
		map.put("list_cart", list_cart);
		return "cartList";
	}

	@RequestMapping("miniCart")
	public String miniCart(HttpSession session,
			@CookieValue(value = "list_cart_cookie", required = false) String list_cart_cookie, ModelMap map) {
		List<T_MALL_SHOPPINGCAR> list_cart = new ArrayList<>();
		T_MALL_USER_ACCOUNT user = (T_MALL_USER_ACCOUNT) session.getAttribute("user");

		// 通过cookie或者session获取购物车数据
		if (user == null) {
			// 用户未登录
			// 从cookie中获得购物车信息
			list_cart = MyJsonUtil.json_to_list(list_cart_cookie, T_MALL_SHOPPINGCAR.class);
		} else {
			// 用户已经未登录
			// 从session中获得用户数据库的信息
			list_cart = (List<T_MALL_SHOPPINGCAR>) session.getAttribute("list_cart_session");
		}
		map.put("list_cart", list_cart);
		return "miniCartList";
	}

	@RequestMapping("add_cart")
	public String add_cart(HttpSession session, HttpServletResponse response,
			@CookieValue(value = "list_cart_cookie", required = false) String list_cart_cookie, T_MALL_SHOPPINGCAR cart,
			ModelMap map, @RequestParam("spu_id") String spu_id) {
		List<T_MALL_SHOPPINGCAR> list_cart = new ArrayList<>();
		int yh_id = cart.getYh_id();
		// 添加购物车的操作
		if (yh_id == 0) {
			// 用户未登录，操作cookie
			if (StringUtils.isBlank(list_cart_cookie)) {
				// 如果cookie中内容为空,但是应该把什么东西放入cookie中呢？
				// 放一个cart的集合

				list_cart.add(cart);

			} else {
				// 如果cookie中内容非空
				// 需要判断cookie中的内容是否与新添加cart中的内容是否相同，
				// 相同则修改，不同则添加

				list_cart = MyJsonUtil.json_to_list(list_cart_cookie, T_MALL_SHOPPINGCAR.class);

				boolean b = if_new_cart(list_cart, cart);
				// 判断是否重复
				if (b) {
					// 新车
					list_cart.add(cart);
				} else {
					// 老车
					for (int i = 0; i < list_cart.size(); i++) {
						// 这里为啥用sku_id做判断呢？
						// 同步session
						if (list_cart.get(i).getSku_id() == cart.getSku_id()) {
							// 修改数量
							list_cart.get(i).setTjshl(list_cart.get(i).getTjshl() + cart.getTjshl());
							// 修改总价
							list_cart.get(i).setHj(list_cart.get(i).getSku_jg() * list_cart.get(i).getTjshl());
						}

					}
				}

			}

			// 覆盖cookie
			Cookie cookie = new Cookie("list_cart_cookie", MyJsonUtil.list_to_json(list_cart));
			cookie.setMaxAge(60 * 60 * 24); // 过期时间
			response.addCookie(cookie);

		} else {
			// 用户已登录，操作db
			// 从session中获得用户数据库的信息
			list_cart = (List<T_MALL_SHOPPINGCAR>) session.getAttribute("list_cart_session");

			if (list_cart == null || list_cart.isEmpty()) {
				// session中都没有数据，db中就更没有数据,直接操作db
				cartService.add_cart(cart);
				// 同步session
				list_cart = new ArrayList<>();
				list_cart.add(cart);
				session.setAttribute("list_cart_session", list_cart);
			} else {
				boolean b = if_new_cart(list_cart, cart);
				// 判断是否重复
				if (b) {
					// 新车
					// 操作db，主键返回带有id
					cartService.add_cart(cart);
					// 同步session
					list_cart.add(cart);
				} else {

					// 同步session
					for (int i = 0; i < list_cart.size(); i++) {
						// 这里为啥用sku_id做判断呢？
						// 同步session
						if (list_cart.get(i).getSku_id() == cart.getSku_id()) {
							// 修改数量
							list_cart.get(i).setTjshl(list_cart.get(i).getTjshl() + cart.getTjshl());
							// 修改总价
							list_cart.get(i).setHj(list_cart.get(i).getSku_jg() * list_cart.get(i).getTjshl());

							// 老车,操作DB
							cartService.update_cart(list_cart.get(i));
						}

					}
				}
			}
		}

		// return "redirect:/cart_success.do";

		return "redirect:/goto_sku_detail.do?sku_id=" + cart.getSku_id() + "&spu_id=" + spu_id;
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

	@RequestMapping("cart_success")
	public String cart_success(ModelMap map) {

		return "cartSuccess";
	}
}
