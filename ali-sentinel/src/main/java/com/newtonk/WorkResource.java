package com.newtonk;

import java.util.Date;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;

import org.springframework.stereotype.Component;

/**
 * 类名称：
 * 类描述：
 * @author：qiang.tang
 * 创建日期：2019/7/25
 */
@Component
public class WorkResource {

	/**
	 * 注解说明：
	 * blockHandler 超量时的降级方法，方法签名必须和保护方法一致，最后参数得加个BlockException，必须是public，默认同个类下。
	 * 				如果要放在其他地方，配合blockHandlerClass使用
	 * blockHandlerClass: 降级方法所在的类，方法必须是static
	 *
	 * fallback：抛出异常时的处理方法，方法签名必须和保护方法一致，可多个Throwable接受对应异常，默认同个类下。
	 * 				如果要放其它地方，配合fallbackClass使用
	 */
	@SentinelResource(value = "HelloWorld",blockHandler = "fastFail")
	public void helloWorld() {
		// 资源中的逻辑
		System.out.println(new Date());
	}

	public void fastFail(BlockException ex){
		System.out.println("快速失败");
	}
}
