package com.wangjikai.domain.po;

import java.util.List;

/**
 * Created by jikai_wang on 2018/2/25.
 * 角色权限中间表role_permission
 */
public class RolePermission {
    private Integer id;
    private Integer roleId;
    private Integer permissionId;
    private List<Integer> permissionRelation; //中间表中的权限集合

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Integer getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(Integer permissionId) {
        this.permissionId = permissionId;
    }

    public List<Integer> getPermissionRelation() {
        return permissionRelation;
    }

    public void setPermissionRelation(List<Integer> permissionRelation) {
        this.permissionRelation = permissionRelation;
    }

    @Override
    public String toString() {
        return "RolePermission{" +
                "id=" + id +
                ", roleId=" + roleId +
                ", permissionId=" + permissionId +
                ", permissionRelation=" + permissionRelation +
                '}';
    }
}
