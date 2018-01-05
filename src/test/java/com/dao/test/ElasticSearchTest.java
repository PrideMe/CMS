package com.dao.test;

import com.wangjikai.domain.Article;
import org.elasticsearch.index.query.QueryBuilders;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 22717 on 2017/11/28.
 * 单元测试
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:application-context.xml"})
public class ElasticSearchTest {
    @Resource
    private ElasticsearchTemplate template;

    @Test
    public void testElasticSearch(){
        //template.createIndex("fuck"); //创建索引
        //template.createIndex(User.class);
        //template.getSetting("test");
        List<User> list = template.queryForList(new CriteriaQuery(new Criteria().and(new Criteria("age").is("23"))),User.class);
        for (User user : list) {
            System.out.println(user);
        }
        User user = new User("马晴","23","女","collage","cecgw");
        //template.getSetting("test");
        //template.putMapping(User.class,"user");
        List<IndexQuery> queries = new ArrayList<>();
        IndexQuery indexQuery = new IndexQueryBuilder().withIndexName("test").withType("user").withId("1").withObject(user).build();
//        queries.add(indexQuery);
        //template.index(indexQuery);

//        IndexQuery indexQuery = new IndexQuery();
//        indexQuery.setIndexName("test");
//        indexQuery.setType("user");
//        indexQuery.setId("1");
//        indexQuery.setObject(user);
//        queries.add(indexQuery);
//        Map map = template.getMapping("cecgw","employee");
        //template.delete
        //template.putMapping("test","user",user);
    }

    //添加、更新
    @Test
    public void testBulk(){
        User user = new User("王吉凯","27","女","杭州","中电");
        List<IndexQuery> queries = new ArrayList<>();
        IndexQuery indexQuery = new IndexQueryBuilder().withIndexName("test").withType("user").withId("2").withObject(user).build();
        queries.add(indexQuery);
        template.bulkIndex(queries);
    }

    @Test
    public void search(){
        //根据id删除
        //String id = template.delete(Article.class,"1");
        //System.out.println(id);
        //索引是否存在
        //System.out.println(template.indexExists(User.class));
        //count查询
//        NativeSearchQueryBuilder searchQueryBuilder = new NativeSearchQueryBuilder();
//        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
//        boolQueryBuilder.must(QueryBuilders.termQuery("id","2"));
//        searchQueryBuilder.withIndices("article_index").withQuery(boolQueryBuilder);
//        long count = template.count(searchQueryBuilder.build());
//        System.out.println(count);
        //queryForObject
//        GetQuery query = new GetQuery();
//        query.setId("2");
//        Article article = template.queryForObject(query,Article.class);
//        System.out.println(article);

        SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(
                QueryBuilders.matchQuery("title","算法题")).build();
        //StringQuery stringQuery = new StringQuery("id=1");
        List<Article> articles = template.queryForList(searchQuery,Article.class);
        for (Article article : articles) {
            System.out.println(article.getTitle());
            System.out.println(article.getContent());
            System.out.println("------------");
        }
    }
}
