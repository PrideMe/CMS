package com.wangjikai.domain;

import java.io.Serializable;
import java.util.List;
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
    private String picon; //图标
    private String purl;
    private Set<Role> roles; //权限持有角色信息
    private List<Permission> child; //父级菜单拥有的子级菜单集合

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

    public String getPicon() {
        return picon;
    }

    public void setPicon(String picon) {
        this.picon = picon;
    }

    public String getPurl() {
        return purl;
    }

    public void setPurl(String purl) {
        this.purl = purl;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public List<Permission> getChild() {
        return child;
    }

    public void setChild(List<Permission> child) {
        this.child = child;
    }

    @Override
    public String toString() {
        return "Permission{" +
                "id=" + id +
                ", pId=" + pId +
                ", pCode='" + pCode + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", picon='" + picon + '\'' +
                ", purl='" + purl + '\'' +
                ", roles=" + roles +
                ", child=" + child +
                '}';
    }
}
