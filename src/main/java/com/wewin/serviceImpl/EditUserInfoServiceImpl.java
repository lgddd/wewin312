package com.wewin.serviceImpl;

import com.wewin.entity.UserInfo;
import com.wewin.mapper.UserInfoMapper;
import com.wewin.service.EditUserInfoService;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.SQLException;


public class EditUserInfoServiceImpl implements EditUserInfoService{

    @Autowired
    private UserInfoMapper userInfoMapper;

    public void editUserInfo(UserInfo userInfo) {
        try {
            userInfoMapper.updateByPrimaryKeySelective(userInfo);
        }catch (Exception  e){
            e.printStackTrace();
        }

    }
}
