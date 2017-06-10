//package source;
//
//import org.springframework.beans.factory.support.DefaultListableBeanFactory;
//import org.springframework.beans.factory.xml.ResourceEntityResolver;
//import org.springframework.context.support.AbstractRefreshableConfigApplicationContext;
//import org.springframework.core.io.Resource;
//
//import java.io.IOException;
//
//public abstract class AbstractXmlApplicationContext extends
//		AbstractRefreshableConfigApplicationContext {
//	protected void loadBeanDefinitions(DefaultListableBeanFactory beanFactory)
//			throws IOException {
//		// 创建读取器，并通过回调设置到BeanFactory中去，这里使用的也是defaultlistablebeanfactory
//		XmlBeanDefinitionReader beanDefinitionReader = new XmlBeanDefinitionReader(
//				beanFactory);
//		// defaultlistablebeanfactory是父类，可直接使用this
//		beanDefinitionReader.setResourceLoader(this);
//		beanDefinitionReader
//				.setEntityResolver(new ResourceEntityResolver(this));
//		// 启动bean定义信息载入
//		initBeanDefinitionReader(beanDefinitionReader);
//		loadBeanDefinitions(beanDefinitionReader);
//	}
//
//	protected void loadBeanDefinitions(XmlBeanDefinitionReader reader) {
//		Resource[] configResource = getConfigResources();
//		// 以resource方式获得配置文件资源
//		if (configResource != null) {
//			reader.loadBeanDefinitions(configResource);
//		}
//		// 以String形式获得配置文件位置
//		String[] configLocations = getConfigLocations();
//		if (configLocations != null) {
//			reader.loadBeanDefinitions(configLocations);
//		}
//	}
//}
