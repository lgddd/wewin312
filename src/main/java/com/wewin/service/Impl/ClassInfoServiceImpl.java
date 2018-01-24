package com.wewin.service.Impl;
import com.wewin.entity.ClassInfo;
import com.wewin.entity.GroupInfo;
import com.wewin.entity.GroupMemberLink;
import com.wewin.mapper.ClassInfoMapper;
import com.wewin.service.ClassInfoService;
import com.wewin.service.GetQrcodeService;
import com.wewin.service.GroupInfoService;
import com.wewin.util.JSONResult;
import net.sf.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;
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
       Integer classId = newClassInfo.getClassId();
       groupInfoService.adddefaultMemberGroup(classId);
       GroupMemberLink link = new GroupMemberLink();
       link.setUserid(newClassInfo.getCreatorid());
       link.setGroupid(groupInfoService.findAllMemberGroup(classId));
       groupInfoService.addmember(link);
       //System.out.println(param);
       if(param!=0){
           String qrcodeURL = null;
           try {
               qrcodeURL = getQrcodeService.getQrcodeUrl(newClassInfo.getClassId().toString());
           } catch (UnsupportedEncodingException e) {
               e.printStackTrace();
           }
           result = new JSONResult(qrcodeURL);
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



    /**
     * 删除创建的班级（以及班级下的分组）
     * @param
     * @return
     */

    public JSONResult deleteClass(Integer classId){
        JSONResult result;
        //1.删除班级下的所有小组
        groupInfoService.deleteClassGroups(classId);
        //2.删除班级
        if(classInfoMapper.deleteByPrimaryKey(classId)!=0){
            result = new JSONResult(Boolean.TRUE,"delete success");
        }else {
            result = new JSONResult(Boolean.FALSE,"delete failed");
        }
        return  result;
    }
    /**
     *
     * 用户加入班级
     */
    public void addmember(String openid,Integer classid){
        //找到该班级的所有成员组
        Integer groupid = groupInfoService.findAllMemberGroup(classid);
        //加到所有成员组
        GroupMemberLink link = new GroupMemberLink();
        link.setGroupid(groupid);
        link.setUserid(openid);
        groupInfoService.addmember(link);
        //班级人数+1
        ClassInfo classInfo = classInfoMapper.selectClassById(classid);
        classInfo.setStudentSize(classInfo.getStudentSize()+1);
        classInfoMapper.updateByPrimaryKey(classInfo);
    }

}

