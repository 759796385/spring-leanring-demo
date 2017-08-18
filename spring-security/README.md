#鉴权配置

##内存型鉴权
`WebSecurityConfigurerAdapter`中重写授权方法
```java
@Bean
public UserDetailsService userDetailsService() throws Exception {
	InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
	manager.createUser(User.withUsername("user").password("password").roles("USER").build());
	manager.createUser(User.withUsername("admin").password("password").roles("USER","ADMIN").build());
	return manager;
}
```
##JDBC鉴权
这里必须先注册数据源,使用
```$xslt
@Autowired
private DataSource dataSource;

@Autowired
public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
	auth
		.jdbcAuthentication()
			.dataSource(dataSource)
			.withDefaultSchema()
			.withUser("user").password("password").roles("USER").and()
			.withUser("admin").password("password").roles("USER", "ADMIN");
}
```

##LDAP鉴权
需配合Apache DS LDAP实例一起使用,LDAP可以近似看成一个用于鉴权的“数据库”

##AuthenticationProvider
你可以定义一个鉴权消费者为`AuthenticationProvider` Bean，专门用于处理鉴权。自定义AuthenticationProvider的接口实现. 
> 只使用在`AuthenticationManagerBuilder `没有注入的情况下

###UserDetailsService
你可以定义一个鉴权消费者为`UserDetailsService` Bean.用于处理鉴权.自定义UserDetailsService接口实现.

#多重Http安全
我们可以配置多个Http安全实例,关键点就是继承`WebSecurityConfigurationAdapter `多次。



#使用方法

oauth2根据使用场景不同，分成了4种模式

- 授权码模式（authorization code）
- 简化模式（implicit）
- 密码模式（resource owner password credentials）
- 客户端模式（client credentials）

获取token 
Password模式 Mactoken：`http://localhost/oauth/token?username=user_1&password=123456&grant_type=password&scope=select&client_id=client_2&client_secret=123456`
返回
```
{
"access_token": "05cb45a6-5dc0-4e07-ac79-ea4d53e42e06",
"token_type": "bearer",
"refresh_token": "41926fae-f428-4de9-bdd4-667854b34e4b",
"expires_in": 43199,
"scope": "select"
}
```
client模式  bearerToken：`http://localhost/oauth/token?grant_type=client_credentials&scope=select&client_id=client_1&client_secret=123456`
```
{
"access_token": "bf4a6b1d-624c-4702-bd88-50d818d54181",
"token_type": "bearer",
"expires_in": 43199,
"scope": "select"
}
```

获取token后可携带accessToken访问受保护的API
`http://localhost/order/1?access_token=bf4a6b1d-624c-4702-bd88-50d818d54181`