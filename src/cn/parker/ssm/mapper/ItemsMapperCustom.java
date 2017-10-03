package cn.parker.ssm.mapper;


import java.util.List;
import org.apache.ibatis.annotations.Param;

import cn.parker.ssm.po.Items;
import cn.parker.ssm.po.ItemsCustom;
import cn.parker.ssm.po.ItemsExample;
import cn.parker.ssm.po.ItemsQueryVo;

public interface ItemsMapperCustom {
    public List<ItemsCustom> findItemList(ItemsQueryVo itemsQueryVo) throws Exception;
}