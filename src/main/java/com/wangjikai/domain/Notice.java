package com.wangjikai.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by 22717 on 2017/10/5.
 * cms通知类
 */
public class Notice implements Serializable {
    private Integer id;
    private String title;
    private String context;
    private Date createdate;
    private User user;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public Date getCreatedate() {
        return createdate;
    }

    public void setCreatedate(Date createdate) {
        this.createdate = createdate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Notice{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", context='" + context + '\'' +
                ", createdate=" + createdate +
                ", user=" + user +
                '}';
    }
}
