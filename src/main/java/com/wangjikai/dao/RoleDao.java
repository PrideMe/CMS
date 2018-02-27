package com.wangjikai.dao;

import com.wangjikai.domain.Role;
import com.wangjikai.domain.po.RolePermission;
import org.apache.ibatis.annotations.Param;
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
    //根据用户的id拿到当前用户的角色信息
    List<Role> selectRolesByUserId(Integer id);
    //根据角色查找关联的权限
    Role getRoleRelationPermission(Integer id);
    //插入中间表role_permission
    void insertRolePermission(List<RolePermission> rolePermissions);
    //根据角色id删除关联表中的关系
    void deleteRolePermissionByRoleId(Integer id);
    //批量删除中间表信息
    void deleteRolePermission(@Param("id") Integer id,@Param("permissionId") List<RolePermission> permissionId);
}
