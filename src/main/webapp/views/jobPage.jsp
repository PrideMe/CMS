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
<table id="jobList" class="table table-striped table-bordered table-hover">
    <thead>
    <tr>
        <th data-column-id="id" data-identifier="true" data-type="numeric" data-visible="true" data-sortable="true">ID</th>
        <th data-column-id="name" data-visible="true">职位</th>
        <th data-column-id="remark" data-visible="true">备注</th>
        <th data-column-id="" data-formatter="operation" data-visible="true">操作</th>
    </tr>
    </thead>
</table>
<script type="text/javascript">
    var formatters = {
        "operation" :function (column,row) {
            return "<button onclick=\"deleteDepartment('"+row.id+"')\" class=\"btn btn-danger btn-xs\">删除</button>";
        }
    };
    //主动加载请求，填充表格数据
    $(function () {
        $("#jobList").bootgrid({
            ajax: true,
            url: "${ctx}/jobData",
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
        $("#jobList").on("loaded.rs.jquery.bootgrid", function (e){
            $("#jobList tr").removeClass("success");
            $("#jobList tr").removeClass("info");
            $("#jobList tr").removeClass("warning");
        });
    });
    //删除部门操作
    function deleteDepartment(id) {
        bootbox.confirm({
            message: "确定要删除所选信息吗？",
            buttons: {
                confirm: {
                    label: '删除',
                    className: 'btn-success'
                },
                cancel: {
                    label: '取消',
                    className: 'btn-danger'
                }
            },
            callback: function (result) {
                if (result) {
                    $.ajax({
                        dataType: "JSON",
                        url: "${ctx}/deleteJob",
                        type: "POST",
                        data: {"id": id},
                        success: function (data) {
                            bootbox.confirm("删除成功！", function (result) {});
                            $("#jobList").bootgrid("reload");
                        },
                        error: function () {
                            alert("失败");
                        }
                    });
                }
            }
        });
    };
</script>
</body>
</html>
