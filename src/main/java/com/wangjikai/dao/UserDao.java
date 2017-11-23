package com.wangjikai.dao;

import com.wangjikai.domain.User;
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
    User selectByLoginnameAndPassword(@Param("loginname") String loginname,@Param("password") String password);
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


}
