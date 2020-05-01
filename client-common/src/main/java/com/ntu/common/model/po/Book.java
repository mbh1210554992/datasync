package com.ntu.common.model.po;

import java.io.Serializable;

public class Book implements Serializable {

    private static final long serialVersionUID = 4541175630805966536L;
    private String bookId;
    private String  bookName;
    private String  bookIsbn;
    private String  bookAuthor;
    private Integer  bookStatus;
    private String  bookInfo;
    private String  bookPublisher;
    private String bookCreateTime;

    public String getBookInfo() {
        return bookInfo;
    }

    public void setBookInfo(String bookInfo) {
        this.bookInfo = bookInfo;
    }

    public String getBookPublisher() {
        return bookPublisher;
    }

    public void setBookPublisher(String bookPublisher) {
        this.bookPublisher = bookPublisher;
    }

    public String getBookCreateTime() {
        return bookCreateTime;
    }

    public void setBookCreateTime(String bookCreateTime) {
        this.bookCreateTime = bookCreateTime;
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

    public String getBookIsbn() {
        return bookIsbn;
    }

    public void setBookIsbn(String bookIsbn) {
        this.bookIsbn = bookIsbn;
    }

    public String getBookAuthor() {
        return bookAuthor;
    }

    public void setBookAuthor(String bookAuthor) {
        this.bookAuthor = bookAuthor;
    }

    public Integer getBookStatus() {
        return bookStatus;
    }

    public void setBookStatus(Integer bookStatus) {
        this.bookStatus = bookStatus;
    }

    @Override
    public String toString() {
        return "Book{" +
                "bookId=" + bookId +
                ", bookName='" + bookName + '\'' +
                ", bookIsbn='" + bookIsbn + '\'' +
                ", bookAuthor='" + bookAuthor + '\'' +
                ", bookStatus='" + bookStatus + '\'' +
                '}';
    }
}
