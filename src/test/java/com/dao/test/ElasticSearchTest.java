package com.dao.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder;
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
}
