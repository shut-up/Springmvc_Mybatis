package cn.parker.ssm.service.impl;

import java.util.List;

import org.aspectj.weaver.Utils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import cn.parker.ssm.mapper.ItemsMapper;
import cn.parker.ssm.mapper.ItemsMapperCustom;
import cn.parker.ssm.po.Items;
import cn.parker.ssm.po.ItemsCustom;
import cn.parker.ssm.po.ItemsQueryVo;
import cn.parker.ssm.service.ItemsService;

public class ItemsServiceImpl implements ItemsService{

	@Autowired
	private ItemsMapperCustom itemsMapperCustom;
	
	@Autowired
	private ItemsMapper itemsMapper;
	
	@Override
	public List<ItemsCustom> findItemList(ItemsQueryVo itemsQueryVo) throws Exception {
		//通过itemsMapperCustom查询数据库
		return itemsMapperCustom.findItemList(itemsQueryVo);
	}

	@Override
	public ItemsCustom findItemsById(Integer id) throws Exception {
		
		Items items = itemsMapper.selectByPrimaryKey(id);
		
		
		
		//还有许多对商品信息进行业务处理
		//.....
		//返回ItemsCustom
		ItemsCustom itemsCustom = null;
		
		if(items != null){
			itemsCustom = new ItemsCustom();
			//将items内容拷贝到itemsCustom中
			BeanUtils.copyProperties(items, itemsCustom);
		}
		
		return itemsCustom;
	}

	@Override
	public void updateItems(Integer id, ItemsCustom itemsCustom) throws Exception {
		//添加业务校验，通常在Service接口对关键参数进行校验
		//检验id是否为空，空就抛出异常（暂不实现）
		
		itemsCustom.setId(id);
		//更新商品信息使用updateByPrimaryKeyWithBLOBs，根据id更新Items表中所有字段，包括大文本类型
		//updateByPrimaryKeyWithBLOBs要去必须传入id
		itemsMapper.updateByPrimaryKeyWithBLOBs(itemsCustom);
	}

	@Override
	public void deleteItems(Integer[] items_id) throws Exception {
		for(int i=0;i<items_id.length;i++)
			itemsMapper.deleteByPrimaryKey(items_id[i]);
	}

}
