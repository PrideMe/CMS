<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <script type="text/javascript" src="${ctx}/js/bootbox.min.js"/>
    <script type="text/javascript" src="${ctx}/js/jquery.bootgrid.min.js"></script>
    <script type="text/javascript" src="${ctx}/js/jquery.bootgrid.fa.min.js"></script>
</head>
<body>
<table id="employeeList" class="table table-striped table-bordered table-hover">
    <thead>
    <tr>
        <th data-column-id="id" data-identifier="true" data-type="numeric" data-visible="false" data-sortable="true">ID</th>
        <th data-column-id="department" data-formatter="dep" data-visible="true">部门</th>
        <th data-column-id="job" data-formatter="job" data-visible="true">职位</th>
        <th data-column-id="name" data-visible="true">姓名</th>
        <%--<th data-column-id="card_id" data-visible="true">ID卡</th>--%>
        <th data-column-id="address"  data-visible="true">地址</th>
        <%--<th data-column-id="post_code"  data-visible="true">邮编</th>--%>
        <th data-column-id="phone"  data-visible="true">手机</th>
        <%--<th data-column-id="qq"  data-visible="true">QQ</th>--%>
        <th data-column-id="email"  data-visible="true">邮箱</th>
        <%--<th data-column-id="sex"  data-visible="true">性别</th>--%>
        <%--<th data-column-id="party"  data-visible="true">政党</th>--%>
        <%--<th data-column-id="birthday" data-formatter="statuss" data-visible="true">生日</th>--%>
        <%--<th data-column-id="race"  data-visible="true">民族</th>--%>
        <%--<th data-column-id="education"  data-visible="true">学历</th>--%>
        <th data-column-id="speciality"  data-visible="true">专业</th>
        <th data-column-id="remark"  data-visible="true">备注</th>
        <th data-column-id="" data-formatter="operation" data-visible="true">操作</th>
    </tr>
    </thead>
</table>
<script type="text/javascript">
    var formatters = {
        "dep" :function (column,row) {
            var temp = row.department.name;
            return temp;
        },
        "job" :function (column,row) {
            var temp = row.job.name;
            return temp;
        },
        "operation" : function (column,row) {
            var info = "";
            info += "<button onclick='' class=\"btn btn-info btn-xs\"><i class=\"fa fa-pencil fa-fw\"></i>修改</button>&nbsp;&nbsp;";
            info += "<button onclick=\"deleteUser('"+row.id+"')\" class=\"btn btn-danger btn-xs\"><i class=\"fa fa-trash-o fa-lg\"></i>删除</button>&nbsp;&nbsp;";
            return info;
        }
    };
    //主动加载请求，填充表格数据
    $(function () {
        $("#employeeList").bootgrid({
            ajax: true,
            url: "${ctx}/employeeData",
            navigation:3, //0代表没有，1、3正常，2隐藏头部
            rowCount:[10,15,20],
            rowSelect: true,   //点击项目选择
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
                search: "查询"
            }
        });
        $("#employeeList").on("loaded.rs.jquery.bootgrid", function (e){
            $("#employeeList tr").removeClass("success");
            $("#employeeList tr").removeClass("info");
            $("#employeeList tr").removeClass("warning");
        });
    });
</script>
</body>
</html>
