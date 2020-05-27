package crud.book.repository;

import crud.book.model.News;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface NewsRepository extends PagingAndSortingRepository<News, Long>,
        JpaSpecificationExecutor<News> {
}
