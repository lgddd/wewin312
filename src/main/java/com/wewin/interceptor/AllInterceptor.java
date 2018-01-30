package com.wewin.interceptor;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * 用于检查 token 的拦截器
 *
 */
public class AllInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String URL="http://www.moonlake.tech/WeWin/menuredirect";//服务器的地址
        //String URL="https://wewinnat.free.ngrok.cc/oauth";//服务器的地址
        String APPID="wxff32e2db397dbbf8";//APPID
        String RedirectUrl="https://open.weixin.qq.com/connect/oauth2/authorize?appid="+APPID+"&redirect_uri="+URL+
                "?response_type=code&scope=snsapi_base&state=1&connect_redirect=1#wechat_redirect";
        System.out.println("根目录---------------------"+request.getContextPath());
        response.sendRedirect(RedirectUrl);

        return false;
    }

}
