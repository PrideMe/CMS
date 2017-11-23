package com.wangjikai.dao;

import com.wangjikai.domain.Notice;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by 22717 on 2017/10/28.
 * notice表操作接口
 */
@Repository
public interface NoticeDao {
    //动态查询公告
    List<Notice> findByPage(Map<String,Object> params);
    //查询条件总数
    Integer count(Map<String,Object> params);
    //查询公告
    Notice findById(Integer id);
    //删除公告
    void deleteById(Integer id);
    //动态插入公告
    void save(Notice notice);
    //动态修改公告
    void update(Notice notice);
}
