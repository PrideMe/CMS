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
<div class="modal fade" id="updatePermission" tabindex="-1">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">×</button>
                <h4 class="modal-title" id="modalLabel2">修改权限</h4>
                <input class="hidden" id="rolePermissionId">
            </div>
            <ul style="margin-left: 2em" id="treeDemo" class="ztree"></ul>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button type="button" onclick="updateRolePermission()" class="btn btn-success">保存</button>
            </div>
        </div>
    </div>
</div>
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
    var zTreeObj;
    var formatters = {
        "operation" :function (column,row) {
            var info = "";
            info += "<button onclick=\"showRoleById('"+row.id+"')\" class=\"btn btn-info btn-xs\"><i class=\"fa fa-pencil fa-fw\"></i> 修改</button>&nbsp;&nbsp;";
            info += "<button onclick=\"deleteRole('"+row.id+"')\" class=\"btn btn-danger btn-xs\"><i class=\"fa fa-trash fa-fw\"></i> 删除</button>&nbsp;&nbsp;";
            info += "<button onclick=\"showPermission('"+row.id+"','"+row.name+"')\" class=\"btn btn-primary btn-xs\"><i class=\"fa fa-vcard fa-fw\"></i> 分配权限</button>&nbsp;&nbsp;";
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
            rowCount:[15,20,25],
            //rowSelect: true,   //点击项目选择
            selection: true,  //点击选择按钮选择
            multiSelect: true,
            keepSelection: true,
            formatters: formatters,  //格式化
            templates:{
                header: "<div id=\"{{ctx.id}}\" class=\"{{css.header}}\"><div class=\"row\"><div class=\"col-sm-12 actionBar\">" +
                "<p style='float: left'><button class=\"btn btn-primary\" type=\"button\">"+
                "<span class=\"fa fa-plus fa-fw\"></span>"+"\n"+"添加角色</button>"+"\n"+
                "<button class=\"btn btn-danger\" type=\"button\">"+
                "<span class=\"fa fa-trash fa-fw\"></span>"+"\n"+"删除角色</button></p>"+
                "<p class=\"{{css.search}}\"></p><p class=\"{{css.actions}}\"></p></div></div></div>"
            },
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
    //显示修改权限
    function showPermission(id,name) {
        $.ajax({
            type:'POST',
            url:'${ctx}/getRolePermission',
            dataType:'JSON',
            data:{"roleId":id},
            success:function(data){
                /*成功后的处理*/
                zTreeObj = $.fn.zTree.init($("#treeDemo"), setting, data);
                //默认展开全部节点
                zTreeObj.expandAll(true);
                $("#rolePermissionId").val(id);

                $("#modalLabel2").text("修改【"+name+"】权限");
                //显示模态框
                $('#updatePermission').modal({backdrop:'static'}).on("hidden.bs.modal", function() {
                    $(this).removeData("bs.modal");
                });
            }
        });
    }
    //修改角色对应的权限
    function updateRolePermission() {
        var checkedNodes = zTreeObj.getCheckedNodes(true);
        //console.log(checkedNodes);
        var array = new Array();
        for(var i=0;i<checkedNodes.length;i++){
            if (!checkedNodes[i].id == 0){
                array.push(checkedNodes[i].id);
            }
        }
        var rolePermissionId = $("#rolePermissionId").val();
        $.ajax({
            type:'POST',
            url:'${ctx}/updateRolePermission',
            dataType:'JSON',
            data:{"rolePermissionId":rolePermissionId,"ids":array},
            success:function(data){
                if (data == "0"){
                    bootbox.alert("修改成功！");
                } else if (data == "1") {
                    bootbox.alert("无法找到具体角色！");
                } else if (data == "2") {
                    bootbox.alert("该角色权限已经置空！");
                } else if (data == "3") {
                    bootbox.alert("没有修改！");
                } else if (data == "4") {
                    bootbox.alert("无变化！");
                } else {
                    bootbox.alert("未知错误！");
                }
            }
        });
    }
</script>
</body>
</html>
