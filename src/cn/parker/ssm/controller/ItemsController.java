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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import cn.parker.ssm.po.Items;
import cn.parker.ssm.po.ItemsCustom;
import cn.parker.ssm.po.ItemsQueryVo;
import cn.parker.ssm.service.ItemsService;
import cn.parker.ssm.service.impl.ItemsServiceImpl;

@Controller
// 为了对url进行分类管理，可以在这定义根路径，最终访问url为根路径+子路径，如：查询商品列表路径为/items/qureyItems.action
@RequestMapping("/items")
public class ItemsController {
	@Autowired
	private ItemsService itemsService;

	/*
	 * 包装类型的pojo参数绑定，input name的值必须和包装类型的pojo属性的属性名一致
	 * 如，input name="itemsCustom.name"，则itemsQueryVo必须要有itemsCustom属性，itemsCustom中必须要有name的属性名
	 */
	@RequestMapping("/queryItems")
	public ModelAndView queryItems(HttpServletRequest request,ItemsQueryVo itemsQueryVo) throws Exception {

		// 测试页面转发可以共享request
		//System.out.println("request.getParameter('id')=" + request.getParameter("id"));

		// 调用service来查找数据库，查询商品列表
		List<ItemsCustom> itemsList = itemsService.findItemList(itemsQueryVo);

		// 创建ModelAndView来填充数据，设置试图
		ModelAndView modelAndView = new ModelAndView();
		// 填充数据,相当于request.setAttribute,在jsp页面通过${itemsList }取出数据
		modelAndView.addObject("itemsList", itemsList);
		// 指定试图
		modelAndView.setViewName("items/itemsList");

		return modelAndView;
	}

/*	// @RequestMapping("/editItems")
	 //限制请求方法为post和get(如果限制方法为post方法则会报错，因为这里请求为get方法)
	 @RequestMapping(value="/editItems",method={RequestMethod.POST,RequestMethod.GET})
	 public ModelAndView editItems() throws Exception {
	
	 ItemsCustom itemsCustom = itemsService.findItemsById(1);
	
	 // 创建ModelAndView来填充数据，设置试图
	 ModelAndView modelAndView = new ModelAndView();
	 // 填充数据,相当于request.setAttribute,在jsp页面通过${itemsList }取出数据
	 modelAndView.addObject("itemsCustom", itemsCustom);
	 // 指定试图，
	 modelAndView.setViewName("items/editItems");
	
	 return modelAndView;
	 }
*/
	
	/*
	 * 返回string类型，可以替代上面返回ModelAndView的editItems方法，此方法较简洁
	 */
	// 限制请求方法为post和get(如果限制方法为post方法则会报错，因为这里请求为get方法)
	@RequestMapping(value = "/editItems", method = { RequestMethod.POST, RequestMethod.GET })
	// 简单类型参数绑定，方法形参的名字必须和request中的属性名一致，不推荐使用,推荐使用@requestParam
	// public String editItems(Model model,Integer id) throws Exception {
	/*
	 * 使用@requestParam 
	 * value：指定request传入参数与形参进行绑定
	 * required：指定参数是否必须传入
	 * defaultValue：如果没有参数传入时，使用默认值
	 */
	public String editItems(Model model,@RequestParam(value = "id", required = true, defaultValue = "") Integer items_id) throws Exception {

		ItemsCustom itemsCustom = itemsService.findItemsById(items_id);

		// 相当于modelAndView.addObject方法
		model.addAttribute("itemsCustom", itemsCustom);

		return "items/editItems";
	}

	
	@RequestMapping("/editItemsSubmit")
	/*
	 * pojo类型的参数绑定，绑定成功的前提是，页面中input的name的值与pojo中的属性值一致 ，如
	 * input name="id"，则ItemsCustom必须有id属性
	 *  Integer id为简单类型的参数绑定， ItemsCustom itemsCustom为pojo类型参数绑定
	 * 形参中pojo对象的属性中有日期类型createtime，需要自定义参数绑定
	 */
	public String editItemsSubmit(HttpServletRequest request, Integer id, ItemsCustom itemsCustom) throws Exception {

		// 解决post乱码，在web.xml文件中配置乱码过滤器
		itemsService.updateItems(id, itemsCustom);

		// 重定向到查询商品列表页面，此时request值无法共享，地址栏变化
		 return "redirect:queryItems.action";

		// 页面转发，request值可共享，地址栏不变
		// return "forward:queryItems.action";

//		return "success";
	}

	
	//批量删除，数组类型参数绑定，形参名必须与input的name的值一致
	@RequestMapping("/deleteItems")
	public String deleteItems(Integer[] items_id)throws Exception{
		//批量删除
		//...
		//
//		itemsService.deleteItems(items_id);//由于外键约束删除不成功，暂时搁置
		System.out.println(items_id.length);
		return "items/itemsList";
		
	}
}
