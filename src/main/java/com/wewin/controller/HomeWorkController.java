package com.wewin.controller;

import com.wewin.entity.ClassInfo;
import com.wewin.entity.HomeWork;
import com.wewin.entity.UserInfo;
import com.wewin.mapper.HomeWorkMapper;
import com.wewin.mapper.UserInfoMapper;
import com.wewin.service.Impl.ClassInfoServiceImpl;
import com.wewin.service.Impl.HomeWorkServiceImpl;
import com.wewin.util.CreateDownLoadUrl;
import com.wewin.util.CreateUpToken;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
public class HomeWorkController {

    @Autowired
    private UserInfoMapper userInfoMapper;
    @Autowired
    private HomeWorkMapper homeWorkMapper;
    @Autowired
    private UserInfo userInfo;
    @Autowired
    private HomeWorkServiceImpl homeWorkService;
    @Autowired
    private ClassInfoServiceImpl classInfoService;

    /**
     * 获取用户发布作业时的一些参数
     * @param
     * @return
     */
    @RequestMapping(value = "/homework_fb")
    public void addHomework(@RequestBody Map<String,String> map, HttpServletRequest httpServletRequest, HttpServletResponse response) throws IOException {

        HomeWork homeWork=new HomeWork();
        String openid=map.get("openid");
        userInfo=userInfoMapper.selectByPrimaryKey(openid);

        homeWork.setOpenid(openid);
        homeWork.setPublisher(userInfo.getNickname());
        homeWork.setTargetclass(map.get("classNo"));
        homeWork.setTitle(map.get("title"));
        homeWork.setDeadline(map.get("endTime"));

        String[] url=map.get("imagesUrl").split(" ");
        int length="http://p2zhcnn8g.bkt.clouddn.com/".length();
        String finalurl="";
        for(int i=0;i<url.length;i++)
        {
            if(!url[i].equals(""))
            {
                finalurl+=url[i].substring(length,url[i].length());
                finalurl+=" ";
            }

        }
        homeWork.setUrl(finalurl);
        SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String publishtime=dateFormat.format(new Date());
        homeWork.setPublishtime(publishtime);
        homeWork.setContent(map.get("content"));

        homeWorkService.addHomeWork(homeWork);
        response.setContentType("text/json;charset=UTF-8");
        response.setHeader("Cache-Control", "no-cache");
        String ans="{\"url\":\"gabear.free.ngrok.cc/work\",\"openid\":\""+openid+"\"}";
        response.getWriter().write(ans);
        //return  new ModelAndView(new RedirectView("http://gabear.free.ngrok.cc/sureInfo?openid="+openid));
    }
    /**
     * 用户发布的作业
     * @param
     * @return
     */
    @RequestMapping(value = "/homework_dilivery")
    public void dilivery(@RequestBody Map<String,String> map,HttpServletRequest httpServletRequest, HttpServletResponse response) throws IOException {

        String openid=map.get("openid");
        String ans="{\"data\":[";
        List<HomeWork> res=new ArrayList<HomeWork>();
        res=homeWorkService.queryHomeWork_publisher(openid);
        for(int i=res.size()-1;i>-1;i--)
        {
            JSONObject jsonObject=JSONObject.fromObject(res.get(i));
            ans+=jsonObject.toString();
            ans+=",";
        }
        if(!ans.equals("{\"data\":["))
            ans=ans.substring(0,ans.length()-1);
        ans+="]}";
        response.setContentType("text/json;charset=UTF-8");
        response.setHeader("Cache-Control", "no-cache");
        response.getWriter().write(ans);
    }
    /**
     * 用户收到的作业
     * @param
     * @return
     */
    @RequestMapping(value = "/homework_receive")
    public void receive(@RequestBody Map<String,String> map, HttpServletRequest httpServletRequest, HttpServletResponse response) throws IOException {
        String openid=map.get("openid");
        Object object=classInfoService.getJoinClassesInfo(openid).getData();
        List<ClassInfo> res=(ArrayList)object;
        String ans="{\"data\":[";
        for(ClassInfo classInfo:res)
        {
            List<HomeWork> tem=homeWorkService.queryHomeWork_user(classInfo.getClassId().toString());
            for(int i=tem.size()-1;i>-1;i--)
            {
                JSONObject jsonObject=JSONObject.fromObject(tem.get(i));
                ans+=jsonObject.toString();
                ans+=",";
            }

        }
        if(!ans.equals("{\"data\":["))
            ans=ans.substring(0,ans.length()-1);
        ans+="]}";
        response.setContentType("text/json;charset=UTF-8");
        response.setHeader("Cache-Control", "no-cache");
        response.getWriter().write(ans);
    }
    @RequestMapping(value = "/homework_edit")
    public void edithomework(@RequestBody Map<String,String> map, HttpServletRequest httpServletRequest, HttpServletResponse response) throws IOException {
        String openid=map.get("openid");
        Object object=classInfoService.getMyClassesInfo(openid).getData();
        List<ClassInfo> res=(ArrayList)object;
        String ans="{\"data\":[";
        for(ClassInfo classInfo:res)
        {
            JSONObject jsonObject=JSONObject.fromObject(classInfo);
            ans+=jsonObject.toString();
            ans+=",";
        }
        String uptoken= CreateUpToken.getUpToken();
        if(!ans.equals("{\"data\":["))
            ans=ans.substring(0,ans.length()-1);
        ans+="],";
        ans+="\"uptoken\":\""+uptoken+"\"}";
        response.setContentType("text/json;charset=UTF-8");
        response.setHeader("Cache-Control", "no-cache");
        response.getWriter().write(ans);
    }

    @RequestMapping(value = "/homework_detail")
    public void homeworkdetail(@RequestBody Map<String,String> map, HttpServletRequest httpServletRequest, HttpServletResponse response) throws IOException {
        String homeworkid=map.get("id");
        HomeWork homeWork=new HomeWork();
        homeWork=homeWorkService.queryHomeWork(homeworkid);
        String[] files=homeWork.getUrl().split(" ");
        String finalurl="";
        for(String file:files)
        {
            if(file.equals(""))
                continue;
            finalurl+=CreateDownLoadUrl.getDownLoadUrl(file);
            finalurl+=" ";
        }
        homeWork.setUrl(finalurl);
        JSONObject jsonObject=JSONObject.fromObject(homeWork);
        String json=jsonObject.toString();
        response.setContentType("text/json;charset=UTF-8");
        response.setHeader("Cache-Control", "no-cache");
        response.getWriter().write(json);
    }
    @RequestMapping(value = "/homework_remove")
    public void homeworkremove(@RequestBody Map<String,String> map, HttpServletRequest httpServletRequest, HttpServletResponse response) throws IOException {
        String homeworkID=map.get("homeworkid");
        HomeWork homeWork=homeWorkService.queryHomeWork(homeworkID);
        String files=homeWork.getUrl();
        String[] filename=files.split(" ");
        for(int i=0;i<filename.length;i++)
        {
            homeWorkService.removeHomeWorkfiles(filename[i]);
        }
        homeWorkMapper.deleteByPrimaryKey(Integer.parseInt(homeworkID));
    }
}
