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

        if(CLASSNO !=null){
            String[] classno = CLASSNO.split("_");
        if(classno.length>=1) CLASSNO = classno[1];
        }
        String EVENT = map.get("Event");//事件类型：subscribe（关注）、unsubscribe（取消关注）、scan（已关注）
        boolean userExisted = scanQrcodeService.userExisted(OPENID);
        if (EVENT.equals("subscribe"))//用户关注
        {
            if (CLASSNO .equals(" ") || CLASSNO == null)  //非扫码关注
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
                classInfoService.addmember(OPENID,Integer.parseInt(CLASSNO));


            }


        }
        else if (EVENT.equals("scan"))//用户在已关注的情况下扫描二维码
        {
            userInfo = userInfoMapper.selectByPrimaryKey(OPENID);
            // --------------------调用接口加入班级------------------
            classInfoService.addmember(OPENID,Integer.parseInt(CLASSNO));
        }



    }

}
