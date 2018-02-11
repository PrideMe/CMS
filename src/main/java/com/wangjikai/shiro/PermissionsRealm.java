package com.wangjikai.shiro;

import com.wangjikai.domain.Permission;
import com.wangjikai.domain.Role;
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
import java.util.ArrayList;
import java.util.List;

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
        //等到登陆时查询到的用户，此时用户不带角色权限信息
        User user = (User) principalCollection.getPrimaryPrincipal();
        if (user != null) {
            List<Role> roles = user.getRoles();
            List<String> roleList = new ArrayList<>();
            List<String> permissionList = new ArrayList<>();
            if (roles != null) {
                for (Role role : roles) {
                    roleList.add(role.getRoleCode());
                    for (Permission permission : role.getPermissions()) {
                        permissionList.add(permission.getpCode());
                    }
                }
                authorizationInfo.addRoles(roleList);
                authorizationInfo.addStringPermissions(permissionList);
                return authorizationInfo;
            }
        }
        return null;
    }

    //登陆相关
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        String username = token.getUsername();
        //char[] password = token.getPassword();
        //只查找用户信息
        User user = cmsService.findUserByLoginname(username);
        if (user != null) {
            //查询用户权限信息
            User userRolePermission = cmsService.selectUserRolePermission(user);
            if (userRolePermission != null){
                user.setRoles(userRolePermission.getRoles());
            }
            SecurityUtils.getSubject().getSession().setAttribute("currentUser", user);
            //进入自定义比较器进行比较
            return new SimpleAuthenticationInfo(user,user.getPassword(),this.getName());
        }else {
            throw new AuthenticationException();
        }
    }
}
