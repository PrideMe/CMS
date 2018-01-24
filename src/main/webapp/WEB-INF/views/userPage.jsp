<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <script type="text/javascript" src="${ctx}/js/bootbox.min.js"/>
    <script type="text/javascript" src="${ctx}/js/jquery.bootgrid.min.js"></script>
    <script type="text/javascript" src="${ctx}/js/jquery.bootgrid.fa.min.js"></script>
    <script>if (window.top !== window.self) {
        window.top.location = window.location;
    }</script>
</head>
<body>
<table id="grid-data" class="table table-striped table-bordered table-hover">
    <thead>
    <tr>
        <th data-column-id="id" data-identifier="true" data-type="numeric" data-visible="false" data-sortable="true">ID</th>
        <th data-column-id="loginname" data-visible="true">登录名</th>
        <th data-column-id="username" data-visible="true">用户名</th>
        <th data-column-id="password" data-visible="true">密码</th>
        <th data-column-id="createdate" data-visible="true">创建时间</th>
        <th data-column-id="status" data-formatter="statuss" data-width="70px" data-header-align="center" data-align="center">状态</th>
        <th data-column-id="" data-formatter="operation" data-visible="true">操作</th>
    </tr>
    </thead>
</table>
<div class="modal fade" id="updateUser" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">×</button>
                <h4 class="modal-title" id="modalLabel">修改数据</h4>
            </div>
            <form id="editUser" class="form-horizontal">
                <div class="modal-body">
                    <div class="input-group hidden">
                        <div class="input-group-addon">id</div>
                        <input class="form-control" id="userId" name="id">
                    </div><br/>
                    <div class="input-group">
                        <div class="input-group-addon">登录名</div>
                        <input class="form-control" id="loginname" name="loginname" placeholder="请输入登录名">
                    </div><br/>
                    <div class="input-group">
                        <div class="input-group-addon">用户名</div>
                        <input class="form-control" id="username" name="username" placeholder="请输入用户名">
                    </div>
                    <div class="input-group hidden">
                        <div class="input-group-addon">密码</div>
                        <input type="password" class="form-control" id="password" name="password" placeholder="请输入密码">
                    </div><br/>
                    <div class="input-group">
                        <div class="input-group-addon">状&nbsp;&nbsp;&nbsp;态</div>
                        <input class="form-control" id="status" name="status" placeholder="请输入状态">
                    </div><br/>
                </div>
            </form>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button type="button" onclick="updateUser()" class="btn btn-success">保存</button>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript">
    var formatters = {
        "statuss" :function (column,row) {
            var info = "";
            if("1" == row.status){
                info = "<button class=\"btn btn-success btn-xs\">正常</button>"
            } else {
                info = "<button class=\"btn btn-warning btn-xs\">冻结</button>";
                //return "<button onclick=\"aaa('"+column.id+"','"+row.username+"')\" class='btn btn-danger btn-xs'>冻结</button>";
            }
            return info;
        },
        "operation" : function (column,row) {
            var info = "";
            info += "<button onclick=\"showUserById('"+row.id+"')\" class=\"btn btn-info btn-xs\"><i class=\"fa fa-pencil fa-fw\"></i>修改</button>&nbsp;&nbsp;";
            info += "<button onclick=\"deleteUser('"+row.id+"')\" class=\"btn btn-danger btn-xs\"><i class=\"fa fa-trash fa-fw\"></i>删除</button>";
            return info;
        }
    };
    //主动加载请求，填充表格数据
    $(function () {
        $("#grid-data").bootgrid({
            ajax: true,
            url: "${ctx}/getUser",
            navigation:3, //0代表没有，1、3正常，2隐藏头部
            rowCount:[10,15,20],
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
                search: "查询登录名"
            }
        });
        $("#grid-data").on("loaded.rs.jquery.bootgrid", function (e){
            $("#grid-data tr").removeClass("success");
            $("#grid-data tr").removeClass("info");
            $("#grid-data tr").removeClass("warning");
        });
    });
    //删除用户操作
    function deleteUser(id) {
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
                        //contentType : 'application/json;charset=utf-8',
                        dataType: "JSON",
                        url: "${ctx}/deleteUser",
                        type: "POST",
                        data: {"id": id},
                        success: function (data) {
                            bootbox.confirm("删除成功！", function (result) {});
                            $("#grid-data").bootgrid("reload");
                        },
                        error: function () {
                            alert("失败");
                        }
                    });
                }
            }
        });
    }
    //显示单个用户
    function showUserById(id) {
        $.ajax({
            dataType: "JSON",
            url: "${ctx}/getUserById",
            type: "POST",
            data: {"id": id},
            success: function (data) {
                //alert(data.loginname);
                $("#modalLabel").text(data.loginname);
                $("#userId").val(data.id);
                $("#loginname").val(data.loginname);
                $("#username").val(data.username);
                $("#password").val(data.password);
                $("#status").val(data.status);
                $('#updateUser').modal({backdrop:'static'}).on("hidden.bs.modal", function() {
                    $(this).removeData("bs.modal");
                });
            },
            error: function () {
                alert("请求失败");
            }
        });
    }
    //修改用户
    function updateUser() {
        var data = $("#editUser").serialize();
        $.ajax({
            dataType: "JSON",
            url: "${ctx}/updateUserById",
            type: "POST",
            data: data,
            success: function (data) {
                bootbox.alert("修改成功！");
                $("#grid-data").bootgrid("reload");
            },
            error: function () {
                alert("修改失败！");
            }
        });
    }
</script>
</body>
</html>
