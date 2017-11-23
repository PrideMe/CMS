package com.dao.test;

import com.wangjikai.dao.*;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by 22717 on 2017/10/6.
 * 结合junit测试数据库
 */
public class DBTest {
    private SqlSession sqlSession;

    @Before //初始化mybatis环境
    public void initEnv(){
        InputStream inputStream = null;
        try {
            inputStream = Resources.getResourceAsStream("mybatis-config.xml");

        } catch (IOException e) {
            e.printStackTrace();
        }
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        sqlSession = sqlSessionFactory.openSession();
    }

    @Test
    public void test() throws NoSuchMethodException, IllegalAccessException, InstantiationException, InvocationTargetException {
        UserDao userDao = sqlSession.getMapper(UserDao.class);
        DepartmentDao departmentDao = sqlSession.getMapper(DepartmentDao.class);
        JobDao jobDao = sqlSession.getMapper(JobDao.class);
        EmployeeDao employeeDao = sqlSession.getMapper(EmployeeDao.class);
        NoticeDao noticeDao = sqlSession.getMapper(NoticeDao.class);
        DocumentDao documentDao = sqlSession.getMapper(DocumentDao.class);

//        Document document = new Document();
//        document.setTitle("公告");
//        document.setFilename("E盘");
//        document.setRemark("测试更新");

//        Map<String,Object> map = new HashMap<>();
//        Document document = new Document();
//        document.setTitle("公告");
//        map.put("document",document);
//        Integer integer = documentDao.count(map);
//        System.out.println(integer);

//        Map<String,Object> map = new HashMap<>();
//        Notice notice = new Notice();
//        notice.setTitle("抗");
//        map.put("notice",notice);
//        Integer integer = noticeDao.count(map);
//        System.out.println(integer);

//        Notice notice = new Notice();
//        notice.setTitle("抗日战争胜利了");
//        notice.setContext("8年抗战，终于成功！");
//        User user = new User();
//        user.setId(3);
//        notice.setUser(user);
//        noticeDao.save(notice);

//        Map<String,Object> map = new HashMap<>();
//        Department department = new Department();
//        department.setId(1002);
//        Employee employee = new Employee();
//        employee.setDepartment(department);
//        map.put("employee",employee);
//
//        List<Employee> lists = employeeDao.findByPage(map);
//        for (Employee list : lists) {
//            System.out.println(list);
//        }

//        Department department = new Department();
//        department.setId(1002);
//        Job job = new Job();
//        job.setId(1001);
//        Employee employee = new Employee();
//        employee.setId(1002);
//        employee.setDepartment(department);
//        employee.setJob(job);
//        employee.setName("李莉莉");
//        employee.setCard_id("4328012134");
//        employee.setAddress("济南历下");
//        employee.setPost_code("251400");
//        employee.setTel("010-88888888");
//        employee.setPhone("13455031827");
//        employee.setQq("549469611");
//        employee.setEmail("wangjikai77@gmail.com");
//        employee.setSex(1);
//        employee.setParty("共青团员");
//        employee.setBirthday(new Date());
//        employee.setRace("回");
//        employee.setEducation("研究生");
//        employee.setSpeciality("计算机");
//        employee.setHobby("LOL");
//        employee.setRemark("最强王者");
//        employeeDao.update(employee);

//        Map<String,Object> map = new HashMap<>();
//        Department department = new Department();
//        department.setId(1005);
//        Employee employee = new Employee();
//        employee.setDepartment(department);
//        Job job = new Job();
//        job.setId(1002);
//        employee.setJob(job);
//        employee.setSex(1);
//        map.put("employee",employee);
//        Integer integer =  employeeDao.count(map);
//        System.out.println(integer);

//        Job job = new Job();
//        job.setId(1011);
//        job.setName("前台");
//        job.setRemark("咨询服务");
//        Map<String,Object> map = new HashMap<>();
//        map.put("job",job);

//        jobDao.update(job);

//        String[] strings = new String[]{"a","b","c"};
//        Method method = DBTest.class.getMethod("say",String.class);
//        for (String string : strings) {
//            method.invoke(DBTest.class.newInstance(),string);
//        }

//        Job job = new Job();
//        job.setName("主管");
//        Map<String,Object> map = new HashMap<>();
//        map.put("job",job);
//        map.put("job",job);
//
//        List<Job> jobs = jobDao.findByPage(map);
//        for (Job job1 : jobs) {
//            System.out.println(job1);
//        }



//        sun.misc.Launcher.getBootstrapClassPath().getURLs();
//        URL[] urls = Launcher.getBootstrapClassPath().getURLs();
//        for (URL url : urls) {
//            System.out.println(url);
//        }

//        List<Job> jobs = jobDao.findAllJob();
//        for (Job job : jobs) {
//            System.out.println(job);
//        }

//        departmentDao.deleteById(1007);
//        Map<String,Object> map = new HashMap<>();
//        Department department = departmentDao.findById(1004);
//        department.setRemark("总公办");
//        departmentDao.update(department);
//        map.put("department",department);
//
//        int a = departmentDao.count(map);
//        System.out.println(a);
//
//        List<Department> list = departmentDao.selectByPage(map);
//        for (Department department1 : list) {
//            System.out.println(department1);
//        }
//        userDao.deleteById(6);
//        User user = new User();
//
//        user.setUsername("管理员");
//        user.setLoginname("15820091357");
//        user.setStatus("1");
//        user.setPassword("123");
//
//        userDao.insertUser(user);
        //user.setStatus("1");

//        Map<String,Object> map = new HashMap<>();
//        map.put("user",user);
//
//        Integer count = userDao.count(map);
//        System.out.println(count);
//        List<User> asd = userDao.selectByPage(map);
//        for (User user1 : asd) {
//            System.out.println(user1);
//        }





        sqlSession.commit();
        sqlSession.close();
    }

    public void say(String name){
        System.out.println("hello"+name);
    }

}
