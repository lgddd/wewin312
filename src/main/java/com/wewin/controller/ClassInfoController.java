package com.wewin.controller;
import com.wewin.entity.ClassInfo;
import com.wewin.service.ClassInfoService;
import com.wewin.service.GetQrcodeService;
import com.wewin.util.JSONResult;
import com.wewin.util.QiniuUtil;
import jdk.nashorn.internal.scripts.JS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.UUID;

@Controller
@RequestMapping("/class")
public class ClassInfoController {

    //public static final String CLASS_ICON_PATH = "F:/clases/wewin/background/classicon/";

    @Autowired
    private ClassInfoService classInfoService;
    @Autowired
    private GetQrcodeService getQrcodeService;
    @Autowired
    private  ClassInfo classInfo;

     /**
      * 查找加入的班级的信息
	 * @param
	 * @return
       */
    @RequestMapping(value = "/detail/findclass" ,method={RequestMethod.GET,RequestMethod.POST})
    public  JSONResult showClassInfo(Integer classId) throws IOException {
        JSONResult result = classInfoService.getClassInfo(classId);
        System.out.println(result.toString());
       return result;
    }

    /**
     * 查找创建的班级的信息
     * @param
     * @return
     */
    @RequestMapping(value = "/setting/findclass" ,method={RequestMethod.GET,RequestMethod.POST})
    public  JSONResult showMyClassInfo(Integer classId) throws IOException {
        return classInfoService.getClassInfo(classId);

    }

    /**
     * 修改创建的班级的信息 修改班级名称
     * @param
     * @return
     */
    @RequestMapping(value = "/setting/update" ,method={RequestMethod.GET,RequestMethod.POST})
    public  JSONResult editMyClassInfo(String classname,Integer classId,  String iconpath) throws IOException {

        return classInfoService.updateClass(classId,classname,iconpath);
    }

    /**
     * 查询用户创建的班级列表 (传入 openid 返回 List<ClassInfo>)
     * @param
     * @return
     */
    @RequestMapping(value = "/findclasses_create" ,method={RequestMethod.GET,RequestMethod.POST})
    public JSONResult findMyClassesInfo(String openid) throws IOException {
       return classInfoService.getMyClassesInfo(openid);

    }

    /**
     * 查询用户加入的班级列表 (传入 openid 返回 List<ClassInfo>)
     * @param
     * @return
     */
    @RequestMapping(value = "/findclasses_join" ,method={RequestMethod.GET,RequestMethod.POST})
    public JSONResult findClassesInfo(String openid) throws IOException {
        return  classInfoService.getJoinClassesInfo(openid);
    }

    /**
     * 创建新班级  传入classname  creatorid  返回二维码URL和班级id
     * @param
     * @return
     */
    @RequestMapping(value = "/new/addclass" ,method={RequestMethod.GET,RequestMethod.POST})
    public JSONResult createClasses(String classname,String creatorid, String iconpath,HttpServletResponse response) throws IOException
    { ClassInfo newClassInfo = new ClassInfo();
        newClassInfo.setClassIcon(iconpath);
        newClassInfo.setCreateTime(new Date().toString());
        newClassInfo.setCreatorid(creatorid);
        newClassInfo.setClassName(classname);
        newClassInfo.setStudentSize(0);
        newClassInfo.setTeacherSize(0);
        return  classInfoService.addClass(newClassInfo);

    }

//    /**
//     * 查询二维码图片：直接从服务器传
//     *
//     * 传入参数 type picName(二维码图片名为班级编号)
//     * 获取班级二维码 type = 1
//     * 获取头像 type = 0
//     * */
//    @RequestMapping(value = "/findicon", method = { RequestMethod.GET, RequestMethod.POST })
//    public void picture(HttpServletRequest request, HttpServletResponse response) {
//        String picName = request.getParameter("picName");
//        //String type = request.getParameter("type");
//        //if("1"==type){
//        String  type = "qrcode\\";
//        //}
//        //else {
//          //  type = "classicon\\";
//        //}
//        response.setContentType("image/png");
//        FileInputStream fis = null;
//        OutputStream os = null;
//        //String request_path = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort();
//        //String img_name = request_path+"/pic_file/"+type+picName+".jpg";
//        String tomcat_path = System.getProperty("user.dir");
//        System.out.println("tomcat_path"+tomcat_path);
//        String bin_path = tomcat_path.substring(tomcat_path.lastIndexOf("\\")+1,tomcat_path.length());
//        String img_name;
//        if(("bin").equals(bin_path)){
//            img_name = tomcat_path.substring(0,System.getProperty("user.dir").lastIndexOf("\\"))+"\\webapps"+"\\pic_file\\"+type+picName;
//            System.out.println("image_path"+img_name);
//        }else{
//            img_name = tomcat_path+"\\webapps"+"\\pic_file\\"+type+picName;
//            System.out.println("image_path"+img_name);
//        }
//        try {
//            fis = new FileInputStream( img_name);
//            os = response.getOutputStream();
//            int count = 0;
//            byte[] buffer = new byte[1024 * 8];
//            while ((count = fis.read(buffer)) != -1) {
//                os.write(buffer, 0, count);
//                os.flush();
//            }
//            fis.close();
//            os.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    /**
     *
     * 删除班级

     */

    @RequestMapping(value = "/deleteclass" ,method={RequestMethod.GET,RequestMethod.POST})
    public   JSONResult deleteClass(Integer classId)  {
        return  classInfoService.deleteClass(classId);

    }




}
