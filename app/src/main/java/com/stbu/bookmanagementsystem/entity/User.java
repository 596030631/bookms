package com.stbu.bookmanagementsystem.entity;

import java.io.Serializable;

/**
 * @className User
 * @description TODO 用户实体类
 * @version 1.0
 */
public class User implements Serializable {
    private String id; // 用户id
    private String name; // 用户姓名
    private String className; // 用户班级
    private String password; // 用户密码
    private String phoneNumber; // 用户手机号

    public User(String id, String password) {
        this.id = id;
        this.password = password;
    }

    public User() {
    }

    public User(String id, String name, String className,
                String password, String phoneNumber) {
        this.id = id;
        this.name = name;
        this.className = className;
        this.password = password;
        this.phoneNumber = phoneNumber;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String classname) {
        this.className = classname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", className='" + className + '\'' +
                ", password='" + password + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }
}
