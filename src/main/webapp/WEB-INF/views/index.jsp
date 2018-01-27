<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no">
    <title>CMS管理系统</title>
    <link rel="stylesheet" href="${ctx}/css/bootstrap.min.css">
    <link rel="stylesheet" href="${ctx}/css/font-awesome.min.css">
    <link rel="stylesheet" href="${ctx}/css/metisMenu.css">
    <link rel="stylesheet" href="${ctx}/css/jquery.bootgrid.min.css">
    <link rel="stylesheet" href="${ctx}/css/nprogress.css">
    <link rel="stylesheet" href="${ctx}/ztree/zTreeStyle.css">
    <link rel="stylesheet" href="${ctx}/css/bootstrap-datetimepicker.css">
    <link href="${ctx}/images/favicon.ico" rel="shortcut icon">
    <script type="text/javascript" src="${ctx}/js/jquery-3.2.1.min.js"></script>
    <script type="text/javascript" src="${ctx}/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="${ctx}/js/metisMenu.min.js"></script>
    <script type="text/javascript" src="${ctx}/js/nprogress.js"></script>
    <script type="text/javascript" src="${ctx}/js/bootstrap-datetimepicker.js"></script>
    <script type="text/javascript" src="${ctx}/js/bootstrap-datetimepicker.zh-CN.js" charset="UTF-8"></script>
    <script type="text/javascript" src="${ctx}/js/echarts.js"></script>
    <script type="text/javascript" src="${ctx}/layer/layer.js"></script>
    <script type="text/javascript" src="${ctx}/ztree/jquery.ztree.core.min.js"></script>
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
        .navbar .nav > li .dropdown-menu {
            margin: 0;
        }
        .navbar .nav > li:hover .dropdown-menu {
            display: block;
        }
        .carousel-control.left {
            background-image: none;
        }
        .carousel-control.right {
            background-image: none;
        }
    </style>
    <script>
        $(function () {
            $('#menu').metisMenu();
        });
        if (window.top !== window.self) {
            window.top.location = window.location;
        }
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
            <button class="btn btn-primary pull-right dropdown-toggle" style="margin-top: 7px;margin-left: 7px;margin-right: 1px;" data-toggle="dropdown"><i class="fa fa-user fa-fw"></i>${currentUser}<i class="caret"></i></button>
            <ul class="dropdown-menu dropdown-menu-right">
                <li><a href="#"><i class="fa fa-info fa-fw"></i>个人资料</a></li>
                <li><a href="${ctx}/logout"><i class="fa fa-sign-out fa-fw"></i>退出</a></li>
            </ul>
        </div>
    </div>
</nav>

<div class="clearfix">
    <aside class="sidebar">
        <nav class="sidebar-nav navbar">
            <ul class="metismenu navbar-collapse collapse" id="menu">
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
                        <shiro:hasRole name="boss">
                            <li>
                                <a href="#" onclick="showAtRight('${ctx}/addUser')">
                                    <span class="sidebar-nav-item-icon fa fa-star fa-fw"></span>添加用户
                                </a>
                            </li>
                        </shiro:hasRole>
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
                        <shiro:hasRole name="boss">
                            <li>
                                <a href="#" onclick="showAtRight('${ctx}/addDepartment')">
                                    <span class="sidebar-nav-item-icon fa fa-circle-o fa-fw"></span>添加部门
                                </a>
                            </li>
                        </shiro:hasRole>
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
                            <a href="#" aria-expanded="false"><span class="sidebar-nav-item-icon fa fa-circle-o fa-fw"></span>
                                小功能<span class="fa plus-times"></span></a>
                            <ul aria-expanded="false" class="collapse">
                                <li><a href="#" onclick="showAtRight('${ctx}/sendMail')"><span class="sidebar-nav-item-icon fa fa-tag fa-fw"></span>发邮件</a></li>
                                <li><a href="#" onclick="showAtRight('${ctx}/search')"><span class="sidebar-nav-item-icon fa fa-search fa-fw"></span>搜索</a></li>
                                <li><a href="#"><span class="sidebar-nav-item-icon fa fa-tag fa-fw"></span>公众号配置</a></li>
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
            <div id="myCarousel" class="carousel slide" style="width: 960px;height: 400px">
                <!-- 轮播（Carousel）指标 -->
                <ol class="carousel-indicators">
                    <li data-target="#myCarousel" data-slide-to="0" class="active"></li>
                    <li data-target="#myCarousel" data-slide-to="1"></li>
                    <li data-target="#myCarousel" data-slide-to="2"></li>
                </ol>
                <!-- 轮播（Carousel）项目 -->
                <div class="carousel-inner">
                    <div class="item active">
                        <img src="${ctx}/images/bg1.jpg" class="img-responsive" alt="First slide">
                    </div>
                    <div class="item">
                        <img src="${ctx}/images/bg2.png" class="img-responsive" alt="Second slide">
                    </div>
                    <div class="item">
                        <img src="${ctx}/images/bg3.jpg" class="img-responsive" alt="Third slide">
                    </div>
                </div>
                <!-- 轮播（Carousel）导航 -->
                <a class="left carousel-control" href="#carousel-example-generic" role="button" data-slide="prev">
                    <span class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>
                    <span class="sr-only">Previous</span>
                </a>
                <a class="right carousel-control" href="#carousel-example-generic" role="button" data-slide="next">
                    <span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>
                    <span class="sr-only">Next</span>
                </a>
            </div>
            <h2>${currentUser}创建模态框（Modal）</h2>
            <shiro:hasPermission name="delegate">
                <h3>具有代表公司权限</h3>
            </shiro:hasPermission>
            <shiro:hasPermission name="manager">
                <h3>具有人事安排权限</h3>
            </shiro:hasPermission>
            <shiro:hasPermission name="interview">
                <h3>具有面试新员工权限</h3>
            </shiro:hasPermission>
            <shiro:hasPermission name="work">
                <h3>具有只有工作权限</h3>
            </shiro:hasPermission>
            <!-- 按钮触发模态框 -->
            <button class="btn btn-primary btn-lg" data-toggle="modal" data-target="#myModal" style="margin-bottom: 30px">开始演示模态框</button>
            <!-- 模态框（Modal） -->
            <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal">&times;</button>
                            <h4 class="modal-title" id="myModalLabel">模态框（Modal）标题</h4>
                        </div>
                        <div class="modal-body"><p>IP地址${ip}</p></div>
                        <div class="progress progress-striped active" style="margin: 0 10px 10px 10px;">
                            <div id="btprogress" class="progress-bar progress-bar-success" style="width: 40%;">
                                <span class="sr-only">40% 完成</span>
                            </div>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                            <button type="button" id="progress_add" class="btn btn-primary">提交更改</button>
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

            <div class="site-demo-button" id="layerDemo" style="margin-bottom: 0;">
                <blockquote class="layui-elem-quote layui-quote-nm">
                    Tips：为了更清晰演示，每触发下述一个例子之前，都会关闭所有已经演示的层
                </blockquote>
                <button data-method="setTop" class="layui-btn">多窗口模式，层叠置顶</button>
                <button data-method="confirmTrans" class="layui-btn">配置一个透明的询问框</button>
                <button data-method="notice" class="layui-btn">示范一个公告层</button>

                <button data-method="offset" data-type="auto" class="layui-btn layui-btn-normal">居中弹出</button>
            </div>
            <br/>

            <div>
                <ul id="treeDemo" class="ztree"></ul>
            </div>
            <br/>

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
            <br/><br/>

            <div id="datetime" class="input-group date form_datetime col-md-5" data-date="" data-date-format="dd MM yyyy" data-link-field="dtp_input2" data-link-format="yyyy-mm-dd">
                <input class="form-control" size="16" type="text" value="" readonly placeholder="时间选择器">
                <span class="input-group-addon"><span class="fa fa-calendar fa-fw"></span></span>
            </div>


            <div id="echarts" style="width: 600px;height:400px;margin-top: 30px"></div>
            <div id="echarts_zhuzhuang" style="width:600px;height:400px;margin-top: 30px"></div>
            <div id="echarts_zhexian" style="width:600px;height:400px;margin-top: 30px"></div>


            <h3>Auto Collapse<small>default</small></h3>

            <div class="panel panel-default">
                <div class="panel-heading">Code
                    <span class="pull-right"><span class="fa fa-code"></span></span>
                </div>
                <div class="panel-body">
                        <pre class=" language-markup"><code class=" language-markup"><span class="token tag"><span
                                class="token tag"><span class="token punctuation">&lt;</span>script</span> <span
                                class="token attr-name">src</span><span class="token attr-value"><span
                                class="token punctuation">=</span><span
                                class="token punctuation">"</span>metisMenu.js<span
                                class="token punctuation">"</span></span><span
                                class="token punctuation">&gt;</span></span><span
                                class="token script language-javascript"></span><span class="token tag"><span
                                class="token tag"><span class="token punctuation">&lt;/</span>script</span><span
                                class="token punctuation">&gt;</span></span>
<span class="token tag"><span class="token tag"><span class="token punctuation">&lt;</span>script</span><span
        class="token punctuation">&gt;</span></span><span class="token script language-javascript">
                        <span class="token function">$</span><span class="token punctuation">(</span><span
                                    class="token keyword">function</span> <span class="token punctuation">(</span><span
                                    class="token punctuation">)</span> <span class="token punctuation">{</span>
                        <span class="token function">$</span><span class="token punctuation">(</span><span
                                    class="token string">'#menu'</span><span class="token punctuation">)</span><span
                                    class="token punctuation">.</span><span class="token function">metisMenu</span><span
                                    class="token punctuation">(</span><span class="token punctuation">)</span><span
                                    class="token punctuation">;</span>
                        <span class="token punctuation">}</span><span class="token punctuation">)</span><span
                                    class="token punctuation">;</span>
</span><span class="token tag"><span class="token tag"><span class="token punctuation">&lt;/</span>script</span><span
                                    class="token punctuation">&gt;</span></span></code></pre>

                </div>
            </div>

        </div>
    </section>
</div>

<script type="text/javascript">
    var zTreeObj;
    // zTree 的参数配置，深入使用请参考 API 文档（setting 配置详解）
    var setting = {
        check : {
            chkStyle:"checkbox",
            enable : true //是否复选框
        }
    };
    // zTree 的数据属性，深入使用请参考 API 文档（zTreeNode 节点数据详解）
    var zNodes = [
        {
            name: "山东省",
            open: true,
            children: [
                {
                    name: "济南市"
                },
                {
                    name: "日照市"
                }
            ]
        },
        {
            name: "浙江省",
            open: true,
            children: [
                {
                    name: "杭州市"
                },
                {
                    name: "温州市"
                }
            ]
        }
    ];
    $(document).ready(function(){
        zTreeObj = $.fn.zTree.init($("#treeDemo"), setting, zNodes);
    });

        //触发事件
        var active = {
            setTop: function(){
                var that = this;
                //多窗口模式，层叠置顶
                layer.open({
                    type: 2 //此处以iframe举例
                    ,title: '当你选择该窗体时，即会在最顶端'
                    ,area: ['390px', '260px']
                    ,shade: 0
                    ,maxmin: true
                    ,offset: [ //为了演示，随机坐标
                        Math.random()*($(window).height()-300)
                        ,Math.random()*($(window).width()-390)
                    ]
                    ,content: 'http://layer.layui.com/test/settop.html'
                    ,btn: ['继续弹出', '全部关闭'] //只是为了演示
                    ,yes: function(){
                        $(that).click();
                    }
                    ,btn2: function(){
                        layer.closeAll();
                    }

                    ,zIndex: layer.zIndex //重点1
                    ,success: function(layero){
                        layer.setTop(layero); //重点2
                    }
                });
            }
            ,confirmTrans: function(){
                //配置一个透明的询问框
                layer.msg('大部分参数都是可以公用的<br>合理搭配，展示不一样的风格', {
                    time: 20000, //20s后自动关闭
                    btn: ['明白了', '知道了', '哦']
                });
            }
            ,notice: function(){
                //示范一个公告层
                layer.open({
                    type: 1
                    ,title: false //不显示标题栏
                    ,closeBtn: false
                    ,area: '300px;'
                    ,shade: 0.8
                    ,id: 'LAY_layuipro' //设定一个id，防止重复弹出
                    ,btn: ['火速围观', '残忍拒绝']
                    ,btnAlign: 'c'
                    ,moveType: 1 //拖拽模式，0或者1
                    ,content: '<div style="padding: 50px; line-height: 22px; background-color: #393D49; color: #fff; font-weight: 300;">你知道吗？亲！<br>layer ≠ layui<br><br>layer只是作为Layui的一个弹层模块，由于其用户基数较大，所以常常会有人以为layui是layerui<br><br>layer虽然已被 Layui 收编为内置的弹层模块，但仍然会作为一个独立组件全力维护、升级。<br><br>我们此后的征途是星辰大海 ^_^</div>'
                    ,success: function(layero){
                        var btn = layero.find('.layui-layer-btn');
                        btn.find('.layui-layer-btn0').attr({
                            href: '${ctx}/index'
                            ,target: '_blank'
                        });
                    }
                });
            }
            ,offset: function(othis){
                var type = othis.data('type')
                    ,text = othis.text();

                layer.open({
                    type: 1
                    ,offset: type //具体配置参考：http://www.layui.com/doc/modules/layer.html#offset
                    ,id: 'layerDemo'+type //防止重复弹出
                    ,content: '<div style="padding: 20px 100px;">'+ text +'</div>'
                    ,btn: '关闭全部'
                    ,btnAlign: 'c' //按钮居中
                    ,shade: 0 //不显示遮罩
                    ,yes: function(){
                        layer.closeAll();
                    }
                });
            }
        };

        $('#layerDemo .layui-btn').on('click', function(){
            var othis = $(this), method = othis.data('method');
            active[method] ? active[method].call(this, othis) : '';
        });


    //自动轮播
    $(function () {
        $("#myCarousel").carousel('cycle');
        $("#myCarousel a.left").click(function(){
            $(".carousel").carousel("prev");
        });
        $("#myCarousel a.right").click(function(){
            $(".carousel").carousel("next");
        });
    });
    //在右侧显示
    function showAtRight(url) {
        NProgress.start();
        $("#rightContent").load(url,function () {
            NProgress.done();
        });
    }
    $("#datetime").datetimepicker({
        format: "yyyy年mm月dd日 - hh:ii:ss",
        //autoclose: true,
        todayHighlight: true,
        language:"zh-CN",
        todayBtn: true
        //pickerPosition: "bottom-center"
    });

    // 基于准备好的dom，初始化echarts实例
    var myChart = echarts.init($("#echarts")[0]);
    var zhuzhuangtu = echarts.init($("#echarts_zhuzhuang")[0]);
    var zhexiantu = echarts.init($("#echarts_zhexian")[0]);

    // 指定图表的配置项和数据
    var option = {
        title: {
            text: 'ECharts 示例'
        },
        tooltip: {},
        legend: {
            data:['销量']
        },
        xAxis: {
            data: ["衬衫","羊毛衫","雪纺衫","裤子","高跟鞋","袜子"]
        },
        yAxis: {},
        series: [{
            name: '销量',
            type: 'bar',
            data: [5, 20, 36, 10, 10, 20]
        }]
    };

    // 使用刚指定的配置项和数据显示图表。
//    myChart.setOption({
//        backgroundColor: '#2c343c',
//        roseType: 'angle',
//        series : [
//            {
//                name:"访问来源",
//                type:"pie",
//                radius:"65%",
//                data:[
//                    {value:235,name:"视频广告"},
//                    {value:274,name:'联盟广告'},
//                    {value:310,name:'邮件营销'},
//                    {value:335,name:'直接访问'},
//                    {value:400,name:'搜索引擎'}
//                ]
//            }
//        ]
//    });
    myChart.setOption({
        xAxis: {
            type: 'value'
        },
        yAxis: {
            type: 'value'
        },
        dataZoom: [
            {   // 这个dataZoom组件，默认控制x轴。
                type: 'slider', // 这个 dataZoom 组件是 slider 型 dataZoom 组件
                start: 10,      // 左边在 10% 的位置。
                end: 60         // 右边在 60% 的位置。
            },
            {   // 这个dataZoom组件，也控制x轴。
                type: 'inside', // 这个 dataZoom 组件是 inside 型 dataZoom 组件
                start: 10,      // 左边在 10% 的位置。
                end: 60         // 右边在 60% 的位置。
            }
        ],
        series: [
            {
                type: 'scatter', // 这是个『散点图』
                itemStyle: {
                    normal: {
                        opacity: 0.8
                    }
                },
                symbolSize: function (val) {
                    return val[2] * 40;
                },
                data: [
                    ["14.616", "7.241", "0.896"],
                    ["3.958", "5.701", "0.955"],
                    ["2.768", "8.971", "0.669"],
                    ["9.051", "9.710", "0.171"],
                    ["14.046", "4.182", "0.536"],
                    ["12.295", "1.429", "0.962"],
                    ["4.417", "8.167", "0.113"],
                    ["0.492", "4.771", "0.785"],
                    ["7.632", "2.605", "0.645"],
                    ["14.242", "5.042", "0.368"]
                ]
            }
        ]
    })
    zhuzhuangtu.setOption({
        color: ['#3398DB'],
        tooltip : {
            trigger: 'axis',
            axisPointer : {            // 坐标轴指示器，坐标轴触发有效
                type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
            }
        },
        grid: {
            left: '3%',
            right: '4%',
            bottom: '3%',
            containLabel: true
        },
        xAxis : [
            {
                type : 'category',
                data : ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun'],
                axisTick: {
                    alignWithLabel: true
                }
            }
        ],
        yAxis : [
            {
                type : 'value'
            }
        ],
        series : [
            {
                name:'直接访问',
                type:'bar',
                barWidth: '60%',
                data:[10, 52, 200, 334, 390, 330, 220]
            }
        ]
    })
    zhexiantu.setOption({
        title: {
            text: '未来一周气温变化',
            subtext: '纯属虚构'
        },
        tooltip: {
            trigger: 'axis'
        },
        legend: {
            data:['最高气温','最低气温']
        },
        toolbox: {
            show: true,
            feature: {
                dataZoom: {
                    yAxisIndex: 'none'
                },
                dataView: {readOnly: false},
                magicType: {type: ['line', 'bar']},
                restore: {},
                saveAsImage: {
                    type:"png",
                    name:"123"
                }
            }
        },
        xAxis:  {
            type: 'category',
            boundaryGap: false,
            data: ['周一','周二','周三','周四','周五','周六','周日']
        },
        yAxis: {
            type: 'value',
            axisLabel: {
                formatter: '{value} °C'
            }
        },
        series: [
            {
                name:'最高气温',
                type:'line',
                data:[11, 11, 15, 13, 12, 13, 10],
                markPoint: {
                    data: [
                        {type: 'max', name: '最大值'},
                        {type: 'min', name: '最小值'}
                    ]
                },
                markLine: {
                    data: [
                        {type: 'average', name: '平均值'}
                    ]
                }
            },
            {
                name:'最低气温',
                type:'line',
                data:[1, -2, 2, 5, 3, 2, 0],
                markPoint: {
                    data: [
                        {name: '周最低', value: -2, xAxis: 1, yAxis: -1.5}
                    ]
                },
                markLine: {
                    data: [
                        {type: 'average', name: '平均值'},
                        [{
                            symbol: 'none',
                            x: '90%',
                            yAxis: 'max'
                        }, {
                            symbol: 'circle',
                            label: {
                                normal: {
                                    position: 'start',
                                    formatter: '最大值'
                                }
                            },
                            type: 'max',
                            name: '最高点'
                        }]
                    ]
                }
            }
        ]
    })
</script>

</body>
</html>