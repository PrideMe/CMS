<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1,user-scalable=no;">
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
    <script>if (window.top !== window.self) {
        window.top.location = window.location;
    }</script>
</head>
<body style="background: #e4e4e4 url(${ctx}/images/login_bg.png) repeat;">
<div class="container">
    <div class="row">
        <div class="col-lg-4 col-sm-7 col-md-6 col-xs-12 col-center-block">
            <form action="${ctx}/register" method="post" class="fh5co-form loginbody animate-box fadeInRight animated-fast"
                  id="loginForm">
                <h2 style="margin-bottom: 20px;">注册</h2>
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
                            <input type="text" name="verifyCode" class="form-control" placeholder="请输入验证码">
                        </div>
                    </div>
                    <img id="kaptchaImage" class="verify-code" style="float: right" src="${ctx}/code">
                </div>
                <div class="clearfix"></div>
                <div class="form-group" style="margin-bottom: 10px">
                    <p><a href="${ctx}/login">去登陆</a></p>
                </div>
                <br>
                <button type="submit" class="btn btn-primary" style="width: 61%" id="loginBtn">注册</button>
            </form>
        </div>
    </div>
</div>
<script type="text/javascript">
    $(function () {
        $('#kaptchaImage').click(function() {
            $(this).attr('src','${ctx}/code?'+Math.random());
        });
    });
</script>
</body>
</html>