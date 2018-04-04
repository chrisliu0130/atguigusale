package com.atguigu.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atguigu.bean.OBJECT_T_MALL_ATTR;
import com.atguigu.dao.AttrDao;
import com.atguigu.service.AttrService;

@Service
public class AttrServiceImpl implements AttrService {

	@Autowired
	AttrDao attrDao;

	@Override
	public void insert_attr(OBJECT_T_MALL_ATTR attr) {
		// 还是先插入属性，在根据返回的主键插入具体的属性值
		attrDao.save_attr(attr);

		// 在根据上面返回的主键插入具体的属性值
		attrDao.save_value(attr);

	}

	@Override
	public List<OBJECT_T_MALL_ATTR> get_attr_list(int flbh2) {
		List<OBJECT_T_MALL_ATTR> list_attr = attrDao.select_attr_list(flbh2);
		return list_attr;
	}

	

	/*
	 * @Override public void insert_attr(@Param("flbh2")int
	 * flbh2, @Param("attrList")List<OBJECT_T_MALL_ATTR> attrList) { for
	 * (OBJECT_T_MALL_ATTR attr : attrList) { attrDao.save_attr(flbh2,attr);
	 * 
	 * attrDao.save_value(attr.getId(),attr.getList_value()); }
	 * 
	 * }
	 */

}
