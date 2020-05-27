package crud.book.service;

import crud.book.model.News;
import crud.book.repository.NewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class NewsService {
    @Autowired
    private NewsRepository newsRepository;

    private boolean existsById(Long id) {
        return newsRepository.existsById(id);
    }

    public News findById(Long id) {
        return newsRepository.findById(id).orElse(null);
    }

    public List<News> findAll(int pageNumber, int rowPerPage) {
        List<News> news = new ArrayList<>();
        Pageable sortedByLastUpdateDesc = PageRequest.of(pageNumber - 1, rowPerPage,
                Sort.by("id").ascending());
        newsRepository.findAll(sortedByLastUpdateDesc).forEach(news::add);
        return news;
    }

    public News save(News news) throws Exception {
        news.setPublished(true);
        news.setUpdateDate(new Date());
        return newsRepository.save(news);
    }

//    public void update(News news) throws Exception {
//        newsRepository.save(news);
//    }

    public void deleteById(Long id) throws Exception {
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

    public List findByTitle(String title) {
        List<News> news = newsRepository.findByTitle(title);
        return news;
    }

}

