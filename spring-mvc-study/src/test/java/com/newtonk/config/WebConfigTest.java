package com.newtonk.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by newtonk on 2017/6/20.
 */
@Configuration
@EnableWebMvc
@ComponentScan(basePackages = {"com.newtonk"})
public class WebConfigTest extends WebMvcConfigurerAdapter {
}
