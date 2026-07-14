import java.util.ArrayList;
import java.util.List;

public class Member {
    private String memberId;

    private String name;

    List<Book> borrowedBooks;

    Member(String memberId, String name, ArrayList<Book> borrowedBooks){
        this.memberId = memberId;
        this.name = name;
        this.borrowedBooks = borrowedBooks;
    }
}
