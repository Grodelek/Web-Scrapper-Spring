package webscrap.scrap;

import jakarta.persistence.*;
import lombok.Data;


@Data
@Entity
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "title")
    private String title;

    @Column(name = "price")
    private String price;
    @Column(name = "stock")
    private String stock;

    public Book(String title, String price, String stock) {
        this.title = title;
        this.price = price;
        this.stock = stock;
    }
    public Book() {
    }
}
