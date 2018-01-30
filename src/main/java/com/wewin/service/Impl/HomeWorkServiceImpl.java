package com.wewin.service.Impl;

import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.util.Auth;
import com.wewin.entity.HomeWork;
import com.wewin.mapper.HomeWorkMapper;
import com.wewin.service.HomeWorkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class HomeWorkServiceImpl implements HomeWorkService {

    @Autowired
    private HomeWorkMapper homeWorkMapper;

    public void addHomeWork(HomeWork homeWork) {
        try
        {
            homeWorkMapper.insertSelective(homeWork);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public void rmHomeWork(HomeWork homeWork) {
        try {
            homeWorkMapper.deleteByPrimaryKey(homeWork.getHomeworkno());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void editHomeWork(HomeWork homeWork) {
        try{
            homeWorkMapper.updateByPrimaryKey(homeWork);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public List<HomeWork> queryHomeWork_publisher(String openID) {
        List<HomeWork> list=new ArrayList<HomeWork>();

        try{
            list=homeWorkMapper.selectByOpenid(openID);
        }catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }

    public List<HomeWork> queryHomeWork_user(String classID) {
        List<HomeWork> list=new ArrayList<HomeWork>();
        try{
            list=homeWorkMapper.selectByTargetclass(classID);
        }catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }
    public HomeWork queryHomeWork(String homeworkNO) {
        HomeWork homeWork=new HomeWork();
        try{
            homeWork=homeWorkMapper.selectByPrimaryKey(Integer.parseInt(homeworkNO));
        }catch (Exception e){
            e.printStackTrace();
        }
        return homeWork;
    }
    public void removeHomeWorkfiles(String filename){
        Configuration cfg = new Configuration(Zone.zone0());
        String accessKey = "9aBwTGn1_ILSNCZe2on837z32uF2nqWCfa0xz1il";
        String secretKey = "zxGwCPuNIc863_alpdfKwPfZA9vF7_WnZ_HVKQvz";
        String bucket = "wewin";
        //String key = "your file key";
        Auth auth = Auth.create(accessKey, secretKey);
        BucketManager bucketManager = new BucketManager(auth, cfg);
        try {
            bucketManager.delete(bucket, filename);
        } catch (QiniuException ex) {
            //如果遇到异常，说明删除失败
            System.err.println("++++++++++++"+ex.code()+"++++++++++++++++++");
            System.err.println("++++++++++++++"+ex.response.toString()+"+++++++++++++");

        }
    }
}
