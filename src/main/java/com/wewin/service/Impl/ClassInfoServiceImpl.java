package com.wewin.service.Impl;
import com.wewin.entity.ClassInfo;
import com.wewin.mapper.ClassInfoMapper;
import com.wewin.service.ClassInfoService;
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

    /**
     * 根据班级id查询班级信息
     * @param id
     * @return
     */
    public ClassInfo  getClassInfo(Integer id) {
        try{
            classInfo = classInfoMapper.selectClassById(id);
            System.out.println(classInfo.toString());
        }catch (Exception e){
            e.printStackTrace();
        }
        return classInfo;

    }

    /**
     * 根据openid查询创建的班级们
     * @param openid
     * @return
     */
   public List<ClassInfo> getMyClassesInfo(String openid){
       List<Map<String,Object>> list = classInfoMapper.selectClassByCreatorId(openid);
       for(Map<String,Object> row:list){
           System.out.println(row);

       }
       return null;
   }
}
