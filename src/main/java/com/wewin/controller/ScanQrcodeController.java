package com.wewin.controller;

import com.wewin.entity.UserInfo;

import com.wewin.service.Impl.ScanQrcodeServiceImpl;
import com.wewin.util.ParseXml;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Map;

/**
 * Created by LeonYuan on 2017/12/5.
 */
@Controller
public class ScanQrcodeController {

    @Autowired
    ScanQrcodeServiceImpl scanQrcodeService;

    @Autowired
    private UserInfo userInfo;
    @Autowired
    private ParseXml parseXml;
    String TOKEN="weixin";

    @RequestMapping(value = "/test",method={RequestMethod.GET,RequestMethod.POST})
    public  String  login(HttpServletRequest request, HttpServletResponse response){
       String id=request.getParameter("id");
         try{
            scanQrcodeService.addUser("11111111");
        }catch (DataAccessException e){
            //return "success";
        }
        //return "redirect:http://gabear.free.ngrok.cc/sureInfo?openid=1111111";
        return "success";

    }
    @RequestMapping(value = "/redirect",method={RequestMethod.GET,RequestMethod.POST})
    public String ScanEvent(HttpServletRequest request) throws Exception {

        InputStream reqStream=request.getInputStream();
        Map<String,String> map=parseXml.getScanInfo(reqStream);
        String OPENID=map.get("FromUserName");//扫码用户的openID
        String CLASSNO=map.get("EventKey");//班级编号
        String EVENT=map.get("Event");//事件类型：subscribe（关注）、unsubscribe（取消关注）、scan（已关注）
        if(EVENT.equals("subscribe"))//用户关注
        {
          scanQrcodeService.addUser(OPENID);
          //return "redirect:http://gabear.free.ngrok.cc/sureInfo?openid"+OPENID;

        }
        else if (EVENT.equals("unsubscribe"))
        {

        }
        else
        {
            return "redirect:http://gabear.free.ngrok.cc/sureInfo?openid"+OPENID;
        }

       return "";
    }

}
