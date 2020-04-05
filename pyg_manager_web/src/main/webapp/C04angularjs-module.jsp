
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>angularjs-模块化编程</title>
    <script type="text/javascript" src="plugins/angularjs/angular.min.js"></script>
    <script type="text/javascript">
        // 定义一个叫onemodule的模块
        var myModule = angular.module("oneModule",[]);
        // 在模块中定义controller
        myModule.controller("helloController",function($scope){
            $scope.greeting = function(){
                return "hello,angular";
            }
        });

    </script>
</head>
<!--
    指定模块
-->
<body ng-app="oneModule">

    <div ng-controller="helloController"><!-- mvc中的c，控制层-->
        <!-- 调用controller中的方法-->
        <p>{{greeting()}}</p><!-- mvc中的v，试图层-->
    </div>
</body>
</html>
