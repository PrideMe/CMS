package com.wangjikai.service;

import com.wangjikai.dao.DepartmentDao;
import com.wangjikai.dao.DocumentDao;
import com.wangjikai.dao.EmployeeDao;
import com.wangjikai.dao.JobDao;
import com.wangjikai.dao.NoticeDao;
import com.wangjikai.dao.PermissionDao;
import com.wangjikai.dao.RoleDao;
import com.wangjikai.dao.UserDao;
import com.wangjikai.domain.Department;
import com.wangjikai.domain.Document;
import com.wangjikai.domain.Employee;
import com.wangjikai.domain.Job;
import com.wangjikai.domain.Notice;
import com.wangjikai.domain.Permission;
import com.wangjikai.domain.Role;
import com.wangjikai.domain.User;
import com.wangjikai.util.Page;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
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
    @Resource
    private RoleDao roleDao;
    @Resource
    private PermissionDao permissionDao;

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
    public User login(String loginname) {
        User user = userDao.selectByLoginname(loginname);
        return user;
    }

    /**
     * 根据id查询用户
     */
    @Override
    @Cacheable(value = "userCache", key = "'user:' + #id")
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
    public User findUserByLoginname(String loginname) {
        return userDao.selectByLoginname(loginname);
    }

    /**
     * 根据用户查询角色权限信息
     * @param user
     * @return
     */
    @Override
    public User selectUserRolePermission(User user) {
        return userDao.selectUserRolePermission(user);
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
    @CacheEvict(value = "userCache", key = "'user:' + #id")
    public void removeUserById(Integer id) {
        userDao.deleteById(id);
    }

    /**
     * 修改用户
     */
    @Override
    @CachePut(value = "userCache", key = "'user:' + #user.id")
    public User modifyUser(User user) {
        userDao.update(user);
        return user;
    }

    /**
     * 添加用户
     */
    @Override
    public void addUser(User user) {
        userDao.insertUser(user);
    }

    /**
     * 获取所有员工
     * 使用缓存、将参数写入key。注意变动
     */
    @Override
    public Page<T> findEmployee(Employee employee, Page<T> page) {
        Map<String,Object> map = new HashMap<>();
        int current = page.getCurrent(); //获取到前台请求的当前页数
        page.setCurrent((current-1)*page.getRowCount());
        map.put("employee",employee);
        map.put("page",page);
        int allCount = employeeDao.count(map);
        List<Employee> employees = employeeDao.findByPage(map);
        page.setTotal(allCount);
        page.setCurrent(current);
        page.setRows(employees);
        //page.setRowCount(page.getRowCount());
        return page;
    }

    /**
     * 根据id删除员工
     */
    @Override
    @CacheEvict(value = "employeeCache", key = "'employee:' + #id")
    public void removeEmployeeById(Integer id) {
        employeeDao.deleteById(id);
    }

    /**
     * 根据id查询员工
     */
    @Override
    @Cacheable(value = "employeeCache", key = "'employee:' + #id")
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
    @CachePut(value = "employeeCache", key = "'employee:' + #employee.id")
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
    @CacheEvict(value = "departmentCache", key = "'department:' + #id")
    public void removeDepartmentById(Integer id) {
        //存在表关联时，首先判断是否使用外键
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
    @Cacheable(value = "departmentCache", key = "'department:' + #id")
    public Department findDepartmentById(Integer id) {
        Department department = departmentDao.findById(id);
        return department;
    }

    /**
     * 修改部门
     */
    @Override
    @CachePut(value = "departmentCache", key = "'department:' + #department.id")
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

    /**
     * 根据id查找角色
     * @param id
     * @return
     */
    @Override
    public Role findRoleById(Integer id) {
        return roleDao.findById(id);
    }

    /**
     * 根据id删除角色
     * @param id
     */
    @Override
    public void deleteRoleById(Integer id) {
        roleDao.deleteById(id);
    }

    /**
     * 修改角色
     * @param role
     */
    @Override
    public void modifyRole(Role role) {
        roleDao.update(role);
    }

    /**
     * 添加角色
     * @param role
     */
    @Override
    public void addRole(Role role) {
        roleDao.addRole(role);
    }

    /**
     * 获取所有符合条件的角色
     * @param role
     * @param page
     * @return
     */
    @Override
    public Page<T> findRole(Role role, Page<T> page) {
        Map<String,Object> map = new HashMap<>();
        int current = page.getCurrent(); //获取到前台请求的当前页数
        page.setCurrent((current-1)*page.getRowCount());
        map.put("role",role);
        map.put("page",page);
        int allCount = roleDao.count(map);
        List<Role> roles = roleDao.selectByPage(map);
        page.setTotal(allCount);
        page.setCurrent(current);
        page.setRows(roles);
        return page;
    }

    /**
     * 根据id查找权限
     * @param id
     * @return
     */
    @Override
    public Permission findPermissionById(Integer id) {
        return permissionDao.findById(id);
    }

    /**
     * 根据id删除权限
     * @param id
     */
    @Override
    public void deletePermissionById(Integer id) {
        permissionDao.deleteById(id);
    }

    /**
     * 修改权限
     * @param permission
     */
    @Override
    public void modifyPermission(Permission permission) {
        permissionDao.update(permission);
    }

    /**
     * 添加权限
     * @param permission
     */
    @Override
    public void addPermission(Permission permission) {
        permissionDao.addPermission(permission);
    }

    /**
     * 获取所有符合条件的角色
     * @param permission
     * @param page
     * @return
     */
    @Override
    public Page<T> findPermission(Permission permission, Page<T> page) {
        Map<String,Object> map = new HashMap<>();
        int current = page.getCurrent(); //获取到前台请求的当前页数
        page.setCurrent((current-1)*page.getRowCount());
        map.put("permission",permission);
        map.put("page",page);
        int allCount = permissionDao.count(map);
        List<Permission> permissions = permissionDao.selectByPage(map);
        page.setTotal(allCount);
        page.setCurrent(current);
        page.setRows(permissions);
        return page;
    }
}
