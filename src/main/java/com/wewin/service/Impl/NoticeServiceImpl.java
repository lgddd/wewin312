package com.wewin.service.Impl;


import com.wewin.entity.*;
import com.wewin.mapper.ClassInfoMapper;
import com.wewin.mapper.NoticeMapper;
import com.wewin.mapper.NoticeMemberMapper;
import com.wewin.service.NoticeService;
import com.wewin.service.ScanQrcodeService;
import com.wewin.util.JSONResult;
import com.wewin.util.QiniuUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;


@Service
public class NoticeServiceImpl implements NoticeService {

    @Autowired
    private NoticeMapper noticeMapper;

    @Autowired
    private NoticeMemberMapper noticeMemberMapper;

    @Autowired
    private ClassInfoMapper classInfoMapper;

    /**
     *  新增公告
     */

    @Override
    @Transactional
    public JSONResult addNotice(Notice newNotice) {
        JSONResult result;
        //创建新公告
        int param1 = noticeMapper.insert(newNotice);

        //更新noticemember
        //需要openid:查询 公告所在 班级 的 user
        List<String> openids = classInfoMapper.selectAllMembers(newNotice.getTargetclass());

        List<NoticeMember> noticemembers =new ArrayList<NoticeMember>();
        for(String openid :openids){
            NoticeMember nm = new NoticeMember();
            nm.setOpenid(openid);
            nm.setNoticeid(newNotice.getId());
            nm.setRead(0);
            noticemembers.add(nm);
        }


        //公告 通知： 0 未读       1 已读
        //活动：     0 未读 未参加 1 已读 参加 2 已读 不参加（活动）

        int param2 = noticeMemberMapper.insertList(noticemembers);
        if(param1!=0 && param2 !=0){
            result = new JSONResult(Boolean.TRUE,"new notice success");
        }else{
            result = new JSONResult(Boolean.TRUE,"new notice failed");
        }
        return result;
    }


    /**
     *  更新公告
     */
    @Override
    @Transactional
    public JSONResult updateNotice(Integer noticeid, String title, String content,String endtime,String pic_url,String mov_url,String file_url) {
        JSONResult result = null;
        Notice notice = noticeMapper.selectByPrimaryKey(noticeid);
        notice.setTitle(title);
        notice.setContent(content);
        notice.setPicUrl(pic_url);
        notice.setMovUrl(mov_url);
        notice.setFileUrl(file_url);
        notice.setEndtime(endtime);

        if(noticeMapper.updateByPrimaryKeySelective(notice)!=0){
            result = new JSONResult(Boolean.TRUE,"update notice success");
        }else{
            result = new JSONResult(Boolean.FALSE,"update notice failed");
        }
        return result;
    }


    /**
     *  删除公告
     */
    @Override
    @Transactional
    public JSONResult deleteNotice(String openid, Integer noticeId){
        JSONResult result = null;
        //判断openid 是否为创建者
        Notice notice = noticeMapper.selectByPrimaryKey(noticeId);
        if(!(notice.getPublisher().equals(openid))){
            result = new JSONResult(Boolean.FALSE,"illeagal publisher");
        }
        //删除公告成员表中相关信息
        int param1 = noticeMemberMapper.deleteByNoticeId(noticeId);
        //删除公告
        int param2 = noticeMapper.deleteByPrimaryKey(noticeId);

        if(param1!=0 && param2 !=0){
            result = new JSONResult(Boolean.TRUE,"delete notice success");
        }else{
            result = new JSONResult(Boolean.TRUE,"delete notice failed");
        }

        return result;
    }



    /**
     * 转换一下Detail的一些信息
     */
    NoticeDetail TransNotice(NoticeDetail detail){
        //+公告类型
        NoticeDetail d = detail;
        if (d.getNoticeType() == 1){
            d.setNoticeType2("公告");
        }else if(d.getNoticeType()==2)
        {
            d.setNoticeType2("通知");
        }else if(d.getNoticeType()==3)
        {
            d.setNoticeType2("活动");
        }


        List<FileURL> picurls = null;
        List<FileURL> movurls = null;
        List<FileURL> fileurls =null;

        //转换url
        if (d.getPic_url() != "[]"){
            JSONArray jsonArray = JSONArray.fromObject(d.getPic_url());

             picurls = (List)JSONArray.toCollection(jsonArray,FileURL.class);
             Iterator it = picurls.iterator();
             List<FileURL> temp = new ArrayList<FileURL>();
             while(it.hasNext()){
                 FileURL f = (FileURL)it.next();
                 //url转换成七牛云updownloadtoken
                 f.setValue(QiniuUtil.getDownLoadToekn("http://p2zhcnn8g.bkt.clouddn.com/"+f.getValue()));
                 int i =0;
                 temp.add(f);
             }
             picurls = temp;
        }
        if(d.getMov_url() != "[]"){

            JSONArray jsonArray = JSONArray.fromObject(d.getMov_url());

            movurls = (List)JSONArray.toCollection(jsonArray,FileURL.class);

            Iterator it = movurls.iterator();
            List<FileURL> temp = new ArrayList<FileURL>();
            while(it.hasNext()){
                FileURL f = (FileURL)it.next();
                f.setValue(QiniuUtil.getDownLoadToekn(f.getValue()));
                temp.add(f);
            }
            movurls = temp;

        }
        if (d.getFile_url()!= "[]"){

            JSONArray jsonArray = JSONArray.fromObject(d.getFile_url());

            fileurls = (List)JSONArray.toCollection(jsonArray,FileURL.class);

            Iterator it = fileurls.iterator();
            List<FileURL> temp = new ArrayList<FileURL>();
            while(it.hasNext()){
                FileURL f = (FileURL)it.next();
                f.setValue(QiniuUtil.getDownLoadToekn(f.getValue()));
                temp.add(f);
            }
            fileurls = temp;
        }

        d.setPicurls(picurls);
        d.setMovurls(movurls);
        d.setFileurls(fileurls);

        return d;
    }

    /**
     *  查找公告详情
     *      List<NoticeDetail> selectNoticeDetail(Integer noticeid);
     */
    @Override
    @Transactional
    public  JSONResult findNoticeDetail(Integer noticeId, String openid){
        //查找公告
        Notice notice= noticeMapper.selectByPrimaryKey(noticeId);

        JSONResult result = null;

        //当类型为活动时
        if(notice.getNoticeno() == 3){
            //更新noticemember表格 isread = 2 已读未参加
            int param1 = noticeMemberMapper.updateIsread(openid, noticeId, 2);
            NoticeDetail noticeDetail =TransNotice( noticeMapper.selectNoticeDetail(noticeId).get(0));

            //当type=3，readnum记录参加活动的人数 不需更新notice的readnum

            if(param1!=0)
            {
                result = new JSONResult(noticeDetail);
            }else{
                result = new JSONResult(Boolean.FALSE,"error");
            }

        }else{
            //当类型为公告或通知时
            //更新noticemember isread = 1
            int param2 = noticeMemberMapper.updateIsread(openid,noticeId,1);
            //更新已读人数
            notice.setReadNum(notice.getReadNum()+1);
            int param3 = noticeMapper.updateByPrimaryKeySelective(notice);

            //获取公告详情
            NoticeDetail noticeDetail =TransNotice( noticeMapper.selectNoticeDetail(noticeId).get(0));

            if(param2!=0 && param3!=0){
                result = new JSONResult(noticeDetail);
            }else{
                result = new JSONResult(Boolean.FALSE,"error");
            }

        }

        return result;
    }


    /**
     *  参加活动 当公告type = 3, isread = 1 已读已参加 isread =2 已读未参加
     */
    @Override
    @Transactional
    public JSONResult joinActivity(Integer noticeId, String openid){
        JSONResult result = null;

        //noticemember 中 isread 更新为1
        Notice notice = noticeMapper.selectByPrimaryKey(noticeId);

        //更新noticemember表格 isread = 1 已读已参加
        int param1 = noticeMemberMapper.updateIsread(openid, noticeId, 1);
        //更新参加人数

        notice.setReadNum(notice.getReadNum()+1);
        int param2 = noticeMapper.updateByPrimaryKeySelective(notice);


        if(param1!=0 && param2 !=0){
            result = new JSONResult(Boolean.TRUE,"joinActivity success");
        }else{
            result = new JSONResult(Boolean.TRUE," joinActivity failed");
        }

        return result;
    }



    /**
     * 查找某个公告的已读/已报名人
     * */
    @Override
    @Transactional
    public JSONResult findReadMember(Integer noticeId){
        List<UserInfo> users =  noticeMemberMapper.selectReadMember(noticeId);
        JSONResult result;
        if(users!=null){
            result = new JSONResult(users);
        }else{
            result = new JSONResult(Boolean.FALSE,"not found  read member");
        }
        return result;
    }


    /**
     * 查找某个公告的未读/未报名人
     * */
    @Override
    @Transactional
    public JSONResult findUnreadMember(Integer noticeId){
        List<UserInfo> users = noticeMemberMapper.selectUnreadMember(noticeId);
        JSONResult result;
        if(users!=null){
            result = new JSONResult(users);
        }else{
            result = new JSONResult(Boolean.FALSE,"not found  unread member");
        }
        return result;
    }

    /**
     * 查找某个用户发布的所有公告
     * */
    @Override
    @Transactional
    public JSONResult findCreatedNotices(String openid){
        List<NoticeOverview> notices = noticeMapper.selectCreatedNotices(openid);
        for(NoticeOverview notice:notices){
            if(notice.getNoticeType()==1){
                notice.setNoticeType2("公告");
            }else if(notice.getNoticeType()==2){
                notice.setNoticeType2("通知");
            }else if(notice.getNoticeType()==3){
                notice.setNoticeType2("活动");
            }
            else notice.setNoticeType2("error");
        }

        JSONResult result;
        if(notices!=null){
            result = new JSONResult(notices);
        }else{
            result = new JSONResult(Boolean.FALSE,"not found created notices");
        }
        return result;
    }

    /**
     * 查找某个用户收到的所有已读公告
     * */
    @Override
    @Transactional
    public JSONResult findReadNotices(String openid){
        List<NoticeOverview> notices = noticeMemberMapper.selectReadNotices(openid);
        for(NoticeOverview notice:notices){
            if(notice.getNoticeType()==1){
                notice.setNoticeType2("公告");
            }else if(notice.getNoticeType()==2){
                notice.setNoticeType2("通知");
            }else if(notice.getNoticeType()==3){
                notice.setNoticeType2("活动");
            }
            else notice.setNoticeType2("error");
        }


        JSONResult result;
        if(notices!=null){
            result = new JSONResult(notices);
        }else{
            result = new JSONResult(Boolean.FALSE,"not found read notices");
        }
        return result;
    }


    /**
     * 查找某个用户收到的所有未读公告
     * */
    @Override
    @Transactional
    public JSONResult findUnreadNotices(String openid){
        List<NoticeOverview> notices = noticeMemberMapper.selectUnreadNotices(openid);

        for(NoticeOverview notice:notices){
            if(notice.getNoticeType()==1){
                notice.setNoticeType2("公告");
            }else if(notice.getNoticeType()==2){
                notice.setNoticeType2("通知");
            }else if(notice.getNoticeType()==3){
                notice.setNoticeType2("活动");
            }
            else notice.setNoticeType2("error");
        }

        JSONResult result;
        if(notices!=null){
            result = new JSONResult(notices);
        }else{
            result = new JSONResult(Boolean.FALSE,"not found unread notices");
        }
        return result;
    }

}
