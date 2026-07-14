import java.util.Objects;

public class Book {
    private String isbn;

    private String title;

    private String author;

    private boolean isAvailable;

    public Book(String isbn, String title, String author) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.isAvailable = true;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    @Override
    public String toString() {
        return "Book{" +
                "isbn='" + isbn + '\'' +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", isAvailable=" + isAvailable +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;                    // same reference -> definitely equal
        if (o == null || getClass() != o.getClass()) return false;  // null or different type -> not equal
        Book book = (Book) o;                           // safe cast, we just checked the class
        return Objects.equals(isbn, book.isbn);         // compare the field(s) that define identity
    }

    @Override
    public int hashCode() {
        return Objects.hash(isbn);
    }
}
