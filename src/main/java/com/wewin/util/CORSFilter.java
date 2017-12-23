
package com.wewin.util;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
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

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletResponse httpServletResponse = (HttpServletResponse) response;

        httpServletResponse.setHeader("Cache-Control", "no-cache");

        httpServletResponse.setContentType("text/json;charset=UTF-8");

        chain.doFilter(request, response);

    }

    public void init(FilterConfig fConfig) throws ServletException {

    }

    public void destroy() {
    }


}