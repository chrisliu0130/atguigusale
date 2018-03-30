package com.atguigu.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atguigu.bean.T_MALL_USER_ACCOUNT;
import com.atguigu.dao.LoginDao;
import com.atguigu.service.LoginService;

@Service
public class LoginServiceImpl implements LoginService {

	@Autowired
	LoginDao loginDao;
	
	@Override
	public T_MALL_USER_ACCOUNT login(T_MALL_USER_ACCOUNT user) {
		return loginDao.select_user(user);
	}

}
