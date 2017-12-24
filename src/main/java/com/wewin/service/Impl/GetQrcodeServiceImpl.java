package com.wewin.service.Impl;

import com.wewin.service.GetQrcodeService;
import com.wewin.util.DownloadQrcode;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import static com.wewin.util.HttpReq.httpRequest;


@Service
public class GetQrcodeServiceImpl implements GetQrcodeService{

    private  String classNo;
    public  String getAuthtoken(){
        String APPID = "wxff32e2db397dbbf8";
        String SECRET = "9e297378ed72157e3c95f94399412927";
        //换取access_token 其中包含了openid
        String URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+APPID+"&secret="+SECRET;
        //发送http请求,获取token
        JSONObject jsonObject = httpRequest(URL, "GET", null);
        String  token =jsonObject.getString("access_token");
        return  token;
    }


    public String getTicket(String classId){
        String TOKEN=getAuthtoken();
        String URL="https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token="+TOKEN;
        //classNo=""+System.currentTimeMillis();
        String PARAM="{\"expire_seconds\": 2592000, \"action_name\": \"QR_STR_SCENE\", \"action_info\": {\"scene\": {\"scene_str\": "+classId+"\"}}}";
        JSONObject jsonObject = httpRequest(URL, "POST", PARAM);
        String ticket=jsonObject.getString("ticket");
        return ticket;

    }



    public String getQrcodeUrl(String classId){
        String ticket=getTicket(classId);
        String URL="https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket="+ URLEncoder.encode(ticket);
        String tomcat_path = System.getProperty("user.dir");
        String bin_path = tomcat_path.substring(tomcat_path.lastIndexOf("\\")+1,tomcat_path.length());
        String pic_path;
        if(("bin").equals(bin_path)){
            pic_path = tomcat_path.substring(0,System.getProperty("user.dir").lastIndexOf("\\"))+"\\webapps"+"\\pic_file\\qrcode\\";
        }else{
            pic_path = tomcat_path+"\\webapps"+"\\pic_file\\qrcode\\";
        }
        DownloadQrcode.sendGetAndSaveFile(URL,pic_path+classId+".jpg");
        String Qrcodeurl=pic_path+classId+".jpg";
        return Qrcodeurl;
    }


}
