package book;

import book.model.Book;
import com.google.inject.persist.Transactional;
import jpa.GenericJpaDao;
import todo.model.Todo;

import java.util.Optional;

public class BookDao extends GenericJpaDao<Book> {

    @Transactional
    public Optional<Book> findByIsbn13(String isbn13){
        Optional<Book> value= null;
        value= Optional.ofNullable(entityManager.createQuery("SELECT b FROM Book b WHERE b.isbn13 = :isbn13", Book.class)
                .setParameter("isbn13", isbn13)
                .getSingleResult());
        return value;
    }
}
