package com.wangjikai.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.abel533.echarts.Option;
import com.github.abel533.echarts.axis.CategoryAxis;
import com.github.abel533.echarts.axis.ValueAxis;
import com.github.abel533.echarts.code.Magic;
import com.github.abel533.echarts.code.Tool;
import com.github.abel533.echarts.code.Trigger;
import com.github.abel533.echarts.feature.MagicType;
import com.github.abel533.echarts.series.Line;
import com.google.code.kaptcha.Producer;
import com.wangjikai.domain.Article;
import com.wangjikai.domain.Department;
import com.wangjikai.domain.Employee;
import com.wangjikai.domain.Job;
import com.wangjikai.domain.Permission;
import com.wangjikai.domain.Role;
import com.wangjikai.domain.User;
import com.wangjikai.domain.po.RolePermission;
import com.wangjikai.service.CmsService;
import com.wangjikai.util.CollectionsUtil;
import com.wangjikai.util.MD5Util;
import com.wangjikai.util.Page;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by 22717 on 2017/11/4.
 * 处理用户请求控制器
 */
@Controller
public class UserController {

    private Logger log = LogManager.getLogger(UserController.class);

    @Resource
    private CmsService cmsService;

    @Resource
    private JavaMailSender mailSender;

    @Resource
    private Producer captchaProducer;

    @Resource
    private ElasticsearchTemplate template;

    @Value("${mail.username}")
    private String mailFrom;

    @Value("${file.path}")
    private String filePath;  //保存文件路径，从配置文件中获得

    //请求登陆页面
    @RequestMapping(value = {"login"},method = RequestMethod.GET)
    public ModelAndView login(HttpServletRequest request){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login");
        Subject currentUser = SecurityUtils.getSubject();
        if (currentUser.isAuthenticated()){
            modelAndView.setViewName("redirect:/index");
            return modelAndView;
        }
        return modelAndView;
    }
    @RequestMapping(value = {"register"},method = RequestMethod.GET)
    public ModelAndView register(HttpServletRequest request){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("register");
        return modelAndView;
    }

    @RequestMapping(value = {"register"},method = RequestMethod.POST)
    @ResponseBody
    public ModelAndView registerData(HttpServletRequest request, String username, String password_again, String verifyCode){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("register");
        HttpSession session = request.getSession();
        String session_code = session.getAttribute("session_code").toString().toLowerCase();
        if (session_code !=null && session_code.equals(verifyCode.toLowerCase())) {
            User user = new User();
            user.setLoginname(username);
            user.setPassword(MD5Util.generateMD5(password_again));
            user.setUsername(username); //用户可以更改
            user.setStatus("1");  //此时可以发送邮件，请求已有用户进行同意，方可登陆系统
            cmsService.register(user);
            log.info("用户："+username+"，注册成功");
            modelAndView.setViewName("login");
        }
        return modelAndView;
    }

    //请求登陆数据
    //1、通过request.getParameter()
    //2、通过反射
    //ModelAndView与ajax通信问题
    @RequestMapping(value = {"/login"},method = RequestMethod.POST)
    public void loginData(HttpServletRequest request,HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=UTF-8");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/login");
        HttpSession session = request.getSession();
        String session_code = "";
        if (session.getAttribute("session_code") != null) {
            session_code = session.getAttribute("session_code").toString().toLowerCase();
        }
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String code = request.getParameter("verifyCode").toLowerCase();

        Subject currentUser = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(username,password);
        try {
            if (session_code.equals(code)) {
                currentUser.login(token);
                //session.setAttribute("currentUser",user.getUsername());
                modelAndView.setViewName("redirect:/");
                session.removeAttribute("session_code");
                log.info("用户："+username+"登入成功");
                response.getWriter().write("0");
                //return modelAndView;
            } else {
                log.info("用户："+username+"登入失败");
                response.getWriter().write("1");
                //response.getWriter().write("验证码输入错误！");
                //return modelAndView;
            }
        }catch (UnknownAccountException e){
            //modelAndView.addObject("error","用户名/密码错误");
            //response.getWriter().write("账户不存在");
            response.getWriter().write("2");
            //return modelAndView;
        }catch (ExcessiveAttemptsException e){
            response.getWriter().write("3");
            //response.getWriter().write("失败次数过多，锁定10分钟！");
            //return modelAndView;
        }catch (IncorrectCredentialsException e) {
            response.getWriter().write("4");
            //response.getWriter().write("密码错误!");
        } catch (LockedAccountException e) {
            response.getWriter().write("5");
            //response.getWriter().write("帐号被锁定!");
        } catch (DisabledAccountException e) {
            response.getWriter().write("6");
            //response.getWriter().write("帐号被禁用!");
        } catch (AuthenticationException e){
            response.getWriter().write("未知错误！");
            //return modelAndView;
        }
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

    @RequestMapping(value = {"/aboutme"},method = RequestMethod.GET)
    public ModelAndView aboutme(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("aboutme");
        Subject subject = SecurityUtils.getSubject();
        User session_user = (User) subject.getSession().getAttribute("currentUser");
        User user = cmsService.findUserById(session_user.getId());
        modelAndView.addObject("currentUser",user);
        return modelAndView;
    }

    //请求访问主页
    @RequestMapping(value = {"/","index"},method = RequestMethod.GET)
    public ModelAndView index(HttpServletRequest request){
        ModelAndView modelAndView = new ModelAndView();
        String ip = request.getHeader("x-forwarded-for");
        if(null == ip || 0 == ip.length() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if(null == ip || 0 == ip.length() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if(null == ip || 0 == ip.length() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Real-IP");
        }
        if(null == ip || 0 == ip.length() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        modelAndView.setViewName("index");
        log.info("IP:"+ip+"登陆");
        modelAndView.addObject("ip",ip);
        Subject currentUser = SecurityUtils.getSubject();
        User user = (User) currentUser.getSession().getAttribute("currentUser");
        modelAndView.addObject("currentUser",user.getUsername());
        List<Permission> permissions = cmsService.getAllPermission();
        modelAndView.addObject("permissions",permissions);
        return modelAndView;
    }

    @RequestMapping(value = "/logout")
    public String logout(HttpServletRequest request,HttpServletResponse response){
        Subject subject= SecurityUtils.getSubject();
        if (subject != null){
            subject.logout();
            HttpSession session = request.getSession();
            //设置用户最后登陆时间
            session.removeAttribute("currentUser");
        }
        //浏览器禁用缓存
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        return "redirect:/login";
    }

    @RequestMapping(value = {"getUser"},method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> abcd(HttpServletRequest request, int current, int rowCount){
        String searchPhrase = request.getParameter("searchPhrase"); //登录名也行，用户名也可
        User user = new User();
        if (!StringUtils.isEmpty(searchPhrase)){
            user.setLoginname(searchPhrase);
            log.info("获取符合参数["+searchPhrase+"]的用户");
        }
        Page<User> page = new Page<>();
        if (StringUtils.isEmpty(current)){
            current = 1;
        } if (StringUtils.isEmpty(rowCount)){
            rowCount = 1;
        }
        page.setRowCount(rowCount);
        page.setCurrent(current);
        page = cmsService.findUser(user,page);   //获取指定用户
        Map<String,Object> map = new HashMap<>();
        map.put("current",current);
        map.put("rowCount",rowCount);
        map.put("rows",page.getRows());
        map.put("total",page.getTotal());
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
    public User updateUserById(HttpServletRequest request, User user){
        Subject currentUser = SecurityUtils.getSubject();
        User session_user = (User) currentUser.getSession().getAttribute("currentUser");
        session_user.setUsername(user.getUsername());
        //获取前台选择框多选数组.为空或相等则不对角色做任何改变
        String[] roles = request.getParameterValues("role");
        if (!StringUtils.isEmpty(roles)) {
            //前台传回来的角色列表 转化为list
            List<Integer> integers = new ArrayList<>();
            for (String role : roles) {
                integers.add(Integer.valueOf(role));
            }
            List<Role> roleArrayList = new ArrayList<>(roles.length);
            List<Integer> userRoles = cmsService.selectUserRoleRelationByUserId(user.getId());
            //userRoles.getRoleRelation()
            Role role = null;
            for (Integer integer : integers) {
                if (!userRoles.contains(integer)) {
                    role = new Role();
                    role.setId(integer);
                    roleArrayList.add(role);
                }
            }
            //设置角色信息
            user.setRoles(roleArrayList);
        }
//        if (roles.length != 0){
//            //前台传回来的角色列表 转化为list
//            List<Integer> integers1 = new ArrayList<>();
//            for (String role : roles) {
//                integers1.add(Integer.valueOf(role));
//            }
//            //后台DB中的角色列表
//            List<Integer> integers2 = new ArrayList<>();
//            List<Role> roleList = cmsService.findUserById(user.getId()).getRoles();
//            //如果DB中此用户存在角色，则比较，如果不存在角色，则直接添加角色
//            if (roleList.size() != 0){
//                for (Role role : roleList) {
//                    integers2.add(role.getId());
//                }
//                //存在用户则开始判断前台传递的角色为1、增加 2、减少 3、乱序 4、相等
//                if (!Arrays.equals(roles,integers2.toArray())) {
//                    if (integers1.size()>integers2.size()){
//                        //数据库中的角色列表全部包含，说明是增加角色的动作
//                        if (integers1.containsAll(integers2)){
//                            integers1.removeAll(integers2);
//                            List<Role> roleArrayList = new ArrayList<>(roles.length);
//                            Role role = null;
//                            for (Integer integer : integers1) {
//                                role = new Role();
//                                role.setId(integer);
//                                roleArrayList.add(role);
//                            }
//                            //设置角色信息
//                            user.setRoles(roleList);
//                        }else {
//                            List<Integer> temp = new ArrayList<>();
//                            for (Integer integer : integers1) {
//                                temp.add(integer);
//                            }
//                            integers1.retainAll(integers2);
//                            integers2.retainAll(temp);
//                            List<Role> roleArrayList = new ArrayList<>(roles.length);
//                            Role role = null;
//                            for (Integer integer : integers1) {
//                                role = new Role();
//                                role.setId(integer);
//                                roleArrayList.add(role);
//                            }
//                            //设置角色信息
//                            user.setRoles(roleList);
//                        }
//                    } else {
//                        //数据库中的角色列表包含前台传入的，说明是减少角色的操作
//                        if (integers2.containsAll(integers1)){
//                        }
//                    }
//                }
//            } else {
//                List<Role> roleArrayList = new ArrayList<>(roles.length);
//                Role role = null;
//                for (String role1 : roles) {
//                    role = new Role();
//                    role.setId(Integer.valueOf(role1));
//                    roleList.add(role);
//                }
//                //设置角色信息
//                user.setRoles(roleList);
//            }
//        }
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

    @RequestMapping(value = {"userPage"})
    public ModelAndView rightPage(){
        ModelAndView modelAndView = new ModelAndView();
        List<Role> roles = cmsService.findAllRoles();
        modelAndView.addObject("roles",roles);
        modelAndView.setViewName("userPage");
        return modelAndView;
    }

    @RequestMapping(value = {"addUser"})
    public ModelAndView addUser(){
        ModelAndView modelAndView = new ModelAndView();
        List<Role> roles = cmsService.findAllRoles();
        modelAndView.addObject("roles",roles);
        modelAndView.setViewName("addUser");
        return modelAndView;
    }

    @RequestMapping(value = {"addUserData"},method = RequestMethod.POST)
    public String addUserData(User user){
        //密码加密
        user.setPassword(MD5Util.generateMD5(user.getPassword()));
//        String[] roles = request.getParameterValues("role");
//        List<Role> roleList = new ArrayList<>(roles.length);
//        Role role = null;
//        for (int i = 0; i < roles.length; i++) {
//            role = new Role();
//            role.setId(Integer.valueOf(roles[i]));
//            roleList.add(role);
//        }
//        //获取角色信息
//        user.setRoles(roleList);
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

    @RequestMapping(value = "/deletePermission",method = RequestMethod.POST)
    @ResponseBody
    public String deletePermission(String id){
        cmsService.deletePermissionById(Integer.valueOf(id));
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
    public Map<String,Object> employeeData(HttpServletRequest request, int current, int rowCount){
        String searchPhrase = request.getParameter("searchPhrase"); //查询短语
        Employee employee = new Employee();
        if (!StringUtils.isEmpty(searchPhrase)){
            employee.setName(searchPhrase);
            log.info("获取符合参数["+searchPhrase+"]的员工");
        }
        Page<Employee> page = new Page<>();
        if (StringUtils.isEmpty(current)){
            current = 1;
        } if (StringUtils.isEmpty(rowCount)){
            rowCount = 1;
        }
        page.setRowCount(rowCount);
        page.setCurrent(current);
        page = cmsService.findEmployee(employee,page);  //获取符合条件的员工
        Map<String,Object> map = new HashMap<>();
        map.put("current",current);
        map.put("rowCount",rowCount);
        map.put("rows",page.getRows());
        map.put("total",page.getTotal());
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
        //构建简单邮件对象
        SimpleMailMessage email = new SimpleMailMessage();
        email.setFrom(mailFrom);
        email.setTo(mailTo);
        email.setSubject(mailSubject);
        email.setText(mailText);
        mailSender.send(email);
        //支持更复杂的邮件格式和内容
//        MimeMessage message = mailSender.createMimeMessage();
//        try {
//            MimeMessageHelper helper = new MimeMessageHelper(message, true);
//            helper.setFrom("15314006321@163.com");
//            helper.setTo(mailTo);
//            helper.setSubject(mailSubject);
//            helper.setText(mailText);
//            //ClassPathResource file = new ClassPathResource("1.png");//附件
//            //helper.addAttachment("1.png",file);
//        } catch (MessagingException e) {
//            e.printStackTrace();
//        }
//        mailSender.send(message);
        return "1";
    }

    @RequestMapping(value = "/code")
    public void getCaptchaCode(HttpServletRequest request,HttpServletResponse response){
        HttpSession session = request.getSession();
        String capText = captchaProducer.createText();
        session.setAttribute("session_code",capText);
        BufferedImage bi = captchaProducer.createImage(capText);
        ServletOutputStream out = null;
        try {
            out = response.getOutputStream();
            ImageIO.write(bi, "jpg", out);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //搜索页
    @RequestMapping(value = "/search",method = RequestMethod.GET)
    public String searchPage(){
        return "searchPage";
    }

    //查询单个字
    @RequestMapping(value = "/zi")
    @ResponseBody
    public List<Article> searchSingle(){
        SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(
                QueryBuilders.matchQuery("title","算法题")).build();
        List<Article> articles = template.queryForList(searchQuery,Article.class);
        return articles;
    }

    //查询天气
    @RequestMapping(value = "/weather")
    @ResponseBody
    public Map<String, String> getWeather(){
        //https://free-api.heweather.com/s6/weather/now?location=%E5%B9%BF%E5%B7%9E&key=6e56fae9eb8e4e5f8fbbc13d1f15c292
        String Url = "https://free-api.heweather.com/s6/weather/now?location=%E5%B9%BF%E5%B7%9E&key=6e56fae9eb8e4e5f8fbbc13d1f15c292";
        StringBuffer strBuf;
        try {
            Url = "https://free-api.heweather.com/s6/weather/now?location="+URLEncoder.encode("济南", "utf-8")+"&key=6e56fae9eb8e4e5f8fbbc13d1f15c292";
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }
        strBuf = new StringBuffer();
        try{
            URL url = new URL(Url);
            URLConnection conn = url.openConnection();
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(),"utf-8"));//转码。
            String line = null;
            while ((line = reader.readLine()) != null)
                strBuf.append(line + " ");
            reader.close();
        }catch(MalformedURLException e) {
            e.printStackTrace();
        }catch(IOException e){
            e.printStackTrace();
        }
        JSONObject jsonData = JSONObject.parseObject(strBuf.toString());
        Map<String, String> weatherInfo = new HashMap<String, String>();
        weatherInfo.put("温度",jsonData.getJSONArray("HeWeather6").getJSONObject(0).getJSONObject("now").getString("fl").toString()+"°C");
        weatherInfo.put("天气",jsonData.getJSONArray("HeWeather6").getJSONObject(0).getJSONObject("now").getString("cond_txt").toString());
        weatherInfo.put("风向",jsonData.getJSONArray("HeWeather6").getJSONObject(0).getJSONObject("now").getString("wind_dir").toString());
        weatherInfo.put("风力",jsonData.getJSONArray("HeWeather6").getJSONObject(0).getJSONObject("now").getString("wind_sc").toString());
        weatherInfo.put("风速",jsonData.getJSONArray("HeWeather6").getJSONObject(0).getJSONObject("now").getString("wind_spd").toString()+"公里/小时");
        return weatherInfo;
    }

    //请求角色页面
    @RequestMapping(value = {"rolePage"})
    public String rolePage(){
        return "rolePage";
    }

    //请求角色数据
    @RequestMapping(value = {"roleData"},method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> roleData(HttpServletRequest request, int current, int rowCount){
        String searchPhrase = request.getParameter("searchPhrase"); //获取查询参数
        Role role = new Role();
        if (!StringUtils.isEmpty(searchPhrase)){
            role.setName(searchPhrase);
            log.info("查询符合参数["+searchPhrase+"]的角色");
        }
        Page<Role> page = new Page<>();
        if (StringUtils.isEmpty(current)){
            current = 1;
        } if (StringUtils.isEmpty(rowCount)){
            rowCount = 1;
        }
        page.setRowCount(rowCount);
        page.setCurrent(current);
        page = cmsService.findRole(role,page);   //获取指定用户
        Map<String,Object> map = new HashMap<>();
        map.put("current",current);
        map.put("rowCount",rowCount);
        map.put("rows",page.getRows());
        map.put("total",page.getTotal());
        return map;
    }

    @RequestMapping(value = "deleteRole",method = RequestMethod.POST)
    @ResponseBody
    public String deleteRole(Integer id){
        cmsService.deleteRoleById(id);
        return "1";
    }

    @RequestMapping(value = "getRoleById",method = RequestMethod.POST)
    @ResponseBody
    public Role getRoleById(Integer id){
        return cmsService.findRoleById(id);
    }

    @RequestMapping(value = "updateRoleById",method = RequestMethod.POST)
    @ResponseBody
    public Role updateRoleById(Role role){
        cmsService.modifyRole(role);
        return role;
    }

    //请求权限页面
    @RequestMapping(value = {"permissionPage"},method = RequestMethod.GET)
    public String permissionPage(){
        return "permissionPage";
    }

    //请求权限数据，注意树形菜单的显示ztree
    @RequestMapping(value = {"permissionData"},method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> permissionData(HttpServletRequest request, int current, int rowCount){
        String searchPhrase = request.getParameter("searchPhrase"); //获取查询参数
        Permission permission = new Permission();
        if (!StringUtils.isEmpty(searchPhrase)){
            permission.setName(searchPhrase);
            log.info("查询符合参数["+searchPhrase+"]的权限");
        }
        Page<Permission> page = new Page<>();
        if (StringUtils.isEmpty(current)){
            current = 1;
        } if (StringUtils.isEmpty(rowCount)){
            rowCount = 1;
        }
        page.setRowCount(rowCount);
        page.setCurrent(current);
        page = cmsService.findPermission(permission,page);   //获取指定权限
        Map<String,Object> map = new HashMap<>();
        map.put("current",current);
        map.put("rowCount",rowCount);
        map.put("rows",page.getRows());  //尝试获取json节点
        map.put("total",page.getTotal());
        return map;
    }

    //ztree树。显示某个角色具有什么权限，有权限则在ztree上打勾
    @RequestMapping("/getRolePermission")
    @ResponseBody
    public List<Permission> getRolePermission(HttpServletRequest request,Integer roleId){
        Page<Permission> page = new Page<>();
        page.setCurrent(1);
        page.setRowCount(100);
        page = cmsService.findPermission(null,page);
        Permission permission = new Permission();
        permission.setId(0);
        permission.setName("角色权限");
        permission.setChecked(true);
        List<Permission> permissions = page.getRows();
        Role role = cmsService.getRoleRelationPermission(roleId);//当前角色以及对应的权限集合
        if (role != null) {
            for (Permission permission1 : permissions) {
                for (Permission permission2 : role.getPermissions()) {
                    if (permission1.getpCode().equals(permission2.getpCode())) {
                        permission1.setChecked(true);
                    }
                }
            }
        }
        permissions.add(permission);
        return permissions;
    }

    //通过ztree的选择，修改角色对应的权限
    @RequestMapping(value = "updateRolePermission",method = RequestMethod.POST)
    @ResponseBody
    public String updateRolePermission(HttpServletRequest request){
        String[] ids = request.getParameterValues("ids[]");
        if (ids == null) {
            ids = new String[]{};
        }
        String rolePermissionId = request.getParameter("rolePermissionId");
        if (StringUtils.isEmpty(rolePermissionId)) {
            return "1";//失败,rolePermissionId为空，无法找到具体角色
        }
        Integer id = Integer.valueOf(rolePermissionId);
        Role role = cmsService.getRoleRelationPermission(id);//当前角色以及对应的权限集合
        Set<Permission> permissions = new HashSet<>();
        if (role != null) {
            permissions = role.getPermissions();
        }
        List<Integer> idsList = new ArrayList<>();        //前台传递进来的权限集合
        List<Integer> permissionsList = new ArrayList<>();//数据库查询出来的权限集合
        //判断ids是否为空，为空则代表前台权限树清空了
        //此时判断后台数据库中是否存在权限，有则删除。无则无操作
        if (ids.length == 0 && permissions.size()!=0) {
            cmsService.deleteRolePermissionByRoleId(id);
            return "2";//该角色权限已经置空
        } else if (ids.length == 0 && permissions.size() == 0) {
            return "3";//没有修改
        } else if (ids.length != 0 && permissions.size() != 0){
            //ids存在，开始判断与后台数据库中的关系：同、增、减、乱
            for (String s : ids) {
                idsList.add(Integer.valueOf(s));
            }
            for (Permission permission : permissions) {
                permissionsList.add(permission.getId());
            }
            //判断前台输入与数据库已经存在的是否相同。不同则继续对比
            if (CollectionsUtil.compareList(idsList,permissionsList)){
                return "4";//无变化
            } else {
                List<Integer> compare = new ArrayList<>();
                List<RolePermission> rolePermissions = new ArrayList<>();
                List<RolePermission> delRolePermissions = new ArrayList<>();
                //前台数据与后台数据对比
                if (idsList.size() > permissionsList.size()){
                    compare.clear();
                    compare.addAll(idsList);
                    compare.removeAll(permissionsList);
                    //需要添加的
                    for (Integer integer : compare) {
                        RolePermission rolePermission = new RolePermission();
                        rolePermission.setRoleId(id);
                        rolePermission.setPermissionId(integer);
                        rolePermissions.add(rolePermission);
                    }
                    cmsService.insertRolePermission(rolePermissions);
                    //需要删除的
                    permissionsList.removeAll(idsList);
                    for (Integer integer : permissionsList) {
                        RolePermission subRolePermission = new RolePermission();
                        subRolePermission.setPermissionId(integer);
                        delRolePermissions.add(subRolePermission);
                    }
                    if (delRolePermissions.size() != 0) {
                        cmsService.deleteRolePermission(id,delRolePermissions);
                    }
                } else if (idsList.size() < permissionsList.size()){
                    compare.clear();
                    compare.addAll(permissionsList);
                    compare.removeAll(idsList);
                    //需要删除的
                    for (Integer integer : compare) {
                        RolePermission rolePermission = new RolePermission();
                        rolePermission.setPermissionId(integer);
                        delRolePermissions.add(rolePermission);
                    }
                    cmsService.deleteRolePermission(id,delRolePermissions);
                    //需要添加的
                    idsList.removeAll(permissionsList);
                    for (Integer integer : idsList) {
                        RolePermission subRolePermission = new RolePermission();
                        subRolePermission.setRoleId(id);
                        subRolePermission.setPermissionId(integer);
                        rolePermissions.add(subRolePermission);
                    }
                    if (rolePermissions.size() != 0) {
                        cmsService.insertRolePermission(rolePermissions);
                    }
                } else {
                    compare.clear();
                    compare.addAll(idsList);
                    compare.removeAll(permissionsList);
                    //需要添加的
                    for (Integer integer : compare) {
                        RolePermission rolePermission = new RolePermission();
                        rolePermission.setRoleId(id);
                        rolePermission.setPermissionId(integer);
                        rolePermissions.add(rolePermission);
                    }
                    cmsService.insertRolePermission(rolePermissions);
                    //需要删除的
                    permissionsList.removeAll(idsList);
                    for (Integer integer : permissionsList) {
                        RolePermission subRolePermission = new RolePermission();
                        subRolePermission.setPermissionId(integer);
                        delRolePermissions.add(subRolePermission);
                    }
                    if (delRolePermissions.size() != 0) {
                        cmsService.deleteRolePermission(id,delRolePermissions);
                    }
                }
            }
        } else {
            //前台传递来的有值，后台数据库没有该角色对应的权限，直接添加
            List<RolePermission> rolePermissions = new ArrayList<>();
            for (String s : ids) {
                idsList.add(Integer.valueOf(s));
            }
            for (Integer integer : idsList) {
                RolePermission rolePermission = new RolePermission();
                rolePermission.setRoleId(id);
                rolePermission.setPermissionId(integer);
                rolePermissions.add(rolePermission);
            }
            //批量插入
            cmsService.insertRolePermission(rolePermissions);
        }
        return "0";
    }

    //更改密码
    //需要对前台传递进来的密码进行判断，否则空指针！此时省略
    @RequestMapping(value = "updatePassword",method = RequestMethod.POST)
    @ResponseBody
    public String updatePassword(String old_password,String new_password){
        Subject currentUser = SecurityUtils.getSubject();
        if (currentUser != null && currentUser.isAuthenticated()){
            //currentUser
            User session_user = (User) currentUser.getSession().getAttribute("currentUser");
            //如果输入的旧密码与session中的一致，则可以对密码进行更改
            if (MD5Util.verify(old_password,session_user.getPassword())){
                session_user.setPassword(MD5Util.generateMD5(new_password));
                session_user.setRoles(null);
                cmsService.modifyUser(session_user);
                return "1";
            } else
                return "2";
        }
        return "0";
    }


    //Echarts图表测试
    @RequestMapping("/tubiao")
    @ResponseBody
    public Option selectRemoveCauses(){
        Option option = new Option();
        option.legend("高度(km)与气温(°C)变化关系");

        option.toolbox().show(true).feature(Tool.mark, Tool.dataView, new MagicType(Magic.line, Magic.bar), Tool.restore, Tool.saveAsImage);

        option.calculable(true);
        option.tooltip().trigger(Trigger.axis).formatter("Temperature : <br/>{b}km : {c}°C");

        ValueAxis valueAxis = new ValueAxis();
        valueAxis.axisLabel().formatter("{value} °C");
        option.xAxis(valueAxis);

        CategoryAxis categoryAxis = new CategoryAxis();
        categoryAxis.axisLine().onZero(false);
        categoryAxis.axisLabel().formatter("{value} km");
        categoryAxis.boundaryGap(false);
        categoryAxis.data(0, 10, 20, 30, 40, 50, 60, 70, 80);
        option.yAxis(categoryAxis);

        Line line = new Line();
        line.smooth(true).name("高度(km)与气温(°C)变化关系").data(15, -50, -56.5, -46.5, -22.1, -2.5, -27.7, -55.7, -76.5).itemStyle().normal().lineStyle().shadowColor("rgba(0,0,0,0.4)");
        option.series(line);
        return option;
    }

//    @RequestMapping("/echarts")
//    @ResponseBody
//    public Option dsafds(){
//        Option option = new Option();
//        option
//    }
    //文件上传
//    @RequestMapping(path = {"/file/updateHeadPicture.action"}, method = {RequestMethod.GET, RequestMethod.POST})
//    public void index(@RequestParam("imagefile") MultipartFile file, HttpServletResponse response) {
//        try {
//            UploadPictureResponse uploadPictureResponse = uploadService.updateHeadPicture(file);
//                 /*
//                 设置编码格式，返回结果json结果，注意其中的对象转化为json字符串格式为:
//                 {"message":"上传图片成功!","success":1,"url":"C:\\\\home\\\\myblog\\\\pic\\\\2f1b63bc4b654a27a7e0c1b1a0fb9270.png"}
//                 所以前端可以直接读取success，message等信息
//                 */
//            response.setContentType( "application/json;charset=UTF-8");
//            response.getWriter().write( JSON.toJSONString(uploadPictureResponse));
//        } catch (IOException e1) {
//            e1.printStackTrace();
//        }
//    }
//
//    @RequestMapping(value = "",method = RequestMethod.POST)
//    public void uploadImage() {
//    }

    //文件下载
    @RequestMapping(value = "/download")
    public ResponseEntity<byte[]> download(HttpServletRequest request){
        String filename = "手机是三鸡-逆天仙尊.txt";
        File file = new File(filePath+File.separator+filename);
        HttpHeaders headers = new HttpHeaders();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String downloadFielName = format.format(new Date())+"手机是三鸡-逆天仙尊.txt";
        String agent = request.getHeader("USER-AGENT");
        String name = "";
        try {
            if (null != agent && (agent.contains("Edge"))) {
                name = URLEncoder.encode(downloadFielName, "UTF8");
                downloadFielName = name.replaceAll("\\+", "%20"); //将加号还原为空格
            } else if (agent.contains("Safari") || agent.contains("Chrome") || agent.contains("Firefox")) {
                downloadFielName = new String(downloadFielName.getBytes("UTF-8"), "iso-8859-1");
            } else { // IE
                name = URLEncoder.encode(downloadFielName, "UTF-8");
                downloadFielName = name.replaceAll("\\+", "%20"); //将加号还原为空格
            }
        } catch (UnsupportedEncodingException e) {
            downloadFielName = "file";
            System.out.println(filename+"字符转换错误，已使用默认名");
        }
        headers.setContentDispositionFormData("attachment",downloadFielName);
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        try {
            return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file),headers, HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<byte[]>(HttpStatus.NOT_FOUND);
        }
    }
}
