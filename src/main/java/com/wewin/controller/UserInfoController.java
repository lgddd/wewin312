package com.wewin.controller;

import com.wewin.entity.UserInfo;
import com.wewin.service.UserInfoService;
import com.wewin.service.Impl.UserInfoServiceImpl;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@Controller
public class UserInfoController {

    private UserInfoService userInfoService=new UserInfoServiceImpl();
    @Autowired
    private UserInfo userInfo=new UserInfo();
    @RequestMapping(value = "/showuserinfo" ,method={RequestMethod.GET,RequestMethod.POST})
    public  void showUserInfo(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String openid=request.getParameter("openid");
        userInfo=userInfoService.getUserInfo(openid);
        JSONObject jsonObject = JSONObject.fromObject(userInfo);
        String  res=jsonObject.toString();
        response.setContentType("text/json;charset=UTF-8");
        response.setHeader("Cache-Control", "no-cache");
        response.getWriter().write(res);
    }





}
