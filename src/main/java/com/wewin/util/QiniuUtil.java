package com.wewin.util;

import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.BatchStatus;
import com.qiniu.util.Auth;

import java.io.IOException;

public class QiniuUtil {
    //设置账号的ak sk
    private static final String ACCESS_KEY="9aBwTGn1_ILSNCZe2on837z32uF2nqWCfa0xz1il";
    private static final String SECRET_KEY="zxGwCPuNIc863_alpdfKwPfZA9vF7_WnZ_HVKQvz";

    //要上传的空间
    private static final String bucket="wewin";
    private static final long expireSeconds = 172800;
    //密钥
    private static final Auth auth = Auth.create(ACCESS_KEY,SECRET_KEY);

    public static String getToken(){
        return auth.uploadToken(bucket,"testwewin", expireSeconds,null);
    }

    public static String getDownLoadToekn(String url)
    {
        String test = url;
        String result = auth.privateDownloadUrl(url);
        return result;
//        return auth.privateDownloadUrl(url);
    }

    //普通上传
    public void upload(String filePath, String fileName) throws IOException{
        //构造一个带指定Zone对象的配置类
        Configuration cfg = new Configuration(Zone.zone0());
        //创建上传对象
        UploadManager uploadManager = new UploadManager(cfg);
       // long expireSeconds = 3600;
        try {
            //调用put方法上传
            Response res = uploadManager.put(filePath, fileName, auth.uploadToken(bucket));
            //打印返回的信息
            //System.out.println(res.bodyString());
        } catch (QiniuException e) {
            Response r = e.response;
            // 请求失败时打印的异常的信息
            //System.out.println(r.toString());
            try {
                //响应的文本信息
                System.out.println(r.bodyString());
            } catch (QiniuException e1) {
                //ignore
            }
        }
    }

    //普通删除
    public void delete(String key) throws IOException {
        //实例化一个BucketManager对象
        BucketManager bucketManager = new BucketManager(auth,null);
        //此处的33是去掉：http://ongsua0j7.bkt.clouddn.com/,剩下的key就是图片在七牛云的名称
        key = key.substring(33);
        //System.out.println("key---------->" + key);
        try {
            //调用delete方法移动文件
            bucketManager.delete(bucket, key);
        } catch (QiniuException e) {
            //捕获异常信息
            Response r = e.response;
            System.out.println(r.toString());
        }
    }

    /**
     * 批量删除七牛云上的文件
     */
    public static void batchdelete(String[] deletelist){
        //构造一个带指定Zone对象的配置类
        Configuration cfg = new Configuration(Zone.zone0());

        BucketManager bucketManager = new BucketManager(auth, cfg);

        //单次批量请求的文件数量不得超过1000
//        String[] keyList = new String[]{
//                "qiniu.jpg",
//                "qiniu.mp4",
//                "qiniu.png",
//        };

        try {

            BucketManager.BatchOperations batchOperations = new BucketManager.BatchOperations();
            batchOperations.addDeleteOp(bucket, deletelist);
            Response response = bucketManager.batch(batchOperations);
            BatchStatus[] batchStatusList = response.jsonToObject(BatchStatus[].class);
            for (int i = 0; i < deletelist.length; i++) {
                BatchStatus status = batchStatusList[i];
                String key = deletelist[i];
                System.out.print(key + "\t");
                if (status.code == 200) {
                    System.out.println("delete success");
                } else {
                    System.out.println(status.data.error);
                }
            }
        } catch (QiniuException ex) {
            System.err.println(ex.response.toString());
        }
    }

}
