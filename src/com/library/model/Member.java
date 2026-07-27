package com.library.model;

import java.util.ArrayList;
import java.util.List;

public class Member {
    private String memberId;

    private String name;

    private List<Book> borrowedBooks;

    public Member(String memberId, String name){
        this.memberId = memberId;
        this.name = name;
        this.borrowedBooks = new ArrayList<>();
    }

    public String getMemberId() {
        return memberId;
    }

    public String getName() {
        return name;
    }

    public List<Book> getBorrowedBooks() {
        return borrowedBooks;
    }

    public void addBorrowedBook(Book book) {
        if (borrowedBooks.contains(book)) {
            return; // or throw an exception - we'll decide when we build custom exceptions
        }
        borrowedBooks.add(book);
    }

    public void removeBorrowedBook(Book book) {
        borrowedBooks.remove(book);
    }

    @Override
    public String toString() {
        return "com.library.model.Member{" +
                "memberId='" + memberId + '\'' +
                ", name='" + name + '\'' +
                ", borrowedBooks=" + borrowedBooks +
                '}';
    }
}
