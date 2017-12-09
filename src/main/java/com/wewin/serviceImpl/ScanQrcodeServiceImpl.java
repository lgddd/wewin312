package com.wewin.serviceImpl;

import com.wewin.entity.UserInfo;
import com.wewin.mapper.UserInfoMapper;
import com.wewin.service.ScanQrcodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


public class ScanQrcodeServiceImpl implements ScanQrcodeService{

    @Autowired
    private UserInfoMapper userInfoMapper=null;
    @Autowired
    private UserInfo userInfo;

    public void delUser(String OPENID) {

    }

    public void addUser(String openid){
        try {
            userInfo.setOpenid(openid);
            userInfoMapper.insertSelective(userInfo);
        }catch (Exception  e){
            e.printStackTrace();
        }

    }

    public void joinClass(String CLASSNO) {

    }
}
