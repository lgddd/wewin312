package com.wewin.mapper;

import com.wewin.entity.Notice;
import com.wewin.entity.NoticeMember;
import com.wewin.entity.NoticeOverview;
import com.wewin.entity.UserInfo;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoticeMemberMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table noticemember
     *
     * @mbggenerated Wed Jan 24 13:41:17 CST 2018
     */

    int deleteByPrimaryKey(Integer id);


    /**
     * 删除指定公告对应数据
     */

    int deleteByNoticeId(Integer noticeid);



    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table noticemember
     *
     * @mbggenerated Wed Jan 24 13:41:17 CST 2018
     */
    int insert(NoticeMember record);

    /**
     * 新增多条noticemember
     */
    int insertList(List<NoticeMember> noticemembers);
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table noticemember
     *
     * @mbggenerated Wed Jan 24 13:41:17 CST 2018
     */
    int insertSelective(NoticeMember record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table noticemember
     *
     * @mbggenerated Wed Jan 24 13:41:17 CST 2018
     */
    NoticeMember selectByPrimaryKey(Integer id);


    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table noticemember
     *
     * @mbggenerated Wed Jan 24 13:41:17 CST 2018
     */
    int updateByPrimaryKeySelective(NoticeMember record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table noticemember
     *
     * @mbggenerated Wed Jan 24 13:41:17 CST 2018
     */
    int updateByPrimaryKey(NoticeMember record);

    /**
     * 查找某个公告的已读/已报名人
     * */
    List<UserInfo> selectReadMember(Integer noticeid);


    /**
     * 查找某个公告的未读/未报名人
     * */
    List<UserInfo> selectUnreadMember(Integer noticeid);

    /**
     * 查找某个用户所有已读公告
     * */
    List<NoticeOverview> selectReadNotices(String openid);

    /**
     * 查找某个用户所有未读公告
     * */
    List<NoticeOverview> selectUnreadNotices(String openid);

    /**
     * 更新isread状态
     */
    int updateIsread(String openid, Integer noticeid, Integer isread);

    /**
     * 删除班级相关公告成员信息
     */
    int deleteClassNotice(Integer classid);

    /**
     * 查看用户是否已经参加活动
     * @param noticeid
     * @param openid
     */
    int isjoinActivity(Integer noticeid, String openid);
}