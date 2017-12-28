package com.wangjikai.quartz;

import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by 22717 on 2017/12/20.
 * 一个简单任务
 */
@Component
public class SimpleJob {
    public void out(){
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println(simpleDateFormat.format(date)+"简单任务执行中···");
    }
}
