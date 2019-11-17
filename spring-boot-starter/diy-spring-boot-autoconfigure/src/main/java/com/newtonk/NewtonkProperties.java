package com.newtonk;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 类名称：
 * 类描述：
 * 创建人：newtonk
 * 创建日期：2019/11/17
 */
@Data
@ConfigurationProperties(prefix = "newtonk.config")
public class NewtonkProperties {
    /**
     * 开启状态
     */
    boolean enable = false;

    /**
     * 年龄
     */
    Integer age;

}
