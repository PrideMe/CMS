package com.wangjikai.service;

import com.wangjikai.domain.Department;
import com.wangjikai.domain.Document;
import com.wangjikai.domain.Employee;
import com.wangjikai.domain.Job;
import com.wangjikai.domain.Notice;
import com.wangjikai.domain.User;
import com.wangjikai.util.Page;

import java.util.List;

/**
 * Created by 22717 on 2017/10/30.
 * 业务逻辑组件。封装DAO对象
 */
public interface CmsService {
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
     * 获取所有用户
     * @param user
     * @return
     */
    Page<User> findUser(User user, Page<User> page);

    /**
     * 根据id删除用户
     * @param id
     */
    void removeUserById(Integer id);

    /**
     * 修改用户
     * @param user
     */
    void modifyUser(User user);

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
    List<Employee> findEmployee(Employee employee);

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
}
