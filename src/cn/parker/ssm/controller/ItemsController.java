package cn.parker.ssm.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.omg.CORBA.PRIVATE_MEMBER;
import org.omg.CORBA.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.HttpRequestHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import cn.parker.ssm.controller.validtorGroup.ValidGroup1;
import cn.parker.ssm.exception.CustomException;
import cn.parker.ssm.po.Items;
import cn.parker.ssm.po.ItemsCustom;
import cn.parker.ssm.po.ItemsQueryVo;
import cn.parker.ssm.service.ItemsService;
import cn.parker.ssm.service.impl.ItemsServiceImpl;
import javassist.expr.NewArray;

@Controller
// 为了对url进行分类管理，可以在这定义根路径，最终访问url为根路径+子路径，如：查询商品列表路径为/items/qureyItems.action
@RequestMapping("/items")
public class ItemsController {
	@Autowired
	private ItemsService itemsService;
	
	@ModelAttribute("itemsTypes")
	public Map<String, String> getItemsType(){
		Map<String, String> itemsTypes = new HashMap<String, String>();
		itemsTypes.put("101", "母婴");
		itemsTypes.put("102", "教育");
		return itemsTypes;

	}

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
	
	//查询商品信息，输出json(RESTful的支持，需要在web.xml中配置rest的前端控制器)
	//itemsView/{id}中的{id}表示占位符，表示将这个位置的参数传递到@PathVariable("id")指定的id参数中
	//如果占位符中的参数名称与形参名称一致，可以不通过@PathVariable指定
	@RequestMapping("itemsView/{id}")
	public @ResponseBody ItemsCustom itemsView(@PathVariable("id") Integer id) throws Exception{
		
		//调用service查询商品信息
		ItemsCustom itemsCustom = itemsService.findItemsById(id);
		
		return itemsCustom;
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
	public String editItems(Model model,@RequestParam(value = "id", required = true, defaultValue = "1") Integer items_id) throws Exception {

		ItemsCustom itemsCustom = itemsService.findItemsById(items_id);
		
		//在controller中进行异常测试
//		if(itemsCustom == null){
//			throw new CustomException("所需要修改的商品不存在！");
//		}

		// 相当于modelAndView.addObject方法
		model.addAttribute("items", itemsCustom);

		return "items/editItems";
	}

	
	
	/*
	 * pojo类型的参数绑定，绑定成功的前提是，页面中input的name的值与pojo中的属性值一致 ，如
	 * input name="id"，则ItemsCustom必须有id属性
	 *  Integer id为简单类型的参数绑定， ItemsCustom itemsCustom为pojo类型参数绑定
	 * 形参中pojo对象的属性中有日期类型createtime，需要自定义参数绑定
	 */
	//在需要校验的pojo前边添加@Validated，在需要校验的pojo后边添加BindingResult bindingResult接收校验出错信息
	//注意：@Validated和BindingResult bindingResult是配对出现，并且形参顺序是固定的（一前一后）。
	//@Validated(value={ValidGroup1.class})指定使用ValidGroup1校验规则，即仅校验商品名长度
	//	pojo数据传入controller方法后，springmvc自动将pojo数据放到request域（此时默认实现数据回显），key等于pojo类型（首字母小写）
	//	若页面中显示的数据使用的不是pojo的类型，如value="${items.name }，使用@ModelAttribute指定pojo回显到页面在request中的key
	//  @ModelAttribute还可以将方法返回值传到页面（本类中第一个方法）
	@RequestMapping("/editItemsSubmit")
	public String editItemsSubmit(HttpServletRequest request,Model model, Integer id,
			@ModelAttribute("items") @Validated(value={ValidGroup1.class}) ItemsCustom itemsCustom,BindingResult bindingResult,
			MultipartFile items_pic//接收商品的图片
			) throws Exception {

		//判断是否有错误信息
		if(bindingResult.hasErrors()){
			  //获取所有错误信息
			  List<ObjectError> allErrors = bindingResult.getAllErrors();
			  //输出错误信息
			  for(ObjectError objectError : allErrors){
				  System.out.println(objectError.getDefaultMessage());
			  }
			  //将错误信息传到页面
			  model.addAttribute("allErrors", allErrors);
			  
			  //直接使用model将pojo回显到页面
			  model.addAttribute("items", itemsCustom);
			  //有错误信息则重新返回到此页
			  return "items/editItems";
		}
		
		//上传图片的原始名称
		String originalFilename = items_pic.getOriginalFilename();
		//上传图片
		if(items_pic!=null && originalFilename!=null && originalFilename.length()>0){
			
			//存储图片的物理路径
			String pic_path = "F:\\develop\\upload\\temp\\";
			
			//新的图片名称（uuid随机数+原始名称后缀名）
			String newfileName = UUID.randomUUID() + originalFilename.substring(originalFilename.lastIndexOf("."));
			//新的图片
			File newFile = new File(pic_path+newfileName);
			//将内存中的数据写入磁盘
			items_pic.transferTo(newFile);
			//将新的图片名称写到itemsCustom中
			itemsCustom.setPic(newfileName);
		}
		
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
	
	
	
	//批量修改页面
	@RequestMapping("/editItemsList")
	public ModelAndView editItemsList(HttpServletRequest request,ItemsQueryVo itemsQueryVo) throws Exception {

		// 调用service来查找数据库，查询商品列表
		List<ItemsCustom> itemsList = itemsService.findItemList(itemsQueryVo);
		
		ModelAndView modelAndView = new ModelAndView();
		
		modelAndView.addObject("itemsList", itemsList);
		
		modelAndView.setViewName("items/editItemsList");

		return modelAndView;
	}

	//批量商品信息修改
	@RequestMapping("/editItemsAllSubmit")
	//通过itemsQueryVo接收批量修改提交的商品信息，存储在itemsQueryVo的edititemsList属性中
	//使用List接收页面提交的批量数据，通过包装pojo接收，在包装polo中定义list<pojo>属性
	public String editItemsAllSubmit(ItemsQueryVo itemsQueryVo) throws Exception{
		for(int i=0;i<itemsQueryVo.getEdititemsList().size();i++){
			ItemsCustom itemsCustom = itemsQueryVo.getEdititemsList().get(i);
			Integer id = itemsCustom.getId();
			itemsService.updateItems(id, itemsCustom);
		}
		
		
		return "success";
	}
}
