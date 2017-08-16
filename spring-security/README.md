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
需配合Apache DS LDAP实例一起使用

##AuthenticationProvider
你可以定义一个鉴权消费者为`AuthenticationProvider` Bean，专门用于处理鉴权。`SpringAuthenticationProvider`就是AuthenticationProvider的一个实现. 