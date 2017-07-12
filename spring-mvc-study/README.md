@(springmvc)
# SpringMVC
[TOC]

定义：Spring Web MVC 是一种基于 Java 的实现了 Web MVC 设计模式的请求驱动类型的轻量级 Web 框架，即使用了 MVC 架 构模式的思想，将 web 层进行职责解耦，基于请求驱动指的就是使用请求-响应模型，框架的目的就是帮助我们简化开 发，Spring Web MVC 也是要简化我们日常 Web 开发的。  

![image](https://github.com/759796385/spring-leanring-demo/raw/master/spring-mvc-study/img/1463450153179.png)

工作步骤：
1. 发送用户请求给前端控制器(ServletDispatcher),前端控制器根据URL确定哪个页面控制器进行处理(即RequestMapping)
2. 页面控制器收到请求后将请求参数绑定成一个对象,即我们对于的action参数,然后进行模型处理,返回一个ModelAndView.
3. 前端控制器收回控制权，根据模型和视图进行渲染视图，然后返回给用户

## 从代码架构上来讲
![image](https://github.com/759796385/spring-leanring-demo/raw/master/spring-mvc-study/img/1469535925133.png)
1.  首先用户发送请求——>`DispatcherServlet`，前端控制器收到请求后自己不进行处理，而是委托给其他的解析器进行处理，DispatcherServlet作为统一访问点，进行全局的流程控制.
2.   `DispatcherServlet`——>`HandlerMapping`:`HandlerMapping`把请求映射成`HandlerExecutionChain`,从名字上可看出映射成一个调用链。这个调用链包括俩：`HandlerInterceptor`，很明显这是拦截器。`Handler`就是咱们的处理器了。别急这个处理器和咱们的action还是有点区别的。
3.  `DispatcherServlet`——>`HandlerAdapter`：HandlerAdapter 将会把处理器包装为适配器，从而支持多种类型的处理器， 即适配器设计模式的应用，从而很容易支持很多类型的处理器,这就对应我们的controller类. 
4.  `HandlerAdapter`——>处理器功能处理方法的调用，HandlerAdapter 将会根据适配的结果调用真正的处理器的功能处理方法，完成功能处理；并返回一个 ModelAndView 对象（包含模型数据、逻辑视图名）； 
5.   ModelAndView 的逻辑视图名——> ViewResolver， ViewResolver 将把逻辑视图名解析为具体的 View，通过这种策略模式，很容易更换其他视图技术； 
6.   View——>渲染，View 会根据传进来的 Model 模型数据进行渲染，此处的 Model 实际是一个 Map 数据结构，因此 很容易支持其他视图技术； 
7.  返回控制权给 DispatcherServlet，由 DispatcherServlet 返回响应给用户，到此一个流程结束。 

F&Q：
前端控制器如何知道把请求分发给哪个处理器？
> 这个就是HandlerMapping的作用啦！

 如何支持多种页面控制器呢
 >配置 HandlerAdapter 从而支持多种类型的页面控制器 

### 核心Dispatcher分发器流程
核心就是`org.springframework.web.servlet.DispatcherServlet`这个控制分发器类的`doDispatch（）`方法
```
//刚开始请求的response的内容都是空的
	protected void doDispatch(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpServletRequest processedRequest = request;
		HandlerExecutionChain mappedHandler = null;
		boolean multipartRequestParsed = false;
		/* 异步请求管理*/
		WebAsyncManager asyncManager = WebAsyncUtils.getAsyncManager(request);
		try {
			ModelAndView mv = null;
			Exception dispatchException = null;
	
			try {
			    //检测当前请求是否是多文件上传请求
				processedRequest = checkMultipart(request);
				multipartRequestParsed = (processedRequest != request);
				//找到当前请求的HandlerMapping，并把请求包装成一个HandlerExecutionChain执行链。第二步
				//默认RequestMappingHandlerMapping
                mappedHandler = getHandler(processedRequest);
                //处理没有handMapping 的请求,设置状态码返回
                if (mappedHandler == null || mappedHandler.getHandler() == null) {
					noHandlerFound(processedRequest, response);
					return;
				}
				//第三部，处理器包装成适配器。变成HandlerAdapter
				HandlerAdapter ha = getHandlerAdapter(mappedHandler.getHandler());
				//处理请求head中的缓存。
				String method = request.getMethod();
				boolean isGet = "GET".equals(method);
				if (isGet || "HEAD".equals(method)) {
					long lastModified = ha.getLastModified(request, mappedHandler.getHandler());
					if (logger.isDebugEnabled()) {
						logger.debug("Last-Modified value for [" + getRequestUri(request) + "] is: " + lastModified);
					}
					if (new ServletWebRequest(request, response).checkNotModified(lastModified) && isGet) {
						return;
					}
				}
				//前置处理器
				if (!mappedHandler.applyPreHandle(processedRequest, response)) {
					return;
				}
				//第四部分：实际的方法调用，并返回一个modelView对象
				mv = ha.handle(processedRequest, response, mappedHandler.getHandler());
				//如果是异步请求，让出dispatcher线程。在后面final添加回调
				if (asyncManager.isConcurrentHandlingStarted()) {
					return;
				}
				//第五部分 视图解析
				applyDefaultViewName(processedRequest, mv);
				//后置处理器
				mappedHandler.applyPostHandle(processedRequest, response, mv);
				... 一系列的catch
		finally {
		    //用于异步请求的添加回调请求
			if (asyncManager.isConcurrentHandlingStarted()) {
				// Instead of postHandle and afterCompletion
				if (mappedHandler != null) {
					mappedHandler.applyAfterConcurrentHandlingStarted(processedRequest, response);
				}
			}
			else {
				// Clean up any resources used by a multipart request.
				if (multipartRequestParsed) {
					//处理多文件上传请求
					cleanupMultipart(processedRequest);
				}
			}
		}
```

### 启动顺序
服务器启动会调用`ServletContainerInitializer`接口的`onStartup（Set<Class<?>> webAppInitializerClasses, ServletContext servletContext）`方法
>注意：webAppInitializerClasses参数是实现了WebApplicationInitializer接口的类的集合。Spring mvc用于加载web配置。
```java
public void onStartup(Set<Class<?>> webAppInitializerClasses, ServletContext servletContext)
			throws ServletException {

		List<WebApplicationInitializer> initializers = new LinkedList<WebApplicationInitializer>();

		if (webAppInitializerClasses != null) {
			for (Class<?> waiClass : webAppInitializerClasses) {
				// 这货怕servlet容器提供无效的类，做一些过滤。
				if (!waiClass.isInterface() && !Modifier.isAbstract(waiClass.getModifiers()) &&
						WebApplicationInitializer.class.isAssignableFrom(waiClass)) {
					try {
						initializers.add((WebApplicationInitializer) waiClass.newInstance());
					}
					catch (Throwable ex) {
						throw new ServletException("Failed to instantiate WebApplicationInitializer class", ex);
					}
				}
			}
		}

		if (initializers.isEmpty()) {
			servletContext.log("No Spring WebApplicationInitializer types detected on classpath");
			return;
		}

		servletContext.log(initializers.size() + " Spring WebApplicationInitializers detected on classpath");
		//初始顺序排序
		AnnotationAwareOrderComparator.sort(initializers);
		//初始化
		for (WebApplicationInitializer initializer : initializers) {
			initializer.onStartup(servletContext);
		}
	}
```
>正常java code容器必须实现`AbstractAnnotationConfigDispatcherServletInitializer`来注册DispatcherServlet。
>有三个方法
>- getRootConfigClasses()：根容器配置
>- getServletConfigClasses() ：Dispatcher 容器配置
>- 当前Dispatcher拦截路径

所以这里会调用`AbstractDispatcherServletInitializer`类的`onStartup`方法
```
@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		super.onStartup(servletContext); //跳进去看
		registerDispatcherServlet(servletContext);
	}
从这看父类的onStartup()方法
```
`AbstractContextLoaderInitializer`的`onStartup()`
```
protected void registerContextLoaderListener(ServletContext servletContext) {
        //这里就是获取根容器上下文.可由开发者注册根容器配置
		WebApplicationContext rootAppContext = createRootApplicationContext();
		if (rootAppContext != null) {
			//给容器添加Resource加载器。默认是classPath加载
			ContextLoaderListener listener = new ContextLoaderListener(rootAppContext);
			//给根容器配置初始器，这里实际上没操作		
		listener.setContextInitializers(getRootApplicationContextInitializers());
			servletContext.addListener(listener);
		}
		else {
			logger.debug("No ContextLo。。。");
		}
	}

protected WebApplicationContext createRootApplicationContext() {
       //由用户自己设置根容器配置
		Class<?>[] configClasses = getRootConfigClasses();
		if (!ObjectUtils.isEmpty(configClasses)) {
			//根容器是一个AnnotationConfigWebApplicationContext
			AnnotationConfigWebApplicationContext rootAppContext = new AnnotationConfigWebApplicationContext();
			//给根容器关联用户配置文件。注意这里还没初始化配置
			rootAppContext.register(configClasses);
			return rootAppContext;
		}
		else {
			return null;
		}
	}
```
然后再看`registerDispatcherServlet(servletContext);`方法，这是Dispatcher容器
```
为什么叫Dispatcher? 因为有
public static final String DEFAULT_SERVLET_NAME = "dispatcher";
//DIspatcher的名字中还有映射路径，可以存在多个Dispatcher的
protected void registerDispatcherServlet(ServletContext servletContext) {
		String servletName = getServletName(); //dispatcher
		Assert.hasLength(servletName, "getServletName() must not return empty or null");
		//创建Dispatcher 容器，由AnnotationConfigWebApplicationContext实现。用户可给容器添加配置文件
		WebApplicationContext servletAppContext = createServletApplicationContext();
		Assert.notNull(servletAppContext,
				"createServletApplicationContext() did not return an application " +
				"context for servlet [" + servletName + "]");
		//将Dispatcher 容器放到上下文引用中。初始FrameworkServlet类
		FrameworkServlet dispatcherServlet = createDispatcherServlet(servletAppContext);
		//容器初始加载器，没啥dispatcherServlet.setContextInitializers(getServletApplicationContextInitializers());
		//这里将servlet关联到 Dispatcher 的web容器中。建立关系
		ServletRegistration.Dynamic registration = servletContext.addServlet(servletName, dispatcherServlet);
		Assert.notNull(registration,
				"Failed to register servlet with name '" + servletName + "'." +
				"Check if there is another servlet registered under the same name.");
		//配置servlet的加载时间，映射路径，异步支持.
		//注意映射路径也是用户自己实现的
		registration.setLoadOnStartup(1);
		registration.addMapping(getServletMappings());
		registration.setAsyncSupported(isAsyncSupported());
		//添加serlvet过滤器
		Filter[] filters = getServletFilters();
		if (!ObjectUtils.isEmpty(filters)) {
			for (Filter filter : filters) {
				registerServletFilter(servletContext, filter);
			}
		}

		customizeRegistration(registration);
	}
```
然后服务器做一些类加载之类的操作，执行完后会调用`HttpServletBean`类的`init()`方法。
```
@Override
public final void init() throws ServletException {
	//初始添加一些初始参数放入properties中
	PropertyValues pvs = new ServletConfigPropertyValues(getServletConfig(), this.requiredProperties);
	BeanWrapper bw = PropertyAccessorFactory.forBeanPropertyAccess(this);
	//初试化ServletContext资源加载器
	ResourceLoader resourceLoader = new ServletContextResourceLoader(getServletContext());
	bw.registerCustomEditor(Resource.class, new ResourceEditor(resourceLoader, getEnvironment()));
	initBeanWrapper(bw);
	bw.setPropertyValues(pvs, true);
	somecode...
	// 启动子容器初试化入口
	initServletBean();
}
```
`FrameworkServlet`继承`HttpServletBean`,看他
```
@Override
	protected final void initServletBean() throws ServletException {
		getServletContext().log("Initializing Spring FrameworkServlet '" + getServletName() + "'");
		if (this.logger.isInfoEnabled()) {
			this.logger.info("FrameworkServlet '" + getServletName() + "': initialization started");
		}
		long startTime = System.currentTimeMillis();

		try {
			//初试化Dispatcher的WebApplication 容器，跳进去看
			this.webApplicationContext = initWebApplicationContext();
			//子类初试化扩展点
			initFrameworkServlet();
		}
		catch (ServletException ex) {
			this.logger.error("Context initialization failed", ex);
			throw ex;
		}
		catch (RuntimeException ex) {
			this.logger.error("Context initialization failed", ex);
			throw ex;
		}

		if (this.logger.isInfoEnabled()) {
			long elapsedTime = System.currentTimeMillis() - startTime;
			this.logger.info("FrameworkServlet '" + getServletName() + "': initialization completed in " +
					elapsedTime + " ms");
		}
	}
	至此 ，整个Dispatcher容器启动成功。Spring mvc完成啦
```
看看Dispatcher容器是如何初试化的
```
protected WebApplicationContext initWebApplicationContext() {
     //获取根容器，Root WebApplicationContext。 实际上就是ServletContext
		WebApplicationContext rootContext =
				WebApplicationContextUtils.getWebApplicationContext(getServletContext());
		WebApplicationContext wac = null;
		//在前面FrameworkServlet初始时，this.webApplicationContext 就已经被设为dispatcher
		if (this.webApplicationContext != null) {
			/* 配置上下文的WebApplicationContext */
			wac = this.webApplicationContext;
			if (wac instanceof ConfigurableWebApplicationContext) {
				ConfigurableWebApplicationContext cwac = (ConfigurableWebApplicationContext) wac;
				if (!cwac.isActive()) {
					// 容器此时
					if (cwac.getParent() == null) {
						// 将根容器ServletContext设置为wac的父容器，作为全局父容器
						cwac.setParent(rootContext);
					}
					// 配置刷新Dispatcher容器,因为他是annotation容器，resource就是我们的@Configuration,正常加载顺序是一样的
					//（配置id，名字，serlvetConfig参数,Configuration）。 注意这里就是一个spring 的ioc容器的初始过程！！！
					//容器初试化后会发送容器初试化事件 Framework订阅了消息，触发onApplicationEvent(ContextRefreshedEvent event)  - > onRefresh()方法调用
					configureAndRefreshWebApplicationContext(cwac);
				}
			}
		}
		if (wac == null) {
			// 查找已经绑定的上下文
			wac = findWebApplicationContext();
		}
		if (wac == null) {
			// 如果没有找到相应的上下文 -> 创建一个本地容器
			wac = createWebApplicationContext(rootContext);
		}

		if (!this.refreshEventReceived) {
			// 上面两个添加的默认初始Dispatcher容器在此刷新，就是配置初始化的web mvc的bean
			// java config启动的容器早已刷新，不会进来
			onRefresh(wac);
		}

		if (this.publishContext) {
			// 将Dispatcher放入 servletContext中
			String attrName = getServletContextAttributeName();
			getServletContext().setAttribute(attrName, wac);
			if (this.logger.isDebugEnabled()) {
				this.logger.debug("Published WebApplicationContext of servlet '" + getServletName() +
						"' as ServletContext attribute with name [" + attrName + "]");
			}
		}

		return wac;
	}
```
DispatcherServlet继承FrameWork，重写onRefresh方法，来看看
```
protected void onRefresh(ApplicationContext context) {
		initStrategies(context);
	}
	//这里做的事可多了
protected void initStrategies(ApplicationContext context) {
		//初试化视图解析器
		initMultipartResolver(context);
		//初始Local解析器
		initLocaleResolver(context);
		initThemeResolver(context);//初始模板解析器
		initHandlerMappings(context);//初始HandlerMapping
		initHandlerAdapters(context);//初始处理器适配器
		initHandlerExceptionResolvers(context);//异常解析器
		initRequestToViewNameTranslator(context);//请求转视图解析器
		initViewResolvers(context);//实体解析器
		initFlashMapManager(context);//flash解析器
		//这些初始化都是从Context中获取对应class类型的bean。有个细节，如果是层级的BeanFactory，会从parant中获取bean
	}
```
至此整个Dispacher IOC容器加载完成，回到之前`initWebApplicationContext()`方法.

### 总结
通常项目里面就配置一个Dispatcher，Dispatcher的webApplication存放着相关的controller、service、组件等bean。

springmvc很多配置直接继承WebMvcConfigurerAdapter，进行配置。
这个配置怎么和Dispatcher 容器关联呢？
>别忘了 java confi是要继承`AbstractAnnotationConfigDispatcherServletInitializer`类的，得实现这个方法。
>```
>protected Class<?>[] getServletConfigClasses() {
>        //web config目录配置
>        return new Class[]{WebConfig.class};
>    }
>
>```

**所有的Dispatcher共享RootApplication上下文.因此对于有多个Dispatcher的项目，配置公共的bean得在`getRootConfigClasses()`的类上。这很重要**

>延伸：对于只有一个Dispatcher的项目（一般总是只有一个），那么rootContext没有什么存在必要。因此spring boot合二为一了。


-----------------------------------------
# spring mvc文档
## DispatcherServlet
Spring的web MVC框架和其他许多web MVC框架一样，由请求驱动，围绕一个中央Servlet设计，将请求分派给控制器，并提供其他促进Web应用程序开发的功能。Spring DispatcherServlet提供更多的功能，他集成了IOC容器， 允许你使用spring 更多特性。

DispatcherServlet是“前端控制器”设计模式的表达（这是Spring Web MVC与许多其他领先的Web框架共享的模式）

![image](https://github.com/759796385/spring-leanring-demo/raw/master/spring-mvc-study/img/1497108309932.png)


DispatcherServlet 实际上就是一个servlet(继承了HttpServlet的子类)，声明在web应用中。对于你的请求映射由DispatcherServlet 去处理，通过url映射。

`ApplicationContext`实例在spring中都有作用域，在Web MVC框架中，每一个`DispatcherServlet `都有自己的`WebApplicationContext`,继承的bean都定义在`WebApplicationContext`中.
**WebApplicationContext包含所有基础设施bean，并且能够被自己的上下文和Servlet实例共享**

通常配置一个serlvet
```
<context-param>
        <param-name>contextConfigLocation</param-name>
        <param-value>/WEB-INF/root-context.xml</param-value>
    </context-param>
    <servlet>
        <servlet-name>dispatcher</servlet-name>
        <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
        <init-param>
            <param-name>contextConfigLocation</param-name>
            <param-value></param-value>
        </init-param>
        <load-on-startup>1</load-on-startup>
    </servlet>
    <servlet-mapping>
        <servlet-name>dispatcher</servlet-name>
        <url-pattern>/*</url-pattern>
    </servlet-mapping>
    <listener>
        <listener-class>org.springframework.web.context.ContextLoaderListener</listener-class>
    </listener>
```
用java注解替代
```
public class GolfingWebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    @Override
    protected Class<?>[] getRootConfigClasses() {
        // GolfingAppConfig defines beans that would be in root-context.xml
        return new Class[] { GolfingAppConfig.class };
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        // GolfingWebConfig defines beans that would be in golfing-servlet.xml
        return new Class[] { GolfingWebConfig.class };
    }

    @Override
    protected String[] getServletMappings() {
        return new String[] { "/golfing/*" };
    }

}
```

## WebApplicationContext中特殊的bean类型

spring的`DispatcherServlet `使用特殊的bean来处理请求和视图，这些bean都是spring mvc 的一部分，你能够选择去配置他们来使用。

| bean类型     |    说明 |
| :-------- |:-------| 
| HandlerMapping | 根据某些标准将传入的请求映射到处理程序和一列预处理和后处理器(处理程序拦截器)。根据不同的HandlerMapping实现的不同细节。最流行的实现是带注释的控制器，但是其它的实现也存在。 | 
|HandlerAdapter| 帮助DispatcherServlet 去执行请求映射，而不管实际调用哪个程序，例如，调用带注释的控制器需要解析各种注释。因此，HandlerAdapter的主要目的是保护DispatcherServlet不受这些细节的影响。|
|HandlerExceptionResolver|映射视图的异常，也允许更复杂的异常处理代码。|
|ViewResolver|解决逻辑上的java路径视图名字转换为实际的View|
|LocaleResolver & LocaleContextResolver|解析本地客户端使用它们自己的视图，用来支持国际化视图|
|ThemeResolver|模板解析器，解析你应用中使用的模板，提供个性化布局|
|MultipartResolver|解析多部分请求，以支持从HTML表单处理文件上传。|
|FlashMapManager|“输入”FlashMap是存储，“输出”FlashMap是检索，可用于将属性从一个请求传递到另一个请求，通常是通过重定向。|

## 默认的DispatcherServlet 配置
DispatcherServlet维护了默认使用的实现列表。这些信息保存在DispatcherServlet.properties。包位于org.springframework.web.servlet.

所有的特殊bean都有默认值，您迟早需要自定义这些bean提供的一个或多个属性。例如，配置一个 InternalResourceViewResolver ，设置它的前缀属性到视图文件的父位置是很常见的。

自主实现的配置将会覆盖默认的配置。

##  DispatcherServlet 处理顺序
1. WebApplicationContext 是用来 搜寻 和这个请求映射的Controller或者其他能处理的元素。默认情况下绑定配置在 `DispatcherServlet.WEB_APPLICATION_CONTEXT_ATTRIBUTE`
2. 区域解析器绑定到请求，以便在处理请求时启用流程中的元素来解析本地语言环境.你可以不配置启用
3. 模板解析器绑定到请求，让视图决定使用哪个模板。如果您不使用模板，您可以忽略它。
4. 如果你需要多文件解析器,请求会检查是否是多文件请求,如果是将被包装在MultipartHttpServletRequest 中处理
5. 搜索一个适当的处理器。如果发现了一个处理器，则会执行与处理程序(预处理程序、后处理器和控制器)相关的执行链，然后以准备模型或直接返回
6. 如果是模型,以视图呈现.如果没有模型返回(也许前置处理器或后置处理器拦截了请求,也许出于安全原因),没有视图返回,整个请求OK

处理异常声明在WebApplicationContext中,他能处理请求过程中抛出的异常.

Spring `DispatcherServlet`还支持Servlet API指定的最后修改日期的返回。确定特定请求的最后一个修改日期的过程非常简单:DispatcherServlet查找一个Controller映射，并测试发现的处理程序是否实现了`LastModified`接口。如果是这样，则将`LastModified`接口的long getLastModified(request)方法的值返回给客户端。

## 控制器 实现
### @Controller定义在控制层class上
@controller注释充当注释类的原型，指示其角色。`Dispatcher`扫描这些带注释的类，用于映射方法和检测`@Requestmapping`注释

自动检测使用`@ComponentScan`来指定包

### 使用@RequestMapping请求映射
可定义在特定方法上和类上。

`@RequestMapping`在类上不是必须的，如果没有设置，所有方法上设置的访问路径都是绝对路径，而不是相对路径
#### @Controller and AOP 代理
一些控制器在运行时需要被AOP代理包装。例如你在方法上声明@Transactional。推荐使用基于类的代理，这是默认的选择，然而如果类实现了非Spring上下文回调的接口，你需要配置代理，将`<tx:annotation-driven/>`改为`<tx:annotation-driven proxy-target-class="true"/>`

#### URI模板模式 
就是`/owners/{ownerId}`这货了。

配合`@PathVariable`使用，当`@PathVariable`使用在Map中，这个map将包含所有URL模板变量。

方法级的@RequestMapping能和类级的@RequestMapping一起使用。

URL模板变量也`支持正则表达式`，` Ant样式 path`，如`/myPath/*.do`

URL模板也支持占位符属性 `${…​}`这种属性变量

springmvc执行`.*`这种后缀匹配。如`/person`将会映射到`/person.*`,这使得通过URL路径(例如/ person)请求资源的不同表示变得容易。pdf / person.xml)。

#### 媒介类型消费（限定Content-Type）
你可以某个类只处理固定的`Content-Type`
```
@PostMapping(path = "/pets", consumes = "application/json")
public void addPet(@RequestBody Pet pet, Model model) {
    // implementation omitted
}
```
也可以取反

#### 媒介类型生产
也可指定固定的返回类型
```
@GetMapping(path = "/pets/{petId}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@ResponseBody
public Pet getPet(@PathVariable String petId, Model model) {
    // implementation omitted
}
```

#### HTTP HEAD 和 HTTP OPTIONS
`@RequestMapping`映射GET方法自然能映射HEAD方法，不需要特别声明。对于HEAD请求，就是Body不会返回，Content-Length依然计算。

`@RequestMapping`也支持OPTION，

### 定义 @RequestMapping 处理方法
**方法参数类型支持表：**
- Request 或者 response对象，如 ServletRequest or HttpServletRequest.
- Session objec ，如HttpSession
- `org.springframework.web.context.request.WebRequest` or `org.springframework.web.context.request.NativeWebRequest`
- java.util.Locale
- java.util.TimeZone (Java 6+) / java.time.ZoneId
- java.io.InputStream / java.io.Reader
- java.io.OutputStream / java.io.Write
- org.springframework.http.HttpMethod
- java.security.Principal
还有很多

#### 使用 @RequestParam绑定请求参数
RequestParam默认是 required是true。
RequestParam也能用在Map中，映射结果就是所有请求参数

#### 使用@RequestBody注解来映射request 的body
可用`HttpMessageConverter`来转换request  body.

`RequestMappingHandlerAdapter `支持@requestBod注解使用以下默认的转换实现
- `ByteArrayHttpMessageConverter `转换byte数组
- `StringHttpMessageConverter `转换String
- `FormHttpMessageConverter ` 转换数据 为Map
- `SourceHttpMessageConverter `转化数据为` javax.xml.transform.Source.`

如果你打算使用XML来做信息流载体，你需要配置`MarshallingHttpMessageConverter `和`Marshaller `和`Unmarshaller`的实现.都在`org.springframework.oxm`包中

**@RequestBody方法参数能够用`@Valid`注解来进行参数校验,使用`Validator `实例来进行校验**

#### 使用@ResponseBody注解映射返回体
Spring 转换对象使用`HttpMessageConverter`

#### 用@RestController创建Rest控制器
Controller实现RestApi so easy,服务间使用JSON/XML 或者定义的媒介类型内容进行通信.
为了方便,还是用那些映射注解，你可以在controller类上注解`@RestController`类

#### 使用 HttpEntity
```
@RequestMapping("/something")
public ResponseEntity<String> handle(HttpEntity<byte[]> requestEntity) throws UnsupportedEncodingException {
    String requestHeader = requestEntity.getHeaders().getFirst("MyRequestHeader"));
    byte[] requestBody = requestEntity.getBody();

    // do something with request header and body

    HttpHeaders responseHeaders = new HttpHeaders();
    responseHeaders.set("MyResponseHeader", "MyValue");
    return new ResponseEntity<String>("Hello World", responseHeaders, HttpStatus.CREATED);
}
```
这个例子可访问请求体和定义返回体。

#### 方法上使用ModelAttribute 
被@ModelAttribute注释的方法会在此controller每个方法执行前被执行，因此对于一个controller映射多个URL的用法来说，要谨慎使用。

**被@ModelAttribute标注的方法参数如果在URL上没有表现，会报错。主要是用于在模型中填充需要的通用属性，例如用状态或某一类型的下拉菜单，或者检索某一命令对象，以便用它来表示HTML表单上的数据**

一个controller可以有多个` @ModelAttribute`修饰的方法

#### 方法参数上用ModelAttribute （主要用于表单请求）
```

@ModelAttribute("user")  
 public User addAccount() {  
    return new User("jz","123");  
 }  

@RequestMapping(value = "/helloWorld")  
 public String helloWorld(@ModelAttribute("user") User user) {  
    user.setUserName("jizhou");  
    return "helloWorld";  
 }  
```
在这个例子里，@ModelAttribute("user") User user注释方法参数，参数user的值来源于addAccount()方法中的model属性。

意思就是从model中检索对应参数，如果model不存在，该参数实例化后放入model，存在则被填充。

so，一个被注解的参数按以下顺序获取
- @SessionAttributes中的属性
-  @ModelAttribute修饰方法返回的模型
-  URI模板中的模型
-  默认构造器实现

#### 使用@SessionAttributes在Session request值班个存储模型属性 
类级别的`@ sessionattributes`注释声明程序使用特定处理的会话属性。这通常会列出模型属性的名称或模型属性的类型，这些属性存储在会话或一些会话存储中，在后续请求之间充当表单备份bean。
```
@Controller
@RequestMapping("/editPet.do")
@SessionAttributes("pet")
public class EditPetForm {
    // ...
}
```

#### 使用@SessionAttribute来访问已存在的session属性
要想移除或添加属性，注入`org.springframework.web.context.request.WebRequest`或`javax.servlet.http.HttpSession`可达到目的。

#### 使用@RequestAttribute 方法request属性

#### 处理application/x-www-form-urlencoded类型数据
浏览器可提交表单数据已GET.POST请求处理，非浏览器可通过PUT方法提交数据，这有一点不一样，因为**Servlet规范要求ServletRequest.getParameter *()家族的方法只支持HTTP POST的表单字段访问，而不是HTTP PUT。**

为了支持PUT和PATCH方法，spring web模块提供了`HttpPutFormContentFilter`过滤器。这个过滤器用来拦截转换内容为`application/x-www-form-urlencoded`的数据，从请求体中读取表单数据，并包装ServletRequest，以便通过ServletRequest.getParameter *()方法家族提供表单数据。

`org.springframework.web.filter.HttpPutFormContentFilter`


#### 使用 @CookieValue  映射cookie值
```
 @GetMapping("/cookie")
   public String displayCookie(@CookieValue("JSESSIONID") String cookie){
       return cookie;
   }
```
如果cookie不是字符串，会自动转换

#### 使用@RequestHeader映射请求头属性
```
@RequestMapping("/displayHeaderInfo.do")
public void displayHeaderInfo(@RequestHeader("Accept-Encoding") String encoding,
        @RequestHeader("Keep-Alive") long keepAlive) {
    //...
}
```
不仅仅修饰String，也能修饰`Map<String, String>,MultiValueMap<String, String>,HttpHeaders `,能包含所有头。

#### 请求方法参数数据类型转换
对于目标类型非字符串且不是基本的java类型，可通过`WebDataBinder `(常用)来处理或者注册`Formatters `
在`FormattingConversionService`

#### 配置WebDataBinder 初始化
一般使用@InitBinder注解在`@ControllerAdvice`类中,或者提供`WebBindingInitializer`配置

#### 配置WebDataBinder 
数据转换，作用于当前Controller中。必须是void方法
      WebDataBinder可注册CustomDateEditor 。也可使用PropertyEditor 实例来进行转换

```
 @InitBinder
    protected void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
    }
    
或者


    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.addCustomFormatter(new DateFormatter("yyyy-MM-dd"));
    }
```

**spring 提供了部分内置属性编辑器默认实现**

| 类名     |     说明 |   是否默认注册   |
| :-------- | :--------:| :------: |
| ByteArrayPropertyEditor|   String<——>byte[]|  true |
|ClassEditor|String<——>Class 当类没有发现抛出IllegalArgumentException|true|
|CustomBooleanEditor|String<——>Boolean <br/> true/yes/on/1转换为true，<br/> false/no/off/0转换为false|true|
|CustomCollectionEditor |数组/Collection——>Collection  <br/> 普通值——>Collection（只包含一个对象）如 String——>Collection<br/>  不允许Collection——>String（单方向转换） |true|
| CustomNumberEditor |String<——>Number(Integer、Long、Double)| true|
| FileEditor |String<——>File | true |
| InputStreamEditor |String——>InputStream  <br/> 单向的，不能InputStream——>String | true|
|LocaleEditor| String<——>Locale| true|
| PatternEditor | String<——>Pattern| true|
| PropertiesEditor |String<——>java.lang.Properties|true|
| URLEditor|String<——>URL| true|
| StringTrimmerEditor |一个用于trim 的 String类型的属性编辑器 如默认删除两边的空格，charsToDelete属性：可以设置为其他字符
emptyAsNull属性：将一个空字符串转化为null值的选项。| false|
|CustomDateEditor| String<——>java.util.Date| false|
  
 **其实就感觉Date转换器有点用**
  
#### 配置自定义的WebBindingInitializer
要绑定初始化外化数据，需要实现`WebBindingInitializer `接口，然后，通过为`AnnotationMethodHandlerAdapter`提供自定义bean配置，从而覆盖默认配置.


#### 使用 @ControllerAdvice and @RestControllerAdvice来增强控制层
` @ControllerAdvice` 是允许实现类通过类路径扫描来自动检测的组件注释。**当使用MVC名称空间或MVC Java配置时，它会自动启用。**

`@ControllerAdvice`可以包含`@ExceptionHandler,@InitBinder,@ModelAttribute `这些注解方法，这些方法将适用于所有控制器上的@ requestmapping方法。

用`@RestControllerAdvice`来处理`@ExceptionHandler`的@ResponseBody

` @ControllerAdvice`和`@RestControllerAdvice`都放在类头上

**注意，当注定目标注解时，如图下所示,Controller也是包含@RestController注解的，因为@RestController就被@Controller注解了。 即@Controller包含所有控制器类**
```
@ControllerAdvice(annotations = Controller.class)
public class AnnotationAdvice {}
```

**这个@ControllerAdvice和@RestControllerAdvice一定要被@ComponentScan扫描到啊，否则无法生效**

>一般来说这个切面增强只用来捕捉全局异常，其他两个没啥用

#### Jackson 序列化过滤视图支持
为了过滤返回的http 对象属性，我们可以用`@JsonView`来控制
>这个偏向jackson的用法了，@JsonFilter对属性过滤更好用。

在方法上声明` @JsonView(User.WithoutPasswordView.class)`,表明返回对象的属性也必须被这个注解包含，没被包含的是不会被jackson序列化的。

#### jackson 对于JSONP的跨域支持
这个需要再用，解决前端跨域问题的。

### 异步请求处理
Spring MVC 3.2引入了基于Servlet 3的异步请求处理，与往常一样也回返回。一个方法能够马上返回一个`Callable `,这个是从Spring MVC管理的线程中生成的。同时，主要的Servlet容器线程退出且被释放用，来处理其他请求。 Spring MVC使用`TaskExecutor`调用一个新线程执行这个callable，当Callable返回时，Servlet容器线程恢复这个请求并返回callable的值。
这个是由Springmvc管理的线程池来处理异步任务。

如果我们有自己的异步任务，来源消息队列啊或者定时任务， 可返回一个`DeferredResult`对象，当其他线程调用`deferredResult.setResult(data);`值时，容器线程才返回。

以下是一些Servlet 3.0异步请求特性:
- 一个`ServletRequest`通过调用request.startAsync()可以放在异步模式中。主要影响的是Serlvet和Filters ，主线程可以退出，但响应将保持开放，以允许处理稍后完成的回调。
- 当调用request.startAsync()返回`AsyncContext`能够进一步控制异步请求.例如提供`dispatch`方法,类似于从Servlet API转发，允许应用程序在Servlet容器线程上恢复请求处理.
- ServletRequest提供对当前DispatcherType的访问，该DispatcherType可用于区分处理初始请求，异步调度，转发和其他调度器类型。

使用`Callable`进行异步请求处理的事件顺序
1. 控制器返回一个Callable
2. Spring mvc开始异步任务并提交一个Callable 给TaskExecutor分发的新线程
3. servlet容器线程中的`DispatcherServlet`和所有过滤器都退出，但是返回任保留
4. Callable产生一个结果，Spring MVC将请求返回给Servlet容器以恢复处理。
5. DispatchcherServlet被再次调用，处理从Callable异步生成的结果处继续。 

DeferredResult的处理顺序也十分相似
1. 控制器将返回一个延DeferredResult，并将其保存在一些内存队列或列表中。
2. spring mvc开始异步处理
3. servlet容器线程中的`DispatcherServlet`和所有过滤器都退出，但是返回任保留
4. 应用在其他线程中设置了DeferredResult的值，Spring MVC将请求发送回Servlet容器。
5. DispatcherServlet再次被调用，恢复处理异步生成的结果。

#### 异步请求的异常处理
异步任务发生异常和正常的控制器发生异常是一样的，也有异常处理机制，Callable发生异常时Spring mvc分发给Servlet 容器，和之前一样，只是用Exception代替值了，进程恢复后也用Exception取代返回值。但当使用DeferredResult 你得选择去调用`setResult` 还是把异常实例设置到`setErrorResult`中。

#### 拦截异步请求
一个`HandlerInterceptor`也可以实现`AsyncHandlerInterceptor`,通过实现它的`afterConcurrentHandlingStarted`方法进行回调.当进行异步处理的时候,将会调用`afterConcurrentHandlingStarted`而不是postHandle和afterCompletion.

一个`HandlerInterceptor`同样可以注册`CallableProcessingInterceptor`或者一个`DeferredResultProcessingInterceptor`用于更深度的集成异步request的生命周期.例如处理一个timeout事件.
deferredResult类也提供了方法,像onTimeout(Runnable)和onCompletion(Runnable)
当使用Callable的时候,你可以通过WebAsyncTask来对它进行包装.这样也可以提供注册timeout与completion方法.
#### HTTP 流
一个Controller方法可以通过使用DeferredResult与Callable来异步的产生它的返回值.并且这个可以被用来实现长轮询技术.就是服务器可以推动事件尽快给客户端。
```
@RequestMapping("/events")
public ResponseBodyEmitter handle() {
    ResponseBodyEmitter emitter = new ResponseBodyEmitter();
    // Save the emitter somewhere..
    return emitter;
}

// In some other thread
emitter.send("Hello once");

// and again later on
emitter.send("Hello again");

// and done at some point
emitter.complete();
```
注意客户端收到数据是分块的

#### http流的服务器发送事件
感觉这货局限性很大，建议使用更高级的websocket技术。
IE浏览器并不支持Server-Sent Events。
Server-Sent Events能够来用于它们的预期使用目的,就是从server发送events到clients.在Spring MVC中可以很容易的实现.仅仅需要返回一个SseEmitter类型的值.


### 配置异步请求
#### servlet 容器配置
考虑继承`AbstractDispatcherServletInitializer`或者`AbstractAnnotationConfigDispatcherServletInitializer`。它们会自动设置这些选项,使它很容易注册过滤器实例。

#### Spring MVC配置 
继承`WebMvcConfigurerAdapter`,重写`configureAsyncSupport`

```
    @Override
    public void configureAsyncSupport(AsyncSupportConfigurer configurer) {
        super.configureAsyncSupport(configurer);
        configurer.setDefaultTimeout(12000);//设置异步请求超时时间 ,超时后报AsyncRequestTimeoutException
        //配置异步请求拦截器地方
//        configurer.registerCallableInterceptors();
//        configurer.registerDeferredResultInterceptors()
        //建议配置线程池 AsyncTaskExecutor ， 默认实现是SimpleAsyncTaskExecutor.
        //可由用户使用同一的线程池管理
        configurer.setTaskExecutor(new ConcurrentTaskExecutor());
    }
```

## 处理映射
所有的HandleMapping 类都继承于`AbstractHandlerMapping`，在`AbstractHandlerMapping`类中你可以自定义以下的属性:
- `interceptors`.定义拦截器列表.
- defaultHandler.定义默认的handler,当DispatcherServlet找不到默认的handler就会使用这个默认的handler.
- order.基于顺序属性的值(具体可以看org.springframework.core.Ordered).Spring会排序找到的所有handler mappings.并将它们排序,并应用第一个匹配的处理程序。
- `alwaysUseFullPath`.**当这个值为true时,Spring在当前Servlet容器中会使用绝对路径会查找合适的handler.如果是false(默认值),path会使用当前Servlet的相对路径。**例如,如果一个Servlet使用/testing/*来映射并且alwaysUseFullPath的属性设置为true.会查找到/testing/viewPage.html,反之如果这个属性设置为false的话,/viewPage.html这个路径将会匹配到.
- urlDecode.默认是true.在Spring2.5中,如果你喜欢比较编码路径,设置这个值为false.但是,HttpServletRequest总是以解码形式在form中暴露Servlet路径.请注意,Servlet路径不会匹配编码后的路径。 

### 使用HandlerInterceptor拦截request
应用一些特殊的功能的时候,拦截器非常有效.例如,身份验证.

在handler mapping中的拦截器必须实现`org.springframework.web.servlet`包下面的HandlerInterceptor.这个接口定义了3个方法: 
- `preHandle(..)`:当真实的handler被执行之前会调用此方法.**方法返回一个boolean值.你可以使用这个方法来打断或者继续处理的执行链.当这个方法返回true的时候,这个handler的执行链将会继续执行;当这个这个方法返回值为false时,DispatcherServlet会假设拦截器已经处理了request(例如:渲染一个合适的页面).并且不会继续执行其它的拦截器和执行链当中真实的handler.**
- `postHandler(..)`:当handler被执行之后会调用此方法. 
- `afterCompletion(..)`:当完整的request完全之后会调用此方法. 

拦截器可以使用interceptors属性来配置,它会出现在所有继承于`AbstractHandlerMapping`的`HandlerMapping`的类中
```
<beans>
    <bean id="handlerMapping"
            class="org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping">
        <property name="interceptors">
            <list>
                <ref bean="officeHoursInterceptor"/>
            </list>
        </property>
    </bean>

    <bean id="officeHoursInterceptor"
            class="samples.TimeBasedAccessInterceptor">
        <property name="openingTime" value="9"/>
        <property name="closingTime" value="18"/>
    </bean>
</beans>
```
Ps:感觉我使用的都是WebMvcConfigurerAdapter类中重写`addInterceptors`方法。

>当使用`RequestMappingHandlerMapping`时实际上用的都是`HandlerMethod `实例,这个HandlerMethod的实例会映射到特殊的Controller方法去调用handler方法.

**注意,这里的拦截器会适用于所有Controller,如果你想缩窄匹配路径,需另外配置**

**HandlerInterceptor 的postHandler方法不能一直适用于@ResponseBody和ResponseEntity方法.在这种情况下,HttpMessageConverter将会在postHandle被执行之前就会写入response并提交.这样就可能改变response,例如添加一个header.你可以通过实现ResponseBodyAdvice或者声明它为一个@ControllerAdvice的bean或者直接在RequestMappingHandlerAdapter去配置它。**

## Handling exceptions
### HandlerExceptionResolver
spring中的`HandlerExceptionResolver`会处理发生在Controller中意想不到的Exceptions。`HandlerExceptionResolver`有点像exception的映射,你能够定义它到web应用描述文件web.xml中。但是,它们提供了一个更加灵活的方法。例如当异常被thrown的时候,它们提供哪个handler正在被执行的信息。而且,当request转发到另外一个URL之前,编程式处理异常会给你更多的选择去处理响应

实现`HandlerExceptionResolver`接口,它只有一个`resolveException(Exception, Handler)`方法需要被实现并且返回一个`ModelAndView`对象。你同样可以使用Spring默认提供的`SimpleMappingExceptionResolver`或者创建一个`@ExceptionHandler`注解的方法。`SimpleMappingExceptionResolver`使你能够获取到class上面可能会throw到的一些异常的名称,然后把它映射到一个页面的名称上面去。这个功能与Servlet API的异常映射功能相等。但是它可以实现来自不同handler的更细粒度的异常映射。
@ExceptionHandler注解可以用来处理被调用方法出现的exception.这些方法可以定义在一个@Controller类中局部方法上或者通过在类上注解@ControllerAdvice可以应用到其它所有的@Controller类中。

### @ExceptionHandler
```

    @ExceptionHandler(IOException.class)
    public ResponseEntity<String> handleIOException(IOException ex) {
        // prepare responseEntity
        return responseEntity;
    }
```
声明在controller中，就处理这个Controller中的异常，建议放在ControllerAdvice中用作全局异常捕捉。

### Handling Standard Spring MVC Exceptions
当处理一个request的时候,Spring MVC可以处理很多异常。如果需要的话,`SimpleMappingExceptionResolver`能够容易的映射异常到默认的错误页面。

`DefaultHandlerExceptionResolver`默认转换了很多异常,有需要可在Spring官网查看，这里就不列了。

## Http缓存支持
好的HTTP缓存策略可以极大地提高web应用程序的性能和客户的体验。“`Cache-Control`”HTTP响应头主要负责此，以及“last - modified”和“ETag”等条件标头。
HTTP响应头'Cache-Control'建议私有的cache(e.g. 浏览器),公共的cache(e.g. 代理)它们可以缓存HTTP响应来再次使用。

`ETag`(实体标签)是一个HTTP/1.1允许返回HTTP响应头，web服务可以用它来决定改变一个给定的URL中的内容。它可以被认为是响应头Last-Modified的更加复杂的继承者。当一个服务返回一个Etag头的时候,在If-None-Match头中随后的GETs中客户端可以使用它的header。如果内容并没有改变,服务器会返回304: Not Modified.

注:`If-Modified-Since`,和`Last-Modified`一样都是用于记录页面最后修改时间的HTTP 头信息，只是**Last-Modified是由服务器往客户端发送的HTTP头**，而**If-Modified-Since 则是由客户端往服务器发送的头**.

### Cache-Control HTTP header
Spring Web MVC应用中支持配置”Cache-Conrol”头的多种用法
Spring Web MVC使用约定的几个APIs : setCachePeriod(int seconds)
- -1  将会生成一个'Cache-Control'响应头. 
- 0  使用 'Cache-Control: no-store'指令禁止缓存. 
- n > 0  将会使用'Cache-Control: max-age=n'缓存给定的reponse它n秒.

三种设置cache的方式
```
CacheControl ccCacheOneHour = CacheControl.maxAge(1, TimeUnit.HOURS);

// Prevent caching - "Cache-Control: no-store"
CacheControl ccNoStore = CacheControl.noStore();

// Cache for ten days in public and private caches,
// public caches should not transform the response
// "Cache-Control: max-age=864000, public, no-transform"
CacheControl ccCustom = CacheControl.maxAge(10, TimeUnit.DAYS).noTransform().cachePublic();
```

#### 给静态资源设置Cache
```
  @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**")
                .addResourceLocations("/public-resources/")
                .setCacheControl(CacheControl.maxAge(1, TimeUnit.HOURS).cachePublic());
    }
```
直接在这配置即可。


#### Controller支持Cache-Controller,ETag和Last Modified
控制器支持`Cache-control`,`Etag`和`If-modified-Since`的http head请求. 推荐使用Cache-Control放入返回体的head头中.当请求头放入Etag值就会涉及到最后修改的值计算,和请求头中的'If-Modified-Since'进行比较.就可能返回304状态码,这些都是http 缓存的知识.


## 基于java代码的容器初试化
在Servlet3.0的环境中,您可以选择以编程方式配置Servlet容器作为替代方法或与web.xml文件组合使用。

一个注册DispatcherServlet的例子
```
import org.springframework.web.WebApplicationInitializer;

public class MyWebApplicationInitializer implements WebApplicationInitializer {

    @Override
    public void onStartup(ServletContext container) {
        XmlWebApplicationContext appContext = new XmlWebApplicationContext();
        appContext.setConfigLocation("/WEB-INF/spring/dispatcher-config.xml");

        ServletRegistration.Dynamic registration = container.addServlet("dispatcher", new DispatcherServlet(appContext));
        registration.setLoadOnStartup(1);
        registration.addMapping("/");
    }

}
```
Spring MVC提供了一个`WebApplicationInitializer`接口，实现这个接口能保证你的配置能自动被检测到并应用于Servlet 3容器的初始化中。`AbstractDispatcherServletInitializer`实现了`WebApplicationInitializer`,直接继承他，我们配置DispatcherServlet更加容易。

**基本的javaConfig就是继承`AbstractAnnotationConfigDispatcherServletInitializer `. 如果使用基于XML的spring配置方式 就继承`AbstractDispatcherServletInitializer`**
`AbstractDispatcherServletInitializer`也提供了方法添加过滤器实例。每个过滤器被添加时，默认的名称都基于其类类型决定，并且它们会被自动地映射到DispatcherServlet下。

-----------------------------------
中文翻译
http://blog.csdn.net/column/details/13408.html

https://linesh.gitbooks.io/spring-mvc-documentation-linesh-translation/content/