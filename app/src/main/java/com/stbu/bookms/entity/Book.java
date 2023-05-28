package com.stbu.bookms.entity;


import java.io.Serializable;

/**
 * @version 1.0
 * @className Book
 * @description TODO 图书实体类
 */
public class Book implements Serializable {
    private String bookId; // 图书编号
    private String bookName; // 图书名称
    private String image; // 图书名称
    private String bookAuth; // 图书作者
    private String bookOwn; // 图书所有者
    private String bookCategory; // 图书类别
    private String bookContent; // 图书介绍
    private int bookNumber; // 图书数量
    private String price = "0.00"; // 图书价格

    private String remakeJson;

    public String getRemakeJson() {
        return remakeJson;
    }

    public void setRemakeJson(String remakeJson) {
        this.remakeJson = remakeJson;
    }
    private String addRemake;

    public String getAddRemake() {
        return addRemake;
    }

    public void setAddRemake(String addRemake) {
        this.addRemake = addRemake;
    }

    public Book() {

    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getBookAuth() {
        return bookAuth;
    }

    public void setBookAuth(String bookAuth) {
        this.bookAuth = bookAuth;
    }

    public String getBookCategory() {
        return bookCategory;
    }

    public void setBookCategory(String bookCategory) {
        this.bookCategory = bookCategory;
    }

    public String getBookContent() {
        return bookContent;
    }

    public void setBookContent(String bookContent) {
        this.bookContent = bookContent;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getBookOwn() {
        return bookOwn;
    }

    public void setBookOwn(String bookOwn) {
        this.bookOwn = bookOwn;
    }

    public Book(String bookId, String bookName, int bookNumber) {
        this.bookId = bookId;
        this.bookName = bookName;
        this.bookNumber = bookNumber;
    }

    public static class BookRemake {
        public String userName;
        public String remake;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public int getBookNumber() {
        return bookNumber;
    }

    public void setBookNumber(int bookNumber) {
        this.bookNumber = bookNumber;
    }

    @Override
    public String toString() {
        return "Book{" +
                "bookId='" + bookId + '\'' +
                ", bookName='" + bookName + '\'' +
                ", image='" + image + '\'' +
                ", bookAuth='" + bookAuth + '\'' +
                ", bookCategory='" + bookCategory + '\'' +
                ", bookContent='" + bookContent + '\'' +
                ", bookNumber=" + bookNumber +
                ", price='" + price + '\'' +
                ", remakeJson='" + remakeJson + '\'' +
                '}';
    }
}
