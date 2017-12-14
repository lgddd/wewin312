package com.wewin.controller;


import com.wewin.entity.GroupInfo;

import com.wewin.service.GroupInfoService;
import com.wewin.service.Impl.ClassInfoServiceImpl;
import com.wewin.service.Impl.GroupInfoServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@RequestMapping("/clsss/member")
public class GroupController {


    private GroupInfoService groupInfoService=new GroupInfoServiceImpl();
    private GroupInfo groupInfo = new GroupInfo();
    /**
     * 查找该班级所有分组
     * @param
     * @return
     */
    @RequestMapping(value = "/findgroups" ,method={RequestMethod.GET,RequestMethod.POST})
    public  void findGroups(HttpServletRequest request, HttpServletResponse response) throws IOException {
    }
    /**
     * 查找分组所有成员
     * @param
     * @return
     */
    @RequestMapping(value = "/findusers" ,method={RequestMethod.GET,RequestMethod.POST})
    public  void findGroupUsers(HttpServletRequest request, HttpServletResponse response) throws IOException {
    }
    /**
     * 新增分组
     * @param
     * @return
     */
    @RequestMapping(value = "/addgroup" ,method={RequestMethod.GET,RequestMethod.POST})
    public  void addGroup(HttpServletRequest request, HttpServletResponse response) throws IOException {
    }
    /**
     * 删除多个分组
     * @param
     * @return
     */
    @RequestMapping(value = "/deletegroup" ,method={RequestMethod.GET,RequestMethod.POST})
    public  void deleteGroup(HttpServletRequest request, HttpServletResponse response) throws IOException {
    }
    /**
     * 小组增加新成员
     * @param
     * @return
     */
    @RequestMapping(value = "/addgroup_members" ,method={RequestMethod.GET,RequestMethod.POST})
    public  void addGroupMembers(HttpServletRequest request, HttpServletResponse response) throws IOException {
    }
    /**
     * 小组删除成员
     * @param
     * @return
     */
    @RequestMapping(value = "/deletemember" ,method={RequestMethod.GET,RequestMethod.POST})
    public  void deleteGroupMember(HttpServletRequest request, HttpServletResponse response) throws IOException {
    }
    /**
     * 小组查看成员的班级名片
     * @param
     * @return
     */
    @RequestMapping(value = "/findmember_ingroup" ,method={RequestMethod.GET,RequestMethod.POST})
    public  void findMemberInfo(HttpServletRequest request, HttpServletResponse response) throws IOException {
    }
    }
