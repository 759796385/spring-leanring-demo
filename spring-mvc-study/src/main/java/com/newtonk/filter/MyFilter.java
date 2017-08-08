package com.newtonk.filter;

import com.newtonk.config.DemoBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 类名称：
 * 类描述：
 * 创建人：tq
 * 创建日期：2017/8/8 0008
 */
public class MyFilter  extends OncePerRequestFilter{

    @Autowired
    private DemoBean demoBean;
    /**
     * Same contract as for {@code doFilter}, but guaranteed to be
     * just invoked once per request within a single request thread.
     * See {@link #shouldNotFilterAsyncDispatch()} for details.
     * <p>Provides HttpServletRequest and HttpServletResponse arguments instead of the
     * default ServletRequest and ServletResponse ones.
     *
     * @param request
     * @param response
     * @param filterChain
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        System.out.println("filter start");
        String auth = request.getHeader("Authorization");
        if(auth == null){
            response.setStatus(401);
            return;
        }

        System.out.println("age" + demoBean.getAge());
        filterChain.doFilter(request,response);
        System.out.println("filter end");
    }
}
