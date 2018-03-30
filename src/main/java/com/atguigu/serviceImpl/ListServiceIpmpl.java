package com.atguigu.serviceImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atguigu.bean.OBJECT_T_MALL_SKU;
import com.atguigu.bean.T_MALL_SKU_ATTR_VALUE;
import com.atguigu.dao.ListDao;
import com.atguigu.service.ListService;

@Service
public class ListServiceIpmpl implements ListService {

	@Autowired
	ListDao listDao;

	@Override
	public List<OBJECT_T_MALL_SKU> get_list_by_flbh2(int flbh2) {
		List<OBJECT_T_MALL_SKU> list_sku = listDao.select_list_by_flbh2(flbh2);
		return list_sku;
	}

	@Override
	public List<OBJECT_T_MALL_SKU> get_list_by_attr(List<T_MALL_SKU_ATTR_VALUE> list_attr, int flbh2) {
		Map<Object, Object> paramMap = new HashMap<Object, Object>();

		StringBuffer subSql = new StringBuffer("");
		// 拼接过滤条件sql语句
		subSql.append(" and sku.id in ( select sku0.sku_id from ");
		if (list_attr != null && list_attr.size() > 0) {

			for (int i = 0; i < list_attr.size(); i++) {
				subSql.append(
						"(select sku_id from t_mall_sku_attr_value where shxm_id = " + list_attr.get(i).getShxm_id()
								+ " and shxzh_id =" + list_attr.get(i).getShxzh_id() + ") sku" + i + "");
				if (list_attr.size() > 1 && i < list_attr.size() - 1) {
					subSql.append(" , ");
				}
			}

			if (list_attr.size() > 1) {
				subSql.append(" where ");

				for (int i = 0; i < list_attr.size() - 1; i++) {
					if (i < list_attr.size() - 1) {
						subSql.append(" sku" + i + ".sku_id = sku" + (i + 1) + ".sku_id ");

						if (list_attr.size() > 2 && list_attr.size() > (i + 2)) {
							subSql.append(" and ");
						}
					}
				}
			}
		}

		subSql.append(" ) ");

		paramMap.put("list_attr", list_attr);
		paramMap.put("subSql", subSql.toString());
		paramMap.put("flbh2",flbh2);

		List<OBJECT_T_MALL_SKU> list_sku = listDao.select_list_by_attr(paramMap);
		return list_sku;
	}

}
