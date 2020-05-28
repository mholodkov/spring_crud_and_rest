package crud.book.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;


@Data
@Entity
@Table(name = "news")
@NoArgsConstructor
public class News {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "published")
    private boolean published;
    @Column(name = "title")
    private String title;
    @Column(name = "description")
    private String description;
    @Column(name = "author")
    private String author;

    @CreationTimestamp
    @Column(name = "creation_date")
    private LocalDate creationDate;
    @Column(name = "update_date")
    private Date updateDate;

}
