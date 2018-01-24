package com.wewin.controller;

;
import com.wewin.entity.UserInfo;
import com.wewin.service.Impl.UserInfoServiceImpl;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@Controller
public class UserInfoController {
    @Autowired
    private UserInfoServiceImpl userInfoService;

    @RequestMapping(value = "/showuserinfo" ,method={RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public  void showUserInfo(@RequestBody Map<String,String> map,HttpServletRequest request, HttpServletResponse response) throws IOException {
        UserInfo userInfo=new UserInfo();
        String openid=map.get("openid");
        userInfo=userInfoService.GetUserInfo(openid);
        JSONObject jsonObject = JSONObject.fromObject(userInfo);
        String  res=jsonObject.toString();
        response.setContentType("text/json;charset=UTF-8");
        response.setHeader("Cache-Control", "no-cache");
        response.getWriter().write(res);
    }
    @RequestMapping(value = "/edituserinfo" ,method={RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public  void editUserInfo(@RequestBody Map<String,String> map, HttpServletRequest request, HttpServletResponse response) throws IOException {
        UserInfo userInfo=new UserInfo();

        String openid=map.get("openid");
        String nickname=map.get("nickname");
        String school=map.get("school");
        String studentno=map.get("studentno");
        String phoneno=map.get("phoneno");
        userInfo.setNickname(nickname);
        userInfo.setSchool(school);
        userInfo.setStudentno(studentno);
        userInfo.setPhoneno(phoneno);
        userInfo.setOpenid(openid);
        if(nickname!=null)
            userInfoService.UpdateUserInfo(userInfo);


    }
}

