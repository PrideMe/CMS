package com.wangjikai.shiro;

import com.wangjikai.domain.User;
import com.wangjikai.service.CmsService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import javax.annotation.Resource;

/**
 * Created by 22717 on 2017/11/27.
 * shiro 权限控制
 */
public class PermissionsRealm extends AuthorizingRealm {

    @Resource
    private CmsService cmsService;

    //权限相关
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        User user = (User) principalCollection.getPrimaryPrincipal();
        if (user != null) {
            //authorizationInfo.addRoles();
            authorizationInfo.addStringPermission("aaa");
            return authorizationInfo;
        }
        return null;
    }

    //登陆相关
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        String username = token.getUsername();
        char[] password = token.getPassword();
        User user = cmsService.findUserByLoginnameAndPassword(username,String.valueOf(password));
        if (null != user){
            SecurityUtils.getSubject().getSession().setAttribute("currentUser", user);
            return new SimpleAuthenticationInfo(user,user.getPassword(),this.getName());
        }else {
            throw new AuthenticationException();
        }
    }
}
