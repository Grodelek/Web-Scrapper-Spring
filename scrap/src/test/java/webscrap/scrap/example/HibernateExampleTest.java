package webscrap.scrap.example;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import webscrap.scrap.Book;
import webscrap.scrap.Scraper;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class HibernateExampleTest {
    private SessionFactory sessionFactory;

    @BeforeEach
    protected void setUp() {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure()
                .build();
        try {
            sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        } catch (Exception e) {
            StandardServiceRegistryBuilder.destroy(registry);
            throw new RuntimeException("Failed to initialize SessionFactory", e);
        }
    }

    @AfterEach
    protected void tearDown() {
        if (sessionFactory != null && !sessionFactory.isClosed()) {
            sessionFactory.close();
        }
    }

    @Test
    void save_books_to_db(){
        Scraper scraper = new Scraper();
        List<Book> books = scraper.scrapeBooks();

        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            for (Book book : books) {
                session.save(book);
            }
            session.getTransaction().commit();
        }

        try (Session session = sessionFactory.openSession()) {
            List<Book> savedBooks = session.createQuery("from Book", Book.class).list();
            assertThat(savedBooks).hasSize(books.size());
        }
    }
}
