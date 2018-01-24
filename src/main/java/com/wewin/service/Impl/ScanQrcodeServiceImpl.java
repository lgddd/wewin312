package com.wewin.service.Impl;

import com.wewin.entity.UserInfo;
import com.wewin.mapper.UserInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ScanQrcodeServiceImpl {

    @Autowired
    private UserInfoMapper userInfoMapper;

    public boolean userExisted(String OPENID) {
        UserInfo userInfo=new UserInfo();
        try {

            userInfo=userInfoMapper.selectByPrimaryKey(OPENID);
        }catch (Exception  e){
            e.printStackTrace();
        }
        if(userInfo==null)
            return false;
        return true;
    }

    public void addUser(String OPENID){
        UserInfo userInfo=new UserInfo();
        try {
            userInfo.setOpenid(OPENID);
            userInfoMapper.insertSelective(userInfo);
        }catch (Exception  e){
            e.printStackTrace();
        }

    }



}
