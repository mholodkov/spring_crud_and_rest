package crud.book.repository;

import crud.book.model.News;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface NewsRepository extends JpaRepository<News, Long> {

  List<News> findByTitle(String title);
}
