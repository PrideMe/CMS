package com.wangjikai.dao;

import com.wangjikai.domain.Role;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by jikai_wang on 2018/1/29.
 * 角色role表操作接口
 */
@Repository
public interface RoleDao {
    //根据角色id查找角色对象
    Role findById(Integer id);
    //根据角色id删除角色对象
    void deleteById(Integer id);
    //动态修改用户
    void update(Role role);
    //动态插入
    void addRole(Role role);
    //动态查询角色
    List<Role> selectByPage(Map<String,Object> params);
    //根据参数查询角色总数
    Integer count(Map<String,Object> params);
}
