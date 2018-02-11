package com.wangjikai.domain.po;

import java.util.List;

/**
 * Created by jikai_wang on 2018/2/9.
 * 用户角色中间表user_role
 */
public class UserRole {
    private Integer id;
    private Integer userId;
    private Integer roleId;
    private List<Integer> roleRelation; //中间表中的角色集合

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public List<Integer> getRoleRelation() {
        return roleRelation;
    }

    public void setRoleRelation(List<Integer> roleRelation) {
        this.roleRelation = roleRelation;
    }

    @Override
    public String toString() {
        return "UserRole{" +
                "id=" + id +
                ", userId=" + userId +
                ", roleId=" + roleId +
                ", roleRelation=" + roleRelation +
                '}';
    }
}
