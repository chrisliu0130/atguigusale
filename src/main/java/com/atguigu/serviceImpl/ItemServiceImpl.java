package com.atguigu.serviceImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atguigu.bean.DETAIL_T_MALL_SKU;
import com.atguigu.bean.T_MALL_PRODUCT_COLOR;
import com.atguigu.bean.T_MALL_PRODUCT_VERSION;
import com.atguigu.bean.T_MALL_SKU;
import com.atguigu.dao.ItemDao;
import com.atguigu.service.ItemService;

@Service
public class ItemServiceImpl implements ItemService {

	@Autowired
	ItemDao itemDao;

	@Override
	public DETAIL_T_MALL_SKU get_sku_detail(int sku_id) {
		Map<Object, Object> paramMap = new HashMap<Object, Object>();
		paramMap.put("sku_id", sku_id);
		DETAIL_T_MALL_SKU obj_sku = itemDao.select_detail_sku(paramMap);
		return obj_sku;
	}

	@Override
	public List<T_MALL_SKU> get_sku_list_by_spu(int spu_id) {
		List<T_MALL_SKU> sku_list = itemDao.select_sku_list_by_spu(spu_id);
		return sku_list;
	}

	@Override
	public List<T_MALL_PRODUCT_COLOR> get_color_by_spu(int spu_id) {
		List<T_MALL_PRODUCT_COLOR> color_list = itemDao.select_color_by_spu(spu_id);
		return color_list;
	}

	@Override
	public List<T_MALL_PRODUCT_VERSION> get_version_by_spu(int spu_id) {
		List<T_MALL_PRODUCT_VERSION> version_list= itemDao.select_version_by_spu(spu_id);
		return version_list;
	}

	@Override
	public int get_sku_id_by_color_and_version(int color_id, int version_id) {
		Map<Object, Object> paramMap=new HashMap<>();
		paramMap.put("color_id", color_id);
		paramMap.put("version_id", version_id);
		int sku_id = itemDao.select_sku_id_by_color_and_version(paramMap);
		return sku_id;
	}

}
