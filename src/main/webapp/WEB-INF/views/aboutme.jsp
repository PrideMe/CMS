<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<html lang="zh-CN">
<head>
    <%--<script type="text/javascript" src="${ctx}/js/jquery-3.2.1.min.js"></script>--%>
    <%--<script type="text/javascript" src="${ctx}/js/bootstrap.min.js"></script>--%>
    <script>if (window.top !== window.self) {
        window.top.location = window.location;
    }</script>
</head>
<body>

<div style="margin-top: 30px;" class="col-xs-8 col-lg-4 col-md-6 col-sm-8">
    <ul id="myTab" class="nav nav-tabs">
        <li class="active">
            <a href="#home" data-toggle="tab">个人资料</a>
        </li>
        <li><a href="#password" data-toggle="tab">密码修改</a></li>
    </ul>
    <div id="myTabContent" class="tab-content">
        <div class="tab-pane fade in active" id="home" style="margin-top: 10px;">
            <form role="form" action="${ctx}/addJob" method="post">
                <div class="form-group">
                    <label>登陆名</label>
                    <input class="form-control" name="name" placeholder="请输入登陆名" value="${currentUser.loginname}">
                </div>
                <div class="form-group">
                    <label>用户名</label>
                    <input class="form-control" name="remark" placeholder="请输入用户名" value="${currentUser.username}">
                </div>
                <div class="form-group">
                    <label>当前角色</label><br>
                    <c:forEach var="roles" items="${currentUser.roles}">
                        <label class='btn btn-warning btn-xs'>${roles.name}</label>
                    </c:forEach>
                </div>

                <input type="button" class="btn btn-success" value="更新"/>
            </form>
        </div>
        <div class="tab-pane fade" id="password" style="margin-top: 10px;">
            <form id="updatePassword">
                <div class="form-group">
                    <label>原密码</label>
                    <input class="form-control" type="password" name="old_password" placeholder="请输入原密码">
                </div>
                <div class="form-group">
                    <label>新密码</label>
                    <input class="form-control" type="password" name="new_password" placeholder="请输入新密码">
                </div>

                <input type="button" onclick="updatePassword()" class="btn btn-success" value="更新"/>
            </form>
        </div>
    </div>
</div>

<script type="text/javascript">
    function updatePassword() {
        $.ajax({
            dataType:"JSON",
            url:"${ctx}/updatePassword",
            type:"POST",
            data:$("#updatePassword").serialize(),
            success:function (data) {
                if (data == '1'){
                    bootbox.alert("更新成功！");
                }else if (data == '2'){
                    bootbox.alert("更新失败！");
                }
            },
            error:function () {
                bootbox.alert("更新失败！");
            }
        });
        $.ajax({
            dataType: "JSON",
            url: "${ctx}/deletePermission",
            type: "POST",
            data: {"id": id}
        });
    }
</script>
</body>
</html>
