package com.wewin.controller;
import com.wewin.entity.ClassInfo;
import com.wewin.service.ClassInfoService;
import com.wewin.service.Impl.ClassInfoServiceImpl;
import com.wewin.util.JSONResult;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/class")
public class ClassInfoController {
    @Autowired
    private ClassInfoService classInfoService=new ClassInfoServiceImpl();
    @Autowired
    private  ClassInfo classInfo = new ClassInfo();

     /**
      * 查找加入的班级的信息
	 * @param
	 * @return
       */
    @RequestMapping(value = "/detail/findclass" ,method={RequestMethod.GET,RequestMethod.POST})
    public  void showClassInfo(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String classId=request.getParameter("classId");
        Integer id;
        id = new Integer(classId.trim());
        classInfo = classInfoService.getClassInfo(id);
        JSONResult result;
        if(classInfo!=null){
            result = new JSONResult(classInfo);
        }else{
            result = new JSONResult("not found classinfo");
        }

        response.setContentType("text/json;charset=UTF-8");
        response.setHeader("Cache-Control", "no-cache");
        response.getWriter().write(result.toString());
    }
    /**
     * 查找创建的班级的信息
     * @param
     * @return
     */
    @RequestMapping(value = "/setting/findclass" ,method={RequestMethod.GET,RequestMethod.POST})
    public  void showMyClassInfo(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String classId=request.getParameter("classId");
        Integer id;
        id = new Integer(classId.trim());
        classInfo = classInfoService.getClassInfo(id);
        JSONResult result;
        if(classInfo!=null){
            result = new JSONResult(classInfo);
        }else{
            result = new JSONResult("not found classinfo");
        }

        response.setContentType("text/json;charset=UTF-8");
        response.setHeader("Cache-Control", "no-cache");
        response.getWriter().write(result.toString());
    }
    /**
     * 修改创建的班级的信息
     * @param
     * @return
     */
    @RequestMapping(value = "/setting/update" ,method={RequestMethod.GET,RequestMethod.POST})
    public  void editMyClassInfo(HttpServletRequest request, HttpServletResponse response) throws IOException {

    }
    /**
     * 查询用户创建的班级列表
     * @param
     * @return
     */
    @RequestMapping(value = "/findclasses_create" ,method={RequestMethod.GET,RequestMethod.POST})
    public  void findMyClassesInfo(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String openid=request.getParameter("openid");
        List<ClassInfo> classes = new ArrayList();
        classes = classInfoService. getMyClassesInfo(openid);
    }

    /**
     * 查询用户加入的班级列表
     * @param
     * @return
     */
    @RequestMapping(value = "/findclasses_join" ,method={RequestMethod.GET,RequestMethod.POST})
    public void findClassesInfo(HttpServletRequest request, HttpServletResponse response) throws IOException {

    }
    /**
     * 创建新班级
     * @param
     * @return
     */
    @RequestMapping(value = "/new/address" ,method={RequestMethod.GET,RequestMethod.POST})
    public void createClasses(HttpServletRequest request, HttpServletResponse response) throws IOException {

    }

}
