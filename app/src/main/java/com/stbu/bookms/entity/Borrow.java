package com.stbu.bookms.entity;

/**
 * @className Borrow
 * @description TODO 互换信息实体类
 * @version 1.0
 */
public class Borrow {
    private String borrowId; // 互换用户id
    private String borrowBookId; // 互换图书id
    private String borrowBookName; // 互换图书数量
    private String address;
    private String buyer_name;
    private String sale_name;
    private String price;
    private String datetime;
    private String remake;

    public Borrow() {

    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBuyer_name() {
        return buyer_name;
    }

    public void setBuyer_name(String buyer_name) {
        this.buyer_name = buyer_name;
    }

    public String getSale_name() {
        return sale_name;
    }

    public void setSale_name(String sale_name) {
        this.sale_name = sale_name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDatetime() {
        return datetime;
    }

    public String getRemake() {
        return remake;
    }

    public void setRemake(String remake) {
        this.remake = remake;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public Borrow(String datetime) {
        this.datetime = datetime;
    }

    public Borrow(String borrowId, String borrowBookId, String borrowBookName) {
        this.borrowId = borrowId;
        this.borrowBookId = borrowBookId;
        this.borrowBookName = borrowBookName;
    }

    public Borrow(String id, String borrowBookId) {
        this.borrowId = id;
        this.borrowBookId = borrowBookId;
    }

    public String getBorrowId() {
        return borrowId;
    }

    public void setBorrowId(String borrowId) {
        this.borrowId = borrowId;
    }

    public String getBorrowBookId() {
        return borrowBookId;
    }

    public void setBorrowBookId(String borrowBookId) {
        this.borrowBookId = borrowBookId;
    }

    public String getBorrowBookName() {
        return borrowBookName;
    }

    public void setBorrowBookName(String borrowBookName) {
        this.borrowBookName = borrowBookName;
    }

    @Override
    public String toString() {
        return "Borrow{" +
                "borrowId='" + borrowId + '\'' +
                ", borrowBookId='" + borrowBookId + '\'' +
                ", borrowBookName='" + borrowBookName + '\'' +
                ", address='" + address + '\'' +
                ", buyer_name='" + buyer_name + '\'' +
                ", sale_name='" + sale_name + '\'' +
                ", price='" + price + '\'' +
                ", datetime='" + datetime + '\'' +
                ", remake='" + remake + '\'' +
                '}';
    }
}
