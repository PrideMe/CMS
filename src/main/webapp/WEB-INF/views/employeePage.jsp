<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <%--<script type="text/javascript" src="${ctx}/js/bootbox.min.js"/>--%>
    <%--<script type="text/javascript" src="${ctx}/js/jquery.bootgrid.min.js"></script>--%>
    <%--<script type="text/javascript" src="${ctx}/js/jquery.bootgrid.fa.min.js"></script>--%>
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
        <%--<th data-column-id="card_id" data-visible="true">ID卡</th>--%>
        <th data-column-id="address"  data-visible="true" data-width="180px">地址</th>
        <%--<th data-column-id="post_code"  data-visible="true">邮编</th>--%>
        <th data-column-id="phone"  data-visible="true" data-width="110px">手机</th>
        <%--<th data-column-id="qq"  data-visible="true">QQ</th>--%>
        <th data-column-id="email"  data-visible="true">邮箱</th>
        <th data-column-id="sex"  data-visible="true" data-formatter="sexx" data-width="70px" data-align="center" data-header-align="center">性别</th>
        <%--<th data-column-id="party"  data-visible="true">政党</th>--%>
        <th data-column-id="birthday" data-formatter="statuss" data-visible="true">生日</th>
        <%--<th data-column-id="race"  data-visible="true">民族</th>--%>
        <%--<th data-column-id="education"  data-visible="true">学历</th>--%>
        <th data-column-id="speciality"  data-visible="true" data-width="80px">专业</th>
        <th data-column-id="remark"  data-visible="true">备注</th>
        <th data-column-id="" data-formatter="operation" data-visible="true">操作</th>
    </tr>
    </thead>
</table>
<div class="modal fade" id="updateEmployee" tabindex="-1" role="dialog">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">×</button>
                <h4 class="modal-title" id="departmentLabel">修改数据</h4>
            </div>
            <form id="editEmployee" class="form-horizontal">
                <div class="modal-body">
                    <div class="input-group hidden">
                        <div class="input-group-addon">id</div>
                        <input class="form-control" id="employeeId" name="id">
                    </div><br/>
                    <div class="input-group">
                        <div class="input-group-addon">部门</div>
                        <input class="form-control" id="employeename" name="name" placeholder="请输入部门">
                    </div><br/>
                    <div class="input-group">
                        <div class="input-group-addon">备注</div>
                        <input class="form-control" id="remark" name="remark" placeholder="请输入备注">
                    </div><br/>
                </div>
            </form>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button type="button" onclick="" class="btn btn-success">保存</button>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript">
    var formatters = {
        "dep" :function (column,row) {
            if (row.department != null){
                var temp = row.department.name;
                return temp;
            }
        },
        "job" :function (column,row) {
            if (row.job != null) {
                var temp = row.job.name;
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
    //显示单个雇员
    function showEmployeeById(id) {
        $.ajax({
            dataType: "JSON",
            url: "${ctx}/getEmployeeById",
            type: "POST",
            data: {"id": id},
            success: function (data) {
                $("#departmentLabel").text(data.name);
                $("#employeeId").val(data.id);
                $("#employeename").val(data.department.name);
                $("#remark").val(data.remark);
            },
            error: function () {
                alert("请求失败");
            }
        });
        $('#updateEmployee').modal({backdrop:'static'}).on("hidden.bs.modal", function() {
            $(this).removeData("bs.modal");
        });
    }
</script>
</body>
</html>
