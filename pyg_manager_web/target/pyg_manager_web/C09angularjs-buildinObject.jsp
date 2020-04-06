<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>angularjs-内置对象</title>
    <script type="text/javascript" src="plugins/angularjs/angular.min.js"></script>
    <script type="text/javascript">
        // 定义一个模块
        var myModule = angular.module("myModule",[]);
        myModule.controller("buildinObjectController",function($scope,$http) {
            // 使用内置对象发送请求
            $scope.findAll = function(){
                $http.get("data.json").success(function(response){
                    $scope.list = response;
                });
            }
        });
    </script>
</head>
<!-- 使用ng-init来初始化方法调用 -->
<body ng-app="myModule" ng-controller="buildinObjectController" ng-init="findAll()">
   <table>
       <!-- 循环遍历-->
       <tr>
           <td>姓名</td>
           <td>数学</td>
           <td>语文</td>
       </tr>
       <!-- 循环遍历数组中的对象-->
       <tr ng-repeat="entity in list">
           <td>{{entity.name}}</td>
           <td>{{entity.shuxue}}</td>
           <td>{{entity.yuwen}}</td>
       </tr>
   </table>
</body>
</html>
