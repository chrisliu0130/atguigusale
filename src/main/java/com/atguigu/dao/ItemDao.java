package com.atguigu.dao;

import java.util.List;
import java.util.Map;

import com.atguigu.bean.DETAIL_T_MALL_SKU;
import com.atguigu.bean.T_MALL_PRODUCT_COLOR;
import com.atguigu.bean.T_MALL_PRODUCT_VERSION;
import com.atguigu.bean.T_MALL_SKU;

public interface ItemDao {

	DETAIL_T_MALL_SKU select_detail_sku(Map<Object, Object> paramMap);

	List<T_MALL_SKU> select_sku_list_by_spu(int spu_id);

	List<T_MALL_PRODUCT_COLOR> select_color_by_spu(int spu_id);

	List<T_MALL_PRODUCT_VERSION> select_version_by_spu(int spu_id);

	int select_sku_id_by_color_and_version(Map<Object, Object> paramMap);

}
