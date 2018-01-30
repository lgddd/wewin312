package com.wewin.mapper;

import com.wewin.entity.ClassInfo;
import com.wewin.entity.ClassInfoExample;
import java.util.List;
import java.util.Map;

import com.wewin.entity.GroupMemberLink;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ClassInfoMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table class
     *
     * @mbggenerated Wed Dec 13 20:47:36 CST 2017
     */
    int countByExample(ClassInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table class
     *
     * @mbggenerated Wed Dec 13 20:47:36 CST 2017
     */
    int deleteByExample(ClassInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table class
     *
     * @mbggenerated Wed Dec 13 20:47:36 CST 2017
     */
    int deleteByPrimaryKey(Integer classId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table class
     *
     * @mbggenerated Wed Dec 13 20:47:36 CST 2017
     */
    int insert(ClassInfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table class
     *
     * @mbggenerated Wed Dec 13 20:47:36 CST 2017
     */
    /**
     * 查找加入的班级
    *
    */

    List<ClassInfo>  selectJoinClass(String openid);



    int insertSelective(ClassInfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table class
     *
     * @mbggenerated Wed Dec 13 20:47:36 CST 2017
     */
    List<ClassInfo> selectByExample(ClassInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table class
     *
     * @mbggenerated Wed Dec 13 20:47:36 CST 2017
     */
    ClassInfo selectClassById(@Param("classId") Integer classId);

    /**
     * 根据openid查询班级信息
     */
    List<ClassInfo> selectClassByCreatorId(@Param("openid") String openid);



    int insertmenber(@Param("link") GroupMemberLink link);

    ClassInfo selectByPrimaryKey(Integer classId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table class
     *
     * @mbggenerated Wed Dec 13 20:47:36 CST 2017
     */
    int updateByExampleSelective(@Param("record") ClassInfo record, @Param("example") ClassInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table class
     *
     * @mbggenerated Wed Dec 13 20:47:36 CST 2017
     */
    int updateByExample(@Param("record") ClassInfo record, @Param("example") ClassInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table class
     *
     * @mbggenerated Wed Dec 13 20:47:36 CST 2017
     */
    int updateByPrimaryKeySelective(ClassInfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table class
     *
     * @mbggenerated Wed Dec 13 20:47:36 CST 2017
     */
    int updateByPrimaryKey(ClassInfo record);

    /**
     * 查找班级所有成员openid
     */
    List<String> selectAllMembers(Integer classid);

    /**判断用户是否加入班级
     *
     */
    int selectIsInClass(String openid, Integer classid);

}