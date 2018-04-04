package com.atguigu.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.atguigu.bean.DETAIL_T_MALL_SKU;
import com.atguigu.bean.T_MALL_PRODUCT_COLOR;
import com.atguigu.bean.T_MALL_PRODUCT_VERSION;
import com.atguigu.bean.T_MALL_SKU;
import com.atguigu.service.ItemService;

@Controller
public class ItemController {

	@Autowired
	ItemService itemService;

	@RequestMapping("select_by_color_version")
	public String select_by_color_version(ModelMap map, @RequestParam("color_id") int color_id,
			@RequestParam("version_id") int version_id, @RequestParam("spu_id") int spu_id) {

		// 可以获得到，System.out.println(color_id+"---"+version_id);
		// 用json的工具把笛卡尔集的集合转成json？然后呢？
		// T_MALL_SKU obj_sku = itemService.get_sku_by_color_and_version(color_id,
		// version_id);
		// map.put("obj_sku", obj_sku);

		// 其实应该查询商品的详细信息对象。。要不直接返回sku_id？这样对
		// 老师的意思是把所有颜色、版本和sku_id的信息做的成笛卡尔集，再转成json的格式，省去下面的这一步吧
		int sku_id = itemService.get_sku_id_by_color_and_version(color_id, version_id);

		// 之后的事情就是再来一套goto_sku_detail里面的内容吧。。。。
		// 但是怎么才能把选出来的单独的这个商品展示在页面上呢？

		// 查询商品详细信息对象
		DETAIL_T_MALL_SKU obj_sku = itemService.get_sku_detail(sku_id);

		// 查询同一个spu_id下其他的sku商品信息(spu_id用)
		List<T_MALL_SKU> list_sku = itemService.get_sku_list_by_spu(spu_id);

		// 顺便查询商品属性列表

		// 包括颜色列表、版本列表,根据spu_id可查出该spu_id下所有的颜色和版本信息
		List<T_MALL_PRODUCT_COLOR> list_color = itemService.get_color_by_spu(spu_id);
		List<T_MALL_PRODUCT_VERSION> list_version = itemService.get_version_by_spu(spu_id);

		map.put("list_color", list_color);
		map.put("list_version", list_version);

		map.put("obj_sku", obj_sku);
		map.put("sku_id", sku_id);
		map.put("spu_id", spu_id);
		map.put("list_sku", list_sku);

		return "skuDetail";
	}

	@RequestMapping("goto_sku_detail")
	public String goto_sku_detail(int sku_id, int spu_id, ModelMap map) {

		// 查询商品详细信息对象
		DETAIL_T_MALL_SKU obj_sku = itemService.get_sku_detail(sku_id);

		// 查询同一个spu_id下其他的sku商品信息(spu_id用)
		List<T_MALL_SKU> list_sku = itemService.get_sku_list_by_spu(spu_id);

		// 顺便查询商品属性列表

		// 包括颜色列表、版本列表,根据spu_id可查出该spu_id下所有的颜色和版本信息
		List<T_MALL_PRODUCT_COLOR> list_color = itemService.get_color_by_spu(spu_id);
		List<T_MALL_PRODUCT_VERSION> list_version = itemService.get_version_by_spu(spu_id);

		map.put("list_color", list_color);
		map.put("list_version", list_version);

		map.put("obj_sku", obj_sku);
		map.put("sku_id", sku_id);
		map.put("spu_id", spu_id);
		map.put("list_sku", list_sku);

		return "skuDetail";
	}
}
