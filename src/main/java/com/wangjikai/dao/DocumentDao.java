package com.wangjikai.dao;

import com.wangjikai.domain.Document;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by 22717 on 2017/10/28.
 * document表操作接口
 */
@Repository
public interface DocumentDao {
    //动态查询文件
    List<Document> findByPage(Map<String,Object> params);
    //动态查询总数
    Integer count(Map<String, Object> params);
    //插入文档
    void save(Document document);
    //查找文档
    Document findById(Integer id);
    //删除文档
    void deleteById(Integer id);
    //更新文档
    void update(Document document);
}
