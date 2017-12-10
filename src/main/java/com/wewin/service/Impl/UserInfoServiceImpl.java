package com.wewin.service.Impl;

import com.wewin.entity.UserInfo;
import com.wewin.mapper.UserInfoMapper;
import com.wewin.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserInfoServiceImpl implements UserInfoService{

    @Autowired
    private UserInfo userInfo;
    @Autowired
    private UserInfoMapper userInfoMapper;
    public UserInfo getUserInfo(String openid){
        try{
            userInfo=userInfoMapper.selectByPrimaryKey(openid);
        }catch (Exception e){
            e.printStackTrace();
        }
        return userInfo;
    }


}
