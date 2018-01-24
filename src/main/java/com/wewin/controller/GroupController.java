package com.wewin.controller;


import com.wewin.entity.GroupInfo;
import com.wewin.entity.UserInfo;
import com.wewin.service.GroupInfoService;
import com.wewin.service.Impl.UserInfoServiceImpl;
import com.wewin.service.UserInfoService;
import com.wewin.util.JSONResult;
import net.sf.json.JSON;
import org.apache.ibatis.jdbc.Null;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@RequestMapping("/member")
public class GroupController {

    @Autowired
    private GroupInfoService groupInfoService;

    @Autowired
    private GroupInfo groupInfo;

    /**
     * 查找该班级所有分组
     * @param
     * @return
     */
    @RequestMapping(value = "/findgroups" ,method={RequestMethod.GET,RequestMethod.POST})
    public  JSONResult findGroups(Integer classId) throws IOException {
        return groupInfoService. getClassGroupsInfo(classId);


    }
    /**
     * 查找分组所有成员
     * @param
     * @return
     */
    @RequestMapping(value = "/findusers" ,method={RequestMethod.GET,RequestMethod.POST})
    public  String findGroupUsers(Integer groupId) throws IOException {
        return groupInfoService.findGroupMembers(groupId).toString();




    }
    /**
     * 新增分组
     * 权限：默认为普通组9 所有成员0 创建者1 老师2 助教3
     * @param
     * @return
     */
    @RequestMapping(value = "/addgroup" ,method={RequestMethod.GET,RequestMethod.POST})
    public  JSONResult addGroup(String groupName,Integer classId) throws IOException {
        GroupInfo c = new GroupInfo();
            c.setClassId(classId);
            c.setGroupName(groupName);
            c.setMemberSize(0);
            c.setGroupAuth(9);
            return groupInfoService.addGroup(c);
    }




    /**
     * 删除多个分组
     * @param
     * @return
     */
    @RequestMapping(value = "/deletegroup" ,method={RequestMethod.GET,RequestMethod.POST})
    public JSONResult deleteGroup(String[] groupIdList) throws IOException {
        for(String groupId:groupIdList){
            groupInfoService.deleteGroupMembers(Integer.parseInt(groupId));
            groupInfoService.deleteGroup(Integer.parseInt(groupId));
        }

        return(new JSONResult(Boolean.TRUE,"delete groups success"));

    }

    /**
     * 小组增加新成员
     * @param
     * @return
     */
    @RequestMapping(value = "/addgroup_members" ,method={RequestMethod.GET,RequestMethod.POST})
    public  JSONResult addGroupMembers(Integer groupId, String[] openIdList) throws IOException {
        return groupInfoService. addGroupMembers(groupId,openIdList);




    }
    /**
     * 小组删除成员
     * @param
     * @return
     */
    @RequestMapping(value = "/deletemember" ,method={RequestMethod.GET,RequestMethod.POST})
    public JSONResult deleteGroupMember(Integer groupId, String openid) throws IOException {
        return groupInfoService. deleteGroupMember(groupId,openid);
    }





    /**
     * 小组查看成员的班级名片
     * @param
     * @return
     */
    @RequestMapping(value = "/findmember_ingroup" ,method={RequestMethod.GET,RequestMethod.POST})
    public  JSONResult findMemberInfo(String openid) throws IOException {
        JSONResult result;
        UserInfoService userInfoService = new UserInfoServiceImpl();

        UserInfo userInfo = userInfoService.GetUserInfo(openid);
        if(userInfo!= null){
            result = new JSONResult(userInfo);
        }else {
            result = new JSONResult(Boolean.FALSE,"not found userinfo");
        }
       return result;


    }
    /**
     * 获取班级全部组以及组中成员
     */
    @RequestMapping(value = "/findgroup_and_members", method = { RequestMethod.GET, RequestMethod.POST })
    public JSONResult findMemberInfo(HttpServletRequest request) throws IOException {
        Integer classId = Integer.valueOf(request.getParameter("classId"));
        return groupInfoService.findGroupAndMembers(classId);
    }



}
