
package com.wewin.util;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 解决Rest接口调用过程中，跨域访问问题
 *
 * @author 刘彦亮
 * @version 1.0
 */
public class CORSFilter implements Filter {
    public CORSFilter() {
    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;

//        httpResponse.addHeader("Access-Control-Allow-Origin", "*");
//        httpResponse.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
//        httpResponse.setHeader("Access-Control-Allow-Methods", "GET, PUT, DELETE, POST");


        //     httpResponse.addHeader("Access-Control-Allow-Origin", "*");
        httpResponse.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        httpResponse.setHeader("Access-Control-Allow-Headers", "x-requested-with");
        httpResponse.addHeader("Access-Control-Max-Age", "1800");//30 min

        filterChain.doFilter(servletRequest, servletResponse);
    }

    public void init(FilterConfig fConfig) throws ServletException {

    }

    public void destroy() {
    }


}