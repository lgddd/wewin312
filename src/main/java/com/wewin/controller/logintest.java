package com.wewin.controller;

import com.wewin.service.LoginHandler;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;

public class logintest {

    @Autowired
    private LoginHandler loginHandler=null;

    @Test
    public void Test(){
        String openid="1111111";
        boolean userif=loginHandler.UserIf(openid);
        System.out.println("结果"+userif);
            if(!userif)
            loginHandler.addUser(openid);
    }

}
