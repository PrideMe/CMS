<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<!DOCTYPE HTML>
<html>
<head>
    <title>mail</title>
    <script type="text/javascript" src="${ctx}/js/bootbox.min.js"/>
    <script>if (window.top !== window.self) {
        window.top.location = window.location;
    }</script>
</head>
<body class="cbp-spmenu-push">
<div class="main-content">
    <div id="page-wrapper">
        <div class="">
            <div class="col-md-8">
                <div class="panel-default">
                    <div class="panel-heading">
                        编写新邮件
                    </div>
                    <div class="panel-body">
                        <div class="alert alert-info">
                            完善发送信息
                        </div>
                        <form id="mailForm" class="form-group">
                            <input type="text" class="form-control" name="mailTo" placeholder="收件人："><br>
                            <input type="text" class="form-control" name="mailSubject" placeholder="主  题："><br>
                            <textarea rows="6" class="form-control" name="mailText" style="resize:vertical" placeholder="正  文："></textarea><br>
                        </form>
                        <button class="btn btn-primary" onclick="sendMail()">发送</button>
                    </div>
                </div>
            </div>
            <div class="clearfix"> </div>
        </div>
    </div>
</div>
<script type="text/javascript">
    function sendMail() {
        $.ajax({
            dataType: "JSON",
            url: "${ctx}/sendMailTo",
            type: "POST",
            data: $("#mailForm").serialize(),
            success: function (data) {
                bootbox.alert("发送成功！",function () {
                    $("input").val("");
                    $("textarea").val("");
                });
            },
            error: function () {
                alert("发送失败！");
            }
        });
    }
</script>
</body>
</html>