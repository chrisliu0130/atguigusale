package com.atguigu.service;

import java.util.List;

import com.atguigu.bean.DETAIL_T_MALL_SKU;
import com.atguigu.bean.T_MALL_PRODUCT_COLOR;
import com.atguigu.bean.T_MALL_PRODUCT_VERSION;
import com.atguigu.bean.T_MALL_SKU;

public interface ItemService {

	DETAIL_T_MALL_SKU get_sku_detail(int sku_id);

	List<T_MALL_SKU> get_sku_list_by_spu(int spu_id);

	List<T_MALL_PRODUCT_COLOR> get_color_by_spu(int spu_id);

	List<T_MALL_PRODUCT_VERSION> get_version_by_spu(int spu_id);

	int  get_sku_id_by_color_and_version(int color_id, int version_id);

}
