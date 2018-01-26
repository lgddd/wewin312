package com.wewin.controller;

import com.wewin.util.JSONResult;
import com.wewin.util.QiniuUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class QiniuController {

    @RequestMapping(value = "/access_qiniutoken" ,method={RequestMethod.GET,RequestMethod.POST})
    public JSONResult getToekn( )  {
        String param = QiniuUtil.getToken();
        System.out.println(param);
        return new JSONResult(param);
    }

    @RequestMapping(value = "/access_downloadtoken" ,method={RequestMethod.GET,RequestMethod.POST})
    public JSONResult getDownLoadToekn(String url)  {
        //  String param =  QiniuUtil.getDownLoadToekn("http://p2zhcnn8g.bkt.clouddn.com/testwewin");

        String param =  QiniuUtil.getDownLoadToekn(url);
        System.out.println(param);
        return new JSONResult(param);
    }

}
