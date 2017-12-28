<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="zh-CN">
<head>
    <title>Title</title>
    <script type="text/javascript" src="${ctx}/js/jquery-3.2.1.min.js"></script>
    <script type="text/javascript" src="${ctx}/js/bootstrap.min.js"></script>
    <script>if (window.top !== window.self) {
        window.top.location = window.location;
    }</script>
</head>
<body>

<div style="margin-top: 30px;" class="col-xs-8 col-lg-4 col-md-6 col-sm-8">
    <form role="form" action="/addDepartment" method="post">
        <div class="form-group">
            <label>部门名</label>
            <input class="form-control" name="name" placeholder="请输入部门名">
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
