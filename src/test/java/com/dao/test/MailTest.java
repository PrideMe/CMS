package com.dao.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * Created by 22717 on 2017/12/10.
 * 邮件发送
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:application-context.xml"})
public class MailTest {
    @Resource
    private JavaMailSender mailSender;

    @Test
    public void sendMail(){
        SimpleMailMessage email = new SimpleMailMessage();
        email.setFrom("15314006321@163.com");
        email.setTo("549469611@qq.com");
        email.setSubject("天气预报");
        email.setText("哈哈哈");
        mailSender.send(email);
    }
}
