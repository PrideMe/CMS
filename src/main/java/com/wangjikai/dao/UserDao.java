package com.wangjikai.dao;

import com.wangjikai.domain.User;
import com.wangjikai.domain.po.UserRole;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by 22717 on 2017/10/5.
 * User表操作接口
 */
@Repository
public interface UserDao {
    //根据登陆名、密码查询用户
    User selectByLoginname(@Param("loginname") String loginname);
    //User selectByLoginname(String loginname);
    //根据id查询用户
    User selectById(Integer id);
    //根据id删除用户
    void deleteById(Integer id);
    //动态修改用户
    void update(User user);
    //动态查询
    List<User> selectByPage(Map<String,Object> params);
    //根据参数查询用户总数
    Integer count(Map<String,Object> params);
    //动态插入
    void insertUser(User user);
    //查询用户的角色权限信息
    User selectUserRolePermission(User user);
    //插入中间表user_role
    void insertUserRole(UserRole userRole);
    //根据用户id去查关联表的角色集合
    List<Integer> selectUserRoleRelationByUserId(Integer integer);
}
