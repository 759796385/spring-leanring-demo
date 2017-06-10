//package source;
//
//import org.springframework.beans.BeanUtils;
//import org.springframework.beans.factory.BeanDefinitionStoreException;
//import org.springframework.beans.factory.config.BeanDefinitionHolder;
//import org.springframework.beans.factory.parsing.BeanComponentDefinition;
//import org.springframework.beans.factory.support.AbstractBeanDefinitionReader;
//import org.springframework.beans.factory.support.BeanDefinitionReaderUtils;
//import org.springframework.beans.factory.xml.BeanDefinitionDocumentReader;
//import org.springframework.beans.factory.xml.BeanDefinitionParserDelegate;
//import org.springframework.core.io.Resource;
//import org.springframework.core.io.support.EncodedResource;
//import org.w3c.dom.Document;
//import org.w3c.dom.Element;
//import org.xml.sax.InputSource;
//
//import java.io.InputStream;
//import java.util.HashSet;
//import java.util.Set;
//
//public class XmlBeanDefinitionReader extends AbstractBeanDefinitionReader {
//	public int loadBeanDefinitions(Resource res)
//			throws BeanDefinitionStoreException {
//		return loadBeanDefinitions(new EncodedResource(res));
//	}
//
//	public int loadBeanDefinitions(EncodedResource encodedResource)
//			throws BeanDefinitionStoreException {
//		Set<EncodedResource> currentResources = this.resourcesCurrentlyBeingLoaded
//				.get();
//		if (currentResources != null) {
//			currentResources = new HashSet<EncodedResource>(4);
//			this.resourcesCurrentlyBeingLoaded.set(currentResources);
//		}
//		//IO操作
//		try{
//			InputStream inputStream = encodedResource.getResource().getInputStream();
//			try{
//				InputSource inputSource = new InputSource(inputStream);
//				if(encodedResource.getEncoding()!=null){
//					inputSource.setEncoding(encodedResource.getEncoding());
//				}
//				//实际载入BeanDefinition的方法
//				return doLoadBeanDifinitions(inputSource,encodedResource.getResource());
//			}
//			//dosome
//		}
//		return 0;
//	}
//
//	// 具体的读取过程
//	public int doLoadBeanDifinitions(InputSource inputSource, Resource resource) {
//		int validationMode = getValidationModeForResource(resource);
//		// 取得xml的document对象，解析过程由documentLoader完成。
//		// documentLoader是DefaultDocumentLoader，在定义documentLoader的地方创建
//		Document doc = this.documentLoader.loadDocument(inputSource,
//				getEntityResolver(), this.errorHandler, validationMode,
//				isNamespaceAware());
//		// 启动对BeanDefinition的解析过程
//		return registerBeanDefinitions(doc, resource);
//	}
//
//	public int registerBeanDefinitions(Document doc, Resource resource) {
//		BeanDefinitionDocumentReader documentReader = createBeanDefinitionDocumentReader();
//		int countBefore = getRegistry().getBeanDefinitionCount();
//		documentReader.registerBeanDefinitions(doc,
//				createReaderContext(resource));
//		return getRegistry().getBeanDefinitionCount() - countBefore;
//	}
//
//	protected BeanDefinitionDocumentReader createBeanDefinitionDocumentReader() {
//		return BeanDefinitionDocumentReader.class.cast(BeanUtils
//				.instantiateClass(this.documentReaderClass));
//	}
//
//	protected void processBeanDefinition(Element ele,
//										 BeanDefinitionParserDelegate delegate) {
//		// 这里按照spring的bean规则解析BeanDefinition
//		// 具体处理委托给BeanDefinitionParserDelegate完成，ele是xml中的BeanDefinition元素
//		BeanDefinitionHolder bdHolder = delegate
//				.parseBeanDefinitionElement(ele);
//		if (bdHolder != null) {
//			bdHolder = delegate.decorateBeanDefinitionIfRequired(ele, bdHolder);
//			// 向ioc容器注册解析得到BeanDefinition
//			BeanDefinitionReaderUtils.registerBeanDefinition(bdHolder,
//					getReaderContext().getRegistry());
//			// BeanDefinition向ioc注册完成，发送消息
//			getReaderContext().fireComponentRegistered(
//					new BeanComponentDefinition(bdHolder));
//		}
//	}
//}
