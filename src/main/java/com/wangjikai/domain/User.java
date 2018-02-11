package com.wangjikai.domain;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

/**
 * Created by 22717 on 2017/10/5.
 * cms用户类
 */
public class User implements Serializable {
    private Integer id;
    private String loginname;
    private String username;
    private String password;
    private String status;
    private Timestamp createdate;
    private List<Role> roles; //用户持有角色信息

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLoginname() {
        return loginname;
    }

    public void setLoginname(String loginname) {
        this.loginname = loginname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Timestamp getCreatedate() {
        return createdate;
    }

    public void setCreatedate(Timestamp createdate) {
        this.createdate = createdate;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", loginname='" + loginname + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", status='" + status + '\'' +
                ", createdate=" + createdate +
                ", roles=" + roles +
                '}';
    }
}
