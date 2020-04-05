
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>angularjs-初始化指令</title>
    <script type="text/javascript" src="plugins/angularjs/angular.min.js"></script>
</head>
<!--
    初始化指令
-->
<body ng-app ng-init="myname='张三'">
    请输入您的名字：<input type="text" ng-model="myname" />
    <br/>
    {{myname}}，您好！
</body>
</html>
