package com.wewin.service.Impl;

import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import com.wewin.service.GetQrcodeService;
import com.wewin.util.DownloadQrcode;
import com.wewin.util.QiniuUtil;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import static com.wewin.util.HttpReq.httpRequest;


@Service
public class GetQrcodeServiceImpl implements GetQrcodeService {


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


    public String getTicket(String classNo){
        String TOKEN=getAuthtoken();
        String URL="https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token="+TOKEN;
        //classNo=""+System.currentTimeMillis();
        String PARAM="{\"expire_seconds\": 2592000, \"action_name\": \"QR_STR_SCENE\", \"action_info\": {\"scene\": {\"scene_str\": "+classNo+"\"}}}";
        JSONObject jsonObject = httpRequest(URL, "POST", PARAM);
        String ticket=jsonObject.getString("ticket");
        return ticket;

    }



    public String getQrcodeUrl(String classId) throws UnsupportedEncodingException {
        String ticket=getTicket(classId);
        String URL="https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket="+ URLEncoder.encode(ticket);
        String tomcat_path = System.getProperty("user.dir");
        String bin_path = tomcat_path.substring(tomcat_path.lastIndexOf("\\")+1,tomcat_path.length());
        String pic_path;
        if(("bin").equals(bin_path)){
            pic_path = tomcat_path.substring(0,System.getProperty("user.dir").lastIndexOf("\\"))+"\\webapps"+"\\qrcode\\";
        }else{
            pic_path = tomcat_path+"\\webapps"+"\\qrcode\\";
        }
        DownloadQrcode.sendGetAndSaveFile(URL,pic_path+classId+".jpg");
        //构造一个带指定Zone对象的配置类
        Configuration cfg = new Configuration(Zone.zone0());
        UploadManager uploadManager = new UploadManager(cfg);
        //...生成上传凭证，然后准备上传
      /*  String accessKey = "VXp1rClYW4OO0om7B2nrpltW4tHcsyTAuCEwQVfo";
        String secretKey = "GjqM1KG5pdx1tzWJIuseO1YYaMqR5szWZEwiLWSD";
        String bucket = "gabear";
        */
        String accessKey = "9aBwTGn1_ILSNCZe2on837z32uF2nqWCfa0xz1il";
        String secretKey = "zxGwCPuNIc863_alpdfKwPfZA9vF7_WnZ_HVKQvz";
        String bucket = "wewin";


        //如果是Windows情况下，格式是 D:\\qiniu\\test.png
        String localFilePath = pic_path+classId+".jpg";
        //默认不指定key的情况下，以文件内容的hash值作为文件名
        String key = classId+".jpg";
        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucket);
        try {
            Response response = uploadManager.put(localFilePath, key, upToken);
            //解析上传成功的结果
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
        } catch (QiniuException ex) {
            Response r = ex.response;
            System.err.println(r.toString());
            try {
                System.err.println(r.bodyString());
            } catch (QiniuException ex2) {
                //ignore
            }
        }
        String fileName = key;
        String domainOfBucket = "p2zhcnn8g.bkt.clouddn.com";
        String encodedFileName = URLEncoder.encode(fileName, "utf-8");
        String finalUrl = String.format("%s/%s", domainOfBucket, encodedFileName);

//        return finalUrl;
        String result = QiniuUtil.getDownLoadToekn("http://p2zhcnn8g.bkt.clouddn.com/"+classId+".jpg");
        return result;

    }


}
