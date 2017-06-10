//package source;
//
//import org.springframework.beans.BeansException;
//import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
//import org.springframework.core.io.DefaultResourceLoader;
//
//public class AbstractApplicationContext extends DefaultResourceLoader {
//	public void refresh() throws BeansException, IllegalStateException {
//		synchronized (this.startupShutdownMonitor) {
//			// 子类启动refreshBeanFactory（）的地方
//			prepareRefresh();
//			ConfigurableListableBeanFactory beanFactory = obtainFreshBeanFactory();
//			prepareBeanFactory(beanFactory);
//			try {
//				// 设置beanFactoy的后置处理
//				pstProcessBeanFactory(beanFactory);
//				// 调用BeanFactory的后处理器，这些后处理器是在bean中定义的。
//				invokeBeanFactoryPostProcessors(beanFactory);
//				// 注册bean的后助力器，在bean创建中调用
//				registerBeanPostProcessors(beanFactory);
//				// 对上下文中的消息源进行初始化
//				initMessageSource();
//				// 初始化上下文中的事件机制。
//				initApplicationEventMulticaster();
//				// 初始化其他特殊bean
//				onRefresh();
//				// 检查坚挺bean并且将这些bean向容器注册
//				registerListeners();
//				// 实例化所有notlazyinit单件
//				finishBeanFactoryInitialization(beanFactory);
//				// 发布容器事件，结束refresh过程
//				finishRefresh();
//			} catch (BeansException ex) {
//				// 防止bean资源占用，在异常处理中，销毁已生成的bean
//				destroyBeans();
//				// 重置active标志
//				cancelRefresh(ex);
//				throw ex;
//			}
//		}
//	}
//
//	protected void prepareBeanFactory(
//			ConfigurableListableBeanFactory beanFactory) {
//		beanFactory.setBeanClassLoader(getClassLoader());
//		beanFactory.setBeanExpressionResolver(arg0);
//	}
//}
