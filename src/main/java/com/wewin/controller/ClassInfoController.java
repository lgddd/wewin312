package com.wewin.controller;
import com.wewin.entity.ClassInfo;
import com.wewin.service.ClassInfoService;
import com.wewin.service.Impl.ClassInfoServiceImpl;
import com.wewin.util.JSONResult;
import net.sf.json.JSONObject;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import java.io.File;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/class")
public class ClassInfoController {
    @Autowired
    private ClassInfoService classInfoService;
    @Autowired
    private  ClassInfo classInfo;

     /**
      * 查找加入的班级的信息
	 * @param
	 * @return
       */
    @RequestMapping(value = "/detail/findclass" ,method={RequestMethod.GET,RequestMethod.POST})
    public  void showClassInfo(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String openId=request.getParameter("classid");
        JSONResult result = classInfoService.getClassInfo(Integer.parseInt(openId));
        response.getWriter().write(result.toString());
    }
    /**
     * 查找创建的班级的信息
     * @param
     * @return
     */
    @RequestMapping(value = "/setting/findclass" ,method={RequestMethod.GET,RequestMethod.POST})
    public  void showMyClassInfo(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String openId=request.getParameter("classid");
        JSONResult result = classInfoService.getClassInfo(Integer.parseInt(openId));
        response.getWriter().write(result.toString());
    }

    /**
     * 修改创建的班级的信息 修改班级名称
     * @param
     * @return
     */
    @RequestMapping(value = "/setting/update" ,method={RequestMethod.GET,RequestMethod.POST})
    public  void editMyClassInfo(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String classname=request.getParameter("classname");
        String classId=request.getParameter("classId");
        JSONResult result = classInfoService.updateClass(Integer.parseInt(classId),classname);
        response.getWriter().write(result.toString());
    }

    /**
     * 查询用户创建的班级列表 (传入 openid 返回 List<ClassInfo>)
     * @param
     * @return
     */
    @RequestMapping(value = "/findclasses_create" ,method={RequestMethod.GET,RequestMethod.POST})
    public  void findMyClassesInfo(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String openId=request.getParameter("openid");
        JSONResult result = classInfoService.getMyClassesInfo(openId);
        response.getWriter().write(result.toString());
    }

    /**
     * 查询用户加入的班级列表 (传入 openid 返回 List<ClassInfo>)
     * @param
     * @return
     */
    @RequestMapping(value = "/findclasses_join" ,method={RequestMethod.GET,RequestMethod.POST})
    public void findClassesInfo(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String openId=request.getParameter("openid");
        JSONResult result = classInfoService.getJoinClassesInfo(openId);
        response.getWriter().write(result.toString());

    }



    /**
     * 创建新班级  传入classname  creatorid
     * @param
     * @return
     */
    @RequestMapping(value = "/new/addclass" ,method={RequestMethod.GET,RequestMethod.POST})
    public void createClasses(HttpServletRequest request, HttpServletResponse response) throws IOException { String openId=request.getParameter("openid");
    String className = request.getParameter("classname");
    String creatorId = request.getParameter("creatorid");
    ClassInfo c = new ClassInfo();
    c.setClassName(className);
    c.setCreatorid(creatorId);
    //c.setClassIcon("");
    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
    c.setCreateTime(df.format(new Date()));

    JSONResult result = classInfoService.addClass(c);
    response.getWriter().write(result.toString());

    }




}
