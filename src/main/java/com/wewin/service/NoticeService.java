package com.wewin.service;
import com.wewin.entity.Notice;
import com.wewin.util.JSONResult;
import net.sf.json.JSON;

public interface NoticeService {

    JSONResult addNotice(Notice notice);

    JSONResult updateNotice(Integer noticeid, String title, String content,String endtime,String pic_url,String mov_url,String file_url);

    JSONResult deleteNotice(String openid, Integer noticeId);

    JSONResult findNoticeDetail(Integer noticeId, String openid);

    JSONResult findReadMember(Integer noticeId);

    JSONResult findUnreadMember(Integer noticeId);

    JSONResult findCreatedNotices(String openid);

    JSONResult findReadNotices(String openid);

    JSONResult findUnreadNotices(String openid);

    JSONResult joinActivity(Integer noticeId, String openid);

    JSONResult findClassNotice(Integer noticeId);

    /**
     *
     * 查找用户是否已经参加活动
     */
    JSONResult isjoinActivity(Integer noticeid, String openid);
}
