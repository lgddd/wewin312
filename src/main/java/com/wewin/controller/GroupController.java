package com.wewin.controller;


import com.wewin.entity.GroupInfo;
import com.wewin.entity.UserInfo;
import com.wewin.service.GroupInfoService;
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
    private UserInfoService userInfoService;
    @Autowired
    private GroupInfo groupInfo;
    @Autowired
    private UserInfo userInfo;
    /**
     * 查找该班级所有分组
     * @param
     * @return
     */
    @RequestMapping(value = "/findgroups" ,method={RequestMethod.GET,RequestMethod.POST})
    public  JSONResult findGroups(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String classId=request.getParameter("classId");
        return groupInfoService. getClassGroupsInfo(classId);


    }
    /**
     * 查找分组所有成员
     * @param
     * @return
     */
    @RequestMapping(value = "/findusers" ,method={RequestMethod.GET,RequestMethod.POST})
    public  JSONResult findGroupUsers(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String groupId=request.getParameter("groupId");
        return groupInfoService.findGroupMembers(Integer.parseInt(groupId));




    }
    /**
     * 新增分组
     * 权限：0 所有成员 1老师 2助教 3用户自定义分组
     * @param
     * @return
     */
    @RequestMapping(value = "/addgroup" ,method={RequestMethod.GET,RequestMethod.POST})
    public  JSONResult addGroup(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String groupName=request.getParameter("groupname");
        String groupAuth=request.getParameter("groupauth");
        String classId=request.getParameter("classId");
        GroupInfo c = new GroupInfo();
            c.setClassId(Integer.parseInt(classId));
            c.setGroupName(groupName);
            c.setMemberSize(0);
            c.setGroupAuth(Integer.parseInt(groupAuth));
            return groupInfoService.addGroup(c);
    }




    /**
     * 删除多个分组
     * @param
     * @return
     */
    @RequestMapping(value = "/deletegroup" ,method={RequestMethod.GET,RequestMethod.POST})
    public JSONResult deleteGroup(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String[] groupIds=request.getParameterValues("groupIdList");
        for(String groupId:groupIds){
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
    public  JSONResult addGroupMembers(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String groupId=request.getParameter("groupId");
        String[] openids = request.getParameterValues("openIdList");
        return groupInfoService. addGroupMembers(Integer.parseInt(groupId),openids);




    }
    /**
     * 小组删除成员
     * @param
     * @return
     */
    @RequestMapping(value = "/deletemember" ,method={RequestMethod.GET,RequestMethod.POST})
    public JSONResult deleteGroupMember(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String groupId=request.getParameter("groupId");
        String openid = request.getParameter("delete_openId");
        return groupInfoService. deleteGroupMember(Integer.parseInt(groupId),openid);
    }





    /**
     * 小组查看成员的班级名片
     * @param
     * @return
     */
    @RequestMapping(value = "/findmember_ingroup" ,method={RequestMethod.GET,RequestMethod.POST})
    public  JSONResult findMemberInfo(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String openid = request.getParameter("openid");
        JSONResult result;
        userInfo = userInfoService.getUserInfo(openid);
        if(userInfo!= null){
            result = new JSONResult(userInfo);
        }else {
            result = new JSONResult(Boolean.FALSE,"not found userinfo");
        }
       return result;


    }
    }
