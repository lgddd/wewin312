package com.wewin.service;
import com.wewin.entity.ClassInfo;

import java.util.List;

public interface ClassInfoService {
    /**
     * 根据班级id查询班级信息
     * @param id
     * @return
     */
    ClassInfo  getClassInfo(Integer id);

    /**
     * 根据openid查询创建的班级们
     * @param openid
     * @return
     */
    List<ClassInfo> getMyClassesInfo(String openid);
}
