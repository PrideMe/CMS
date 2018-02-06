<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<html lang="zh-CN">
<head>
    <title>Title</title>
    <script>if (window.top !== window.self) {
        window.top.location = window.location;
    }</script>
    <style type="text/css">
        html,body {
            height: 100%;
        }
    </style>
</head>
<body>

<div style="margin-top: 30px;" class="col-xs-8 col-lg-4 col-md-6 col-sm-8">
    <form role="form" action="${ctx}/addUserData" method="post">
        <div class="form-group">
            <label>登录名</label>
            <input class="form-control" id="loginname" name="loginname" placeholder="请输入登录名">
        </div>
        <div class="form-group">
            <label>用户名</label>
            <input class="form-control" id="username" name="username" placeholder="请输入用户名">
        </div>
        <div class="form-group">
            <label>密码</label>
            <input type="password" class="form-control" id="password" name="password" placeholder="请输入密码">
        </div>
        <div class="form-group">
            <label>状态</label>
            <input class="form-control" id="status" name="status" placeholder="请输入状态">
        </div>
        <div class="form-group">
            <label>角色</label>
            <select id="lunch" class="selectpicker" data-live-search="true" title="Please select a lunch ...">
                <option>Hot Dog, Fries and a Soda</option>
                <option>Burger, Shake and a Smile</option>
                <option>Sugar, Spice and all things nice</option>
                <option>中</option>
                <option>美国</option>
                <option>日本</option>
                <option>Baby Back Ribs</option>
                <option>A really really long option made to illustrate an issue with the live search in an inline form</option>
            </select>
        </div>
        <button type="submit" class="btn btn-success">添加</button>
    </form>
</div>

<script type="text/javascript">
    $(document).ready(function () {
        $('.selectpicker').selectpicker({
            size: 7
        });
    });
</script>
</body>
</html>
