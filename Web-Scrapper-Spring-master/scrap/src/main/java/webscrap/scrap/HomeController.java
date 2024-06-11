package webscrap.scrap;

import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.stereotype.Controller;
        import org.springframework.ui.Model;
        import org.springframework.web.bind.annotation.GetMapping;

        import java.util.List;

@Controller
public class HomeController {
    @Autowired
    private ScrapingService scrapingService;

    @GetMapping("/")
    public String home(Model model){
        List<Book> books = scrapingService.getScrapedBooks();
        model.addAttribute("books", books);
        return "home";
    }
}
