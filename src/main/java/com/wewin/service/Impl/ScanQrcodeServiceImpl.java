package com.wewin.service.Impl;
import com.wewin.service.GetQrcodeService;
import com.wewin.entity.UserInfo;
import com.wewin.mapper.UserInfoMapper;
import com.wewin.service.GetQrcodeService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import static com.wewin.util.HttpReq.httpRequest;
@Service
public class ScanQrcodeServiceImpl {

    @Autowired
    private UserInfoMapper userInfoMapper;

    public boolean userExisted(String OPENID) {
        UserInfo userInfo=new UserInfo();
        try {

            userInfo=userInfoMapper.selectByPrimaryKey(OPENID);
        }catch (Exception  e){
            e.printStackTrace();
        }
        if(userInfo==null)
            return false;
        return true;
    }

    public void addUser(String OPENID){
        UserInfo userInfo=new UserInfo();
        try {
            userInfo.setOpenid(OPENID);
            userInfoMapper.insertSelective(userInfo);
        }catch (Exception  e){
            e.printStackTrace();
        }

    }


    public String GetAvatar(String openid){
        GetQrcodeService getQrcodeService=new GetQrcodeServiceImpl();
        String token=getQrcodeService.getAuthtoken();
        String url="https://api.weixin.qq.com/cgi-bin/user/info?access_token="+token+"&openid="+openid+"&lang=zh_CN";
        JSONObject jsonObject=httpRequest(url,"GET",null);
        String imgurl=jsonObject.getString("headimgurl");
        return imgurl;
    }



}
