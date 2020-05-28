package crud.book.controller;


import crud.book.model.News;
import crud.book.service.NewsService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class NewsController {

  private final NewsService newsService;

  private static final int ROW_PER_PAGE = 5;


  @Value("${msg.title}")
  private String title;

  public NewsController(NewsService newsService) {
    this.newsService = newsService;
  }

  @GetMapping(value = {"/", "/index"})
  public String index(Model model) {
    model.addAttribute("title", title);
    return "index";
  }

  @GetMapping(value = "/news")
  public String News(@RequestParam(required = false, defaultValue = "") String title, Model model,
                     @RequestParam(value = "page", defaultValue = "1") int pageNumber) {
    Iterable<News> news;
    if (title != null && !title.isEmpty()) {
      news = newsService.findByTitle(title);
    } else {
      news = newsService.findAll(pageNumber, ROW_PER_PAGE);
    }

    long count = newsService.count();
    boolean hasPrev = pageNumber > 1;
    boolean hasNext = (pageNumber * ROW_PER_PAGE) < count;
    // КРАСАВА, МОЛОДЕЦ!
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

    return "news-create";
  }

  @PostMapping(value = "/news/add")
  public String News(Model model,
                     @ModelAttribute("news") News news) {
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
                           @ModelAttribute("news") News news) {

    news.setId(newsId);
    newsService.saveUpdateBook(news);
    return "redirect:/news";
  }

  @PostMapping("/news/{newsId}/delete")
  public String deleteUser(@PathVariable("newsId") Long newsId) throws Exception {
    newsService.deleteById(newsId);
    return "redirect:/news";
  }

}

