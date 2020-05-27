package crud.book.controller;


import crud.book.model.News;
import crud.book.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class NewsController {
//    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final int ROW_PER_PAGE = 10;

    @Autowired
    private NewsService newsService;


    @Value("${msg.title}")
    private String title;

    @GetMapping(value = {"/", "/index"})
    public String index(Model model) {
        model.addAttribute("title", title);
        return "index";
    }

    @GetMapping(value = "/news")
    public String News(Model model,
                       @RequestParam(value = "page", defaultValue = "1") int pageNumber) {
        List<News> news = newsService.findAll(pageNumber, ROW_PER_PAGE);

        long count = newsService.count();
        boolean hasPrev = pageNumber > 1;
        boolean hasNext = (pageNumber * ROW_PER_PAGE) < count;
        model.addAttribute("news", news);
        model.addAttribute("hasPrev", hasPrev);
        model.addAttribute("prev", pageNumber - 1);
        model.addAttribute("hasNext", hasNext);
        model.addAttribute("next", pageNumber + 1);
        return "news-list";
    }

    @GetMapping(value = {"/news/add"})
    public String showAddUser(Model model) {
        News news = new News();
        model.addAttribute("add", true);
        model.addAttribute("news", news);

        return "news-edit";
    }

    @PostMapping(value = "/news/add")
    public String News(Model model,
                       @ModelAttribute("news") News news) throws Exception {
        news.setPublished(true);
        News newNews = newsService.save(news);
        return "redirect:/news";
    }

    @GetMapping(value = {"/news/{newsId}/edit"})
    public String showEditNews(Model model, @PathVariable long newsId) {
        News news = null;
        news = newsService.findById(newsId);
        model.addAttribute("add", false);
        model.addAttribute("news", news);
        return "news-edit";
    }

    @PostMapping(value = {"/news/{newsId}/edit"})
    public String updateUser(Model model,
                             @PathVariable long newsId,
                             @ModelAttribute("news") News news) throws Exception {

        news.setId(newsId);
        newsService.saveUpdateBook(news);
        return "redirect:/news";
    }

    @GetMapping("/news/{newsId}/delete")
    public String deleteUser(@PathVariable("newsId") Long newsId) throws Exception {
        newsService.deleteById(newsId);
        return "redirect:/news";
    }

}

