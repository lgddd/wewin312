package com.wewin.service.Impl;
import com.wewin.entity.ClassInfo;
import com.wewin.entity.GroupInfo;
import com.wewin.mapper.ClassInfoMapper;
import com.wewin.service.ClassInfoService;
import com.wewin.service.GetQrcodeService;
import com.wewin.service.GroupInfoService;
import com.wewin.util.JSONResult;
import net.sf.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service
public  class ClassInfoServiceImpl implements ClassInfoService {
    @Autowired
    private ClassInfo classInfo;
    @Autowired
    private ClassInfoMapper classInfoMapper;
    @Autowired
    private GetQrcodeService getQrcodeService;
    @Autowired
    private GroupInfoService groupInfoService;
    /**
     * 根据班级id查询班级信息
     * @param id
     * @return
     */
    public JSONResult  getClassInfo(Integer id) {
        ClassInfo classinfo= classInfoMapper.selectClassById(id);
        JSONResult result;
        if(classinfo!=null){
            result = new JSONResult(classinfo);
        }else{
            result = new JSONResult(Boolean.FALSE,"not found classinfo");
        }
        return result;

    }

    /**
     * 根据openid查询创建的班级们
     * @param openid
     * @return
     */
   public JSONResult getMyClassesInfo(String openid){
       List<ClassInfo> list = classInfoMapper.selectClassByCreatorId(openid);
       JSONResult result;
       if(list!=null){
           result = new JSONResult(list);
       }else{
           result = new JSONResult(Boolean.FALSE,"not found list of classinfo");
       }
       return result;
   }



    /**
     * 根据openid查询加入的班级们
     * @param openid
     * @return
     */
    public JSONResult getJoinClassesInfo(String openid){
        List<ClassInfo> list = classInfoMapper.selectJoinClass(openid);
        JSONResult result;
        if(list!=null){
            result = new JSONResult(list);
        }else{
            result = new JSONResult(Boolean.FALSE,"not found list of classinfo");
        }
        return result;

    }


    /**
     * 创建新班级 创建成功返回班级二维码URL
     * @param
     * @return
     */
   public JSONResult addClass(ClassInfo newClassInfo){
       JSONResult result;
       int param = classInfoMapper.insert(newClassInfo);
       int classId = newClassInfo.getClassId();
       GroupInfoService groupInfoService = new GroupInfoServiceImpl();
       groupInfoService.addAllMemberGroup(classId);
       //System.out.println(param);
       if(param!=0){
           String qrcodeURL = getQrcodeService.getQrcodeUrl(newClassInfo.getClassId().toString());
           result = new JSONResult(Boolean.TRUE,"add success,classid:"+classId+"QrcodeURL"+qrcodeURL);
       }else {
           result = new JSONResult(Boolean.FALSE,"add failed");
       }
        return result;
   }

    /**
     * 更新班级信息
     * @param
     * @return
     */
    public JSONResult updateClass(Integer classid, String classname,String iconpath){
        JSONResult result;
        ClassInfo newClassInfo = classInfoMapper.selectClassById(classid);
        newClassInfo.setClassName(classname);
        if(null!=iconpath) {
            newClassInfo.setClassIcon(iconpath);
        }
        if(classInfoMapper.updateByPrimaryKeySelective(newClassInfo)!=0){
            result = new JSONResult(Boolean.TRUE,"update success");
        }else {
            result = new JSONResult(Boolean.FALSE,"update failed");
        }
        return result;
    }
}

