package com.wangjikai.service;

import com.wangjikai.dao.DepartmentDao;
import com.wangjikai.dao.DocumentDao;
import com.wangjikai.dao.EmployeeDao;
import com.wangjikai.dao.JobDao;
import com.wangjikai.dao.NoticeDao;
import com.wangjikai.dao.UserDao;
import com.wangjikai.domain.Department;
import com.wangjikai.domain.Document;
import com.wangjikai.domain.Employee;
import com.wangjikai.domain.Job;
import com.wangjikai.domain.Notice;
import com.wangjikai.domain.User;
import com.wangjikai.util.Page;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 22717 on 2017/11/3.
 * 管理系统服务层接口实现类
 */
@Service(value = "cmsService")
public class CmsServiceImpl<T> implements CmsService<T> {

    @Resource
    private UserDao userDao;
    @Resource
    private EmployeeDao employeeDao;
    @Resource
    private DepartmentDao departmentDao;
    @Resource
    private JobDao jobDao;
    @Resource
    private NoticeDao noticeDao;
    @Resource
    private DocumentDao documentDao;

    /**
     * 用户注册
     * @param user
     */
    @Override
    public void register(User user) {
        userDao.insertUser(user);
    }

    /**
     * 用户登陆
     */
    @Override
    public User login(String loginname, String password) {
        User user = userDao.selectByLoginnameAndPassword(loginname,password);
        return user;
    }

    /**
     * 根据id查询用户
     */
    @Override
    public User findUserById(Integer id) {
        User user = userDao.selectById(id);
        return user;
    }

    /**
     * 根据用户名查找
     * @param loginname
     * @return
     */
    @Override
    public User findUserByLoginnameAndPassword(String loginname, String password) {
        return userDao.selectByLoginnameAndPassword(loginname,password);
    }

    /**
     * 获取所有用户,可以传递分页值
     */
    @Override
    public Page<T> findUser(User user, Page<T> page) {
        Map<String,Object> map = new HashMap<>();
        int current = page.getCurrent();
        page.setCurrent((current-1)*page.getRowCount());
        map.put("user",user);
        map.put("page",page);
        int allCount = userDao.count(map);
        page.setTotal(allCount);
        List<User> users = userDao.selectByPage(map);
        page.setCurrent(current);
        page.setRows(users);
        return page;
    }

    /**
     * 根据id删除用户
     */
    @Override
    public void removeUserById(Integer id) {
        userDao.deleteById(id);
    }

    /**
     * 修改用户
     */
    @Override
    public void modifyUser(User user) {
        userDao.update(user);
    }

    /**
     * 添加用户
     */
    @Override
    public void addUser(User user) {
        userDao.insertUser(user);
    }

    /**
     * 获取所有员工.暂时不分页
     */
    @Override
    public List<Employee> findEmployee(Employee employee) {
        Map<String,Object> map = new HashMap<>();
        map.put("employee",employee);
        int allCount = employeeDao.count(map);
        List<Employee> employees = employeeDao.findByPage(map);
        return employees;
    }

    /**
     * 根据id删除员工
     */
    @Override
    public void removeEmployeeById(Integer id) {
        employeeDao.deleteById(id);
    }

    /**
     * 根据id查询员工
     */
    @Override
    public Employee findEmployeeById(Integer id) {
        Employee employee = employeeDao.findById(id);
        return employee;
    }

    /**
     * 添加员工
     */
    @Override
    public void addEmployee(Employee employee) {
        employeeDao.save(employee);
    }

    /**
     * 修改员工
     */
    @Override
    public void modifyEmployee(Employee employee) {
        employeeDao.update(employee);
    }

    /**
     * 获取所有部门，暂时不分页
     */
    @Override
    public List<Department> findDepartment(Department department) {
        Map<String,Object> map = new HashMap<>();
        map.put("department",department);
        int allCount = departmentDao.count(map);
        List<Department> departments = departmentDao.selectByPage(map);
        return departments;
    }

    /**
     * 获取所有部门
     */
    @Override
    public List<Department> findALLDepartment() {
        List<Department> departments = departmentDao.findAllDepartment();
        return departments;
    }

    /**
     * 根据id删除部门
     */
    @Override
    public void removeDepartmentById(Integer id) {
        departmentDao.deleteById(id);
    }

    /**
     * 添加部门
     */
    @Override
    public void addDepartment(Department department) {
        departmentDao.save(department);
    }

    /**
     * 根据id查找部门
     */
    @Override
    public Department findDepartmentById(Integer id) {
        Department department = departmentDao.findById(id);
        return department;
    }

    /**
     * 修改部门
     */
    @Override
    public void modifyDepartment(Department department) {
        departmentDao.update(department);
    }

    /**
     * 获取所有职位
     */
    @Override
    public List<Job> findAllJob() {
        List<Job> jobs = jobDao.findAllJob();
        return jobs;
    }

    /**
     * 获取所有职位
     */
    @Override
    public List<Job> findJob(Job job) {
        Map<String,Object> map = new HashMap<>();
        map.put("job",job);
        int allCount = jobDao.count(map);
        List<Job> jobs = jobDao.findByPage(map);
        return jobs;
    }

    /**
     * 根据id删除职位
     */
    @Override
    public void removeJobById(Integer id) {
        jobDao.deleteById(id);
    }

    /**
     * 添加职位
     */
    @Override
    public void addJob(Job job) {
        jobDao.save(job);
    }

    /**
     * 根据id查找职位
     */
    @Override
    public Job findJobById(Integer id) {
        Job job = jobDao.findById(id);
        return job;
    }

    /**
     * 修改职位
     */
    @Override
    public void modifyJob(Job job) {
        jobDao.update(job);
    }

    /**
     * 获取所有公告
     */
    @Override
    public List<Notice> findNotice(Notice notice) {
        Map<String,Object> map = new HashMap<>();
        map.put("notice",notice);
        int allCount = noticeDao.count(map);
        List<Notice> notices = noticeDao.findByPage(map);
        return notices;
    }

    /**
     * 根据id查询公告
     */
    @Override
    public Notice findNoticeById(Integer id) {
        Notice notice = noticeDao.findById(id);
        return notice;
    }

    /**
     * 根据id删除公告
     */
    @Override
    public void removeNoticeById(Integer id) {
        noticeDao.deleteById(id);
    }

    /**
     * 添加公告
     */
    @Override
    public void addNotice(Notice notice) {
        noticeDao.save(notice);
    }

    /**
     * 修改公告
     */
    @Override
    public void modifyNotice(Notice notice) {
        noticeDao.update(notice);
    }

    /**
     * 获取所有文档
     */
    @Override
    public List<Document> findDocument(Document document) {
        Map<String,Object> map = new HashMap<>();
        map.put("document",document);
        int allCount = documentDao.count(map);
        List<Document> documents = documentDao.findByPage(map);
        return documents;
    }

    /**
     * 添加文档
     */
    @Override
    public void addDocument(Document document) {
        documentDao.save(document);
    }

    /**
     * 根据id查询文档
     */
    @Override
    public Document findDocumentById(Integer id) {
        Document document = documentDao.findById(id);
        return document;
    }

    /**
     * 根据id删除文档
     */
    @Override
    public void removeDocumentById(Integer id) {
        documentDao.deleteById(id);
    }

    /**
     * 修改文档
     */
    @Override
    public void modifyDocument(Document document) {
        documentDao.update(document);
    }
}
