package com.wewin.util;

import com.qiniu.util.Auth;

public class CreateUpToken {
    public static String getUpToken(){
        String accessKey = "9aBwTGn1_ILSNCZe2on837z32uF2nqWCfa0xz1il";
        String secretKey = "zxGwCPuNIc863_alpdfKwPfZA9vF7_WnZ_HVKQvz";
        String bucket = "wewin";
        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucket);
        return upToken;
    }

}
