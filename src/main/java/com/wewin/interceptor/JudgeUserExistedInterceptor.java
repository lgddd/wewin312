package com.wewin.interceptor;

import com.wewin.entity.UserInfo;
import com.wewin.mapper.UserInfoMapper;
import com.wewin.service.Impl.ScanQrcodeServiceImpl;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.wewin.util.HttpReq.httpRequest;

public class JudgeUserExistedInterceptor extends HandlerInterceptorAdapter {
    @Autowired
    private ScanQrcodeServiceImpl scanQrcodeService;
    @Autowired
    private UserInfoMapper userInfoMapper;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        /*
        不用过滤的：
         menu
         menuredirect
         redirect
         */

        String openid = request.getParameter("openid");

        if(openid != null && !(openid.equals(""))) return true;

        String RedirectUrl = "";//引导用户关注页面
        response.sendRedirect(RedirectUrl);
        return false;
        //UserInfo userInfo=new UserInfo();
        //String CODE = request.getParameter("code");
        //String APPID = "wxff32e2db397dbbf8";
       // String SECRET = "9e297378ed72157e3c95f94399412927";
        //换取access_token 其中包含了openid
        //String URL = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code".replace("APPID", APPID).replace("SECRET", SECRET).replace("CODE", CODE);
        //发送http请求,获取openID
        //JSONObject jsonObject = httpRequest(URL, "GET", null);
//        String  openid =jsonObject.getString("openid");
//        boolean UserExisted=scanQrcodeService.userExisted(openid);
//        userInfo=userInfoMapper.selectByPrimaryKey(openid);
//        String nickname=userInfo.getNickname();
//        if(UserExisted&&nickname!=null&&!(nickname.equals("")))//用户存在且用户名不为空
//        return true;
//        String RedirectUrl="http://www.moonlake.tech/wewinFront/#/sureInfo?openid="+openid;//跳转到用户信息编辑页面
//        response.sendRedirect(RedirectUrl);
//        return false;
    }
}
