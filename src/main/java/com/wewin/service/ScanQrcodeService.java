package com.wewin.service;

public interface ScanQrcodeService {
    public  boolean userExisted(String OPENID);
    public  void addUser(String OPENID);
    public String GetAvatar(String openid);
    public  void addUser(String OPENID,String Avatar);
}
