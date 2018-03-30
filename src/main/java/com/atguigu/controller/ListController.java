package com.atguigu.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.atguigu.bean.MODET_T_MALL_SKU_ATTR_VALUE;
import com.atguigu.bean.OBJECT_T_MALL_SKU;
import com.atguigu.service.ListService;

@Controller
public class ListController {

	@Autowired
	ListService listService;

	/*
	 * public String get_list_by_attr(@RequestParam("param_array[]")String[]
	 * param_array,ModelMap map) {
	 */
	@RequestMapping("get_list_by_attr")
	public String get_list_by_attr(MODET_T_MALL_SKU_ATTR_VALUE list_attr, int flbh2, ModelMap map) {

		// 根据属性查询列表
		List<OBJECT_T_MALL_SKU> list_sku = listService.get_list_by_attr(list_attr.getList_attr(), flbh2);
		map.put("flbh2", flbh2);
		map.put("list_sku", list_sku);

		return "skuList";
	}
}
