package webscrap.scrap;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;


public class Scraper {
    private static final int NUM_THREADS = 10;

    public List<Book> scrapeBooks() {
            String url = "https://books.toscrape.com/catalogue/page-";
            List<Book> bookList = new ArrayList<>();
            ExecutorService executorService = Executors.newFixedThreadPool(NUM_THREADS);
            List<Callable<List<Book>>> tasks = new ArrayList<>();
            for(int i=1; i<=10; i++){
                String pageUrl = url + i + ".html";
                tasks.add(() -> scrapeBooksFromPage(pageUrl));
            }
        try {
            List<Future<List<Book>>> results = executorService.invokeAll(tasks);
            for (Future<List<Book>> result : results) {
                bookList.addAll(result.get());
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        } finally {
            executorService.shutdown();
        }
        return bookList;
    }

    private List<Book> scrapeBooksFromPage(String url) {
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

