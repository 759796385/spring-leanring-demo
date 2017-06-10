package com.newtonk.externalizedConfig;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * Created by newtonk on 2017/5/29.
 */
@Component
@Setter
@Getter
public class ValueTest {
    //获取配置源中app.name中属性。 等同于com.newtonk.environment.PropertySource中配置
    @Value("${app.name}")
    private String name;
}
