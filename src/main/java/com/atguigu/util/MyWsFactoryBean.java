package com.atguigu.util;

import org.apache.cxf.frontend.ClientProxyFactoryBean;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;

public class MyWsFactoryBean {

	/*
	 * 调用第三方接口需要第三方接口的wsdl，最好再让对方提供其对应的方法的接口。。。。
	 * 
	 * 先得到一个服务器端发布的wsdl文件，解析创建一个接口，
	 * 然后通过该接口以及wsdl中的发布的地址创建客户端，
	 * 在调用接口的方法即可调用服务器上的方法。
	 * 
	 * JaxWsProxyFactoryBean extends ClientProxyFactoryBean
	 */
	public static <T> T getMyWs(String url, Class<T> t) {
		JaxWsProxyFactoryBean jwfb = new JaxWsProxyFactoryBean();
		jwfb.setAddress(url);
		jwfb.setServiceClass(t);
		T create = (T) jwfb.create();
		return create;
	}

}
