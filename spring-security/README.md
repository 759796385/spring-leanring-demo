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


# 架构和实现
## 核心组件
### SecurityContextHolder，SecurityContext ，Authentication 
SecurityContextHolder是最基本的对象，这个对象存储了当前应用程序上下文的详细信息，主要是包含的主体(principal)的信息。
默认情况下SecurityContextHolder使用ThreadLocal存储信息，保证信息是线程安全的。当然你也可以配置，在启动时指定一个策略去存储上下文。
-  `SecurityContextHolder.MODE_GLOBAL`
- `SecurityContextHolder.MODE_INHERITABLETHREADLOCAL`
- `SecurityContextHolder.MODE_THREADLOCAL`默认值

spring security使用`Authentication`鉴权对象来代表用户身份。
获取用户信息
```
Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

if (principal instanceof UserDetails) {
String username = ((UserDetails)principal).getUsername();
} else {
String username = principal.toString();
}
```

### UserDetailsService -- 加载鉴权用户信息
从上面的实例代码中，你可以从`Authentication `对象中获取`principal `.

SecurityContext 是线程安全的（默认情况，可根据需求修改）。Spring Security中的大多数身份验证机制都将`UserDetails`的一个实例作为主体(`principal`)返回

大多情况`principal`都可以转换为`UserDetails` 对象，`UserDetails` 是一个核心接口。在某些扩展情况下他代表着`principal`

`UserDetailsService`只有一个方法。
```
UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
```
**这是在Spring Security中为用户加载信息最常用的方法，您将看到在需要用户信息时，它将在整个框架中使用（用来加载用户信息）。**
成功鉴权后，`UserDetails` 用来构建`Authentication` 对象，并存储在`SecurityContextHolder`之中。
我们提供了一些`UserDetailsService`的实现。有内存映射map(`InMemoryDaoImpl`),JDBC(`JdbcDaoImpl`).一般情况下都是用户自己实现。实现通常是在现有的数据访问对象（DAO）的封装，代表员工，客户或应用程序的其他用户.

 请记住，无论您的UserDetailsService返回什么，都可以从security上下文中获得。
> 他不负责用户身份鉴权,身份鉴权由`AuthenticationManager`负责.如果你想自定义身份鉴权,直接实现`AuthenticationProvider`.

### GrantedAuthority 授权
`Authentication `除了提供`principal`对象,还有`getAuthorities()`方法.这个方法返回`GrantedAuthority `对象数组,一个`GrantedAuthority`对应授权给一个 `principal`.通常这个授权指的就是角色,就像`ROLE_ADMIN`.

这些角色用来配置给web授权、方法授权和域对象授权，Spring security其他部分的鉴权就需要这个角色。
**`GrantedAuthority`对象也是`UserDetailsService`加载的**

`GrantedAuthority`对象就是一个全应用类的权限，它不是特定于给定域对象的。 可以理解为角色就是权限，不必再细分，否则会导致浪费太多内存。

### 总结
- `SecurityContextHolder`：提供获取`SecurityContext`的地方。
- `SecurityContext`：持有鉴权对象和特定于请求的安全信息
- `Authentication`：代表用户信息在spring security的管理下。
- `GrantedAuthority`：用来表示授予委托人的应用程序范围的权限。
- `UserDetails`:提供必要的信息，以从应用程序的DAO或其他安全数据源构建一个Authentication对象。
- `UserDetailsService`:生成UserDetails。当通过以字符串形式的用户名验证。

## Authentication 鉴权
鉴权过程
1. 使用账号和密码组合的方式去获取`UsernamePasswordAuthenticationToken `的实例。(实际上是获取`Authentication`接口的实例)
2. 将这个令牌传递给`AuthenticationManager`实例去验证。
3. 当验证成功，`AuthenticationManager`返回一个填充好的`Authentication`对象。
4. `SecurityContextHolder.getContext().setAuthentication(…​)`调用这个方法建立安全上下文，传递返回的身份验证对象。 

简易的过程代码表示如下：
```
public class AuthenticationExample {
private static AuthenticationManager am = new SampleAuthenticationManager();

public static void main(String[] args) throws Exception {
	BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

	while(true) {
	System.out.println("Please enter your username:");
	String name = in.readLine();
	System.out.println("Please enter your password:");
	String password = in.readLine();
	try {
		Authentication request = new UsernamePasswordAuthenticationToken(name, password);
		Authentication result = am.authenticate(request);
		SecurityContextHolder.getContext().setAuthentication(result);
		break;
	} catch(AuthenticationException e) {
		System.out.println("Authentication failed: " + e.getMessage());
	}
	}
	System.out.println("Successfully authenticated. Security context contains: " +
			SecurityContextHolder.getContext().getAuthentication());
}
}

class SampleAuthenticationManager implements AuthenticationManager {
static final List<GrantedAuthority> AUTHORITIES = new ArrayList<GrantedAuthority>();

static {
	AUTHORITIES.add(new SimpleGrantedAuthority("ROLE_USER"));
}

public Authentication authenticate(Authentication auth) throws AuthenticationException {
	if (auth.getName().equals(auth.getCredentials())) {
	return new UsernamePasswordAuthenticationToken(auth.getName(),
		auth.getCredentials(), AUTHORITIES);
	}
	throw new BadCredentialsException("Bad Credentials");
}
}
```

### 设置 SucurityContextHolder 内容
spring security并不管你如何把Authentication 放到SecurityContextHolder中。唯一的要求是SecurityContextHolder包含一个Authentication ，他在`AbstractSecurityInterceptor `需要授权用户操作之前代表当事人。

你可以基于spring security中写一个过滤器或者MVC控制器提供拦截功能来达到鉴权。

## web程序中的鉴权
一个标准的鉴权流程
1. 你访问主页，并点击链接。
2. 请求进入服务器，服务器决定是否返回您请求的受保护的资源
3. 由于您目前未通过身份验证，服务器会发回一个响应，指出您必须进行身份验证。响应将是HTTP响应代码或重定向到特定网页。
4. 根据认证机制，您的浏览器将重定向到特定的web页面，以便您可以填写表单，或者浏览器将以某种方式检索您的身份（通过一个基本的身份验证对话框，一个cookie，一个X.509凭证等。）
5. 浏览器将返回对服务器的响应。这将是一个包含您填写的表单内容的HTTP POST，或者包含您的身份验证细节的HTTP头。
6. 接下来，服务器将决定所显示的凭证是否有效。如果它们有效，下一步就会发生。如果他们是无效的，通常你的浏览器会被要求再试一次(所以你回到上面的第2步)。
7. 您所做的导致身份验证过程的原始请求将被重试。希望您已经通过认证获得了足够的权限来访问受保护的资源。如果您有足够的访问权限，请求将会成功。否则，您将收到一个HTTP错误代码403，这意味着“禁止”。

spring security有不同的类去负责上面的各个步骤。主要的参与者是`ExceptionTranslationFilter`,`AuthenticationEntryPoint `,还有一个鉴权机制（通过调用`AuthenticationManager`）.

### ExceptionTranslationFilter
他是一个Spring安全过滤器，它负责检测抛出的任何Spring安全异常。异常通常由`AbstractSecurityInterceptor`抛出，他是授权服务的主要提供者。
ExceptionTranslationFilter 提供的服务
- 如果主体已经过身份验证，因此仅缺少足够的访问权限——如上面第7步所示。 抛403异常。
- 如果委托人没有经过认证，因此我们需要开始第三步。 指向AuthenticationEntryPoint

### AuthenticationEntryPoint
他就负责上面流程的第三步。每个web应用程序都有一个默认的身份验证策略，每个主要的身份验证系统都有自己的AuthenticationEntryPoint实现。
### Authentication Mechanism 认证机制
一旦您的浏览器提交了您的鉴权认证信息（不管http表单还是http head），需要在服务器上有一些“收集”这些身份验证细节的东西。这对应的是上面的第六步。在Spring Security中，我们有一个特殊名称，用于表示从用户代理收集身份验证详细信息的功能。`Authentication Mechanism`，一旦从用户代理中收集了身份验证信息，一个请求被构建成`Authentication `对象并交给`AuthenticationManager`。

当认证机制返回一个完全填充的Authentication对象后，这代表请求是有效的，把Authentication放入`SecurityContextHolder`中。并导致重新尝试原始请求(上文第7步)。如果`AuthenticationManager`拒绝了请求，认证机制将要求用户代理重试(第2步 ）


### 存储SecurityContext 在请求中
根据应用程序的类型，可能需要一个策略来存储用户操作之间的安全上下文。在典型的web应用程序中，用户登录一次，然后通过他们的会话Id标识身份。服务缓存主体信息在会话有效时间内。在spring security中，在请求过程中存储securityContext的职责落在了`SecurityContextPersistenceFilter`上。
**默认情况下，上下文存储在HttpSession中。它将每个请求的上下文恢复到SecurityContextHolder中，当请求完成后，也要清理上下文。 安全起见，不应直接使用httpSession，应使用`SecurityContextHolder`来代替。**

许多其他类型的应用程序(例如，无状态的restFul web服务)不使用HTTP会话，并将在每个请求上重新进行身份验证。但是最重要的是`SecurityContextPersistenceFilter `任然包含在链中，保证请求完成后`SecurityContextHolder`被清理。 