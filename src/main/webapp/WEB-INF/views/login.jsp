<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>CMS管理系统</title>
    <link type="text/css" rel="stylesheet" href="${ctx}/css/bootstrap.min.css"/>
    <link type="text/css" rel="stylesheet" href="${ctx}/css/css.css"/>
    <link type="text/css" rel="stylesheet" href="${ctx}/css/bootstrapValidator.css"/>
    <link href="${ctx}/images/favicon.ico" rel="shortcut icon">
    <script type="text/javascript" src="${ctx}/js/jquery-3.2.1.min.js"></script>
    <script type="text/javascript" src="${ctx}/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="${ctx}/js/bootstrapValidator.js"></script>
</head>
<body>
<div class="container">
    <div class="row">
        <div class="col-lg-4 col-sm-7 col-md-6 col-xs-10 col-center-block">
            <div class="form-top">
                <form action="${ctx}/login" method="post" class="loginbody" id="loginForm">
                    <div class="form-group">
                        <input type="text" name="username" placeholder="用户名" class="form-control" id="form-username">
                    </div>
                    <div class="form-group">
                        <input type="password" name="password" placeholder="密码" class="form-control" id="form-password">
                    </div>
                    <button type="submit" class="btn btn-primary" id="loginBtn">登陆</button>
                </form>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript">
    $(function () {
        $(document).keydown(function (event) {
            if (event.keyCode == 13) {
                $("#loginBtn").click();
            }
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
                            message: '用户名不能为空',
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