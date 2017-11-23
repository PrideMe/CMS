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
    <link href="${ctx}/images/favicon.ico" rel="shortcut icon">
    <script type="text/javascript" src="${ctx}/js/jquery-3.2.1.min.js"></script>
    <script type="text/javascript" src="${ctx}/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="${ctx}/js/metisMenu.min.js"></script>
    <script type="text/javascript" src="${ctx}/js/nprogress.js"></script>
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
        <%--<div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
            <ul class="nav navbar-nav">
                <li class="active"><a href="#">Link <span class="sr-only">(current)</span></a></li>
                <li><a href="#">Link</a></li>
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">Dropdown <span class="caret"></span></a>
                    <ul class="dropdown-menu">
                        <li><a href="#">Action</a></li>
                        <li><a href="#">Another action</a></li>
                        <li><a href="#">Something else here</a></li>
                        <li role="separator" class="divider"></li>
                        <li><a href="#">Separated link</a></li>
                        <li role="separator" class="divider"></li>
                        <li><a href="#">One more separated link</a></li>
                    </ul>
                </li>
            </ul>
            <form class="navbar-form navbar-left">
                <div class="form-group">
                    <input type="text" class="form-control" placeholder="Search">
                </div>
                <button type="submit" class="btn btn-default">Submit</button>
            </form>
            <ul class="nav navbar-nav navbar-right">
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">Dropdown <span class="caret"></span></a>
                    <ul class="dropdown-menu">
                        <li><a href="#">Action</a></li>
                        <li><a href="#">Another action</a></li>
                        <li><a href="#">Something else here</a></li>
                        <li role="separator" class="divider"></li>
                        <li><a href="#">Separated link</a></li>
                    </ul>
                </li>
            </ul>
        </div>--%>
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
            <button class="btn btn-primary btn-lg" data-toggle="modal" data-target="#myModal">开始演示模态框</button>
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
            <h3>Auto Collapse<small>default</small></h3>
            <div class="panel panel-default">
                <div class="panel-heading">
                    Code
                    <span class="pull-right">
                            <span class="fa fa-code"></span>
                        </span>
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
            <div class="panel panel-default">
                <div class="panel-heading">
                    Code
                    <span class="pull-right">
                            <span class="fa fa-code"></span>
                        </span>
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
            <div class="panel panel-default">
                <div class="panel-heading">
                    Code
                    <span class="pull-right">
                            <span class="fa fa-code"></span>
                        </span>
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
            <div class="panel panel-default">
                <div class="panel-heading">
                    Code
                    <span class="pull-right">
                            <span class="fa fa-code"></span>
                        </span>
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
            <div class="panel panel-default">
                <div class="panel-heading">
                    Code
                    <span class="pull-right">
                            <span class="fa fa-code"></span>
                        </span>
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
            <div class="panel panel-default">
                <div class="panel-heading">
                    Code
                    <span class="pull-right">
                            <span class="fa fa-code"></span>
                        </span>
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
</script>

</body>
</html>