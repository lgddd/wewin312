package com.wewin.service;
import com.wewin.entity.ClassInfo;
import com.wewin.entity.UserInfo;
import com.wewin.util.JSONResult;

import java.util.List;

public interface ClassInfoService {
    /**
     * 根据班级id查询班级信息
     * @param id
     * @return
     */
    JSONResult getClassInfo(Integer id);

    /**
     * 根据openid查询创建的班级们
     * @param openid
     * @return
     */
    JSONResult getMyClassesInfo(String openid);


    /**
     * 根据openid查询加入的班级们
     * @param openid
     * @return
     */
    JSONResult getJoinClassesInfo(String openid);
    /**
     * 创建班级,返回创建结果信息
     * @param
     * @return
     */

    JSONResult addClass(ClassInfo newClassInfo);

    /**
     * 修改班级信息,返回修改结果信息
     * @param
     * @return
     */

    JSONResult updateClass(Integer classid, String classname,String iconpath);

    /**
     * 删除创建的班级（以及班级下的分组）
     * @param
     * @return
     */

    JSONResult deleteClass(Integer classId);

    /**
     *
     * 用户加入班级
     */
    void addmember(String openid,Integer classid);


    /**
     * 判断用户是否加入班级
     * @param openid
     * @param classid
     * @return
     */
    int isinclass(String openid, Integer classid);
}
