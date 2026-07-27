package com.library.service;

import com.library.model.Book;
import com.library.model.Member;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileService {

    public void saveBooks(List<Book> books, String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (Book book : books) {
                writer.write(book.getIsbn());
                writer.write(",");
                writer.write(book.getTitle());
                writer.write(",");
                writer.write(book.getAuthor());
                writer.write(",");
                String isAvailable = String.valueOf(book.isAvailable());
                writer.write(isAvailable);
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Failed to save Books : " + e.getMessage());
        }
    }

    public List<Book> loadBooks(String filePath) {
        List<Book> books = new ArrayList<>();
        try(BufferedReader reader = new BufferedReader(new FileReader(filePath))){
            String line;
            String[] arr;
            String isbn, title, author;
            boolean isAvaiable;
            while ((line = reader.readLine()) != null) {
                arr = line.split(",");
                isbn = arr[0];
                title = arr[1];
                author = arr[2];
                isAvaiable = Boolean.parseBoolean(arr[3]);
                Book temp = new Book(isbn, title, author);
                temp.setAvailable(isAvaiable);
                books.add(temp);
            }
        }
        catch (IOException e){
            System.out.println("Failed to load Books : " + e.getMessage());
        }
        return books;
    }

    // same pair for members
    public void saveMembers(List<Member> members, String filePath){
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (Member member : members){
                writer.write(member.getMemberId());
                writer.write(",");
                writer.write(member.getName());
                writer.write(",");
                for ( Book book : member.getBorrowedBooks()) {
                    writer.write(book.getIsbn());
                    if(book.getIsbn() != null) writer.write(";");
                }
                writer.newLine();
            }
        }
        catch (IOException e) {
            System.out.println("Failed to save Members : " + e.getMessage());
        }
    }

    public List<Member> loadMembers(Library library, String filePath) {
        List<Member> members = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            String[] arr;
            String memId, name;
            while ((line = reader.readLine()) != null) {
                arr = line.split(",");
                memId = arr[0];
                name = arr[1];
                Member tempMember = new Member(memId, name);

                String isbnBlob = arr.length > 2 ? arr[2] : "";
                String[] bookIsbns = isbnBlob.split(";");
                for (String isbn : bookIsbns) {
                    if (isbn.isEmpty()) continue;
                    Book book = library.findBook(isbn);
                    if (book != null) {
                        tempMember.addBorrowedBook(book);
                    }
                }
                members.add(tempMember);
            }
        } catch (IOException e) {
            System.out.println("Failed to load members: " + e.getMessage());
        }
        return members;
    }
}
