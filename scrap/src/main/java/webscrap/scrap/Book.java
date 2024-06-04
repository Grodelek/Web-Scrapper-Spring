package webscrap.scrap;

import lombok.Data;
@Data
public class Book {
    private String title;
    private String price;
    private String stock;

    public Book(String title, String price, String stock) {
        this.title = title;
        this.price = price;
        this.stock = stock;
    }
}
