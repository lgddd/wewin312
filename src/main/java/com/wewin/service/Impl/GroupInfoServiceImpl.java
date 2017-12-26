package com.wewin.service.Impl;

import com.wewin.entity.GroupInfo;
import com.wewin.entity.GroupMemberLink;
import com.wewin.entity.UserInfo;
import com.wewin.mapper.GroupInfoMapper;
import com.wewin.service.GroupInfoService;
import com.wewin.util.JSONResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GroupInfoServiceImpl implements GroupInfoService {
    @Autowired
    private GroupInfo groupInfo;
    @Autowired
    private GroupInfoMapper groupInfoMapper;


    /**
     * 根据classid查询班级们所有分组
     * @param classid
     * @return
     */

    public JSONResult  getClassGroupsInfo(String classid){
        List<GroupInfo> list = groupInfoMapper.selectByClassId(Integer.parseInt(classid));
        JSONResult result;
        if(list!=null){
            result = new JSONResult(list);
        }else{
            result = new JSONResult(Boolean.FALSE,"not found list of groupInfo");
        }
        return result;
    }

    /**
     * 新建分组
     * @param newGroup
     * @return
     */
    public JSONResult addGroup(GroupInfo newGroup){
        JSONResult result;
        if(groupInfoMapper.insertSelective(newGroup)!=0){
            result = new JSONResult(Boolean.TRUE,"add group success");
        }else {
            result = new JSONResult(Boolean.FALSE,"add group failed");
        }
        return result;
    };

    /**
     * 创建班级时新建默认分组 所有成员
     */
    public void addAllMemberGroup(int classId){
        GroupInfo c = new GroupInfo();
        c.setClassId(classId);
        c.setGroupName("所有成员");
        c.setMemberSize(1);
        c.setGroupAuth(0);
        addGroup(c);
    }

    /**
     * 删除分组
     * @param
     * @return
     */
    public int deleteGroup(Integer groupId){
        if(groupInfoMapper.deleteGroup(groupId)!=0){
            return 1;
        }else return 0;
    };



    /**
     * 小组新增多个新成员
     * @param
     * @return
     */
    public JSONResult addGroupMembers(Integer groupId,String[] openids){


        JSONResult result = null;
        List<GroupMemberLink> links = new ArrayList<GroupMemberLink>();

        for (String id:openids){
            GroupMemberLink link = new GroupMemberLink();
            link.setGroupid(groupId);
            link.setUserid(id);
            links.add(link);
        }
        int size = links.size();
        if(groupInfoMapper.insertmenbers(links)!=0){

            GroupInfo groupInfo = getClassGroupsInfo(groupId);
            groupInfo.setMemberSize(groupInfo.getMemberSize()+size);
            groupInfoMapper.updateByPrimaryKeySelective(groupInfo);
           result = new JSONResult(Boolean.TRUE,"add groupmember success");

        }else {
            result = new JSONResult(Boolean.FALSE,"add groupmember failed");
        }
        return result;
    }


    /**
     * 小组删除一个成员
     * @param
     * @return
     */
    public  JSONResult deleteGroupMember(Integer groupId,String openid){

        JSONResult result = null;
        GroupMemberLink link = new GroupMemberLink();
        link.setUserid(openid);
        link.setGroupid(groupId);
        if(groupInfoMapper.deletemember(link)!=0){
            GroupInfo groupInfo = getClassGroupsInfo(groupId);
            groupInfo.setMemberSize(groupInfo.getMemberSize()-1);
            groupInfoMapper.updateByPrimaryKeySelective(groupInfo);
            result = new JSONResult(Boolean.TRUE,"delete groupmember success");
        }else {
            result = new JSONResult(Boolean.FALSE,"delete groupmember failed");
        }
        return result;
    }



    /**
     * 删除小组全部成员 删除成功返回1 失败返回0
     * @param
     * @return
     */
    public int deleteGroupMembers(Integer groupId){
        if(groupInfoMapper.deletemembers(groupId)!=0){return 1;}
        else return 0;
    }



    /**
     * 查找小组全部成员
     * @param
     * @return
     */
    public JSONResult findGroupMembers(Integer groupId){
        JSONResult result = null;
        List<UserInfo> list = groupInfoMapper.selectGroupMembers(groupId);
        if(list !=null){result = new JSONResult(list);}
        else{
            result = new JSONResult(Boolean.FALSE,"Not found member");
        }
        return result;

    }
    /**
     *
     * 查找小组信息
     */
    public GroupInfo  getClassGroupsInfo(int groupid){
       return  groupInfoMapper.selectByPrimaryKey(groupid);
    }
}
