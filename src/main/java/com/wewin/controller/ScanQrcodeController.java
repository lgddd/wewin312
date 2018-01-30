package com.wewin.controller;

import com.wewin.entity.UserInfo;
import com.wewin.mapper.UserInfoMapper;
import com.wewin.service.ClassInfoService;
import com.wewin.service.Impl.ScanQrcodeServiceImpl;
import com.wewin.util.ParseXml;
import org.springframework.beans.factory.annotation.Autowired;
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
    private ScanQrcodeServiceImpl scanQrcodeService;
    @Autowired
    private UserInfoMapper userInfoMapper;
    @Autowired
    private ClassInfoService classInfoService;

    private UserInfo userInfo = new UserInfo();
    @Autowired
    private ParseXml parseXml;

    @RequestMapping(value = "/redirect", method = {RequestMethod.GET, RequestMethod.POST})
    public void ScanEvent(HttpServletRequest request,HttpServletResponse response) throws Exception {

        InputStream reqStream = request.getInputStream();
        Map<String, String> map = parseXml.getScanInfo(reqStream);
        String OPENID = map.get("FromUserName");//扫码用户的openID
        String CLASSNO = map.get("EventKey");//班级编号
        System.out.println("----1---------------------------------------------------------\n");
        System.out.println(CLASSNO);
        System.out.println("----1---------------------------------------------------------\n");

        String EVENT = map.get("Event");//事件类型：subscribe（关注）、unsubscribe（取消关注）、scan（已关注）
        System.out.println("----EVENT---------------------------------------------------------\n");
        System.out.println(EVENT);
        System.out.println("----EVENT---------------------------------------------------------\n");
        boolean userExisted = scanQrcodeService.userExisted(OPENID);
        if (EVENT.equals("subscribe"))//用户关注
        {
            if (CLASSNO .equals(" ") || CLASSNO == null ||CLASSNO.equals(""))  //非扫码关注
            {
                if (!userExisted) {
                    scanQrcodeService.addUser(OPENID);
                }
            }
            else //扫码关注
            {
                if (!userExisted) {
                    scanQrcodeService.addUser(OPENID);
                }
                userInfo = userInfoMapper.selectByPrimaryKey(OPENID);
               // --------------------调用接口加入班级------------------
                System.out.println("-----2--------------------------------------------------------\n");
                System.out.println(CLASSNO);
                System.out.println("-----2--------------------------------------------------------\n");

                String[] classno = CLASSNO.split("_");
                System.out.println("-----3--------------------------------------------------------\n");
                System.out.println(classno);
                System.out.println("------3-------------------------------------------------------\n");

                CLASSNO = classno[1];
                classInfoService.addmember(OPENID,Integer.parseInt(CLASSNO));


            }


        }
        else if (EVENT.equals("SCAN"))//用户在已关注的情况下扫描二维码
        {
            userInfo = userInfoMapper.selectByPrimaryKey(OPENID);
            //--------------------判断用户是否已经加入班级了
            // --------------------没加入则调用接口加入班级------------------
             if(classInfoService.isinclass(OPENID,Integer.parseInt(CLASSNO)) == 0)
                 classInfoService.addmember(OPENID,Integer.parseInt(CLASSNO));

        }else if(EVENT.equals("VIEW")){
            //点菜单事件

        }



    }

}
