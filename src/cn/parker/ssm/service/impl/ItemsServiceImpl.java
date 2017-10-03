package cn.parker.ssm.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import cn.parker.ssm.mapper.ItemsMapperCustom;
import cn.parker.ssm.po.ItemsCustom;
import cn.parker.ssm.po.ItemsQueryVo;
import cn.parker.ssm.service.ItemsService;

public class ItemsServiceImpl implements ItemsService{

	@Autowired
	private ItemsMapperCustom itemsMapperCustom;
	
	@Override
	public List<ItemsCustom> findItemList(ItemsQueryVo itemsQueryVo) throws Exception {
		//通过itemsMapperCustom查询数据库
		return itemsMapperCustom.findItemList(itemsQueryVo);
	}

}
