package com.wewin.controller;

import com.qiniu.util.Auth;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class CreateUpToken {

    @RequestMapping(value="/uptoken")
    public void CreateUpToken(HttpServletRequest httpServletRequest, HttpServletResponse response) throws IOException {
        String accessKey = "9aBwTGn1_ILSNCZe2on837z32uF2nqWCfa0xz1il";
        String secretKey = "zxGwCPuNIc863_alpdfKwPfZA9vF7_WnZ_HVKQvz";
        String bucket = "wewin";
        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucket);
        String res="{'uptoken':'"+upToken+"'}";
        response.setContentType("text/json;charset=UTF-8");
        response.setHeader("Cache-Control", "no-cache");
        response.getWriter().write(res);
    }

}
