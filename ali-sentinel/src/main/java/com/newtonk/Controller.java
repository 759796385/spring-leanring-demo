package com.newtonk;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 类名称：
 * 类描述：
 * @author：qiang.tang
 * 创建日期：2019/7/25
 */
@RestController
@RequestMapping("/")
public class Controller {
	@Resource
	WorkResource workResource;

	@GetMapping("/")
	public String test(){
		for (int i = 0; i <30 ; i++) {

			workResource.helloWorld();
		}
		System.out.println("return --------------");
		return "String";
	}
}
