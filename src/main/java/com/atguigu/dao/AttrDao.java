package com.atguigu.dao;

import java.util.List;

import com.atguigu.bean.OBJECT_T_MALL_ATTR;

public interface AttrDao {

	void save_attr(OBJECT_T_MALL_ATTR attr);

	void save_value(OBJECT_T_MALL_ATTR attr);

	List<OBJECT_T_MALL_ATTR> select_attr_list(int flbh2);

	/*void save_attr(int flbh2, OBJECT_T_MALL_ATTR attr);

	void save_value(int flbh2, List<T_MALL_VALUE> list_value);*/

}
