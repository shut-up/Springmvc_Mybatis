package cn.parker.ssm.service;

import java.util.List;

import cn.parker.ssm.po.ItemsCustom;
import cn.parker.ssm.po.ItemsQueryVo;



public interface ItemsService {
	public List<ItemsCustom> findItemList(ItemsQueryVo itemsQueryVo) throws Exception;
}
