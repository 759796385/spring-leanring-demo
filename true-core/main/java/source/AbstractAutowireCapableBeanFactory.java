//package source;
//
//import org.springframework.beans.BeanWrapper;
//import org.springframework.beans.BeanWrapperImpl;
//import org.springframework.beans.factory.support.RootBeanDefinition;
//
//import java.lang.reflect.Constructor;
//
//public class AbstractAutowireCapableBeanFactory {
//	protected Object createBean(final String beanName,
//								final RootBeanDefinition mbd, final Object[] args) {
//		// somecode
//
//		// 判断bean是否可以实例化，这个类是否可以通过类装载器来载入
//		resolveBeanClass(mbd, beanName);
//		mbd.prepareMethodOverrides();
//		// 如果bean配置了bean后处理器，将直接返回proxy
//		Object bean = resolveBeforeInstantiation(beanName, mbd);
//		if (bean != null) {
//			return bean;
//		}
//		// 这里就是创建bean了
//		Object beanInstance = doCreateBean(beanName, mbd, args);
//		return beanInstance;
//	}
//
//	// 接着看doCreateBean中bean如何生成
//	protected Object doCreateBean(final String beanName,
//								  final RootBeanDefinition mbd, final Object[] args) {
//		// BeanWrapper 来持有创建出来的bean
//		BeanWrapper instanceWrapper = null;
//		if (mbd.isSingleton()) {// 如果是singleton，把缓存中的bean先移除
//			instanceWrapper = this.factoryBeanInstanceCache.remove(beanName);
//		}
//		if (instanceWrapper == null) {
//			// 创建bean，由craeteBeanInstance来完成
//			instanceWrapper = createBeanInstance(beanName, mbd, args);// 又是封装的bean创建
//		}
//		// 一些其他处理
//
//		/* 这里是对bean的初始化，依赖注入往往在这发生 */
//		Object exposedObject = bean;
//		populateBean(beanName, mbd, instanceWrapper);// 设置依赖关系
//		exposedObject = initializeBean(beanName, exposedObject, mbd);// 初始化bean
//
//		// 一些其他的处理代码
//		return exposedObject;
//	}
//
//	protected BeanWrapper createBeanInstance(String beanName,
//											 RootBeanDefinition mbd, Object[] args) {
//		// 确认要创建的bean实例的类可以实例化
//		Class beanClass = resolveBeanClass(mbd, beanName);
//		// 使用工厂方法对bean实例化 ①
//		if(mbd.getFactoryMethodName()!=null){
//			return instantiateUsingFactoryMethod(beanName,mbd,args);
//		}
//		//自动装配实例化
//		if(mbd.resolvedConstructorOrFactoryMethod!=null){
//			if(mbd.constructorArgumentsResolved){
//				return autowireConstructor(beanName,mbd,null,args);
//			}else{
//				return instantiateBean(beanName,mbd);
//			}
//		}
//		//使用构造函数实例化
//		Constructor[] ctors = determineConstructorsFromBeanPostProcessors(beanClass,beanName);
//		if(ctors!=null||...){
//			return autowireConstructor(beanName,mbd,ctors,args);
//		}
//		//使用默认构造函数对bean实例化
//		return instantiateBean(beanName,mbd);
//	}
//
//	// 最常见实例化过程instantiateBean
//	protected BeanWrapper instantiateBean(String beanName,
//										  RootBeanDefinition mbd) {
//		// 默认实例化策略是使用CGLIB来对Bean进行实例化
//		Object beanInstace = getInstantiationStrategy().instantiate(mbd,
//				beanName, this);
//		BeanWrapper bw = new BeanWrapperImpl(beanInstance);
//		initBeanWrapper(bw);
//		return bw;
//	}
//
//}
