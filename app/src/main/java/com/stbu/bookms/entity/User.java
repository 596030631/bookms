package com.stbu.bookms.entity;

import java.io.Serializable;

/**
 * @className User
 * @description TODO 用户实体类
 * @version 1.0
 */
public class User implements Serializable {
    private String id; // 用户id
    private String name; // 用户姓名
    private String address; // 用户班级
    private String password; // 用户密码
    private String phoneNumber; // 用户手机号
    private String amount; // 用户金额

    public User(String id, String password) {
        this.id = id;
        this.password = password;
    }

    public User() {
    }

    public User(String id, String name, String className,
                String password, String phoneNumber, String amount) {
        this.id = id;
        this.name = name;
        this.address = className;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.amount = amount;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String classname) {
        this.address = classname;
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

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {//返回对象类的所有属性
        return "User{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", className='" + address + '\'' +
                ", password='" + password + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", amount='" + amount + '\'' +
                '}';
    }
}
