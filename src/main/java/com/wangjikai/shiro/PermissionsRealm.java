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
import java.util.Set;

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
        //查询用户权限信息
        User userWithRoleAndPermission = cmsService.selectUserRolePermission(user);
        if (userWithRoleAndPermission != null) {
            Set<Role> roles = userWithRoleAndPermission.getRoles();
            List<String> roleList = new ArrayList<>();
            List<String> permissionList = new ArrayList<>();
            if (user != null) {
                for (Role role : roles) {
                    roleList.add(role.getRoleCode());
                    for (Permission permission : role.getPermissions()) {
                        permissionList.add(permission.getName());
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
        char[] password = token.getPassword();
        //只查找用户信息，不包含角色权限信息
        User user = cmsService.findUserByLoginnameAndPassword(username,String.valueOf(password));
        if (null != user){
            SecurityUtils.getSubject().getSession().setAttribute("currentUser", user);
            return new SimpleAuthenticationInfo(user,user.getPassword(),this.getName());
        }else {
            throw new AuthenticationException();
        }
    }
}
