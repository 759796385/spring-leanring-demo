//package source;
//
//import org.springframework.beans.factory.BeanDefinitionStoreException;
//import org.springframework.beans.factory.config.BeanDefinition;
//import org.springframework.beans.factory.support.AbstractBeanDefinition;
//
//import java.util.Map;
//import java.util.concurrent.ConcurrentHashMap;
//
//public class RegistBeanDefinition {
//	private final Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<String, BeanDefinition>();
//
//	public void registerBeanDefinition(String beanName,
//									   BeanDefinition beanDefinition) throws BeanDefinitionStoreException {
//		if (beanDefinition instanceof AbstractBeanDefinition) {
//			((AbstractBeanDefinition) beanDefinition).validate();
//		}
//		// 注册保证串行
//		synchronized (this.beanDefinitionMap) {
//			// 检查BeanDefinition是否有同名的注册，如果有且不允许覆盖就抛异常
//			Object oldBeanDefinition = this.beanDefinitionMap.get(beanName);
//			if (oldBeanDefinition != null) {
//				if (!this.allowBeanDefinitionOverriding) {
//					throw new BeanDefinitionStoreException("");
//				} else {
//					// 日志记录替换
//				}
//			} else {
//				this.beanDefinitionName.add(beanName);
//				this.frozenBeanDefinitionNames = null;
//			}
//			this.beanDefinitionMap.put(beanName, beanDefinition);
//			resetBeanDefition(beanName);
//		}
//	}
//
//}
