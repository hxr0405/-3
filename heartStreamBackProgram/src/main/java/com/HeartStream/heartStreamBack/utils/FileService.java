package com.HeartStream.heartStreamBack.utils;

import com.aliyun.oss.OSS;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.PutObjectRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

@Service
public class FileService {

    @Autowired
    private OSSUtil ossUtil; // 引入 OSSUtil

    public FileService(OSSUtil ossUtil) {
        this.ossUtil = ossUtil; // 通过构造函数注入
    }

    public String getFileUrl(String bucketName, String objectName) {
        OSS ossClient = ossUtil.getOSSClient(); // 使用注入的 OSSUtil 实例
        try {
            // 生成文件URL
            String fileUrl = ossClient.generatePresignedUrl(bucketName, objectName, new java.util.Date(System.currentTimeMillis() + 3600 * 1000)).toString();
            return fileUrl;
        } finally {
            ossClient.shutdown();
        }
    }

    public void downloadFile(String bucketName, String objectName, String localFilePath) {
        OSS ossClient = ossUtil.getOSSClient(); // 使用注入的 OSSUtil 实例
        try {
            // 下载文件
            OSSObject ossObject = ossClient.getObject(bucketName, objectName);
            InputStream inputStream = ossObject.getObjectContent();
            FileOutputStream outputStream = new FileOutputStream(new File(localFilePath));

            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }

            outputStream.close();
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ossClient.shutdown();
        }
    }

    public String uploadFile(String bucketName, String objectName, MultipartFile file) {
        OSS ossClient = ossUtil.getOSSClient(); // 使用注入的 OSSUtil 实例
        try {
            // 获取文件输入流
            InputStream inputStream = file.getInputStream();
            // 创建PutObjectRequest对象
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, objectName, inputStream);
            // 上传文件
            ossClient.putObject(putObjectRequest);
            // 生成文件URL
            String fileUrl = ossClient.generatePresignedUrl(bucketName, objectName, new java.util.Date(System.currentTimeMillis() + 3600 * 1000)).toString();
            return fileUrl;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            ossClient.shutdown();
        }
    }
}


