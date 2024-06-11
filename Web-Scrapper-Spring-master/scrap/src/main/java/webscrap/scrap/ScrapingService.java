package webscrap.scrap;

import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ScrapingService {
    public List<Book> getScrapedBooks(){
        Scraper scraper = new Scraper();
        return scraper.scrapeBooks();
    }
}
