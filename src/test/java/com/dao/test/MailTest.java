package com.dao.test;

import com.wangjikai.domain.Article;
import com.wangjikai.repository.ArticleRepository;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

/**
 * Created by 22717 on 2017/12/10.
 * 引入spring的配置文件相当于在service层进行测试。
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:application-context.xml"})
public class MailTest {
    @Resource
    private JavaMailSender mailSender;

    @Resource
    private ArticleRepository articleRepository;

    @Value("${mail.username}")
    private String mailFrom;//不适用于在controller中使用，因为spring与springMVC不属于同一个容器

    @Test
    public void sendMail(){
        SimpleMailMessage email = new SimpleMailMessage();
        email.setFrom(mailFrom);
        email.setTo("549469611@qq.com");
        email.setSubject("腾讯是傻逼天气预报");
        email.setText("哈哈sdsdf哈");
        mailSender.send(email);
    }
    //发送富文本
    @Test
    public void sendRichMail(){
        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(mailFrom);
            helper.setTo("549469611@qq.com");
            helper.setSubject("23天werwr");
            helper.setText("<img src=\"http://img003.21cnimg.com/photos/album/20160417/m600/1034D87B0C999D86D89BA3DECEB4A460.jpeg\">\n" +
                    "<font color='red'>重新设置密码：http://baidu.com/</font>\n" +
                    "<h4>测试乱码</h4>\n",true);
            //ClassPathResource file = new ClassPathResource("sensitive.txt");//附件
            FileSystemResource fileSystemResource = new FileSystemResource("E:\\IntelliJ_IDEA_Project_Files\\CMS\\src\\main\\webapp\\images\\1.png");
            helper.addAttachment("1.png",fileSystemResource);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        mailSender.send(message);
    }
    //测试quartz，junit不支持多线程测试，只能使用main方法执行
    public static void main(String[] args) {
//        ApplicationContext content = new ClassPathXmlApplicationContext("application-context.xml");
//        content.getBean("schedulerFactoryBean");
        System.out.println(Long.parseLong(UUID.randomUUID().toString().replace("-","")));
    }

    @Test
    public void elastic(){
//        for (int i = 0; i < 50; i++) {
//            Article article = new Article();
//            article.setId(new Date().getTime());
//            article.setTitle("查询方法的封装");
//            article.setContent("2月25日早，陈冠希在其个人社交平台分享了一张自己女儿Alaia的萌照并配文祝大家节日快乐。照片中小公主睁着大大的眼睛，露出小舌头，十分乖萌可爱。");
//            articleRepository.save(article);
            QueryBuilder builder = QueryBuilders.matchAllQuery();
            Iterable<Article> articles = articleRepository.search(builder);
        for (Article article : articles) {
            System.out.println(article);
        }
//        }
        //QueryBuilder queryBuilder = new QueryStringQueryBuilder("第5季");
//        SearchQuery searchQuery = new  NativeSearchQueryBuilder().build();
//        //searchQuery.addFields("第5季");
//        searchQuery.addFields();
//        Iterable<Article> articles = articleRepository.search(searchQuery);
//        for (Article article : articles) {
//            System.out.println(article.toString());
//        }
    }


    @Test
    public void save(){
        Article article = new Article();
        article.setId(1L);
        article.setTitle("陈冠希");
        article.setContent("陈冠希在其个人社交平台分享了一张自己女儿Alaia的萌照并配文祝大家节日快乐。照片中小公主睁着大大的眼睛，露出小舌头，十分乖萌可爱。");
        articleRepository.save(article);
    }

    @Test
    public void baseTest(){
        try {
            FileInputStream fileInputStream = new FileInputStream("E:\\U盘\\chrome.txt");
            FileOutputStream outputStream = new FileOutputStream("E:\\U盘\\chrome2.txt");
            byte[] aa = new byte[30];
            int s = 0;
            while ((s=fileInputStream.read(aa))!=-1){
                outputStream.write(aa,0,s);
            }
            System.out.println(new String(aa,0,20));
            fileInputStream.close();
            outputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
