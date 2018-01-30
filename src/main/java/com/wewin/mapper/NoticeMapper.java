package com.wewin.mapper;

import com.wewin.entity.Notice;
import com.wewin.entity.NoticeDetail;
import com.wewin.entity.NoticeOverview;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoticeMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table notice
     *
     * @mbggenerated Wed Jan 24 13:41:17 CST 2018
     */
    int deleteByPrimaryKey(Integer id);


    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table notice
     *
     * @mbggenerated Wed Jan 24 13:41:17 CST 2018
     */
    int insert(Notice record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table notice
     *
     * @mbggenerated Wed Jan 24 13:41:17 CST 2018
     */
    int insertSelective(Notice record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table notice
     *
     * @mbggenerated Wed Jan 24 13:41:17 CST 2018
     */
    Notice selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table notice
     *
     * @mbggenerated Wed Jan 24 13:41:17 CST 2018
     */
    int updateByPrimaryKeySelective(Notice record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table notice
     *
     * @mbggenerated Wed Jan 24 13:41:17 CST 2018
     */
    int updateByPrimaryKey(Notice record);

    /**
     * 查找某用户发布的所有公告
     */
    List<NoticeOverview> selectCreatedNotices(String openid);

    /**
     * 查找公告详情
     */
    List<NoticeDetail> selectNoticeDetail(Integer noticeid);

    /**
     * 查找某个班级的所有公告
     * @param classId
     * @return
     */
    List<NoticeOverview> selectClassNotices(Integer classId);
}