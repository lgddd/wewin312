package com.wewin.service;

import com.wewin.entity.GroupInfo;
import com.wewin.entity.GroupMemberLink;
import com.wewin.util.JSONResult;
import net.sf.json.JSON;

import java.util.List;

public interface GroupInfoService {
    /**
     * 根据classid查询班级的所有分组
     * @param classid
     * @return
     */
    JSONResult getClassGroupsInfo(Integer classid);



    /**
     * 根据classid查询班级的所有分组
     * @param classid
     * @return
     */
    void deleteClassGroups(Integer classid);

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


    /**
     * 创建班级时新建默认分组 所有成员
     */
    void adddefaultMemberGroup(Integer classId);
    /**
     *
     * 查找小组信息
     */
    GroupInfo  getGroupInfo(int groupid);

    /**
     * 小组新增一个成员
     */
    int addmember(GroupMemberLink link);


    /**
     * 查找小组以及小组中的成员
     *
     * @param
     * @return
     */
    JSONResult findGroupAndMembers(Integer classId);


    /**查找班级的所有成员组
     * */
    Integer findAllMemberGroup(Integer classId);

    /**
     * 全部成员组删除某成员
     * @param classid
     * @param groupid
     * @param openid
     * @return
     */
    JSONResult deletefromclass(Integer classid,Integer groupid,String openid);


}
