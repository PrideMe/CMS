package com.wangjikai.controller;

import com.alibaba.fastjson.JSONObject;
import com.google.code.kaptcha.Producer;
import com.wangjikai.domain.Article;
import com.wangjikai.domain.Department;
import com.wangjikai.domain.Employee;
import com.wangjikai.domain.Job;
import com.wangjikai.domain.Permission;
import com.wangjikai.domain.Role;
import com.wangjikai.domain.User;
import com.wangjikai.service.CmsService;
import com.wangjikai.util.MD5Util;
import com.wangjikai.util.Page;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
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
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.System.out;

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

    //请求登陆页面
    @RequestMapping(value = {"login"},method = RequestMethod.GET)
    public ModelAndView login(HttpServletRequest request){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login");
        HttpSession session = request.getSession();
        if (null != session.getAttribute("currentUser")){
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
            user.setStatus("1");
            cmsService.register(user);
            log.info("用户："+username+"，注册成功");
            modelAndView.setViewName("login");
        }
        return modelAndView;
    }

    //请求登陆数据
    //1、通过request.getParameter()
    //2、通过反射
    @RequestMapping(value = {"/login"},method = RequestMethod.POST)
    public ModelAndView loginData(HttpServletRequest request){
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
                return modelAndView;
            } else {
                log.info("用户："+username+"登入失败");
                return modelAndView;
            }
        }catch (UnknownAccountException e){
            out.println("用户名/密码错误");
            return modelAndView;
        }catch (ExcessiveAttemptsException e){
            out.println("失败次数过多，锁定");
            return modelAndView;
        }catch (AuthenticationException e){
            out.println(e.getMessage());
            return modelAndView;
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
        modelAndView.addObject("currentUser",session_user);
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
    public User updateUserById(User user){
        Subject currentUser = SecurityUtils.getSubject();
        User session_user = (User) currentUser.getSession().getAttribute("currentUser");
        session_user.setUsername(user.getUsername());
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

    @RequestMapping("/tests")
    @ResponseBody
    public List<Permission> ztree(){
        Page<Permission> page = new Page<>();
        page.setCurrent(1);
        page.setRowCount(100);
        page = cmsService.findPermission(null,page);
        List<Permission> permissions = page.getRows();
        return permissions;
    }
}
