package com.wewin.service;

import com.wewin.entity.UserInfo;
import com.wewin.mapper.UserInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginHandler {

    @Autowired
    private UserInfoMapper userInfoMapper=null;
    @Autowired
    private UserInfo userInfo;
    public boolean UserIf(String openid){

        userInfo=userInfoMapper.selectByPrimaryKey(openid);
        if(userInfo.getOpenid()==null)
            return false;
        return true;

    }
    public void addUser(String openid){
        userInfo.setOpenid(openid);
        //userInfo.setAvatar("test");
        //userInfo.setNickname("sss");
        userInfoMapper.insertSelective(userInfo);
    }
}
