package com.wewin.entity;

import org.springframework.stereotype.Component;

@Component
public class GroupMemberLink {

    private String userid;
    private Integer groupid;

    public void setGroupid(Integer groupid) {
        this.groupid = groupid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public Integer getGroupid() {
        return groupid;
    }

    public String getUserid() {
        return userid;
    }
}
