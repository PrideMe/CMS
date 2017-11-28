package com.dao.test;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.io.Serializable;

/**
 * Created by 22717 on 2017/11/28.
 * ElasticSearch 文档
 */
@Document(indexName = "test",type = "user")
public class User implements Serializable {
    @Id
    private Long id;
    private String name;
    private String age;
    private String sex;
    private String schoole;
    private String corporation;

    public User() {
    }

    public User(String name, String age, String sex, String schoole, String corporation) {
        this.name = name;
        this.age = age;
        this.sex = sex;
        this.schoole = schoole;
        this.corporation = corporation;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getSchoole() {
        return schoole;
    }

    public void setSchoole(String schoole) {
        this.schoole = schoole;
    }

    public String getCorporation() {
        return corporation;
    }

    public void setCorporation(String corporation) {
        this.corporation = corporation;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age='" + age + '\'' +
                ", sex='" + sex + '\'' +
                ", schoole='" + schoole + '\'' +
                ", corporation='" + corporation + '\'' +
                '}';
    }
}
