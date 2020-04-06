<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>angularjs-循环数组</title>
    <script type="text/javascript" src="plugins/angularjs/angular.min.js"></script>
    <script type="text/javascript">
        // 定义一个模块
        var myModule = angular.module("myModule",[]);
        myModule.controller("arrayController",function($scope) {
            $scope.list = [100,192,203,434];
        });
    </script>
</head>

<body ng-app="myModule" ng-controller="arrayController">
   <table>
       <!-- 循环遍历-->
       <tr ng-repeat ="x in list">
           <td>{{x}}</td>
       </tr>
   </table>
</body>
</html>
