package com.wewin.entity;

public class NoticeOverview {
    Integer noticeid;
    String noticeName;
    Integer noticeType;
    String noticeType2;
    String creatorName;
    String className;
    String createTime;
    String noticeContent;
    String end_time;

    public Integer getNoticeid() {
        return noticeid;
    }

    public Integer getNoticeType() {
        return noticeType;
    }

    public String getClassName() {
        return className;
    }

    public String getCreateTime() {
        return createTime;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public String getEnd_time() {
        return end_time;
    }

    public String getNoticeName() {
        return noticeName;
    }

    public String getNoticeContent() {
        return noticeContent;
    }

    public String getNoticeType2() {
        return noticeType2;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public void setNoticeContent(String noticeContent) {
        this.noticeContent = noticeContent;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public void setNoticeid(Integer noticeid) {
        this.noticeid = noticeid;
    }

    public void setNoticeName(String noticeName) {
        this.noticeName = noticeName;
    }

    public void setNoticeType(Integer noticeType) {
        this.noticeType = noticeType;
    }

    public void setNoticeType2(String noticeType2) {
        this.noticeType2 = noticeType2;
    }

}
