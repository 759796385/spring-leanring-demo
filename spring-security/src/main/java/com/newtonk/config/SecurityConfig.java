package com.newtonk.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

/**
 * 类名称：
 * 类描述：
 * 创建人：tq
 * 创建日期：2017/8/16 0016
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * 启用WebSecurityConfigurerAdapter后，注销功能自动实现。 url: /login?logout
     *
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        http
//                .authorizeRequests()
//                //确保对我们的应用程序的任何请求都要求用户进行身份验证
//                .anyRequest().authenticated()
//                .and()
//                //允许用户使用基于表单的登录进行身份验证,会默认生成一个登陆验证的页面，可自定义配置登陆地址
//                .formLogin()
////                    .loginPage("/login")
//                .and()
//                //允许用户使用HTTP Basic身份验证进行身份验证
//                .httpBasic();
        http.authorizeRequests()
                .antMatchers("/about/**").permitAll() //开放鉴权
                .antMatchers("/v1/admin").hasRole("ADMIN") //限制角色为ROLE_ADMIN的用户访问，由于使用hasRole方法，无需指定ROLE_前缀
                .antMatchers("/db/**").access("hasRole('ADMIN') and hasRole('DBA')") //使用access可以自定义权限表达式
                .antMatchers("/oauth/*").permitAll()
                .anyRequest().authenticated()//除此之外的请求都需要鉴权
        .and().formLogin().and().httpBasic(); //开放表单登录 ，默认url：/login
    }


    /**
     * 可通过继承WebSecurityConfigurerAdapter多次，配置多个Http安全实例
     * order用于表示加载顺序，不标明默认最后
     */
    @Configuration
    @Order(1) //加order注解用于指定这个先注册
    public static class ApiWebSecurityConfigurationAdapter extends WebSecurityConfigurerAdapter {
        protected void configure(HttpSecurity http) throws Exception {
            http
                    .antMatcher("/api/**")
                    .authorizeRequests()
                    .anyRequest().hasRole("ADMIN")
                    .and()
                    .httpBasic(); //这个配置只用于/API开头的URL
        }
    }

    //注销配置
//    protected void configure(HttpSecurity http) throws Exception {
//        http
//                .logout()
//                .logoutUrl("/my/logout")      //配置注销url。 必须是post方法
//                .logoutSuccessUrl("/my/index")   //注销后跳转路径
//                .logoutSuccessHandler(logoutSuccessHandler) //指定一个注销处理消费者，一但指定，logoutSuccessUrl()配置就被忽略
//                .invalidateHttpSession(true)    //注销是否使session失效。
//                .addLogoutHandler(logoutHandler)
//                .deleteCookies(cookieNamesToClear)
//                .and()
//		...
//    }

/**
 * 用户注册用户信息的类，根据实际用户访问方式，重写此方法
 * 实质：用来填充授权的安全对象，将之置入SecurityContextConfigurer
 */
    @Bean
    @Override
    protected UserDetailsService userDetailsService(){
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(User.withUsername("user_1").password("123456").authorities("USER").build());
        manager.createUser(User.withUsername("user_2").password("123456").authorities("USER").build());
        return manager;
    }

    /**
     * 重写此类也可自定义认证方式
     */
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.inMemoryAuthentication()
//                .withUser("user_1").password("123456").authorities("USER")
//                .and()
//                .withUser("user_2").password("123456").authorities("USER");
//    }




    /**
     * 用来暴露父类中的 AuthenticationManager
     */
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        AuthenticationManager authenticationManager = super.authenticationManagerBean();
        return authenticationManager;
    }

//    @Configuration
//    public static class FormLoginWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {
//
//        @Override
//        protected void configure(HttpSecurity http) throws Exception {
//            http
//                    .authorizeRequests()
//                    .anyRequest().authenticated()
//                    .and()
//                    .formLogin();
//        }
//    }

}
