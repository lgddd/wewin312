package com.wewin.service.Impl;

import com.wewin.entity.ClassInfo;
import com.wewin.entity.GroupInfo;
import com.wewin.entity.GroupMemberLink;
import com.wewin.entity.UserInfo;
import com.wewin.mapper.ClassInfoMapper;
import com.wewin.mapper.GroupInfoMapper;
import com.wewin.service.GroupInfoService;
import com.wewin.util.JSONResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class GroupInfoServiceImpl implements GroupInfoService {
    @Autowired
    private GroupInfo groupInfo;
    @Autowired
    private GroupInfoMapper groupInfoMapper;
    @Autowired
    private ClassInfoMapper classInfoMapper;
    /**
     * 根据classid查询班级们所有分组
     * @param classid
     * @return
     */

    public JSONResult  getClassGroupsInfo(Integer classid){
        List<GroupInfo> list = groupInfoMapper.selectByClassId(classid);
        JSONResult result;
        if(list!=null){
            result = new JSONResult(list);
        }else{
            result = new JSONResult(Boolean.FALSE,"not found list of groupInfo");
        }
        return result;
    }


    /**
     * 删除班级下的所有分组
     * @param classid
     * @return
     */
    @Override
    @Transactional
    public void deleteClassGroups(Integer classid) {
        List<GroupInfo> list = groupInfoMapper.selectByClassId(classid);
        for (GroupInfo group : list) {
            groupInfoMapper.deleteGroup(group.getGroupId());
        }
    }
    /**
     * 新建分组
     * @param newGroup
     * @return
     */
    @Override
    @Transactional
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
    @Override
    @Transactional
    public void adddefaultMemberGroup(Integer classId){

        GroupInfo allMemberGroup = new GroupInfo();
        allMemberGroup.setClassId(classId);
        allMemberGroup.setGroupName("所有成员");
        allMemberGroup.setMemberSize(1);
        allMemberGroup.setGroupAuth(0);
        addGroup(allMemberGroup);
        GroupInfo TeacherGroup = new GroupInfo();
        TeacherGroup.setClassId(classId);
        TeacherGroup.setGroupName("老师");
        TeacherGroup.setMemberSize(0);
        TeacherGroup.setGroupAuth(1);
        addGroup(TeacherGroup);
        GroupInfo assistantGroup = new GroupInfo();
        assistantGroup.setClassId(classId);
        assistantGroup.setGroupName("助教");
        assistantGroup.setMemberSize(0);
        assistantGroup.setGroupAuth(2);
        addGroup(assistantGroup);
    }

    /**
     * 删除分组
     * @param
     * @return
     */
    @Override
    @Transactional
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
    @Override
    @Transactional
    public JSONResult addGroupMembers(Integer groupId,String[] openids){

        //Integer membernum = openids.length;
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

            GroupInfo groupInfo = getGroupInfo(groupId);
            groupInfo.setMemberSize(groupInfo.getMemberSize()+size);

            groupInfoMapper.updateByPrimaryKeySelective(groupInfo);


            //当小组为老师(1) 助教（2）组 +tsize -ssize
            if(groupInfo.getGroupAuth() == 1||groupInfo.getGroupAuth() ==2){
                ClassInfo targetclass = classInfoMapper.selectClassById(groupInfo.getClassId());
                targetclass.setTeacherSize(targetclass.getTeacherSize()+openids.length);
               // targetclass.setStudentSize(targetclass.getStudentSize()-openids.length);
                classInfoMapper.updateByPrimaryKey(targetclass);
            }

           result = new JSONResult(Boolean.TRUE,"add groupmember success");

        }else {
            result = new JSONResult(Boolean.FALSE,"add groupmember failed");
        }
        return result;
    }


    /**
     * 小组删除一个成员
     *
     * @param
     * @return
     */
    @Override
    @Transactional
    public  JSONResult deleteGroupMember(Integer groupId,String openid){

        JSONResult result = null;
        GroupMemberLink link = new GroupMemberLink();
        link.setUserid(openid);
        link.setGroupid(groupId);
        if(groupInfoMapper.deletemember(link)!=0){
            GroupInfo groupInfo = getGroupInfo(groupId);
            groupInfo.setMemberSize(groupInfo.getMemberSize()-1);
            groupInfoMapper.updateByPrimaryKeySelective(groupInfo);
            //如果是老师组 老师人数-1
            //当小组为老师(1) 助教（2）组 +tsize
            if(groupInfo.getGroupAuth() == 1||groupInfo.getGroupAuth() ==2){
                ClassInfo targetclass = classInfoMapper.selectClassById(groupInfo.getClassId());
                targetclass.setTeacherSize(targetclass.getTeacherSize()-1);
               // targetclass.setStudentSize(targetclass.getStudentSize()+1);
                classInfoMapper.updateByPrimaryKey(targetclass);
            }



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
    @Override
    @Transactional
    public int deleteGroupMembers(Integer groupId){
        if(groupInfoMapper.deletemembers(groupId)!=0){return 1;}
        else return 0;
    }



    /**
     * 查找小组全部成员
     * @param
     * @return
     */
    @Override
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
    @Override
    public GroupInfo  getGroupInfo(int groupid){

        return  groupInfoMapper.selectByPrimaryKey(groupid);
    }

    /**
     * 小组新增一个成员
     */
    @Override
    @Transactional
    public  int addmember(GroupMemberLink link){

        if(groupInfoMapper.insertmenber(link)!=0){

            //加成员成功时，小组size+1
            GroupInfo groupInfo = getGroupInfo(link.getGroupid());
            groupInfo.setMemberSize(groupInfo.getMemberSize()+1);
            groupInfoMapper.updateByPrimaryKeySelective(groupInfo);
           return 1;

        }else return 0;

    }

    /**
     * 查找小组以及小组中的成员
     *
     * @param classId
     * @return
     */
    @Override
    public JSONResult findGroupAndMembers(Integer classId) {
        JSONResult result;
        try {
            result = new JSONResult(groupInfoMapper.selectGroupAndMembers(classId));
        } catch (Exception e) {
            result = new JSONResult(1, e);
        }
        return result;
    }

    /**查找班级的所有成员组
     * */
    public Integer findAllMemberGroup(Integer classId){
        List<GroupInfo> list = groupInfoMapper.selectallmembergroup(classId);
        Integer groupid = null;
        for(GroupInfo groupInfo:list){
            groupid = groupInfo.getGroupId();
        }
        return groupid;
    }

    /**
     * 全部成员组删除某成员
     * @param classid
     * @param groupid
     * @param openid
     * @return
     */
    @Override
    @Transactional
    public JSONResult deletefromclass(Integer classid,Integer groupid,String openid){
        //先判断是否在别的组里 如老师 助教
        //1.先删作业
        //2.再删公告
        //3.再删组关系？？
        groupInfoMapper.deletememberfromclass(classid,groupid,openid);
        //更新组人数
        GroupInfo  groupInfo = groupInfoMapper.selectByPrimaryKey(groupid);
        groupInfo.setMemberSize(groupInfo.getMemberSize()-1);
        groupInfoMapper.updateByPrimaryKeySelective(groupInfo);
        //更新班级全部成员数
        ClassInfo classInfo = classInfoMapper.selectClassById(classid);
        classInfo.setStudentSize(classInfo.getStudentSize()-1);
        classInfoMapper.updateByPrimaryKey(classInfo);



        if(groupInfoMapper.deletememberfromclass(classid,groupid,openid)!=0){
            return  new JSONResult(Boolean.TRUE,"delete from class success");
        }else return new JSONResult(Boolean.FALSE);
    }
}
