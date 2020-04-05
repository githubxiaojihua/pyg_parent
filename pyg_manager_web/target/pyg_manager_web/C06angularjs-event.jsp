
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>angularjs-事件指令</title>
    <script type="text/javascript" src="plugins/angularjs/angular.min.js"></script>
    <script type="text/javascript">
        // 定义一个模块
        var myModule = angular.module("myModule",[]);
        myModule.controller("eventController",function($scope) {
            $scope.add = function(){
                $scope.z = parseInt($scope.x) + parseInt($scope.y);
            };
        });
    </script>
</head>

<body ng-app="myModule" ng-controller="eventController">
    x:<input type="text" ng-model="x"/>
    y:<input type="text" ng-model="y"/>
    <!-- -->
    <button ng-click="add()">计算</button>
    结果：{{z}}
</body>
</html>
