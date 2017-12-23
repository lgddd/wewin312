package com.wewin.service;

import com.wewin.entity.GroupInfo;
import com.wewin.util.JSONResult;
import net.sf.json.JSON;

import java.util.List;

public interface GroupInfoService {
    /**
     * 根据classid查询班级的所有分组
     * @param openid
     * @return
     */
    JSONResult getClassGroupsInfo(String openid);

    /**
     * 新建分组
     * @param newGroup
     * @return
     */
    JSONResult addGroup(GroupInfo newGroup);

    /**
     * 小组新增多个新成员
     * @param
     * @return
     */
    JSONResult addGroupMembers(Integer groupId, String[] openids);

    /**
     * 小组删除一个成员
     * @param
     * @return
     */
    JSONResult deleteGroupMember(Integer groupId,String openid);


    /**
     * 删除小组全部成员
     * @param
     * @return
     */
    int deleteGroupMembers(Integer groupId);


    /**
     * 删除分组
     * @param
     * @return
     */
    int deleteGroup(Integer groupId);

    /**
     * 查找小组全部成员
     * @param
     * @return
     */
    JSONResult findGroupMembers(Integer groupId);



}
