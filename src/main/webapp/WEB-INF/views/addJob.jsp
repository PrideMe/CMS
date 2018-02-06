<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<html lang="zh-CN">
<head>
    <title>Title</title>
    <script>if (window.top !== window.self) {
        window.top.location = window.location;
    }</script>
</head>
<body>

<div style="margin-top: 30px;" class="col-xs-8 col-lg-4 col-md-6 col-sm-8">
    <form role="form" action="${ctx}/addJob" method="post">
        <div class="form-group">
            <label>职位名</label>
            <input class="form-control" name="name" placeholder="请输入职位名">
        </div>
        <div class="form-group">
            <label>备注</label>
            <input class="form-control" name="remark" placeholder="请输入备注">
        </div>

        <button type="submit" class="btn btn-success">添加</button>
    </form>
</div>

<script type="text/javascript">
</script>
</body>
</html>
