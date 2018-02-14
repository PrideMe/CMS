package com.wangjikai.shiro;

import com.wangjikai.controller.UserController;
import com.wangjikai.util.MD5Util;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by jikai_wang on 2018/2/3.
 * shiro自定义的密码加密验证
 */
public class CustomCredentialsMatcher extends SimpleCredentialsMatcher {

    private Logger log = LogManager.getLogger(UserController.class);
    //缓存密码登陆次数
    private Cache<String,AtomicInteger> passwordRetryCache;

    public CustomCredentialsMatcher(CacheManager cacheManager) {
        passwordRetryCache = cacheManager.getCache("passwordRetryCache");
    }

    /**
     * 对doCredentialsMatch进行重写
     * @param token 用户输入
     * @param info 数据库中的信息
     * @return
     */
    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) token;
        String username = ((UsernamePasswordToken) token).getUsername();
        //取得缓存中用户的输入错误次数
        AtomicInteger retryCount = passwordRetryCache.get(username);
        if (retryCount == null) {
            retryCount = new AtomicInteger(0);
            passwordRetryCache.put(username, retryCount);
        }
        if (retryCount.incrementAndGet() > 5) {
            //如果输入密码错误次数超过5次，抛出异常
            log.warn("警告：用户："+username+"输入密码次数多于5次，系统予以冻结10分钟处罚");
            throw new ExcessiveAttemptsException();
        }
        char[] tokenCredentials = usernamePasswordToken.getPassword();
        String password = String.valueOf(tokenCredentials);
        log.info("用户："+username+"使用密码："+password+"开始验证");
        String accountCredentials = (String) info.getCredentials();
        boolean result = MD5Util.verify(password,accountCredentials);
        if (result) {
            //验证通过，移出缓存中的【次数】数据
            passwordRetryCache.remove(username);
        } else {
            //验证失败
            log.info("用户："+username+"使用密码："+password+"验证失败");
        }
        return result;
    }
}
