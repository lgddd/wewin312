package com.wewin.entity;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class GroupMembers {
    private Integer groupId;

    private String groupName;

    private Integer classId;

    private Integer memberSize;

    private Integer groupAuth;

    private List<UserInfo> users = new ArrayList<UserInfo>();

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public Integer getClassId() {
        return classId;
    }

    public void setClassId(Integer classId) {
        this.classId = classId;
    }

    public Integer getMemberSize() {
        return memberSize;
    }

    public void setMemberSize(Integer memberSize) {
        this.memberSize = memberSize;
    }

    public Integer getGroupAuth() {
        return groupAuth;
    }

    public void setGroupAuth(Integer groupAuth) {
        this.groupAuth = groupAuth;
    }

    public List<UserInfo> getUsers() {
        return users;
    }

    public void setUsers(List<UserInfo> users) {
        this.users = users;
    }

}
