<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1,user-scalable=no">
    <title>CMS管理系统</title>
    <link type="text/css" rel="stylesheet" href="${ctx}/css/bootstrap.min.css"/>
    <link type="text/css" rel="stylesheet" href="${ctx}/css/css.css"/>
    <link rel="stylesheet" href="${ctx}/css/font-awesome.min.css">
    <link type="text/css" rel="stylesheet" href="${ctx}/css/bootstrapValidator.css"/>
    <link type="text/css" rel="stylesheet" href="${ctx}/css/animate.css"/>
    <link href="${ctx}/images/favicon.ico" rel="shortcut icon">
    <script type="text/javascript" src="${ctx}/js/jquery-3.2.1.min.js"></script>
    <script type="text/javascript" src="${ctx}/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="${ctx}/js/bootstrapValidator.js"></script>
    <script type="text/javascript">
        if (window.top !== window.self) {
            window.top.location = window.location;
        }
    </script>
</head>
<%--<body style="background: #e4e4e4 url(${ctx}/images/login_bg.png) repeat;">--%>
<body style="background: #e4e4e4 url(${ctx}/images/login_bg.jpg) repeat;">
<div class="container">
    <div class="row">
        <div class="col-lg-4 col-sm-7 col-md-6 col-xs-12 vertical-center">
            <form class="fh5co-form loginbody animate-box fadeInRight animated-fast" id="loginForm">
                <h2 style="margin-bottom: 20px;">登陆</h2>
                <div class="form-group">
                    <div class="input-group">
                        <div class="input-group-addon">
                            <i class="fa fa-user fa-fw"></i>
                        </div>
                        <input type="text" name="username" placeholder="用户名" class="form-control" id="form-username">
                    </div>
                </div>
                <div class="form-group">
                    <div class="input-group">
                        <div class="input-group-addon">
                            <i class="fa fa-lock fa-fw"></i>
                        </div>
                        <input type="password" name="password" placeholder="密码" class="form-control" id="form-password">
                    </div>
                </div>
                <div class="form-inline">
                    <div class="form-group" style="width: 45%;float: left">
                        <div class="input-group">
                            <input type="text" id="verifyCode" name="verifyCode" class="form-control" placeholder="请输入验证码">
                        </div>
                    </div>
                    <img id="kaptchaImage" class="verify-code" style="float: right" src="${ctx}/code">
                </div>
                <div class="clearfix"></div>
                <div class="form-group" style="margin-bottom: 10px;font-size: 15px;">
                    <p><a href="${ctx}/register">注册账号</a> | <a href="${ctx}/forgot3.html">忘记密码</a></p>
                </div>
                <br>
                <input type="button" class="btn btn-primary" style="width: 61%" id="loginBtn" value="登陆"/>
            </form>
        </div>
    </div>
</div>
<script type="text/javascript">
    $("#loginBtn").click(function () {
        login();
    });
    function login() {
        $.ajax({
            type:'POST',
            url:'${ctx}/login',
            dataType:'text',
            data:$("#loginForm").serialize(),
            success:function(data){
                if (data == '0') {
                    window.location.href="${ctx}/index";
                } else if (data == '1'){
                    $("#verifyCode").val("");
                    alert("验证码错误！");
                    $("#kaptchaImage").attr('src','${ctx}/code?'+Math.random());
                } else if (data == '2'){
                    $("#form-username").val("");
                    $("#form-password").val("");
                    alert("账户不存在！");
                } else if (data == '3'){
                    alert("失败次数过多，锁定10分钟！");
                } else if (data == '4'){
                    alert("密码错误！");
                    $("#form-password").val("");
                } else if (data == '5'){
                    $("#form-username").val("");
                    $("#form-password").val("");
                    alert("帐号被锁定！");
                } else if (data == '6'){
                    $("#form-username").val("");
                    $("#form-password").val("");
                    alert("帐号被禁用！");
                } else {
                    window.location.href="${ctx}/login";
                    alert("未知错误！");
                }
            },
            error:function (data) {
                window.location.href="${ctx}/login";
            }
        });
    }
//    $(function(){
//        if (window.history && window.history.pushState) {
//            $(window).on('popstate', function () {
//                //当点击浏览器的 后退和前进按钮 时才会被触发，
//                window.history.pushState('forward', null, '');
//                window.history.forward(1);
//            });
//        }
//        window.history.pushState('forward', null, '');  //在IE中必须得有这两行
//        window.history.forward(1);
//
//    });
    $(function() {
        $(document).keydown(function (event) {
            if (event.keyCode == 13) {
                login();
                return false;
            }
        });
        $('#kaptchaImage').click(function() {
            $(this).attr('src','${ctx}/code?'+Math.random());
        });
    });
    $(document).ready(function() {
        $('#loginForm').bootstrapValidator({
            message: 'This value is not valid',
            feedbackIcons: { /*input状态样式图片*/
                valid: 'glyphicon glyphicon-ok',
                invalid: 'glyphicon glyphicon-remove',
                validating: 'glyphicon glyphicon-refresh'
            },
            fields: {
                username: {
                    message: 'The username is not valid',
                    validators: {
                        notEmpty: {
                            message: '用户名不能为空'
                        },
                        threshold:6,//有6字符以上才发送ajax请求，（input中输入一个字符，插件会向服务器发送一次，设置限制，6字符以上才开始）
                        stringLength: {
                            min: 6,
                            max: 30,
                            message: '用户名必须是多于6个字符少于30个字符'
                        },
                        //emailAddress: {  //校验email地址
                        //    message: '请输入正确的邮件地址如：123@qq.com'
                        //},
                        remote: {
                            url:'${ctx}/check',
                            type: 'POST',
                            delay :  2000,//每输入一个字符，就发ajax请求，服务器压力还是太大，设置2秒发送一次ajax（默认输入一个字符，提交一次，服务器压力太大）
                            message:'用户名已存在'
                        },
                        regexp: {
                            regexp: /^[a-zA-Z0-9_\.]+$/,
                            message: '用户名只能由字母，数字，点号和下划线组成'
                        }
                    }
                },
                password: {
                    validators: {
                        notEmpty: {
                            message: '密码不能为空'
                        },
                        stringLength: {
                            min: 6,
                            max: 30,
                            message: '密码必须是多于6个字符少于30个字符'
                        },
                        different:{   //要求必须不同
                            field:'username',
                            message:'密码不能与用户名相同'
                        }
                        //identical:{  //要求必须相同
                        //    field:'password',  //需要进行比较的input name值
                        //    message:'用户名不能与密码相同'  //比较厚不相同时提示
                        //}
                    }
                },
                verifyCode: {
                    validators: {
                        notEmpty: {
                            message: '验证码不能为空'
                        },
                        stringLength: {
                            min: 4,
                            max: 4,
                            message: '验证码为4个字符'
                        },
                        regexp: {
                            regexp: /^[a-zA-Z0-9]+$/,
                            message: '验证码只能是字母，数字组成'
                        }
                    }
                }
            }
        });
        //.on('success.form.bv',function (e) { //点击提交之后
        //    //防止表单提交
        //    e.preventDefault();
        //    //获取表单实例
        //    var $form = $(e.target);
        //    //获取BootstrapValidator实例
        //    var bv = $form.data('bootstrapValidator');
        //});
    });
</script>
</body>
</html>