package com.atguigu.dao;

import java.util.List;
import java.util.Map;

import com.atguigu.bean.OBJECT_T_MALL_SKU;

public interface ListDao {

	List<OBJECT_T_MALL_SKU> select_list_by_flbh2(int flbh2);

	List<OBJECT_T_MALL_SKU> select_list_by_flbh2(Map<Object, Object> paramMap);

	List<OBJECT_T_MALL_SKU> select_list_by_attr(Map<Object, Object> paramMap);

}
