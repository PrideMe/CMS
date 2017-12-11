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
<table id="departmentList" class="table table-striped table-bordered table-hover">
    <thead>
    <tr>
        <th data-column-id="id" data-identifier="true" data-type="numeric" data-visible="true" data-sortable="true">ID</th>
        <th data-column-id="name" data-visible="true">部门</th>
        <th data-column-id="remark" data-visible="true">备注</th>
        <th data-column-id="" data-formatter="operation" data-visible="true">操作</th>
    </tr>
    </thead>
</table>
<div class="modal fade" id="updateDepartment" tabindex="-1" role="dialog">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">×</button>
                <h4 class="modal-title" id="departmentLabel">修改数据</h4>
            </div>
            <form id="editDepartment" class="form-horizontal">
                <div class="modal-body">
                    <div class="input-group hidden">
                        <div class="input-group-addon">id</div>
                        <input class="form-control" id="departmentId" name="id">
                    </div><br/>
                    <div class="input-group">
                        <div class="input-group-addon">部门</div>
                        <input class="form-control" id="departmentname" name="name" placeholder="请输入部门">
                    </div><br/>
                    <div class="input-group">
                        <div class="input-group-addon">备注</div>
                        <input class="form-control" id="remark" name="remark" placeholder="请输入备注">
                    </div><br/>
                </div>
            </form>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button type="button" onclick="updateDepartment()" class="btn btn-success">保存</button>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript">
    var formatters = {
        "operation" :function (column,row) {
            var info = "";
            info += "<button onclick=\"showDepartmentById('"+row.id+"')\" class=\"btn btn-info btn-xs\"><i class=\"fa fa-pencil fa-fw\"></i>修改</button>&nbsp;&nbsp;";
            info += "<button onclick=\"deleteDepartment('"+row.id+"')\" class=\"btn btn-danger btn-xs\"><i class=\"fa fa-trash fa-fw\"></i>删除</button>";
            return info;
        }
    };
    //主动加载请求，填充表格数据
    $(function () {
        $("#departmentList").bootgrid({
            ajax: true,
            url: "${ctx}/departmentData",
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
                search: "查询"
            }
        });
        $("#departmentList").on("loaded.rs.jquery.bootgrid", function (e){
            $("#departmentList tr").removeClass("success");
            $("#departmentList tr").removeClass("info");
            $("#departmentList tr").removeClass("warning");
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
                        url: "${ctx}/deleteDepartment",
                        type: "POST",
                        data: {"id": id},
                        success: function (data) {
                            bootbox.confirm("删除成功！", function (result) {});
                            $("#departmentList").bootgrid("reload");
                        },
                        error: function () {
                            alert("失败");
                        }
                    });
                }
            }
        });
    }
    //显示单个部门
    function showDepartmentById(id) {
        $.ajax({
            dataType: "JSON",
            url: "${ctx}/getDepartmentById",
            type: "POST",
            data: {"id": id},
            success: function (data) {
                $("#departmentLabel").text(data.name);
                $("#departmentId").val(data.id);
                $("#departmentname").val(data.name);
                $("#remark").val(data.remark);
            },
            error: function () {
                alert("请求失败");
            }
        });
        $('#updateDepartment').modal({backdrop:'static'}).on("hidden.bs.modal", function() {
            $(this).removeData("bs.modal");
        });
    }
    //修改部门
    function updateDepartment() {
        var data = $("#editDepartment").serialize();
        $.ajax({
            dataType: "JSON",
            url: "${ctx}/updateDepartmentById",
            type: "POST",
            data: data,
            success: function (data) {
                bootbox.alert("修改成功！");
                $("#departmentList").bootgrid("reload");
            },
            error: function () {
                alert("修改失败！");
            }
        });
    }
</script>
</body>
</html>
