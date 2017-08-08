package com.newtonk.config;

import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.*;
import java.util.EnumSet;

/**
 * 类名称：
 * 类描述：
 * 创建人：tq
 * 创建日期：2017/6/14 0014
 */
public class GolfingWebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
    @Override
    protected Class<?>[] getRootConfigClasses() {
        //Root Application config
        return new Class[]{RootConfig.class};
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        //web config目录配置
        return new Class[]{WebConfig.class};
    }

    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        super.onStartup(servletContext);
        initFilters(servletContext);
    }
    private void initFilters(ServletContext servletContext) {
        addFilter(servletContext, "myFilter", new DelegatingFilterProxy("myFilter"));
        initCharacterEncodingFilter(servletContext);
    }
    protected void initCharacterEncodingFilter(ServletContext servletContext) {
        CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
        //characterEncodingFilter.setForceEncoding(true);
        characterEncodingFilter.setEncoding("UTF-8");
        addFilter(servletContext, "characterEncodingFilter", characterEncodingFilter);
    }

    protected EnumSet<DispatcherType> getDispatcherTypes() {
        return  EnumSet.of(DispatcherType.REQUEST, DispatcherType.FORWARD, DispatcherType.INCLUDE, DispatcherType.ASYNC);
    }

    /**
     * AbstractDispatcherServletInitializer  registerServletFilter 添加过滤器源码实现
     * @param servletContext
     * @param filterName
     * @param filter
     */
    protected void addFilter(ServletContext servletContext, String filterName, Filter filter) {
        FilterRegistration.Dynamic filterRegistration = servletContext.addFilter(filterName, filter);
        filterRegistration.setAsyncSupported(isAsyncSupported());
        filterRegistration.addMappingForUrlPatterns(getDispatcherTypes(), false, "/*");
    }
}
