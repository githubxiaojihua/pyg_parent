
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>angularjs-表达式</title>
    <script type="text/javascript" src="plugins/angularjs/angular.min.js"></script>
</head>
<!-- np-app指令用于告诉angularjs应用当前这个元素是根元素，所有angularjs应用都必须一个根元素
     html文档中只允许有一个ng-app，如果有多个那么只有第一个有作用。
-->
<body ng-app>
    获取表达式{{100+100}}
</body>
</html>
