<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="zh-CN">
<head>
    <title>Title</title>
    <script type="text/javascript" src="${ctx}/js/jquery-3.2.1.min.js"></script>
    <script type="text/javascript" src="${ctx}/js/bootstrap.min.js"></script>
</head>
<body>

<div style="margin-top: 30px;" class="col-xs-8 col-lg-4 col-md-6 col-sm-8">
    <form role="form" action="/addUserData" method="post">
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

        <button type="submit" class="btn btn-success">添加</button>
    </form>
</div>

<script type="text/javascript">
    $(function () {
        $("h4").click(function () {
            alert("天气变冷，注意感冒")
        })
    })
</script>
</body>
</html>
