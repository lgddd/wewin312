package com.wewin.controller;
import com.wewin.entity.ClassInfo;
import com.wewin.service.ClassInfoService;
import com.wewin.service.GetQrcodeService;
import com.wewin.service.Impl.ClassInfoServiceImpl;
import com.wewin.util.HttpReq;
import com.wewin.util.JSONResult;
import net.sf.json.JSONObject;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import java.io.File;
import javax.jws.WebParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;
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
    public  JSONResult showClassInfo(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String openId=request.getParameter("classid");
       return classInfoService.getClassInfo(Integer.parseInt(openId));
    }
    /**
     * 查找创建的班级的信息
     * @param
     * @return
     */
    @RequestMapping(value = "/setting/findclass" ,method={RequestMethod.GET,RequestMethod.POST})
    public  JSONResult showMyClassInfo(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String openId=request.getParameter("classid");
        return classInfoService.getClassInfo(Integer.parseInt(openId));

    }

    /**
     * 修改创建的班级的信息 修改班级名称
     * @param
     * @return
     */
    @RequestMapping(value = "/setting/update" ,method={RequestMethod.GET,RequestMethod.POST})
    public  JSONResult editMyClassInfo(HttpServletRequest request, @RequestParam(value = "file", required = false) MultipartFile file,HttpServletResponse response) throws IOException {
        String iconpath = null;
        if (!file.isEmpty()) {

            String uuid = UUID.randomUUID().toString().replaceAll("-", "");
            // 获得文件类型（可以判断如果不是图片，禁止上传）
            String contentType = file.getContentType();
            // 获得文件后缀名称
            String suffix = contentType.substring(contentType.indexOf("/") + 1);

            String tomcat_path = System.getProperty("user.dir");
            String bin_path = tomcat_path.substring(tomcat_path.lastIndexOf("\\")+1,tomcat_path.length());
            //保存路径
            String path;
            if(("bin").equals(bin_path)){
                path = tomcat_path.substring(0,System.getProperty("user.dir").lastIndexOf("\\"))+"\\webapps"+"\\pic_file\\classicon\\"+uuid+".jpg";
            }else{
                path = tomcat_path+"\\webapps"+"\\pic_file\\classicon\\"+uuid+".jpg";
            }


            file.transferTo(new File(path));
            iconpath = uuid + "." + suffix;

        }
        String classname=request.getParameter("classname");
        String classId=request.getParameter("classId");

        return classInfoService.updateClass(Integer.parseInt(classId),classname, iconpath);



    }

    /**
     * 查询用户创建的班级列表 (传入 openid 返回 List<ClassInfo>)
     * @param
     * @return
     */
    @RequestMapping(value = "/findclasses_create" ,method={RequestMethod.GET,RequestMethod.POST})
    public   JSONResult findMyClassesInfo(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String openId=request.getParameter("openid");
        return classInfoService.getMyClassesInfo(openId);
    }

    /**
     * 查询用户加入的班级列表 (传入 openid 返回 List<ClassInfo>)
     * @param
     * @return
     */
    @RequestMapping(value = "/findclasses_join" ,method={RequestMethod.GET,RequestMethod.POST})
    public JSONResult findClassesInfo(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String openId=request.getParameter("openid");
        return  classInfoService.getJoinClassesInfo(openId);


    }



    /**
     * 创建新班级  传入classname  creatorid  返回二维码URL和班级id
     * @param
     * @return
     */

    @RequestMapping(value = "/new/addclass" ,method={RequestMethod.GET,RequestMethod.POST})
    public JSONResult createClasses(HttpServletRequest request, @RequestParam(value = "file", required = false) MultipartFile file,HttpServletResponse response) throws IOException
    { ClassInfo newClassInfo = new ClassInfo();
        String iconpath = "";
        if (!file.isEmpty()) {

        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        // 获得文件类型（可以判断如果不是图片，禁止上传）
        String contentType = file.getContentType();
        // 获得文件后缀名称
        String suffix = contentType.substring(contentType.indexOf("/") + 1);

        String tomcat_path = System.getProperty("user.dir");
        String bin_path = tomcat_path.substring(tomcat_path.lastIndexOf("\\")+1,tomcat_path.length());
        //保存路径
        String path;
        if(("bin").equals(bin_path)){
            path = tomcat_path.substring(0,System.getProperty("user.dir").lastIndexOf("\\"))+"\\webapps"+"\\pic_file\\classicon\\"+uuid+".jpg";
        }else{
            path = tomcat_path+"\\webapps"+"\\pic_file\\classicon\\"+uuid+".jpg";
        }


        file.transferTo(new File(path));
        iconpath = uuid + "." + suffix;

    }
        String className=request.getParameter("classname");
        String openId=request.getParameter("creatorid");
        newClassInfo.setClassIcon(iconpath);
        newClassInfo.setCreateTime(new Date().toString());
        newClassInfo.setCreatorid(openId);
        newClassInfo.setClassName(className);
        newClassInfo.setStudentSize(0);
        newClassInfo.setTeacherSize(1);
        return  classInfoService.addClass(newClassInfo);

    }

    /**
     * 传入参数 type picName(二维码图片名为班级编号)
     * 获取班级二维码 type = 0
     * 获取头像 type = 1
     * */
    @RequestMapping(value = "/findicon", method = { RequestMethod.GET, RequestMethod.POST })
    public void picture(HttpServletRequest request, HttpServletResponse response) {
        String picName = request.getParameter("picName");
        String type = request.getParameter("type");
        if("1"==type){
            type = "qrcode\\";
        }
        else {
            type = "classicon\\";
        }
        type = "qrcode\\";
        response.setContentType("image/png");
        FileInputStream fis = null;
        OutputStream os = null;
        //String request_path = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort();
        //String img_name = request_path+"/pic_file/"+type+picName+".jpg";
        String tomcat_path = System.getProperty("user.dir");
        System.out.println("tomcat_path"+tomcat_path);
        String bin_path = tomcat_path.substring(tomcat_path.lastIndexOf("\\")+1,tomcat_path.length());
        String img_name;
        if(("bin").equals(bin_path)){
            img_name = tomcat_path.substring(0,System.getProperty("user.dir").lastIndexOf("\\"))+"\\webapps"+"\\pic_file\\"+type+picName+".jpg";
            System.out.println("image_path"+img_name);
        }else{
            img_name = tomcat_path+"\\webapps"+"\\pic_file\\"+type+picName+".jpg";
            System.out.println("image_path"+img_name);
        }
        try {
            fis = new FileInputStream( img_name);
            os = response.getOutputStream();
            int count = 0;
            byte[] buffer = new byte[1024 * 8];
            while ((count = fis.read(buffer)) != -1) {
                os.write(buffer, 0, count);
                os.flush();
            }
            fis.close();
            os.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    @RequestMapping(value = "/test2" ,method={RequestMethod.GET,RequestMethod.POST})
    public   JSONResult testClassesInfo2(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ClassInfo newClassInfo = new ClassInfo();
        newClassInfo.setClassName("test");
        newClassInfo.setCreatorid("abc");
        newClassInfo.setCreateTime(new Date().toString());
        newClassInfo.setStudentSize(0);
        newClassInfo.setTeacherSize(0);
        return  classInfoService.addClass(newClassInfo);

    }



}
