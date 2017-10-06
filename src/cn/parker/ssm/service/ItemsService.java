package cn.parker.ssm.service;

import java.util.List;

import cn.parker.ssm.po.ItemsCustom;
import cn.parker.ssm.po.ItemsQueryVo;



public interface ItemsService {
	//查询所有商品信息
	public List<ItemsCustom> findItemList(ItemsQueryVo itemsQueryVo) throws Exception;
	
	//根据id查询商品信息
	public ItemsCustom findItemsById(Integer id) throws Exception;
	
	//根据id更新商品信息,id:需要修改商品 的id，itemsCustom需要更新的商品的信息
	public void updateItems(Integer id,ItemsCustom itemsCustom)throws Exception;
	
	//根据id更新商品信息,id:需要修改商品 的id，itemsCustom需要更新的商品的信息
	public void deleteItems(Integer[] items_id)throws Exception;
}
