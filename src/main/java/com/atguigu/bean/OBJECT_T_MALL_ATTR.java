package com.atguigu.bean;

import java.util.List;

/*
 * 感觉上这就是一个包装类,包装一个属性
 * 一个属性（T_MALL_ATTR）中可以包含多个属性值（T_MALL_VALUE）
 * 但是为什么要继承呢？因为还要被包装？考虑到扩展性？
 * ：：在创建bean的时候，属性与数据库中的字段基本上都是一一对应的
 * 
 * 多重对象的同步参数表单提交：
 *    页面上一次性传递的T_MALL_VALUE为多条记录，应该用一个List集合接受
 * 根据“后台有setter才能封装”，因此需要把这个List集合包装一下。同理，T_MALL_ATTR的传递
 * 也是多条记录的，也需要有setter的List集合，
 */
public class OBJECT_T_MALL_ATTR extends T_MALL_ATTR {

	private List<T_MALL_VALUE> list_value;

	public List<T_MALL_VALUE> getList_value() {
		return list_value;
	}

	public void setList_value(List<T_MALL_VALUE> list_value) {
		this.list_value = list_value;
	}

}
