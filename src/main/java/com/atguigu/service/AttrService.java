package com.atguigu.service;

import java.util.List;

import com.atguigu.bean.OBJECT_T_MALL_ATTR;
import com.atguigu.bean.OBJECT_T_MALL_SKU;

public interface AttrService {

	void insert_attr(OBJECT_T_MALL_ATTR attr);

	List<OBJECT_T_MALL_ATTR> get_attr_list(int flbh2);


	/*void insert_attr(int flbh2, List<OBJECT_T_MALL_ATTR> attrList);
*/
}
