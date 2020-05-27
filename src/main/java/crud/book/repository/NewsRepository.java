package crud.book.repository;

import crud.book.model.News;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface NewsRepository extends PagingAndSortingRepository<News, Long>,
        JpaSpecificationExecutor<News> {
    List<News> findByTitle(String title);
}
