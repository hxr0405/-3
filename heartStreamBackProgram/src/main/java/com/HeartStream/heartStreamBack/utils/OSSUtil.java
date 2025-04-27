package com.HeartStream.heartStreamBack.utils;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class OSSUtil {

    @Value("${oss.endpoint}")
    private String endpoint; // 移除 static

    @Value("${oss.accessKeyId}")
    private String accessKeyId; // 移除 static

    @Value("${oss.accessKeySecret}")
    private String accessKeySecret; // 移除 static

    public OSS getOSSClient() {
        if (endpoint == null || accessKeyId == null || accessKeySecret == null) {
            throw new IllegalArgumentException("OSS configuration is incomplete.");
        }
        return new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
    }
}



