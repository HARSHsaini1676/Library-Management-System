package com.library;

import com.library.exception.BookNotAvailableException;
import com.library.exception.MemberNotFoundException;
import com.library.model.Book;
import com.library.model.Member;
import com.library.service.Library;

import java.util.List;

public class LibraryTest {

    static int passCount = 0;
    static int failCount = 0;

    static void check(String testName, boolean condition) {
        if (condition) {
            System.out.println("PASS: " + testName);
            passCount++;
        } else {
            System.out.println("FAIL: " + testName);
            failCount++;
        }
    }

    public static void main(String[] args) {
        Library library = new Library();

        // ---------- Book tests ----------
        Book b1 = new Book("101", "Java Basics", "John Doe");
        Book b2 = new Book("102", "Effective Java", "Joshua Bloch");
        Book b3 = new Book("103", "Harry Potter and the Chamber of Secrets", "J.K. Rowling");

        library.addBook(b1);
        library.addBook(b2);
        library.addBook(b3);

        check("Book 101 added and findable", library.findBook("101") != null);
        check("Nonexistent book returns null", library.findBook("999") == null);
        check("All 3 books present", library.getAllBooks().size() == 3);

        // ---------- Member tests ----------
        Member m1 = new Member("M1", "Alice");
        Member m2 = new Member("M2", "Bob");

        library.registerMember(m1);
        library.registerMember(m2);

        check("Member M1 registered and findable", library.findMember("M1") != null);
        check("Nonexistent member returns null", library.findMember("M99") == null);

        // ---------- Borrow tests ----------
        try {
            library.borrowBook("101", "M1");
            check("Borrow 101 by M1 succeeds", !library.findBook("101").isAvailable());
        } catch (Exception e) {
            check("Borrow 101 by M1 succeeds", false);
        }

        try {
            library.borrowBook("101", "M2"); // already borrowed by M1
            check("Borrowing already-borrowed book throws", false); // should never reach here
        } catch (BookNotAvailableException e) {
            check("Borrowing already-borrowed book throws", true);
        } catch (MemberNotFoundException e) {
            check("Borrowing already-borrowed book throws", false); // wrong exception type
        }

        try {
            library.borrowBook("999", "M1"); // book doesn't exist
            check("Borrowing nonexistent book throws", false);
        } catch (BookNotAvailableException e) {
            check("Borrowing nonexistent book throws", true);
        } catch (MemberNotFoundException e) {
            check("Borrowing nonexistent book throws", false);
        }

        try {
            library.borrowBook("102", "M99"); // member doesn't exist
            check("Borrowing with nonexistent member throws", false);
        } catch (MemberNotFoundException e) {
            check("Borrowing with nonexistent member throws", true);
        } catch (BookNotAvailableException e) {
            check("Borrowing with nonexistent member throws", false);
        }

        // ---------- Return tests ----------
        try {
            library.returnBook("101", "M1");
            check("Return 101 by M1 succeeds", library.findBook("101").isAvailable());
        } catch (Exception e) {
            check("Return 101 by M1 succeeds", false);
        }

        try {
            library.returnBook("102", "M1"); // 102 was never borrowed
            check("Returning a non-borrowed book throws", false);
        } catch (BookNotAvailableException e) {
            check("Returning a non-borrowed book throws", true);
        } catch (MemberNotFoundException e) {
            check("Returning a non-borrowed book throws", false);
        }

        // ---------- Search tests ----------
        List<Book> harryResults = library.searchBookByTitle("harry");
        check("Case-insensitive partial search finds Harry Potter", harryResults.size() == 1);

        List<Book> noResults = library.searchBookByTitle("xyz");
        check("Search with no matches returns empty list", noResults.isEmpty());

        // ---------- Summary ----------
        System.out.println("\n--- Results: " + passCount + " passed, " + failCount + " failed ---");
    }
}