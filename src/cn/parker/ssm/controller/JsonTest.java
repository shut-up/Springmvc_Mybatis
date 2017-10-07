package cn.parker.ssm.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.parker.ssm.po.ItemsCustom;

//json交互测试
@Controller
public class JsonTest {
	
	//请求json串，输出json串
	//@RequestBody将商品信息的json串转成itemsCustom对象
	//@ResponseBody将itemsCustom对象转成json串
	@RequestMapping("/requestJson")
	public @ResponseBody ItemsCustom requestJson(@RequestBody ItemsCustom itemsCustom){
		
		return itemsCustom;
	}

	// 商品修改提交，提交普通form表单数据，响应json
	@RequestMapping("/responseJson")
	public @ResponseBody ItemsCustom responseJson(ItemsCustom itemsCustom) throws Exception {

		System.out.println(itemsCustom);

		return itemsCustom;
	}
}
