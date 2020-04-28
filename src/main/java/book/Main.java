package book;

import book.model.Book;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.ArrayList;
import java.util.List;

import static book.model.BookGenerator.createBooks;


public class Main {

    private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("bookdb");

    private static List<Book> getBooks() {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT b FROM Book b ORDER BY b.id", Book.class).getResultList();
        } finally {
            em.close();
        }
    }

    public static void main(String[] args) {
        ArrayList<Book> books = createBooks(1000, true);
        getBooks().forEach(System.out::println);

    }
}