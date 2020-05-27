package crud.book.service;

import crud.book.model.News;
import crud.book.repository.NewsRepository;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class NewsService {

  private final NewsRepository newsRepository;

  // @Autowired лучше не использовать, надо юзать инжект через конструкторы, а еще можно по разному + ломбок прикрутить
  public NewsService(NewsRepository newsRepository) {
    this.newsRepository = newsRepository;
  }

  private boolean existsById(Long id) {
    return newsRepository.existsById(id);
  }

  public News findById(Long id) {
    return newsRepository.findById(id).orElse(null);
  }

  public List<News> findAll(int pageNumber, int rowPerPage) {
    return newsRepository.findAll(
        PageRequest.of(
            pageNumber - 1, rowPerPage,
            Sort.by("id").ascending()
        )
    )
        .getContent();
  }

  public News save(News news) {
    news.setPublished(true);
    news.setUpdateDate(new Date());
    return newsRepository.save(news);
  }


  public void deleteById(Long id) throws Exception {
    // хорошая проверка
    if (!existsById(id)) {
      throw new Exception("Cannot find User with id: " + id);
    } else {
      newsRepository.deleteById(id);
    }
  }

  public Long count() {
    return newsRepository.count();
  }

  public void saveUpdateBook(News news) {
    final Optional<News> optionalBook = newsRepository.findById(news.getId());
    if (optionalBook.isPresent()) {
      final News bookFromDb = optionalBook.get();
      bookFromDb.setUpdateDate(new Date());
      newsRepository.save(bookFromDb);
    }
  }

  public List<News> findByTitle(String title) {
    return newsRepository.findByTitle(title);
  }

}

