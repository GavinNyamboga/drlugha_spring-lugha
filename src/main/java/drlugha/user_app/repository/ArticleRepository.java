package drlugha.user_app.repository;

import drlugha.user_app.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {
    // Add any custom methods if needed
}
