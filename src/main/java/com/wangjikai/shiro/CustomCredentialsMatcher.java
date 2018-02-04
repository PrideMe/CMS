package com.wangjikai.shiro;

import com.wangjikai.controller.UserController;
import com.wangjikai.util.MD5Util;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;

/**
 * Created by jikai_wang on 2018/2/3.
 * shiro自定义的密码加密验证
 */
public class CustomCredentialsMatcher extends SimpleCredentialsMatcher {

    private Logger log = LogManager.getLogger(UserController.class);
    /**
     * 对doCredentialsMatch进行重写
     * @param token 用户输入
     * @param info 数据库中的信息
     * @return
     */
    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) token;
        char[] tokenCredentials = usernamePasswordToken.getPassword();
        String password = String.valueOf(tokenCredentials);
        log.info("用户："+((UsernamePasswordToken) token).getUsername()+"使用密码："+password+"开始验证");
        String accountCredentials = (String) info.getCredentials();
        boolean result = MD5Util.verify(password,accountCredentials);
        if (!result) {
            log.info("用户："+((UsernamePasswordToken) token).getUsername()+"使用密码："+password+"验证失败");
        }
        return result;
    }
}
