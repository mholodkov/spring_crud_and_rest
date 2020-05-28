package crud.book.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import crud.book.exception.MyCoolException;
import crud.book.model.News;
import crud.book.repository.NewsRepository;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/news")
public class NewsApiController {

  private final NewsRepository newsRepository;

  public NewsApiController(NewsRepository newsRepository) throws JsonProcessingException {
    this.newsRepository = newsRepository;
  }

  @GetMapping
  public Iterable findAll() {
    return newsRepository.findAll();
  }

  @GetMapping("/title/{newsTitle}")
  public List findByTitle(@PathVariable String newsTitle) {
    return newsRepository.findByTitle(newsTitle);
  }

  @GetMapping("/{id}")
  public News findOne(@PathVariable Long id) {
    return newsRepository.findById(id)
        .orElseThrow(() -> new MyCoolException("boooM, hello its cool exception"));
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public News logNews(@RequestBody News news) {
    news.setCreationDate(LocalDate.now());
    news.setUpdateDate(new Date());
    return newsRepository.save(news);
  }

  @DeleteMapping("/{id}")
  public void delete(@PathVariable Long id) {
    newsRepository.findById(id)
        .orElseThrow(() -> new MyCoolException("boooM, hello its cool exception"));
    newsRepository.deleteById(id);
  }

  @PutMapping("/{id}")
  public News updateNews(@RequestBody News news, @PathVariable Long id) {
    if (news.getId() != id) {
      throw new MyCoolException("boooM, hello its cool exception");
    }
    News newsFromDb = newsRepository.findById(id)
        .orElseThrow(() -> new MyCoolException("boooM, hello its cool exception"));
    newsFromDb.setUpdateDate(new Date());
    newsFromDb.setAuthor(news.getAuthor());
    newsFromDb.setPublished(news.isPublished());
    newsFromDb.setTitle(news.getTitle());
    newsFromDb.setDescription(news.getDescription());

    return newsRepository.save(newsFromDb);
  }

//  private static final ObjectMapper MAPPER = new ObjectMapper();
//
//  private static String jsonNews = "{\n"
//      + "    \"title\": \"pending\"\n"
//      + "}";
//
//  public static void main(String[] args) throws JsonProcessingException {
//    News news = MAPPER.readValue(jsonNews, News.class);
//    System.out.println(news);
//  }

}
