package com.wangjikai.repository;

import com.wangjikai.domain.Article;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by 22717 on 2017/12/20.
 * 文章查询dao
 */
@Repository
public interface ArticleRepository extends ElasticsearchRepository<Article,Long> {
}
