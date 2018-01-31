package com.wangjikai.service;

import com.wangjikai.domain.Department;
import com.wangjikai.domain.Document;
import com.wangjikai.domain.Employee;
import com.wangjikai.domain.Job;
import com.wangjikai.domain.Notice;
import com.wangjikai.domain.Permission;
import com.wangjikai.domain.Role;
import com.wangjikai.domain.User;
import com.wangjikai.util.Page;

import java.util.List;

/**
 * Created by 22717 on 2017/10/30.
 * 业务逻辑组件。封装DAO对象
 */
public interface CmsService<T> {

    /**
     * 用户注册
     * @param user
     */
    void register(User user);

    /**
     * 用户登陆
     * @param loginname
     * @param password
     * @return User
     */
    User login(String loginname,String password);

    /**
     * 根据id查询用户
     * @param id
     * @return
     */
    User findUserById(Integer id);

    /**
     * 根据用户名密码查找用户
     * @param loginname
     * @return
     */
    User findUserByLoginnameAndPassword(String loginname, String password);

    /**
     * 根据用户查找角色与权限
     * @param user
     * @return
     */
    User selectUserRolePermission(User user);

    /**
     * 获取所有用户
     * @param user
     */
    Page<T> findUser(User user, Page<T> page);

    /**
     * 根据id删除用户
     * @param id
     */
    void removeUserById(Integer id);

    /**
     * 修改用户
     * @param user
     */
    User modifyUser(User user);

    /**
     * 添加用户
     * @param user
     */
    void addUser(User user);

    /**
     * 获取所有员工
     * @param employee
     * @return
     */
    Page<T> findEmployee(Employee employee, Page<T> page);

    /**
     * 根据id删除员工
     * @param id
     */
    void removeEmployeeById(Integer id);

    /**
     * 根据id查询员工
     * @param id
     * @return
     */
    Employee findEmployeeById(Integer id);

    /**
     * 添加员工
     * @param employee
     */
    void addEmployee(Employee employee);

    /**
     * 修改员工
     * @param employee
     */
    void modifyEmployee(Employee employee);

    /**
     * 获取所有部门
     * @param department
     * @return
     */
    List<Department> findDepartment(Department department);

    /**
     * 获取所有部门
     * @return
     */
    List<Department> findALLDepartment();

    /**
     * 根据id删除部门
     * @param id
     */
    void removeDepartmentById(Integer id);

    /**
     * 添加部门
     * @param department
     */
    void addDepartment(Department department);

    /**
     * 根据id查找部门
     * @param id
     * @return
     */
    Department findDepartmentById(Integer id);

    /**
     * 修改部门
     * @param department
     */
    void modifyDepartment(Department department);

    /**
     * 获取所有职位
     * @return
     */
    List<Job> findAllJob();

    /**
     * 获取所有职位
     * @param job
     * @return
     */
    List<Job> findJob(Job job);

    /**
     * 根据id删除职位
     * @param id
     */
    void removeJobById(Integer id);

    /**
     * 添加职位
     * @param job
     */
    void addJob(Job job);

    /**
     * 根据id查找职位
     * @param id
     * @return
     */
    Job findJobById(Integer id);

    /**
     * 修改职位
     * @param job
     */
    void modifyJob(Job job);

    /**
     * 获取所有公告
     * @param notice
     * @return
     */
    List<Notice> findNotice(Notice notice);

    /**
     * 根据id查询公告
     * @param id
     * @return
     */
    Notice findNoticeById(Integer id);

    /**
     * 根据id删除公告
     * @param id
     */
    void removeNoticeById(Integer id);

    /**
     * 添加公告
     * @param notice
     */
    void addNotice(Notice notice);

    /**
     * 修改公告
     * @param notice
     */
    void modifyNotice(Notice notice);

    /**
     * 获取所有文档
     * @param document
     * @return
     */
    List<Document> findDocument(Document document);

    /**
     * 添加文档
     * @param document
     */
    void addDocument(Document document);

    /**
     * 根据id查询文档
     * @param id
     * @return
     */
    Document findDocumentById(Integer id);

    /**
     * 根据id删除文档
     * @param id
     */
    void removeDocumentById(Integer id);

    /**
     * 修改文档
     * @param document
     */
    void modifyDocument(Document document);

    /**
     * 根据id查找角色
     * @param id
     * @return
     */
    Role findRoleById(Integer id);

    /**
     * 根据id删除角色
     * @param id
     */
    void deleteRoleById(Integer id);

    /**
     * 修改角色
     * @param role
     */
    void modifyRole(Role role);

    /**
     * 添加角色
     * @param role
     */
    void addRole(Role role);

    /**
     * 获取所有符合条件的角色
     * @param role
     * @return
     */
    Page<T> findRole(Role role, Page<T> page);

    /**
     * 根据id查找权限
     * @param id
     * @return
     */
    Permission findPermissionById(Integer id);

    /**
     * 根据id删除权限
     * @param id
     */
    void deletePermissionById(Integer id);

    /**
     * 修改权限
     * @param permission
     */
    void modifyPermission(Permission permission);

    /**
     * 添加权限
     * @param permission
     */
    void addPermission(Permission permission);

    /**
     * 获取所有符合条件的角色
     * @param permission
     * @param page
     * @return
     */
    Page<T> findPermission(Permission permission, Page<T> page);
}
