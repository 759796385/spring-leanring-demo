//package source;
//
//import org.springframework.beans.BeansException;
//import org.springframework.beans.factory.BeanDefinitionStoreException;
//import org.springframework.beans.factory.support.DefaultListableBeanFactory;
//import org.springframework.core.io.Resource;
//import org.springframework.core.io.ResourceLoader;
//import org.springframework.core.io.UrlResource;
//import org.springframework.core.io.support.ResourcePatternResolver;
//import org.springframework.util.Assert;
//
//import java.io.IOException;
//import java.net.MalformedURLException;
//import java.net.URL;
//import java.util.Set;
//
//public abstract class AbstractRefreshableApplicationContext extends
//		AbstractApplicationContext {
//	protected final void refreshBeanFactory() throws BeansException{
//		if(hasBeanFactory()){//如果已创建容器，则销毁并关闭容器
//			destroyBeands();
//			closeBeanFactory();
//		}
//		try{
//			DefaultListableBeanFactory beanFactory = createBeanFactory();
//			beanFactory.setSerializationId(getId());
//			customizeBeanFactory(beanFactory);
//			loadBeanDefinitions(beanFactory);
//			sychronized(this.beanFactoryMonitory){
//				this.beanFactory= beanFactory;
//			}
//		}catch(IOException ex){
//			//
//		}
//	}
//
//	/**
//	 * 这个就是创建DefaultListableBeanFactory的地方，getInternalParentBeanFactory()的实现在
//	 * AbstractApplicationContext中
//	 * ，根据容器已有的双亲容器信息来生成对应DefaultListableBeanFactory的双亲容器
//	 *
//	 * @return
//	 */
//	protected DefaultListableBeanFactory createBeanFactory() {
//		return new DefaultListableBeanFactory(getInternalParentBeanFactory());
//	}
//
//	/**
//	 * 这个是使用BeanDefinition载入bean定义的地方，有很多种载入方式。
//	 * 设置成抽象方法。这个类中使用xml来定义bean，由子类去实现这个抽象方法。
//	 */
//	protected abstract void loadBeanDefinitions(
//			DefaultListableBeanFactory beanFactory) throws IOException,
//			BeansException;
//
//	public int loadBeanDefinitions(String location, Set actualResources)
//			throws BeanDefinitionStoreException {
//		ResourceLoader resourceLoader = getResourceLoader();
//		if (resourceLoader == null) {
//			throw new BeanDefinitionStoreException("");
//		}
//
//		// 对Resource的路径模型进行解析，比如设置的ant格式的路径定义，得到需要的resource集合
//		if (resourceLoader instanceof ResourcePatternResolver) {
//			try {
//				// 调用DefaultRourceLoader的getResource完成resource定位
//				Resource[] resources = ((ResourcePatternResolver) resourceLoader)
//						.getResource(location);
//				getResources(location);
//				int loadCount = loadBeanDefinitions(resources);
//				if (actualResources != null) {
//					for (int i = 0; i < resources.length; i++) {
//						actualResources.add(resources[i]);
//					}
//				}
//				return loadCount;
//			} catch (IOException ex) {
//
//			}
//		} else {
//			// 调用DefaultRourceLoader的getResource完成resource定位
//			Resource resource = resourceLoader.getResource(location);
//			int loadCount = loadBeanDefinitions(resource);
//			if (actualResources != null) {
//				actualResources.add(resources);
//			}
//			return loadCount;
//		}
//		return 0;
//	}
//
//	/**
//	 * DefaultRourceLoader的getResource方法实现
//	 */
//	public Resource resourcegetResource(String location) {
//		Assert.notNull(location, "location is not be null");
//		// 处理带有classpath标志的resource
//		if (location.startsWith(CLASSPATH_ALL_URL_PREFIX)) {
//			return new ClassPathContextResource(
//					location.substring(CLASSPATH_ALL_URL_PREFIX.length()),
//					getClassLoader());
//		} else {
//			try {
//				// 处理带URL的resource
//				URL url = new URL(location);
//				return new UrlResource(url);
//			} catch (MalformedURLException ex) {
//				// 既不是classpath也不是url的定位，这个getResource的任务交给getResourceByPath(),这个方法通常由子类实现
//				return getResourceByPath(location);
//			}
//		}
//	}
//}
