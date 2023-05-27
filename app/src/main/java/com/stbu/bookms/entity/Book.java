package com.stbu.bookms.entity;


import java.io.Serializable;

/**
 * @className Book
 * @description TODO 图书实体类
 * @version 1.0
 */
public class Book implements Serializable {
    private String bookId; // 图书编号
    private String bookName; // 图书名称
    private int bookNumber; // 图书数量

    public Book() {

    }

    public Book(String bookId, String bookName, int bookNumber) {
        this.bookId = bookId;
        this.bookName = bookName;
        this.bookNumber = bookNumber;
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
                ", bookNumber=" + bookNumber +
                '}';
    }
}
