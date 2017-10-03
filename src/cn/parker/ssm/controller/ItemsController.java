package cn.parker.ssm.controller;

import java.util.List;

import org.omg.CORBA.PRIVATE_MEMBER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import cn.parker.ssm.po.ItemsCustom;
import cn.parker.ssm.service.ItemsService;

@Controller
public class ItemsController {
	@Autowired
	private ItemsService itemsService;

	@RequestMapping("/queryItems")
	public ModelAndView queryItems() throws Exception {
		List<ItemsCustom> itemsList = itemsService.findItemList(null);
		
		// 创建ModelAndView来填充数据，设置试图
		ModelAndView modelAndView = new ModelAndView();
		// 填充数据,相当于request.setAttribute,在jsp页面通过${itemsList }取出数据
		modelAndView.addObject("itemsList", itemsList);
		// 指定试图
		modelAndView.setViewName("items/itemsList");

		return modelAndView;
	}
}
