package com.wangjikai.util;

import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;

import java.io.IOException;

/**
 * Created by jikai_wang on 2018/1/8.
 * 七牛云存储工具-上传
 */

public class QiniuUtil {
    //设置账号的ACCESS_KEY和SECRET_KEY
    String ACCESS_KEY = "UPl0zmY7s0UpmlN05soGqRnVVt2zoxMqwB7yeLKB";
    String SECRET_KEY = "wSquF-i3n49rNzflNM_MKyRdCb1Tm-DGrReIPCja";

    //要上传的空间
    String bucketname = "head-imafges"; //对应要上传到七牛上 你的那个路径（自己建文件夹 注意设置公开）
    //上传到七牛后保存的文件名
    String key = "test";
    //上传文件的路径
    String FilePath = "E:\\javaweb学习视频\\day65-springMVC\\图解\\01_springmvc工作流.JPG";  //本地要上传文件路径

    //密钥配置
    Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);
    Configuration cfg = new Configuration(Zone.zone0());
    //创建上传对象
    UploadManager uploadManager = new UploadManager(cfg);

    //简单上传，使用默认策略，只需要设置上传的空间名就可以了
    public String getUpToken(){
        return auth.uploadToken(bucketname);
    }
    //普通上传
    public void upload() throws IOException{
        try {
            //调用put方法上传
            Response res = uploadManager.put(FilePath, key, getUpToken());
            System.out.println(res.bodyString());  //打印返回的信息
        } catch (QiniuException e) {
            Response r = e.response;
            // 请求失败时打印的异常的信息
            System.out.println(r.toString());
            try {
                System.out.println(r.bodyString());//响应的文本信息
            } catch (QiniuException e1) {
            }
        }
    }

    public static void main(String args[]) throws IOException{
        new QiniuUtil().upload();
    }

}
