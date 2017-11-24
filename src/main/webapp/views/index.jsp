<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>CMS管理系统</title>
    <link rel="stylesheet" href="${ctx}/css/bootstrap.min.css">
    <link rel="stylesheet" href="${ctx}/css/font-awesome.min.css">
    <link rel="stylesheet" href="${ctx}/css/metisMenu.css">
    <link rel="stylesheet" href="${ctx}/css/jquery.bootgrid.min.css">
    <link rel="stylesheet" href="${ctx}/css/nprogress.css">
    <link rel="stylesheet" href="${ctx}/css/bootstrap-datetimepicker.css">
    <link href="${ctx}/images/favicon.ico" rel="shortcut icon">
    <script type="text/javascript" src="${ctx}/js/jquery-3.2.1.min.js"></script>
    <script type="text/javascript" src="${ctx}/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="${ctx}/js/metisMenu.min.js"></script>
    <script type="text/javascript" src="${ctx}/js/nprogress.js"></script>
    <script type="text/javascript" src="${ctx}/js/bootstrap-datetimepicker.js"></script>
    <script type="text/javascript" src="${ctx}/js/bootstrap-datetimepicker.zh-CN.js" charset="UTF-8"></script>
    <style type="text/css">
        *{
            margin: 0;
            padding: 0;
        }
        .sidebar {
            display: block;
            position: fixed;
            height: 100%;
            float: left;
            top: 50px;
            min-width: 200px;
            background: #333;
        }
        .content {
            margin-left: 210px;
            margin-top: 50px;
            top: 50px;
        }
        .sidebar-nav ul {
            padding: 0;
            margin: 0;
            list-style: none;
        }
        .sidebar-nav ul li, .sidebar-nav ul a {
             display: block;
        }
        .sidebar-nav-item-icon {
            padding-right: 5px;
        }
        .sidebar-nav ul a {
             padding: 10px 20px;
             color: #aaa;
             border-top: 1px solid rgba(0, 0, 0, 0.3);
             box-shadow: 0px 1px 0px rgba(255, 255, 255, 0.05) inset;
             text-shadow: 0px 1px 0px rgba(0, 0, 0, 0.5);
        }
        .sidebar-nav ul ul a {
            padding: 10px 30px;
            background-color: rgba(255, 255, 255, 0.2);
        }
        .sidebar-nav ul ul ul a {
            padding: 10px 50px;
            background-color: rgba(255, 255, 255, 0.1);
        }
        a {
            color: #337ab7;
            text-decoration: none;
        }
        .sidebar-nav ul a:hover, .sidebar-nav ul a:focus, .sidebar-nav ul a:active {
            color: #ffffff;
            text-decoration: none;
        }
        .content {
            display: block;
            overflow: hidden;
            width: auto;
        }
        .table-hover>tbody>tr:hover {
            background-color: #dff0d8;
            //color: #5ad66c
        }
        .table>tbody>tr.active>td,
        .table>tbody>tr.active>th,
        .table>tbody>tr>td.active,
        .table>tbody>tr>th.active,
        .table>tfoot>tr.active>td,
        .table>tfoot>tr.active>th,
        .table>tfoot>tr>td.active,
        .table>tfoot>tr>th.active,
        .table>thead>tr.active>td,
        .table>thead>tr.active>th,
        .table>thead>tr>td.active,
        .table>thead>tr>th.active {
            background-color: #dff0d8;
            /*color: #2bda20*/
        }
        .table>tbody>tr.active>td{
            background-color: #dff0d8;
        }
        .table-hover>tbody>tr.active:hover>td,
        .table-hover>tbody>tr.active:hover>th,
        .table-hover>tbody>tr:hover>.active,
        .table-hover>tbody>tr>td.active:hover,
        .table-hover>tbody>tr>th.active:hover{
            background-color: #dff0d8;
        }
    </style>
    <script>
        $(function () {
            $('#menu').metisMenu();
        });
    </script>


    <!-- Latest compiled and minified CSS -->
    <link rel="stylesheet" href="//cdnjs.cloudflare.com/ajax/libs/bootstrap-table/1.11.1/bootstrap-table.min.css">

    <!-- Latest compiled and minified JavaScript -->
    <script src="//cdnjs.cloudflare.com/ajax/libs/bootstrap-table/1.11.1/bootstrap-table.min.js"></script>

    <!-- Latest compiled and minified Locales -->
    <script src="//cdnjs.cloudflare.com/ajax/libs/bootstrap-table/1.11.1/locale/bootstrap-table-zh-CN.min.js"></script>


</head>

<body>
<nav class="navbar navbar-inverse navbar-fixed-top">
    <div class="container-fluid">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="${ctx}/index">CMS人事管理系统</a>
        </div>
        <div id="navbar" class="navbar-collapse collapse">
            <ul class="nav navbar-nav navbar-right">
                <li><a href="#" onclick="showAtRight('${ctx}/userPage')"><i class="fa fa-users"></i> 用户列表</a></li>
                <li><a href="#" onclick="showAtRight('${ctx}/userPage')"><i class="fa fa-list-alt"></i> 产品列表</a></li>
                <li><a href="#" onclick="showAtRight('${ctx}/userPage')" ><i class="fa fa-list"></i> 订单列表</a></li>
            </ul>
        </div>
    </div>
</nav>

<div class="clearfix">
    <aside class="sidebar">
        <nav class="sidebar-nav">
            <ul class="metismenu" id="menu">
                <li class="active">
                    <a href="#" aria-expanded="true">
                        <span class="sidebar-nav-item-icon fa fa-id-card fa-lg"></span>
                        <span class="sidebar-nav-item">用户管理</span>
                        <span class="fa arrow"></span>
                    </a>
                    <ul aria-expanded="true" class="collapse in">
                        <li>
                            <a href="#" onclick="showAtRight('${ctx}/userPage')">
                                <span class="sidebar-nav-item-icon fa fa-code-fork fa-fw"></span>用户查询
                            </a>
                        </li>
                        <li>
                            <a href="#" onclick="showAtRight('${ctx}/addUser')">
                                <span class="sidebar-nav-item-icon fa fa-star fa-fw"></span>添加用户
                            </a>
                        </li>
                    </ul>
                </li>
                <li>
                    <a href="#" aria-expanded="false">
                        <span class="sidebar-nav-item-icon fa fa-th fa-lg"></span>
                        部门管理<span class="fa arrow"></span>
                    </a>
                    <ul aria-expanded="false" class="collapse">
                        <li>
                            <a href="#" onclick="showAtRight('${ctx}/departmentPage')">
                                <span class="sidebar-nav-item-icon fa fa-circle-o fa-fw"></span>部门查询
                            </a>
                        </li>
                        <li>
                            <a href="#" onclick="showAtRight('${ctx}/addDepartment')">
                                <span class="sidebar-nav-item-icon fa fa-circle-o fa-fw"></span>添加部门
                            </a>
                        </li>
                    </ul>
                </li>
                <li>
                    <a href="#" aria-expanded="false">
                        <span class="sidebar-nav-item-icon fa fa-keyboard-o fa-lg"></span>
                        职位管理<span class="fa arrow"></span></a>
                    <ul aria-expanded="false" class="collapse">
                        <li>
                            <a href="#" onclick="showAtRight('${ctx}/jobPage')">
                                <span class="sidebar-nav-item-icon fa fa-circle-o fa-fw"></span>职位查询
                            </a>
                        </li>
                        <li>
                            <a href="#" onclick="showAtRight('${ctx}/addJob')">
                                <span class="sidebar-nav-item-icon fa fa-circle-o fa-fw"></span>添加职位
                            </a>
                        </li>
                        <li>
                            <a href="#" aria-expanded="false"><span class="sidebar-nav-item-icon fa fa-circle-o fa-fw"></span>Menu 1.3 <span class="fa plus-times"></span></a>
                            <ul aria-expanded="false" class="collapse">
                                <li><a href="#"><span class="sidebar-nav-item-icon fa fa-tag fa-fw"></span>item 1.3.1</a></li>
                                <li><a href="#"><span class="sidebar-nav-item-icon fa fa-tag fa-fw"></span>item 1.3.2</a></li>
                            </ul>
                        </li>
                    </ul>
                </li>
                <li>
                    <a href="#" aria-expanded="false">
                        <span class="sidebar-nav-item-icon fa fa-user-o fa-lg"></span>员工管理<span class="fa arrow"></span>
                    </a>
                    <ul aria-expanded="false" class="collapse">
                        <li>
                            <a href="#" onclick="showAtRight('${ctx}/employeePage')">
                                <span class="sidebar-nav-item-icon fa fa-circle-o fa-fw"></span>员工查询
                            </a>
                        </li>
                        <li>
                            <a href="#">
                                <span class="sidebar-nav-item-icon fa fa-circle-o fa-fw"></span>添加员工
                            </a>
                        </li>
                    </ul>
                </li>
                <li>
                    <a href="#" aria-expanded="false">
                        <span class="sidebar-nav-item-icon fa fa-map-o fa-lg"></span>
                        公告管理<span class="fa arrow"></span></a>
                    <ul aria-expanded="false" class="collapse">
                        <li><a href="#"><span class="sidebar-nav-item-icon fa fa-circle-o fa-fw"></span>查询公告</a></li>
                        <li><a href="#"><span class="sidebar-nav-item-icon fa fa-circle-o fa-fw"></span>添加公告</a></li>
                        <li><a href="#"><span class="sidebar-nav-item-icon fa fa-circle-o fa-fw"></span>删除公告</a></li>
                        <li><a href="#"><span class="sidebar-nav-item-icon fa fa-circle-o fa-fw"></span>修改公告</a></li>
                    </ul>
                </li>
            </ul>
        </nav>
    </aside>
    <section class="content">
        <div class="col-xs-12" id="rightContent">
            <h2>创建模态框（Modal）</h2>
            <!-- 按钮触发模态框 -->
            <button class="btn btn-primary btn-lg" data-toggle="modal" data-target="#myModal" style="margin-bottom: 30px">开始演示模态框</button>
            <!-- 模态框（Modal） -->
            <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                            <h4 class="modal-title" id="myModalLabel">模态框（Modal）标题</h4>
                        </div>
                        <div class="modal-body">在这里添加一些文本</div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                            <button type="button" class="btn btn-primary">提交更改</button>
                        </div>
                    </div>
                </div>
            </div>


            <div class="panel panel-default">
                <div class="panel-heading">查询条件</div>
                <div class="panel-body">
                    <table data-toggle="table">
                        <thead>
                        <tr>
                            <th>商品ID</th>
                            <th>商品名称</th>
                            <th>商品价格</th>
                        </tr>
                        </thead>
                        <tbody>
                        <tr>
                            <td>1</td>
                            <td>iphone X</td>
                            <td>￥8899</td>
                        </tr>
                        <tr>
                            <td>2</td>
                            <td>海尔洗衣机</td>
                            <td>￥3580</td>
                        </tr>
                        </tbody>
                    </table>
                </div>
            </div>

            <div id="toolbar" class="btn-group">
                <button id="btn_add" type="button" class="btn btn-default">
                    <span class="fa fa-plus fa-fw" aria-hidden="true"></span>新增
                </button>
                <button id="btn_edit" type="button" class="btn btn-default">
                    <span class="fa fa-pencil fa-fw" aria-hidden="true"></span>修改
                </button>
                <button id="btn_delete" type="button" class="btn btn-default">
                    <span class="fa fa-trash-o fa-fw" aria-hidden="true"></span>删除
                </button>
            </div>

            <div id="datetime" class="input-group date form_date col-md-5" data-date="" data-date-format="dd MM yyyy" data-link-field="dtp_input2" data-link-format="yyyy-mm-dd">
                <input class="form-control" size="16" type="text" value="" readonly placeholder="时间选择器">
                <span class="input-group-addon"><span class="fa fa-calendar fa-fw"></span></span>
            </div>


            <h3>Auto Collapse<small>default</small></h3>

            <div class="panel panel-default">
                <div class="panel-heading">Code
                    <span class="pull-right"><span class="fa fa-code"></span></span>
                </div>
                <div class="panel-body">
                        <pre class=" language-markup"><code class=" language-markup"><span class="token tag"><span class="token tag"><span class="token punctuation">&lt;</span>script</span> <span class="token attr-name">src</span><span class="token attr-value"><span class="token punctuation">=</span><span class="token punctuation">"</span>metisMenu.js<span class="token punctuation">"</span></span><span class="token punctuation">&gt;</span></span><span class="token script language-javascript"></span><span class="token tag"><span class="token tag"><span class="token punctuation">&lt;/</span>script</span><span class="token punctuation">&gt;</span></span>
<span class="token tag"><span class="token tag"><span class="token punctuation">&lt;</span>script</span><span class="token punctuation">&gt;</span></span><span class="token script language-javascript">
                        <span class="token function">$</span><span class="token punctuation">(</span><span class="token keyword">function</span> <span class="token punctuation">(</span><span class="token punctuation">)</span> <span class="token punctuation">{</span>
                        <span class="token function">$</span><span class="token punctuation">(</span><span class="token string">'#menu'</span><span class="token punctuation">)</span><span class="token punctuation">.</span><span class="token function">metisMenu</span><span class="token punctuation">(</span><span class="token punctuation">)</span><span class="token punctuation">;</span>
                        <span class="token punctuation">}</span><span class="token punctuation">)</span><span class="token punctuation">;</span>
</span><span class="token tag"><span class="token tag"><span class="token punctuation">&lt;/</span>script</span><span class="token punctuation">&gt;</span></span></code></pre>

                </div>
            </div>

        </div>
    </section>
</div>

<script type="text/javascript">
    //在右侧显示
    function showAtRight(url) {
        NProgress.start();
        $("#rightContent").load(url,function () {
            NProgress.done();
        });
    }
    $("#datetime").datetimepicker({
        format: "yyyy年mm月dd日 - hh:ii:ss",
        autoclose: true,
        todayHighlight: true,
        language:"zh-CN",
        todayBtn: true,
        pickerPosition: "bottom-center"
    });
</script>

</body>
</html>