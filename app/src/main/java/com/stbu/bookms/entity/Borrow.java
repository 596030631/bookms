package com.stbu.bookms.entity;

/**
 * @className Borrow
 * @description TODO 借阅信息实体类
 * @version 1.0
 */
public class Borrow {
    private String borrowId; // 借阅用户id
    private String borrowBookId; // 借阅图书id
    private String borrowBookName; // 借阅图书数量

    public Borrow() {

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
                '}';
    }
}
