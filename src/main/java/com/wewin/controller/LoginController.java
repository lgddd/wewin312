package com.wewin.controller;

import com.wewin.service.LoginHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import net.sf.json.*;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.URL;
import java.security.SecureRandom;

/**
 * Created by LeonYuan on 2017/12/5.
 */
@Controller
public class LoginController {

@Autowired
public LoginHandler loginHandler;

    @RequestMapping(value = "/", method = {RequestMethod.GET})
    public String tesr()throws DataAccessException {
        String openid="3333";
    try{
        loginHandler.addUser(openid);
    }catch (DataAccessException e){
        //return "success";
    }

    return "success";
    }
    //登录跳转
    @RequestMapping(value = "/redirect", method = {RequestMethod.GET})
    public String wexinRedirect() throws Exception {
        String URL="";//服务器的地址
        String APPID="";//APPID
        return "redirect:https://open.weixin.qq.com/connect/oauth2/authorize?appid="+APPID+"&redirect_uri="+URL+"?response_type=code&scope=snsapi_base&state=1&connect_redirect=1#wechat_redirect";
    }


    @RequestMapping(value = "/oauth", method = {RequestMethod.GET})
    public String login(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {

        String CODE = request.getParameter("code");
        String APPID = "";
        String SECRET = "";
        //换取access_token 其中包含了openid
        String URL = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code".replace("APPID", APPID).replace("SECRET", SECRET).replace("CODE", CODE);
        //发送http请求,获取openID
        JSONObject jsonObject = httpRequest(URL, "GET", null);
        String  openid =jsonObject.getString("openid");
        boolean userif=loginHandler.UserIf(openid);
        if(!userif)
            loginHandler.addUser(openid);
        //model.addAttribute("user", wechatUser);
        return "";

    }
    public static JSONObject httpRequest(String requestUrl, String requestMethod, String outputStr)
    {
        JSONObject jsonObject = null;
        StringBuffer buffer = new StringBuffer();
        try
        {
            TrustManager[] tm = { new MyX509TrustManager() };
            SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
            sslContext.init(null, tm, new SecureRandom());

            SSLSocketFactory ssf = sslContext.getSocketFactory();

            URL url = new URL(requestUrl);
            HttpsURLConnection httpUrlConn = (HttpsURLConnection)url.openConnection();
            httpUrlConn.setSSLSocketFactory(ssf);

            httpUrlConn.setDoOutput(true);
            httpUrlConn.setDoInput(true);
            httpUrlConn.setUseCaches(false);

            httpUrlConn.setRequestMethod(requestMethod);

            if ("GET".equalsIgnoreCase(requestMethod)) {
                httpUrlConn.connect();
            }

            if (outputStr != null) {
                OutputStream outputStream = httpUrlConn.getOutputStream();

                outputStream.write(outputStr.getBytes("UTF-8"));
                outputStream.close();
            }

            InputStream inputStream = httpUrlConn.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String str = null;
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }
            bufferedReader.close();
            inputStreamReader.close();

            inputStream.close();
            inputStream = null;
            httpUrlConn.disconnect();
            jsonObject = JSONObject.fromObject(buffer.toString());
        } catch (ConnectException ce) {
            // logger.error("Weixin server connection timed out.");
        } catch (Exception e) {
            //logger.error("https request error:{}", e);
        }
        return jsonObject;
    }
}
