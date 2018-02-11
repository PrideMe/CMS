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
<table id="roleList" class="table table-striped table-bordered table-hover">
    <thead>
    <tr>
        <th data-column-id="id" data-identifier="true" data-type="numeric" data-visible="false" data-sortable="true">ID</th>
        <th data-column-id="name">角色</th>
        <th data-column-id="description">描述</th>
        <th data-column-id="" data-formatter="operation" data-visible="true">操作</th>
    </tr>
    </thead>
</table>
<div class="modal fade" id="updateRole" tabindex="-1">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">×</button>
                <h4 class="modal-title" id="modalLabel">修改数据</h4>
            </div>
            <form id="editRole" class="form-horizontal">
                <div class="modal-body">
                    <div class="input-group hidden">
                        <div class="input-group-addon">id</div>
                        <input class="form-control" id="roleId" name="id">
                    </div><br/>
                    <div class="input-group">
                        <div class="input-group-addon">名称</div>
                        <input class="form-control" id="rolename" name="name" placeholder="请输入名称">
                    </div><br/>
                    <div class="input-group">
                        <div class="input-group-addon">代号</div>
                        <input class="form-control" id="roleCode" name="roleCode" placeholder="请输入代号">
                    </div><br/>
                    <div class="input-group">
                        <div class="input-group-addon">描述</div>
                        <input class="form-control" id="r_description" name="description" placeholder="请输入描述">
                    </div><br/>
                </div>
            </form>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button type="button" onclick="updateRole()" class="btn btn-success">保存</button>
            </div>
        </div>
    </div>
</div>
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
                //默认展开全部节点
                zTreeObj.expandAll(true);
            }
        });
    });
    var formatters = {
        "operation" :function (column,row) {
            var info = "";
            info += "<button onclick=\"showRoleById('"+row.id+"')\" class=\"btn btn-info btn-xs\"><i class=\"fa fa-pencil fa-fw\"></i>修改</button>&nbsp;&nbsp;";
            info += "<button onclick=\"deleteRole('"+row.id+"')\" class=\"btn btn-danger btn-xs\"><i class=\"fa fa-trash fa-fw\"></i>删除</button>";
            return info;
        }
    };
    //主动加载请求，填充表格数据
    $(function () {
        $("#roleList").bootgrid({
            searchSettings: {
                delay: 1000 //每一秒执行一次搜索
            },
            ajax: true,
            url: "${ctx}/roleData",
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
                search: "查询角色"
            }
        });
        $("#roleList").on("loaded.rs.jquery.bootgrid", function (e){
            $("#roleList tr").removeClass("success");
            $("#roleList tr").removeClass("info");
            $("#roleList tr").removeClass("warning");
        });
    });
    //删除角色操作
    function deleteRole(id) {
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
                        url: "${ctx}/deleteRole",
                        type: "POST",
                        data: {"id": id},
                        success: function (data) {
                            bootbox.confirm("删除成功！", function (result) {});
                            $("#roleList").bootgrid("reload");
                        },
                        error: function () {
                            alert("失败");
                        }
                    });
                }
            }
        });
    }
    //修改角色
    function updateRole() {
        var data = $("#editRole").serialize();
        $.ajax({
            dataType: "JSON",
            url: "${ctx}/updateRoleById",
            type: "POST",
            data: data,
            success: function (data) {
                bootbox.alert("修改成功！");
                $("#updateRole").modal("hide");
                $("#roleList").bootgrid("reload");
            },
            error: function () {
                alert("修改失败！");
            }
        });
    }
    //显示单个角色
    function showRoleById(id) {
        $.ajax({
            dataType: "JSON",
            url: "${ctx}/getRoleById",
            type: "POST",
            data: {"id": id},
            success: function (data) {
                $("#roleId").val(data.id);
                $("#rolename").val(data.name);
                $("#roleCode").val(data.roleCode);
                $("#r_description").val(data.description);
                $('#updateRole').modal({backdrop:'static'}).on("hidden.bs.modal", function() {
                    $(this).removeData("bs.modal");
                });
            },
            error: function () {
                bootbox.alert("请求失败");
            }
        });
    }
</script>
</body>
</html>
