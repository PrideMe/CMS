<%@ page language="java" pageEncoding="UTF-8" isELIgnored="false" contentType="text/html; charset=UTF-8" %>
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
</head>
<body>
<div class="container">
    <div class="row">
        <div class="col-lg-4 col-sm-7 col-md-6 col-center-block">
            <div class="form-top">
                <form action="${ctx}/login" method="post" class="loginbody">
                    <div class="form-group">
                        <input type="text" name="username" placeholder="Username..." class="form-control" id="form-username">
                    </div>
                    <div class="form-group">
                        <input type="password" name="password" placeholder="Password..." class="form-control" id="form-password">
                    </div>
                    <button type="submit" class="btn" id="loginBtn">登陆</button>
                </form>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript" src="${ctx}/js/jquery-3.2.1.min.js"></script>
<script type="text/javascript">
    $(function () {
        $(document).keydown(function (event) {
            if (event.keyCode == 13) {
                $("#loginBtn").click();
            }
        })
    });
</script>
</body>
</html>