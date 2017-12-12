package com.wangjikai.controller;

import com.wangjikai.domain.Department;
import com.wangjikai.domain.Employee;
import com.wangjikai.domain.Job;
import com.wangjikai.domain.User;
import com.wangjikai.service.CmsService;
import com.wangjikai.util.Page;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 22717 on 2017/11/4.
 * 处理用户请求控制器
 */
@Controller
public class UserController {

    @Resource
    private CmsService cmsService;

    @Resource
    private JavaMailSender mailSender;

    //请求登陆页面
    @RequestMapping(value = {"login"},method = RequestMethod.GET)
    public String login(){
        return "login";
    }

    //请求登陆数据
    //1、通过request.getParameter()
    //2、通过反射
    @RequestMapping(value = {"login"},method = RequestMethod.POST)
    public String loginData(HttpServletRequest request){
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        //拿到所有参数
//        Map<String,String[]> map = request.getParameterMap();
//        for (String s : map.keySet()) {
//            System.out.println(s+"="+request.getParameter(s));
//        }
        Subject currentUser = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(username,password);
        try {
            currentUser.login(token);
        }catch (UnknownAccountException e){
            System.out.println("用户名/密码错误");
            return "login";
        }catch (ExcessiveAttemptsException e){
            System.out.println("失败次数过多，锁定");
            return "login";
        }catch (AuthenticationException e){
            System.out.println(e.getMessage());
            return "login";
        }
        return "redirect:/";
        //User user = cmsService.login(username,password);
        //request.setAttribute("",user);
    }

    //检查用户名冲突
    @RequestMapping(value = "/check",method = RequestMethod.POST)
    @ResponseBody
    public  Map<String,Object> checkUsername(String username){
        //System.out.println(username);
        Map<String,Object> map = new HashMap<>();
        if ("wangjikai".equals(username))
            map.put("valid",false);
        else
            map.put("valid",true);
        return map;
    }

    //请求访问主页
    @RequestMapping(value = {"/","index"},method = RequestMethod.GET)
    public String index(){
        return "index";
    }

    @RequestMapping(value = {"getUser"})
    @ResponseBody
    public Map<String,Object> abcd(HttpServletRequest request){
        List<User> list = cmsService.findUser(null);   //获取所有用户
        String rowCount = request.getParameter("rowCount");
        String current = request.getParameter("current");
        Integer i_rowCount = Integer.valueOf(rowCount);
        Integer i_current = Integer.valueOf(current);

        Page page = new Page();
        page.setCurrent(i_current);
        page.setRowCount(i_rowCount);

        //System.out.println("请求当前页："+i_current);
        //System.out.println("需要显示数："+i_rowCount);

        Map<String,Object> map = new HashMap<>();
        map.put("current",i_current);
        map.put("rowCount",i_rowCount);
        int a;
        int b = i_current*i_rowCount - list.size();
        int c = list.size()>(i_current*i_rowCount)?i_rowCount:(i_rowCount-b);
        //System.out.println("实际显示数："+c);
        if (c%i_rowCount!=0){
            a = list.size();
        } else {
            a = (i_current-1)*i_rowCount+i_rowCount;
        }
        map.put("rows",list.subList((i_current-1)*i_rowCount,a));
        map.put("total",list.size());
        return map;
    }

    @RequestMapping(value = {"getUserById"},method = RequestMethod.POST)
    @ResponseBody
    public User getUserById(int id){
        Map<String,Object> map = new HashMap<>();
        User user = cmsService.findUserById(id);
        return user;
    }

    @RequestMapping(value = {"updateUserById"},method = RequestMethod.POST)
    @ResponseBody
    public User updateUserById(User user){
        //System.out.println(user);
        cmsService.modifyUser(user);
        return user;
    }

    @RequestMapping(value = {"getDepartmentById"},method = RequestMethod.POST)
    @ResponseBody
    public Department getDepartmentById(int id) {
        Map<String, Object> map = new HashMap<>();
        Department department = cmsService.findDepartmentById(id);
        return department;
    }

    @RequestMapping(value = {"updateDepartmentById"},method = RequestMethod.POST)
    @ResponseBody
    public Department updateDepartmentById(Department department){
        //System.out.println(department);
        cmsService.modifyDepartment(department);
        return department;
    }

    @RequestMapping(value = {"getJobById"},method = RequestMethod.POST)
    @ResponseBody
    public Job getJobById(int id){
        Map<String,Object> map = new HashMap<>();
        Job job = cmsService.findJobById(id);
        return job;
    }

    @RequestMapping(value = {"updateJobById"},method = RequestMethod.POST)
    @ResponseBody
    public Job updateJobById(Job job){
        cmsService.modifyJob(job);
        return job;
    }

    @RequestMapping(value = {"getEmployeeById"},method = RequestMethod.POST)
    @ResponseBody
    public Employee getEmployeeById(int id){
        Map<String,Object> map = new HashMap<>();
        Employee employee = cmsService.findEmployeeById(id);
        return employee;
    }

    @RequestMapping(value = {"left"})
    public String madsn(){
        return "left";
    }

    @RequestMapping(value = {"userPage"})
    public String rightPage(){
        return "userPage";
    }

    @RequestMapping(value = {"addUser"})
    public String addUser(){
        return "addUser";
    }

    @RequestMapping(value = {"addUserData"},method = RequestMethod.POST)
    public String addUserData(User user){
        cmsService.addUser(user);
        return "redirect:/index";
    }

    @RequestMapping(value = "deleteUser",method = RequestMethod.POST)
    @ResponseBody
    public String deleteUser(String id){
        //System.out.println(id);
        cmsService.removeUserById(Integer.valueOf(id));
        return "1";
    }

    @RequestMapping(value = "deleteDepartment",method = RequestMethod.POST)
    @ResponseBody
    public String deleteDepartment(String id){
        cmsService.removeDepartmentById(Integer.valueOf(id));
        return "1";
    }

    @RequestMapping(value = "deleteJob",method = RequestMethod.POST)
    @ResponseBody
    public String deleteJob(String id){
        cmsService.removeJobById(Integer.valueOf(id));
        return "1";
    }

    @RequestMapping(value = {"addDepartment"})
    public String addDepartment(){
        return "addDepartment";
    }

    @RequestMapping(value = {"addDepartment"},method = RequestMethod.POST)
    public String addDepartment(Department department){
        cmsService.addDepartment(department);
        return "redirect:/index";
    }

    @RequestMapping(value = {"departmentPage"})
    public String departmentPage(HttpServletRequest request){
        return "departmentPage";
    }

    @RequestMapping(value = {"departmentData"})
    @ResponseBody
    public Map<String,Object> departmentData(HttpServletRequest request){
        List<Department> departments = cmsService.findALLDepartment();//获取所有部门
        String rowCount = request.getParameter("rowCount");
        String current = request.getParameter("current");
        Integer i_rowCount = Integer.valueOf(rowCount);
        Integer i_current = Integer.valueOf(current);
        Map<String,Object> map = new HashMap<>();
        map.put("current",i_current);
        map.put("rowCount",i_rowCount);
        int a = 0;
        int b = i_current*i_rowCount - departments.size();
        int c = departments.size()>(i_current*i_rowCount)?i_rowCount:(i_rowCount-b);
        //System.out.println("实际显示数："+c);
        if (c%i_rowCount!=0){
            a = departments.size();
        } else {
            a = (i_current-1)*i_rowCount+i_rowCount;
        }
        map.put("rows",departments.subList((i_current-1)*i_rowCount,a));
        map.put("total",departments.size());
        return map;
    }

    @RequestMapping(value = {"jobPage"})
    public String jobPage(HttpServletRequest request){
        return "jobPage";
    }

    @RequestMapping(value = {"jobData"})
    @ResponseBody
    public Map<String,Object> jobData(HttpServletRequest request){
        List<Job> jobs = cmsService.findAllJob();//获取所有职位
        String rowCount = request.getParameter("rowCount");
        String current = request.getParameter("current");
        Integer i_rowCount = Integer.valueOf(rowCount);
        Integer i_current = Integer.valueOf(current);
        Map<String,Object> map = new HashMap<>();
        map.put("current",i_current);
        map.put("rowCount",i_rowCount);
        int a = 0;
        int b = i_current*i_rowCount - jobs.size();
        int c = jobs.size()>(i_current*i_rowCount)?i_rowCount:(i_rowCount-b);
        //System.out.println("实际显示数："+c);
        if (c%i_rowCount!=0){
            a = jobs.size();
        } else {
            a = (i_current-1)*i_rowCount+i_rowCount;
        }
        map.put("rows",jobs.subList((i_current-1)*i_rowCount,a));
        map.put("total",jobs.size());
        return map;
    }

    @RequestMapping(value = {"employeePage"})
    public String employeePage(HttpServletRequest request){
        return "employeePage";
    }

    @RequestMapping(value = {"employeeData"})
    @ResponseBody
    public Map<String,Object> employeeData(HttpServletRequest request){
        List<Employee> employees = cmsService.findEmployee(null);  //获取所有员工
        String rowCount = request.getParameter("rowCount");
        String current = request.getParameter("current");
        Integer i_rowCount = Integer.valueOf(rowCount);
        Integer i_current = Integer.valueOf(current);
        Map<String,Object> map = new HashMap<>();
        map.put("current",i_current);
        map.put("rowCount",i_rowCount);
        int a = 0;
        int b = i_current*i_rowCount - employees.size();
        int c = employees.size()>(i_current*i_rowCount)?i_rowCount:(i_rowCount-b);
        //System.out.println("实际显示数："+c);
        if (c%i_rowCount!=0){
            a = employees.size();
        } else {
            a = (i_current-1)*i_rowCount+i_rowCount;
        }
        map.put("rows",employees.subList((i_current-1)*i_rowCount,a));
        map.put("total",employees.size());
        return map;
    }

    @RequestMapping(value = {"addJob"})
    public String addJob(){
        return "addJob";
    }

    @RequestMapping(value = {"addJob"},method = RequestMethod.POST)
    public String addJob(Job job){
        cmsService.addJob(job);
        return "redirect:/index";
    }

    @RequestMapping(value = "/sendMail")
    public String sendMail(){
        return "sendMail";
    }

    @RequestMapping(value = "/sendMailTo",method = RequestMethod.POST)
    @ResponseBody
    public String sendMailTo(String mailTo,String mailSubject,String mailText){
        SimpleMailMessage email = new SimpleMailMessage();
        email.setFrom("15314006321@163.com");
        email.setTo(mailTo);
        email.setSubject(mailSubject);
        email.setText(mailText);
        mailSender.send(email);
        return "1";
    }
}
