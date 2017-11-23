<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<html lang="zh-CN">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=GBK">
    <title>404错误</title>
    <meta content="width=device-width, initial-scale=1.0" name="viewport" />
    <link href=”http://vjs.zencdn.net/c/video-js.css” rel=”stylesheet”>
    <script src=”http://vjs.zencdn.net/c/video.js”></script>
    <link href="http://vjs.zencdn.net/5.0.2/video-js.css" rel="stylesheet">
    <script src="http://vjs.zencdn.net/ie8/1.1.0/videojs-ie8.min.js"></script>
    <script src="http://vjs.zencdn.net/5.0.2/video.js"></script>
</head>
<body>
<h1>资源没找到，看个视频吧</h1>

<video id=”my_video_1″ class=”video-js vjs-default-skin” controls preload=”auto” width=”640″ height=”264″ poster=”my_video_poster.png” data-setup=”{}”>
    <source src="${ctx}/av/SHKD-736.mp4" type=’video/mp4′>
</video>



</body>
</html>
