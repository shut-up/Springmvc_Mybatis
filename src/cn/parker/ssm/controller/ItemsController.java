package cn.parker.ssm.controller;

import java.util.ArrayList;
import java.util.List;

import org.omg.CORBA.PRIVATE_MEMBER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import cn.parker.ssm.po.Items;
import cn.parker.ssm.po.ItemsCustom;
import cn.parker.ssm.service.ItemsService;
import cn.parker.ssm.service.impl.ItemsServiceImpl;

@Controller
public class ItemsController {
	@Autowired
	private ItemsService itemsService;

	@RequestMapping("/queryItems")
	public ModelAndView queryItems() throws Exception {
		//调用service来查找数据库，查询商品列表
		List<ItemsCustom> itemsList = itemsService.findItemList(null);
		
//		List<Items> itemsList = new ArrayList<Items>();
//		Items item_1 = new Items();
//		item_1.setName("111111华硕笔记本");
//		item_1.setPrice(5000f);
//		item_1.setDetail("华硕最新款笔记本");
//		
//		Items item_2 = new Items();
//		item_2.setName("iphone X");
//		item_2.setPrice(9000f);
//		item_2.setDetail("最新款苹果手机，苹果十周年产品");
//		
//		itemsList.add(item_1);
//		itemsList.add(item_2);
//	
		
		// 创建ModelAndView来填充数据，设置试图
		ModelAndView modelAndView = new ModelAndView();
		// 填充数据,相当于request.setAttribute,在jsp页面通过${itemsList }取出数据
		modelAndView.addObject("itemsList", itemsList);
		// 指定试图
		modelAndView.setViewName("items/itemsList");

		return modelAndView;
	}
}
