package com.wangjikai.dao;

import com.wangjikai.domain.Job;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by 22717 on 2017/10/16.
 * job表操作接口
 */
@Repository
public interface JobDao {
    //根据id查询职业
    Job findById(int id);
    //查找所有
    List<Job> findAllJob();
    //动态查询
    List<Job> findByPage(Map<String,Object> params);
    //查询总数
    Integer count(Map<String,Object> params);
    //根据id删除
    void deleteById(Integer id);
    //动态保存
    void save(Job job);
    //动态更新
    void update(Job job);
}
