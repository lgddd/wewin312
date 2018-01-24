package com.wewin.service.Impl;

import com.wewin.entity.UserInfo;
import com.wewin.mapper.UserInfoMapper;
import com.wewin.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserInfoServiceImpl implements UserInfoService {


    @Autowired
    private UserInfoMapper userInfoMapper;
    public UserInfo GetUserInfo(String openid){
        UserInfo userInfo=new UserInfo();
        try{
            userInfo=userInfoMapper.selectByPrimaryKey(openid);
        }catch (Exception e){
            e.printStackTrace();
        }
        return userInfo;
    }
    public void UpdateUserInfo(UserInfo userInfo){
        try{
            userInfoMapper.updateByPrimaryKeySelective(userInfo);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
