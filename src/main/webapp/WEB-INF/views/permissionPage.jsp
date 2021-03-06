<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <script>if (window.top !== window.self) {
        window.top.location = window.location;
    }</script>
</head>
<body>
<table id="permissionList" class="table table-striped table-bordered table-hover">
    <thead>
    <tr>
        <th data-column-id="id" data-identifier="true" data-type="numeric" data-visible="false" data-sortable="true">ID</th>
        <th data-column-id="name">角色</th>
        <th data-column-id="picon" data-formatter="icon">图标</th>
        <th data-column-id="description">描述</th>
        <th data-column-id="" data-formatter="operation" data-visible="true">操作</th>
    </tr>
    </thead>
</table>
<script type="text/javascript">
    var formatters = {
        "operation" :function (column,row) {
            var info = "";
            info += "<button class=\"btn btn-info btn-xs\"><i class=\"fa fa-pencil fa-fw\"></i>修改</button>&nbsp;&nbsp;";
            info += "<button onclick=\"deletePermission('"+row.id+"')\" class=\"btn btn-danger btn-xs\"><i class=\"fa fa-trash fa-fw\"></i>删除</button>";
            return info;
        },
        "icon":function (column,row) {
            return "<i class=\"fa "+row.picon+" fa-fw\"></i>";
        }
    };
    //主动加载请求，填充表格数据
    $(function () {
        $("#permissionList").bootgrid({
            searchSettings: {
                delay: 1000 //每一秒执行一次搜索
            },
            ajax: true,
            url: "${ctx}/permissionData",
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
                search: "查询权限"
            }
        }).on("selected.rs.jquery.bootgrid",function (e,rows) {
            //var rowIds = $("#permissionList").bootgrid("getSelectedRows");
            //alert(rowIds[0])
        });
        $("#permissionList").on("loaded.rs.jquery.bootgrid", function (e){
            $("#permissionList tr").removeClass("success");
            $("#permissionList tr").removeClass("info");
            $("#permissionList tr").removeClass("warning");
        });
    });
    //删除菜单操作
    function deletePermission(id) {
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
                        url: "${ctx}/deletePermission",
                        type: "POST",
                        data: {"id": id},
                        success: function (data) {
                            bootbox.confirm("删除成功！", function (result) {});
                            $("#permissionList").bootgrid("reload");
                        },
                        error: function () {
                            alert("失败");
                        }
                    });
                }
            }
        });
    }
</script>
</body>
</html>
