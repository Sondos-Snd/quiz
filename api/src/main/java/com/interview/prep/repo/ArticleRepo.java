package com.interview.prep.repo;

import com.interview.prep.domain.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "articles")
public interface ArticleRepo extends JpaRepository<Article, Long> {}
