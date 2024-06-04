package webscrap.scrap;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Scraper {
        public List<Book> scrapeBooks() {
            String url = "https://books.toscrape.com/";
            List<Book> bookList = new ArrayList<>();
            try {
                Document document = Jsoup.connect(url).get();
                Elements books = document.select(".product_pod");

                for (Element bk : books) {
                    String title = bk.select("h3 > a").text();
                    String price = bk.select(".price_color").text();
                    String stock = bk.select(".instock").text();
                    bookList.add(new Book(title, price, stock));
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
            return bookList;
        }
    }

