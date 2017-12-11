package newtonk.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * 类名称：
 * 类描述：
 * 创建人：tq
 * 创建日期：2017/6/30 0030
 */
@RestController
public class ProductController {

    @GetMapping("/date")
    public Date serviceDate(){
        return new Date();
    }
}
