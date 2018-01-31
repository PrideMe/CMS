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
    var formatters = {
        "dep" :function (column,row) {
            if (row.department != null){
                var temp = row.department.name;
                return temp;
            }
        },
        "sexx" :function (column,row) {
            var temp = "";
            if ("1" == row.sex){
                temp = "男";
            }else {
                temp = "女";
            }
            return temp;
        },
        "operation" : function (column,row) {
            var info = "";
            info += "<button onclick=\"showEmployeeById('"+row.id+"')\" class=\"btn btn-info btn-xs\"><i class=\"fa fa-pencil fa-fw\"></i>修改</button>&nbsp;&nbsp;";
            info += "<button onclick=\"deleteUser('"+row.id+"')\" class=\"btn btn-danger btn-xs\"><i class=\"fa fa-trash fa-fw\"></i>删除</button>&nbsp;&nbsp;";
            return info;
        }
    };
    //主动加载请求，填充表格数据
    $(function () {
        $("#employeeList").bootgrid({
            ajax: true,
            url: "${ctx}/employeeData",
            navigation:3, //0代表没有，1、3正常，2隐藏头部
            rowCount:[15,20,25],
            //rowSelect: true,   //点击项目选择
            selection: true,  //点击选择按钮选择
            multiSelect: true,
            keepSelection: true,
            formatters: formatters,  //格式化
            labels: {
                all: "全部",
                infos: "显示{{ctx.start}}～{{ctx.end}}条， 共{{ctx.total}}条",
                loading: "加载中...",
                noResults: "没有相关数据",
                refresh: "刷新",
                search: "查询姓名"
            }
        });
        $("#employeeList").on("loaded.rs.jquery.bootgrid", function (e){
            $("#employeeList tr").removeClass("success");
            $("#employeeList tr").removeClass("info");
            $("#employeeList tr").removeClass("warning");
        });
    });
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
