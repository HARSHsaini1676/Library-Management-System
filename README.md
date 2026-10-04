# Library Management System

A command-line Library Management System built in Java. Manage books and members, borrow and return books, and search the catalog — all persisted to local text files.

## Features

- **Add Books** — Register new books by ISBN, title, and author
- **Register Members** — Add library members with a unique ID
- **Borrow Books** — Check out available books to registered members
- **Return Books** — Return borrowed books back to the library
- **Search Books** — Case-insensitive partial search by title
- **View All Books** — List every book and its availability status
- **File Persistence** — Book and member data saved to CSV text files on exit and loaded on startup

## Project Structure

```
src/com/library/
├── Main.java                          # CLI menu and application entry point
├── LibraryTest.java                   # Hand-rolled test suite
├── model/
│   ├── Book.java                      # Book entity (ISBN, title, author, availability)
│   └── Member.java                    # Member entity (ID, name, borrowed books)
├── service/
│   ├── Library.java                   # Core library logic (add, borrow, return, search)
│   └── FileService.java              # CSV file read/write for persistence
├── exception/
│   ├── BookNotAvailableException.java # Thrown when a book can't be borrowed/returned
│   └── MemberNotFoundException.java   # Thrown when a member ID doesn't exist
└── txtFiles/
    ├── books.txt                      # Persisted book data
    └── members.txt                    # Persisted member data
```

## Prerequisites

- Java 8 or higher (JDK)

## How to Compile and Run

From the project root directory:

```bash
# Compile all source files
javac -d out src/com/library/model/*.java src/com/library/exception/*.java src/com/library/service/*.java src/com/library/Main.java

# Run the application
java -cp out com.library.Main
```

## How to Run Tests

```bash
# Compile tests (along with source files)
javac -d out src/com/library/model/*.java src/com/library/exception/*.java src/com/library/service/*.java src/com/library/LibraryTest.java

# Run the test suite
java -cp out com.library.LibraryTest
```

Expected output:

```
PASS: Book 101 added and findable
PASS: Nonexistent book returns null
PASS: All 3 books present
PASS: Member M1 registered and findable
PASS: Nonexistent member returns null
PASS: Borrow 101 by M1 succeeds
PASS: Borrowing already-borrowed book throws
PASS: Borrowing nonexistent book throws
PASS: Borrowing with nonexistent member throws
PASS: Return 101 by M1 succeeds
PASS: Returning a non-borrowed book throws
PASS: Case-insensitive partial search finds Harry Potter
PASS: Search with no matches returns empty list

--- Results: 13 passed, 0 failed ---
```

## Usage

When you run the application you'll see an interactive menu:

```
1. Add a book
2. Register a member
3. Borrow a book
4. Return a book
5. Search books by title
6. View all books
7. Exit
Choose an option:
```

Data is automatically saved when you exit via option 7.

## License

This project is for educational purposes.
