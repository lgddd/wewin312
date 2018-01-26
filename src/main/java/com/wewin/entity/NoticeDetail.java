package com.wewin.entity;

import javax.print.DocFlavor;
import java.util.List;

public class NoticeDetail {
    String noticeName;
    Integer noticeType;
    String noticeType2;
    String CreatorId;
    String creatorName;
    String className;
    String createTime;
    Integer readNum;
    String noticeContent;
    String end_time;
    String pic_url;
    String mov_url;
    String file_url;
    List<FileURL> picurls;
    List<FileURL> movurls;
    List<FileURL> fileurls;


    public List<FileURL> getMovurls() {
        return movurls;
    }

    public List<FileURL> getFileurls() {
        return fileurls;
    }

    public List<FileURL> getPicurls() {
        return picurls;
    }

    public void setMovurls(List<FileURL> movurls) {
        this.movurls = movurls;
    }

    public void setPicurls(List<FileURL> picurls) {
        this.picurls = picurls;
    }

    public void setFileurls(List<FileURL> fileurls) {
        this.fileurls = fileurls;
    }

    public String getNoticeType2() {
        return noticeType2;
    }

    public String getNoticeContent() {
        return noticeContent;
    }

    public String getNoticeName() {
        return noticeName;
    }

    public String getEnd_time() {
        return end_time;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public String getCreateTime() {
        return createTime;
    }

    public String getClassName() {
        return className;
    }

    public Integer getNoticeType() {
        return noticeType;
    }

    public Integer getReadNum() {
        return readNum;
    }

    public String getCreatorId() {
        return CreatorId;
    }

    public String getFile_url() {
        return file_url;
    }

    public String getMov_url() {
        return mov_url;
    }

    public String getPic_url() {
        return pic_url;
    }

    public void setNoticeType2(String noticeType2) {
        this.noticeType2 = noticeType2;
    }

    public void setNoticeType(Integer noticeType) {
        this.noticeType = noticeType;
    }

    public void setNoticeName(String noticeName) {
        this.noticeName = noticeName;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public void setNoticeContent(String noticeContent) {
        this.noticeContent = noticeContent;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public void setReadNum(Integer readNum) {
        this.readNum = readNum;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public void setCreatorId(String creatorId) {
        CreatorId = creatorId;
    }

    public void setMov_url(String mov_url) {
        this.mov_url = mov_url;
    }

    public void setPic_url(String pic_url) {
        this.pic_url = pic_url;
    }

    public void setFile_url(String file_url) {
        this.file_url = file_url;
    }
}
