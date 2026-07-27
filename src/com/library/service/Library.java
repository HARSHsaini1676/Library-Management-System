package com.library.service;

import com.library.exception.BookNotAvailableException;
import com.library.exception.MemberNotFoundException;
import com.library.model.Book;
import com.library.model.Member;

import java.util.ArrayList;
import java.util.List;

public class Library {

    private List<Book> books;

    private List<Member> members;

    public Library() {
        this.books = new ArrayList<>();
        this.members = new ArrayList<>();
    }

    public void addBook(Book book){
        books.add(book);
    }

    public void registerMember(Member member){
        members.add(member);
    }

    public Book findBook(String isbn) {
        for (Book element : books) {
            if (element.getIsbn().equals(isbn)) {
                return element;
            }
        }
        return null;
    }
    public Member findMember(String memberId){
        for (Member mem : members){
            if(mem.getMemberId().equals(memberId)) {
                return mem;
            }
        }
        return null;
    }

    public void borrowBook(String isbn, String memberId) throws BookNotAvailableException, MemberNotFoundException {
        Book book = findBook(isbn);
        Member member = findMember(memberId);
        if (book == null) {
            throw new BookNotAvailableException("No book found with ISBN: " + isbn);
        }

        if(member == null){
            throw new MemberNotFoundException("com.library.model.Member not found with memberId: " + memberId);
        }

        if(!book.isAvailable()){
            throw new BookNotAvailableException("com.library.model.Book is already borrowed");
        }
        book.setAvailable(false);

        member.addBorrowedBook(book);
    }

    public void returnBook(String isbn, String memberId) throws BookNotAvailableException, MemberNotFoundException {
        Book book = findBook(isbn);
        Member member = findMember(memberId);

        if (book == null) {
            throw new BookNotAvailableException("No book found with ISBN: " + isbn);
        }
        if (member == null) {
            throw new MemberNotFoundException("com.library.model.Member not found with memberId: " + memberId);
        }
        if (book.isAvailable()) {
            throw new BookNotAvailableException("com.library.model.Book was not borrowed, cannot return");
        }

        book.setAvailable(true);
        member.removeBorrowedBook(book);
    }
    public List<Book> searchBookByTitle(String title) {

        List<Book> search = new ArrayList<>();

        for (Book element : books) {
            if (element.getTitle().toLowerCase().contains(title.toLowerCase())) {
                search.add(element);
            }
        }
        return search;
    }
}
