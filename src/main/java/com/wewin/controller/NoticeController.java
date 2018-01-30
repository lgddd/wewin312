package com.wewin.controller;

import com.wewin.entity.ClassInfo;
import com.wewin.entity.FileURL;
import com.wewin.entity.Notice;
import com.wewin.service.NoticeService;
import com.wewin.util.JSONResult;
import net.sf.json.JSON;
import net.sf.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.Cleaner;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.*;

@Controller
@RequestMapping("/notice")
public class NoticeController {
    /**
     * 参数说明
     * type=1 公告：
     *      isread = 0 未读 isread = 1 已读 readnum 已读人数
     * type=2 消息：
     *      isread = 0 未读 isread = 1 已读 readnum 已读人数
     * type=3 活动：
     *      isread = 0 未读 isread = 1 已读已参加 isread = 2 已读未参加 readnum 已参加人数
     */
    @Autowired
    private NoticeService noticeService;

    @Autowired
    private Notice notice;

    /**
     * 创建新公告  传入 创建者openid，公告名，班级id,，类型 1公告 2通知 3活动，截至日期，文字内容，图片，视频，文件
     * @param
     * @return
     */
    @RequestMapping(value = "/addnotice" ,method={RequestMethod.GET,RequestMethod.POST})
    public JSONResult createNotice(String openid, String title, Integer classid, Integer notice_type, String endtime, String content, String[] url) throws IOException
    {   Notice newNotice = new Notice();
        newNotice.setPublisher(openid);
        newNotice.setContent(content);
        newNotice.setEndtime(endtime);
        newNotice.setNoticeno(notice_type);
        newNotice.setReadNum(0);
        newNotice.setTitle(title);
        newNotice.setTargetclass(classid);
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);



        int day = cal.get(Calendar.DATE);
        newNotice.setPublishtime(year+"-"+month+"-"+day);

        //存JSON?
        List<FileURL> picurls = new ArrayList<FileURL>();
        List<FileURL> movurls = new ArrayList<FileURL>();
        List<FileURL> fileurls = new ArrayList<FileURL>();
        int i = 0,j=0,k=0;
        if(url!=null) {
            for (String u : url) {
                //1.图片 jsp 3.mov 2.file doc
                String type = u.substring(u.lastIndexOf(".") + 1, u.length()).toLowerCase();

                if (type.equals("jpg")||type.equals("jpeg")||type.equals("png")) {
                    picurls.add(new FileURL(i, u));
                    i++;
                } else if (type.equals("mov")) {
                    movurls.add(new FileURL(j, u));
                    j++;
                } else if (type.equals("pdf")) {
                    fileurls.add(new FileURL(k, u));
                    k++;
                } else {
                    return new JSONResult(Boolean.FALSE, "illegal file");
                }
            }
        }
        newNotice.setPicUrl(JSONArray.fromObject(picurls).toString());
        newNotice.setFileUrl(JSONArray.fromObject(fileurls).toString());
        newNotice.setMovUrl(JSONArray.fromObject(movurls).toString());



        return  noticeService.addNotice(newNotice);

    }

    /**
     * 修改公告  传入 创建者openid，公告名，班级id,，类型 1公告 2通知 3活动，截至日期，文字内容，图片，视频，文件
     * @param
     * @return
     */
    @RequestMapping(value = "/updatenotice" ,method={RequestMethod.GET,RequestMethod.POST})
    public JSONResult updateNotice(String title, Integer noticeid,  String endtime, String content, String[] url) throws IOException
    {

    //存JSON?
        List<FileURL> picurls = new ArrayList<FileURL>();
        List<FileURL> movurls = new ArrayList<FileURL>();
        List<FileURL> fileurls = new ArrayList<FileURL>();

        int i = 0,j=0,k=0;
        for(String u:url){
            //1.图片 jsp 3.mov 2.file:doc
            String type = u.substring(u.lastIndexOf(".") + 1, u.length()).toLowerCase();
            if(type.equals("jpg")||type.equals("jpeg")||type.equals("png")){
                picurls.add(new FileURL(i,u));
                i++;
            }else if(type.equals("mov")){
                movurls.add(new FileURL(j,u));
                j++;
            }else if(type.equals("pdf")){
                fileurls.add(new FileURL(k,u));
                k++;
            }else return new JSONResult(Boolean.FALSE,"illegal file");
        }

        String json1= null;
        String json2= null;
        String json3 = null;

        if(picurls.size()!=0){
            json1 = JSONArray.fromObject(picurls).toString();
        }
        if (movurls.size()!=0){
            json2 = JSONArray.fromObject(movurls).toString();
        }
        if (fileurls.size()!=0){
            json3 = JSONArray.fromObject(fileurls).toString();
        }
        return  noticeService.updateNotice(noticeid, title, content,endtime,json1,json2,json3);

    }


    /**
     *
     * 删除公告
     */
    @RequestMapping(value = "/deletenotice" ,method={RequestMethod.GET,RequestMethod.POST})
    public   JSONResult deleteNotice(String openid, Integer noticeId)  {
        return  noticeService.deleteNotice(openid, noticeId);
    }



    /**
     *
     * 查找某个公告的详细内容
     */
    @RequestMapping(value = "/findnotice" ,method={RequestMethod.GET,RequestMethod.POST})
    public   JSONResult findNoticeDetail(Integer noticeId,String openid)  {
        return  noticeService.findNoticeDetail(noticeId, openid);
    }



    /**
     *
     * 参加活动
     */
    @RequestMapping(value = "/join_activity" ,method={RequestMethod.GET,RequestMethod.POST})
    public   JSONResult joinActivity(Integer noticeId,String openid)  {
        return  noticeService.joinActivity(noticeId, openid);
    }

    /**
     *
     * 查找用户是否已经参加活动
     */
    @RequestMapping(value = "/isjoinactivity" ,method={RequestMethod.GET,RequestMethod.POST})
    public   JSONResult isJoinActivity(Integer noticeid,String openid)  {
        return  noticeService.isjoinActivity(noticeid, openid);
    }


    /**
     *
     * 查找某个公告的已读/已报名人
     */
    @RequestMapping(value = "/findreaded" ,method={RequestMethod.GET,RequestMethod.POST})
    public   JSONResult findReadMember(Integer noticeId)  {
        return  noticeService.findReadMember(noticeId);
    }



    /**
     *
     * 查找某个公告的未读/未报名人
     */
    @RequestMapping(value = "/findunreaded" ,method={RequestMethod.GET,RequestMethod.POST})
    public   JSONResult findUnreadMember(Integer noticeId)  {

        return  noticeService.findUnreadMember(noticeId);
    }

    /**
     *
     * 查找该用户发布的所有公告
     */
    @RequestMapping(value = "/find_created_notices" ,method={RequestMethod.GET,RequestMethod.POST})
    public   JSONResult findCreatedNotices(String openid)  {
        JSONResult jsonResult = noticeService.findCreatedNotices(openid);
        return  jsonResult;
    }

    /**
     *
     * 查找该用户所有已读公告
     */
    @RequestMapping(value = "/find_read_notices" ,method={RequestMethod.GET,RequestMethod.POST})
    public   JSONResult findReadNotices(String openid)  {

        return  noticeService.findReadNotices(openid);
    }

    /**
     *
     * 查找该用户所有未读公告
     */
    @RequestMapping(value = "/find_unread_notices" ,method={RequestMethod.GET,RequestMethod.POST})
    public   JSONResult findUnreadNotices(String openid)  {

        return  noticeService.findUnreadNotices(openid);
    }





    /**
     *
     * 查找某个公告的详细内容
     */
    @RequestMapping(value = "/test" ,method={RequestMethod.GET,RequestMethod.POST})
    public   JSONResult testNoticeDetail()  {
       JSONResult result =noticeService.findNoticeDetail(24, "test2");
       System.out.println(result.toString());
        return  result;
    }

}
