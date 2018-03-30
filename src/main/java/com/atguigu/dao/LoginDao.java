package com.atguigu.dao;

import com.atguigu.bean.T_MALL_USER_ACCOUNT;

public interface LoginDao {

	T_MALL_USER_ACCOUNT select_user(T_MALL_USER_ACCOUNT user);

}
