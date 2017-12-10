package com.wewin.mapper;

import com.wewin.entity.UserInfo;
import com.wewin.entity.UserInfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserInfoMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table yhxx
     *
     * @mbggenerated Sun Dec 10 00:54:45 GMT+08:00 2017
     */
    int countByExample(UserInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table yhxx
     *
     * @mbggenerated Sun Dec 10 00:54:45 GMT+08:00 2017
     */
    int deleteByExample(UserInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table yhxx
     *
     * @mbggenerated Sun Dec 10 00:54:45 GMT+08:00 2017
     */
    int deleteByPrimaryKey(String openid);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table yhxx
     *
     * @mbggenerated Sun Dec 10 00:54:45 GMT+08:00 2017
     */
    int insert(UserInfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table yhxx
     *
     * @mbggenerated Sun Dec 10 00:54:45 GMT+08:00 2017
     */
    int insertSelective(UserInfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table yhxx
     *
     * @mbggenerated Sun Dec 10 00:54:45 GMT+08:00 2017
     */
    List<UserInfo> selectByExample(UserInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table yhxx
     *
     * @mbggenerated Sun Dec 10 00:54:45 GMT+08:00 2017
     */
    UserInfo selectByPrimaryKey(String openid);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table yhxx
     *
     * @mbggenerated Sun Dec 10 00:54:45 GMT+08:00 2017
     */
    int updateByExampleSelective(@Param("record") UserInfo record, @Param("example") UserInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table yhxx
     *
     * @mbggenerated Sun Dec 10 00:54:45 GMT+08:00 2017
     */
    int updateByExample(@Param("record") UserInfo record, @Param("example") UserInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table yhxx
     *
     * @mbggenerated Sun Dec 10 00:54:45 GMT+08:00 2017
     */
    int updateByPrimaryKeySelective(UserInfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table yhxx
     *
     * @mbggenerated Sun Dec 10 00:54:45 GMT+08:00 2017
     */
    int updateByPrimaryKey(UserInfo record);
}