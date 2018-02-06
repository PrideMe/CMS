<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <title>search</title>
    <style type="text/css">
        dl, dt, dd, ol, ul, li{ list-style:none;}
        #main{width:730px;margin:75px auto 0;}
        .nav{margin-bottom:10px;}
        .searchList{float:left;padding-left:5px;}
        .searchList .searchItem{float:left;margin-right:15px;font-size:14px;padding:0 0 2px 2px;cursor:pointer;height:18px;}
        .searchList .searchItem.current{color:#0080cc;border-bottom:2px solid #9cc813;font-weight:bold;}

        .inputArea{position:relative;margin-bottom:65px;}
        .inputArea .searchInput{border:1px solid #bfbfbf;padding:0 15px;outline:none;height:40px;*height:39px;*line-height:40px;width:520px; background:url(${ctx}/images/searchinputbg.png);font-size:14px;}
        .inputArea .searchButton{position:absolute;left:550px;*left:552px;*top:1px;width:106px;height:42px;*height:41px;background:url(${ctx}/images/seachbtn.png) no-repeat;border:none;cursor:pointer;}

        .inputArea .dataList{display:none;position:absolute;left:0;top:42px;*top:43px;width:550px;padding:5px 0;background:#fff;border:1px solid #bfbfbf;border-top:none;}
        .inputArea .dataList li{padding:2px 15px;font-size:14px;}
        .inputArea .dataList li:hover{background:#f0f0f0;color:#0080cc;font-weight:bold;}
    </style>
</head>
<body>
<div id="container">
    <div id="bd">
        <div id="main" style="height: 320px">
            <div class="nav ue-clear">
                <ul class="searchList">
                    <li class="searchItem current">新闻</li>
                    <li class="searchItem">小说</li>
                </ul>
            </div>
            <div class="inputArea">
                <input type="text" class="searchInput" />
                <input type="button" class="searchButton" onclick="javascript:window.location='result.html'" />
                <ul class="dataList">
                    <li>斗破苍穹</li>
                    <li>斗罗大陆</li>
                    <li>明朝那些事</li>
                    <li>极品家丁</li>
                    <li>武动乾坤</li>
                    <li>凡人修仙传</li>
                    <li>绝世唐门</li>
                    <li>盗墓笔记</li>
                    <li>混沌剑神</li>
                </ul>
            </div>
        </div>
    </div>
    <script type="text/javascript">
        $('.searchList').on('click', '.searchItem', function(){
            $('.searchList .searchItem').removeClass('current');
            $(this).addClass('current');
        });

        // 联想下拉显示隐藏
        $('.searchInput').on('focus', function(){
            $('.dataList').show()
        });

        // 联想下拉点击
        $('.dataList').on('click', 'li', function(){
            var text = $(this).text();
            $('.searchInput').val(text);
            $('.dataList').hide()
        });
    </script>
</div>
</body>
</html>
