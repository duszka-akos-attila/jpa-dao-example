package book.model;

import com.github.javafaker.Faker;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.time.ZoneId;
import java.util.ArrayList;


public class BookGenerator {

    private static void insertBooksIntoDatabase(ArrayList<Book> books){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("bookdb");
        EntityManager em = emf.createEntityManager();

        try{
            em.getTransaction().begin();
            books.forEach(em::persist);
            em.getTransaction().commit();
        }
        finally {
            em.close();
            emf.close();
        }
    }

    private static ArrayList<Book> generateBooks(int numberOfBooks) {
        Faker faker =new Faker();
        ArrayList<Book> books=new ArrayList<>();

        for(int i=0;i<numberOfBooks;i++) {
            Book book = Book.builder()
                    .isbn13(faker.code().isbn13())
                    .author(faker.book().author())
                    .title(faker.book().title())
                    .format(faker.options().option(Book.Format.class))
                    .publisher(faker.book().publisher())
                    .publicationDate(faker.date().birthday().toInstant().atZone(ZoneId.systemDefault()).toLocalDate())
                    .pages(faker.number().numberBetween(10,5000))
                    .available(faker.bool().bool())
                    .build();
            books.add(book);
        }

        return books;
    }


    //createBooks method without parameters creates a book
    public static ArrayList<Book> createBooks(){
        return generateBooks(1);
    }


    //createBooks method with an int parameter creates @numberOfBooks amount of books.
    public static ArrayList<Book> createBooks(int numberOfBooks){
        return generateBooks(numberOfBooks);
    }


    //createBooks method with a boolean parameter creates a book and insert it into the database if @insertIntoDatabase is true.
    public static ArrayList<Book> createBooks(boolean insertIntoDatabase){
        ArrayList<Book> book=generateBooks(1);

        if(insertIntoDatabase)
            insertBooksIntoDatabase(book);

            return book;
    }


    //Basic method with an int and a boolean parameters, creates @numberOfBooks amount of books and insert them into the database if @insertIntoDatabase is true.
    public static ArrayList<Book> createBooks(int numberOfBooks, boolean insertIntoDatabase){
        ArrayList<Book> books=generateBooks(numberOfBooks);

        if(insertIntoDatabase)
            insertBooksIntoDatabase(books);

            return books;
    }

}
