<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <link rel="stylesheet" href="${ctx}/css/bootstrap.min.css">
    <link rel="stylesheet" href="${ctx}/ztree/zTreeStyle.css">
    <script type="text/javascript" src="${ctx}/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="${ctx}/js/jquery-3.2.1.min.js"></script>
    <script type="text/javascript" src="${ctx}/js/bootbox.min.js"/>
    <script type="text/javascript" src="${ctx}/js/jquery.bootgrid.min.js"></script>
    <script type="text/javascript" src="${ctx}/js/jquery.bootgrid.fa.min.js"></script>
    <script type="text/javascript" src="${ctx}/ztree/jquery.ztree.core.min.js"></script>
    <script>if (window.top !== window.self) {
        window.top.location = window.location;
    }</script>
</head>
<body>
<table id="employeeList" class="table table-striped table-bordered table-hover">
    <thead>
    <tr>
        <th data-column-id="id" data-identifier="true" data-type="numeric" data-visible="false" data-sortable="true">ID</th>
        <th data-column-id="department" data-formatter="dep" data-width="160px">部门</th>
        <th data-column-id="job" data-formatter="job" data-width="160px">职位</th>
        <th data-column-id="name" data-width="80px">姓名</th>
    </tr>
    </thead>
</table>
<ul id="treeDemo" class="ztree"></ul>
<script type="text/javascript">
    var setting = {
        check : {
            chkStyle:"checkbox",
            enable : true   //是否复选框
        },
        data: {
            simpleData: {
                enable: true
            }
        }
    };
    $(document).ready(function(){
        $.ajax({
            type:'POST',
            url:'${ctx}/tests',
            dataType:'JSON',
            success:function(data){
                /*成功后的处理*/
                zTreeObj = $.fn.zTree.init($("#treeDemo"), setting, data);
            }
        });
    });
</script>
</body>
</html>
