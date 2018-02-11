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
<div class="modal fade" id="updateJob" tabindex="-1" role="dialog">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">×</button>
                <h4 class="modal-title" id="jobLabel">修改数据</h4>
            </div>
            <form id="editjob" class="form-horizontal">
                <div class="modal-body">
                    <div class="input-group hidden">
                        <div class="input-group-addon">id</div>
                        <input class="form-control" id="jobId" name="id">
                    </div><br/>
                    <div class="input-group">
                        <div class="input-group-addon">职位</div>
                        <input class="form-control" id="jobname" name="name" placeholder="请输入职位">
                    </div><br/>
                    <div class="input-group">
                        <div class="input-group-addon">备注</div>
                        <input class="form-control" id="remark" name="remark" placeholder="请输入备注">
                    </div><br/>
                </div>
            </form>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button type="button" onclick="updateJob()" class="btn btn-success">保存</button>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript">
    var formatters = {
        "operation" :function (column,row) {
            var info = "";
            info += "<button onclick=\"showJobById('"+row.id+"')\" class=\"btn btn-info btn-xs\"><i class=\"fa fa-pencil fa-fw\"></i>修改</button>&nbsp;&nbsp;";
            info += "<button onclick=\"deleteDepartment('"+row.id+"')\" class=\"btn btn-danger btn-xs\"><i class=\"fa fa-trash fa-fw\"></i>删除</button>";
            return info;
        }
    };
    //主动加载请求，填充表格数据
    $(function () {
        $("#jobList").bootgrid({
            searchSettings: {
                delay: 1000 //每一秒执行一次搜索
            },
            ajax: true,
            url: "${ctx}/jobData",
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
                search: "查询职位"
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
    }
    //显示单个职位
    function showJobById(id) {
        $.ajax({
            dataType: "JSON",
            url: "${ctx}/getJobById",
            type: "POST",
            data: {"id": id},
            success: function (data) {
                $("#jobLabel").text(data.name);
                $("#jobId").val(data.id);
                $("#jobname").val(data.name);
                $("#remark").val(data.remark);
            },
            error: function () {
                alert("请求失败");
            }
        });
        $('#updateJob').modal({backdrop:'static'}).on("hidden.bs.modal", function() {
            $(this).removeData("bs.modal");
        });
    }
    //修改职位
    function updateJob() {
        var data = $("#editjob").serialize();
        $.ajax({
            dataType: "JSON",
            url: "${ctx}/updateJobById",
            type: "POST",
            data: data,
            success: function (data) {
                bootbox.alert("修改成功！");
                $("#jobList").bootgrid("reload");
            },
            error: function () {
                alert("修改失败！");
            }
        });
    }
</script>
</body>
</html>
