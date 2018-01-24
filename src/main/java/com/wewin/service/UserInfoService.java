package com.wewin.service;

import com.wewin.entity.UserInfo;

public interface UserInfoService {
    public UserInfo GetUserInfo(String openid);
    public void UpdateUserInfo(UserInfo userInfo);
}
