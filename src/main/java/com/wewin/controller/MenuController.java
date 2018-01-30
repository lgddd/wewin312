package com.wewin.controller;

import com.wewin.entity.UserInfo;
import com.wewin.mapper.UserInfoMapper;
import net.sf.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import static com.wewin.util.HttpReq.httpRequest;

@Controller
public class MenuController {
    @Autowired
    private UserInfoMapper userInfoMapper;

    @RequestMapping(value = "/menu")
    public String GetOpenId(HttpServletRequest request, HttpServletResponse response){
        //String URL="http://wewinnat.free.ngrok.cc/oauth/";//服务器的地址
        String URL="http://www.moonlake.tech/WeWin/menuredirect";//服务器的地址
        //String URL="https://wewinnat.free.ngrok.cc/oauth";//服务器的地址
        String APPID="wxff32e2db397dbbf8";//APPID
//        String RedirectUrl="https://open.weixin.qq.com/connect/oauth2/authorize?appid="+APPID+"&redirect_uri="+URL+
//                "?response_type=code&scope=snsapi_base&state=1&connect_redirect=1#wechat_redirect";

        System.out.println("----menu menu---------------------------------------------------------\n");
        //System.out.println(jsonObject);
        System.out.println("----menu menu---------------------------------------------------------\n");

        return "redirect:https://open.weixin.qq.com/connect/oauth2/authorize?appid="+APPID+"&redirect_uri="+URL+ "?response_type=code&scope=snsapi_base&state=1&connect_redirect=1#wechat_redirect";
    }
    @RequestMapping(value="/menuredirect")
    public  ModelAndView Redirect(HttpServletRequest request, HttpServletResponse response){
        UserInfo userInfo=new UserInfo();
        String CODE = request.getParameter("code");
        String APPID = "wxff32e2db397dbbf8";
        String SECRET = "9e297378ed72157e3c95f94399412927";
        //换取access_token 其中包含了openid
        String URL = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code".replace("APPID", APPID).replace("SECRET", SECRET).replace("CODE", CODE);
        //发送http请求,获取openID
        JSONObject jsonObject = httpRequest(URL, "GET", null);
        System.out.println("----request find openid---------------------------------------------------------\n");
        System.out.println(jsonObject);
        System.out.println("----request find openid---------------------------------------------------------\n");
        String OPENID =jsonObject.getString("openid");
        userInfo=userInfoMapper.selectByPrimaryKey(OPENID);
        String Nickname=userInfo.getNickname();
        if(Nickname==null||Nickname.equals(""))
        {
            return  new ModelAndView(new RedirectView("http://www.moonlake.tech/wewinFront/#/sureInfo?openid="+OPENID));
        }
        //return "redirect：gabear.free.ngrok.cc/sureInfo?openid="+OPENID;//跳转到用户编辑页面

        return  new ModelAndView(new RedirectView("http://www.moonlake.tech/wewinFront/#/class?openid="+OPENID));
       /* String servletPath=request.getServletPath();

        if (servletPath.equals(" ")) {
            return "";

        }
        else if(servletPath.equals("")){

        }

        return "";*/
    }
}
