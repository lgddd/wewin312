package com.wewin.util;

import com.qiniu.util.Auth;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class CreateDownLoadUrl {
    public static String getDownLoadUrl(String filename) throws UnsupportedEncodingException {

        String domainOfBucket = "http://p2zhcnn8g.bkt.clouddn.com";
        String encodedFileName = URLEncoder.encode(filename, "utf-8");
        String publicUrl = String.format("%s/%s", domainOfBucket, encodedFileName);
        String accessKey = "9aBwTGn1_ILSNCZe2on837z32uF2nqWCfa0xz1il";
        String secretKey = "zxGwCPuNIc863_alpdfKwPfZA9vF7_WnZ_HVKQvz";
        Auth auth = Auth.create(accessKey, secretKey);
        long expireInSeconds = 3600;//1小时，可以自定义链接过期时间
        String finalUrl = auth.privateDownloadUrl(publicUrl, expireInSeconds);
        return finalUrl;
    }
}
