package cn.parker.ssm.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.omg.CORBA.PRIVATE_MEMBER;
import org.omg.CORBA.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.HttpRequestHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import cn.parker.ssm.po.Items;
import cn.parker.ssm.po.ItemsCustom;
import cn.parker.ssm.service.ItemsService;
import cn.parker.ssm.service.impl.ItemsServiceImpl;

@Controller
//为了对url进行分类管理，可以在这定义根路径，最终访问url为根路径+子路径，如：查询商品列表路径为/items/qureyItems.action
@RequestMapping("/items")
public class ItemsController {
	@Autowired
	private ItemsService itemsService;

	@RequestMapping("/queryItems")
	public ModelAndView queryItems(HttpServletRequest request) throws Exception {
		
		//测试页面转发可以共享request
		System.out.println("request.getParameter('id')="+request.getParameter("id"));
		
		//调用service来查找数据库，查询商品列表
		List<ItemsCustom> itemsList = itemsService.findItemList(null);
		
		// 创建ModelAndView来填充数据，设置试图
		ModelAndView modelAndView = new ModelAndView();
		// 填充数据,相当于request.setAttribute,在jsp页面通过${itemsList }取出数据
		modelAndView.addObject("itemsList", itemsList);
		// 指定试图
		modelAndView.setViewName("items/itemsList");

		return modelAndView;
	}
	
////	@RequestMapping("/editItems")
//	//限制请求方法为post和get(如果限制方法为post方法则会报错，因为这里请求为get方法)
//	@RequestMapping(value="/editItems", method={RequestMethod.POST,RequestMethod.GET})
//	public ModelAndView editItems() throws Exception {
//		
//		ItemsCustom itemsCustom = itemsService.findItemsById(1);
//		
//		// 创建ModelAndView来填充数据，设置试图
//		ModelAndView modelAndView = new ModelAndView();
//		// 填充数据,相当于request.setAttribute,在jsp页面通过${itemsList }取出数据
//		modelAndView.addObject("itemsCustom", itemsCustom);
//		// 指定试图，
//		modelAndView.setViewName("items/editItems");
//
//		return modelAndView;
//	}

	/*
	 * 返回string类型，可以替代上面返回ModelAndView的editItems方法，此方法较简洁
	 */
	//限制请求方法为post和get(如果限制方法为post方法则会报错，因为这里请求为get方法)
	@RequestMapping(value="/editItems", method={RequestMethod.POST,RequestMethod.GET})
	public String editItems(Model model) throws Exception {
		
		ItemsCustom itemsCustom = itemsService.findItemsById(1);
		
		//相当于modelAndView.addObject方法
		model.addAttribute("itemsCustom", itemsCustom);

		return "items/editItems";
	}
	
	@RequestMapping("/editItemsSubmit")
	public String editItemsSubmit(HttpServletRequest request) throws Exception {
		
//		// 创建ModelAndView来填充数据，设置试图
//		ModelAndView modelAndView = new ModelAndView();
//		// 指定试图,返回成功页面
//		modelAndView.setViewName("success");
		
		//重定向到查询商品列表页面，此时request值无法共享，地址栏变化
//		return "redirect:queryItems.action";
		
		//页面转发，request值可共享，地址栏不变
		return "forward:queryItems.action";

//		return "success";
	}
	
	
}
