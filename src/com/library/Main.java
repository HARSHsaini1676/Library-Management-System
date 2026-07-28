package com.library;

import com.library.exception.BookNotAvailableException;
import com.library.exception.MemberNotFoundException;
import com.library.model.Book;
import com.library.model.Member;
import com.library.service.FileService;
import com.library.service.Library;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Library library = new Library();
        FileService fileService = new FileService();

        List<Book> loadedBooks = fileService.loadBooks("books.txt");
        for (Book book : loadedBooks) {
            library.addBook(book);
        }

        List<Member> loadedMembers = fileService.loadMembers(library, "members.txt");
        for (Member member : loadedMembers) {
            library.registerMember(member);
        }

        // menu loop goes here next
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("1. Add a book");
            System.out.println("2. Register a member");
            System.out.println("3. Borrow a book");
            System.out.println("4. Return a book");
            System.out.println("5. Search books by title");
            System.out.println("6. View all books");
            System.out.println("7. Exit");
            System.out.print("Choose an option: ");

            if (!scanner.hasNextInt()) {
                System.out.println("Please enter a valid number.");
                scanner.nextLine(); // consume the bad input so it doesn't loop forever
                continue; // skip back to the top of the while loop, show menu again
            }


            int choice = scanner.nextInt();
            scanner.nextLine(); // consume leftover newline

            switch (choice){
                case 1:
                    System.out.println("You are now adding a book");
                    System.out.println("Enter the isbn for book : ");
                    String isbn = scanner.nextLine();
                    System.out.println("Enter the title for book : ");
                    String title = scanner.nextLine();
                    System.out.println("Enter the author for book : ");
                    String author = scanner.nextLine();
                    Book book = new Book(isbn, title, author);
                    if (library.findBook(isbn) == null) {
                        library.addBook(book);
                        System.out.println("Book has been added to the library with isbn : " + isbn);
                    }
                    else {
                        System.out.println("A book with this ISBN already exists.");
                    }
                    break;

                case 2:
                    System.out.println("You are now registering a member");
                    System.out.println("Enter the memberID : ");
                    String memberID = scanner.nextLine();
                    System.out.println("Enter the member name : ");
                    String name = scanner.nextLine();
                    Member member = new Member(memberID, name);
                    if(library.findMember(memberID) == null){
                        library.registerMember(member);
                        System.out.println("Member has been registered with memberID : " + memberID);
                    }
                    else {
                        System.out.println("A member with this memberId already exists");
                    }
                    break;

                case 3:
                    System.out.println("You are now borrowing book");
                    System.out.println("Enter the ISBN of the book to borrow : ");
                    String borrowIsbn = scanner.nextLine();
                    System.out.println("Enter your member ID : ");
                    String borrowMemberId = scanner.nextLine();
                    try {
                        library.borrowBook(borrowIsbn, borrowMemberId);
                        System.out.println("Book has been successfully borrowed with isbn " + borrowIsbn + " by Member " + borrowMemberId);
                    } catch (BookNotAvailableException | MemberNotFoundException e) {
                        System.out.println("Could not borrow book: " + e.getMessage());
                    }
                    break;

                case 4:
                    System.out.println("You are now returning the book");
                    System.out.println("Enter the ISBN of the book to return : ");
                    String returnIsbn = scanner.nextLine();
                    System.out.println("Enter your member ID : ");
                    String returnMemberId = scanner.nextLine();
                    try {
                        library.returnBook(returnIsbn, returnMemberId);
                        System.out.println("Book has been successfully returned with isbn " + returnIsbn + " by Member " + returnMemberId);
                    } catch (BookNotAvailableException | MemberNotFoundException e) {
                        System.out.println("Could not return book: " + e.getMessage());
                    }
                    break;

                case 5:
                    System.out.println("Book Search welcomes you");
                    System.out.println("Enter the title of book you want to search : ");
                    String booktitle = scanner.nextLine();
                    List<Book> books = library.searchBookByTitle(booktitle);

                    if(books.isEmpty()) System.out.println("Sorry for inconvenience, We're increasing our database Next time it will be available");
                    for(Book book1 : books){
                        System.out.println(book1);
                    }
                    break;

                case 6:
                    System.out.println("Showing all books available in library");
                    List<Book> allBooks = library.getAllBooks();
                    if (allBooks.isEmpty()) {
                        System.out.println("No books in the library yet.");
                    }
                    for(Book book1: allBooks){
                        System.out.println(book1);
                    }
                    break;

                case 7:
                    fileService.saveBooks(library.getAllBooks(), "books.txt");
                    fileService.saveMembers(library.getAllMembers(), "members.txt" );
                    running = false;
                    System.out.println("Good Bye");
                    break;


                default:
                    System.out.println("Functionality not added yet");
                    break;
            }
        }
    }
}
