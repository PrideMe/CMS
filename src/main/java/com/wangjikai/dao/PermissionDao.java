package com.wangjikai.dao;

import com.wangjikai.domain.Permission;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by jikai_wang on 2018/1/29.
 * 权限permission表操作接口
 */
@Repository
public interface PermissionDao {
    //根据权限id查找权限对象
    Permission findById(Integer id);
    //根据权限id删除权限对象
    void deleteById(Integer id);
    //动态修改权限
    void update(Permission permission);
    //动态插入
    void addPermission(Permission permission);
    //动态查询权限
    List<Permission> selectByPage(Map<String,Object> params);
    //根据参数查询权限总数
    Integer count(Map<String,Object> params);
}
