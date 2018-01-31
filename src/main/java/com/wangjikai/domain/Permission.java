package com.wangjikai.domain;

import java.io.Serializable;
import java.util.Set;

/**
 * Created by jikai_wang on 2018/1/24.
 * 权限实体类
 */
public class Permission implements Serializable{
    private Integer id;
    private Integer pId; //父标号
    private String pCode; //权限代号
    private String name;
    private String description;
    private String url;
    private Set<Role> roles; //权限持有角色信息

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getpId() {
        return pId;
    }

    public void setpId(Integer pId) {
        this.pId = pId;
    }

    public String getpCode() {
        return pCode;
    }

    public void setpCode(String pCode) {
        this.pCode = pCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "Permission{" +
                "id=" + id +
                ", pId=" + pId +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", url='" + url + '\'' +
                ", roles=" + roles +
                '}';
    }
}
