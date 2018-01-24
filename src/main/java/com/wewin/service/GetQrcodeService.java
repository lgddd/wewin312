package com.wewin.service;

import java.io.UnsupportedEncodingException;

public interface GetQrcodeService {
    String classNo=null;
    public String getQrcodeUrl(String classId) throws UnsupportedEncodingException;
}
