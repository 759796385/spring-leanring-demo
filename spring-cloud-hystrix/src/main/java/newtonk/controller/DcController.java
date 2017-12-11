package newtonk.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * 类名称：
 * 类描述：
 * 创建人：tq
 * 创建日期：2017/6/30 0030
 */
@RestController
public class DcController {

    /**
     * rest调用模板
     */
    @Autowired
    private RestTemplate restTemplate;



    /**
     * 设置回退方法
     */
    @HystrixCommand(fallbackMethod = "defaultMethod",commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds",value = "5000"),
            @HystrixProperty(name = "metrics.rollingStats.timeInMilliseconds",value = "10000"),
    },threadPoolProperties = {
            @HystrixProperty(name = "coreSize",value = "1"),
            @HystrixProperty(name = "maxQueueSize",value = "10")
    })
    @GetMapping("/consumer")
    public String cusumer(){
        return restTemplate.getForObject("http://eureka-product/dc", String.class);
    }

    public String defaultMethod(){
        System.out.println("熔断器调用默认方法");
        return "默认方法";
    }
}
