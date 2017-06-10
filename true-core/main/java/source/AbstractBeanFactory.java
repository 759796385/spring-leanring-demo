//package source;
//
//import org.springframework.beans.BeansException;
//import org.springframework.beans.factory.BeanFactory;
//import org.springframework.beans.factory.ObjectFactory;
//import org.springframework.beans.factory.config.Scope;
//import org.springframework.beans.factory.support.RootBeanDefinition;
//
//public class AbstractBeanFactory {
//	public Object getBean(String name) throws BeansException {
//		return doGetBean(name, null, null, false);
//	}
//
//	// 各种getbean的重载都是调用doGetBean
//	protected <T> T doGetBean(final String beanName, final Class<T> requiredType,
//							  final Object[] args, boolean typeCheckOnly) {
//		//先从缓存中获取bean,单例模式的bean就无需重复创建了啦
//		Object sharedInstance = getSingleton(beanName);
//		//对当前IOC容器中的BeanDefinition进行检查，是否能取出bean，如果当前容器没有，则去双亲ioc容器中查找
//		BeanFactory parentBeanFactory = getParentBeanFactory();
//		if(parentBeanFactory!=null&!&containsBeanDefinition(beanName)){
//			//不存在bean且父容器不为空，递归调用父类的getbean
//			String nameLookup= originalBeanName(beanName);
//			return parentBeanFactory.getBean(nameLookup,args);
//		}
//		//根据bean的名字取得BeanDefinition
//		final RootBeanDefinition mbd = getMergedLocalBeanDefinition(beanName);
//		//获取当前bean的所有依赖bean，触发getBean的递归调用，直到取到一个没有任何bean的依赖为止
//		String[] dependsOn = mbd.getDependsOn();
//		if(dependsOn!=null){
//			for (String dependsOnBean:dependsOn) {
//				getBean(dependsOnBean);//递归所有依赖bean
//				registerDependentBean(dependsOnBean,beanName);
//			}
//		}
//		/*下面就是创建Bean实例的地方*/
//		if(mbd.isSingleton()){//单例bean
//			sharedInstance = getSingleton(beanName,new ObjectFactory(){
//				@Override
//				public Object getObject() throws BeansException {
//					return createBean(beanName,mbd,args);
//				}
//			});
//		}else if(mbd.isPrototype()){//prototype类型的bean
//			Object prototypeInstance = null;
//			try{
//				beforePrototypeCreation(beanName);//bean 前置处理器
//				prototypeInstance = createBean(beanName,mbd,args);
//			}finally{
//				afterPrototypeCreation(beanName);//bean后置处理器
//			}
//		}else{//其他类型bean
//			String scopeName = mbd.getScope();
//			final Scope scope= this.scopes.get(scopeName);
//			//类似prototype的前后处理器
//			return createBean(beanName,mbd,args);
//		}
//		//一些类型检查代码，查看有没有问题
//		return bean;
//	}
//}
