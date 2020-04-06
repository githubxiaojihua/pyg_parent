<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>angularjs-循环对象数组</title>
    <script type="text/javascript" src="plugins/angularjs/angular.min.js"></script>
    <script type="text/javascript">
        // 定义一个模块
        var myModule = angular.module("myModule",[]);
        myModule.controller("arrayController",function($scope) {
            // 定义对象数组
            $scope.list = [
                {name:'张三',shuxue:100,yuwen:90},
                {name:'李四',shuxue:88,yuwen:73},
                {name:'王五',shuxue:89,yuwen:99}
            ];
        });
    </script>
</head>

<body ng-app="myModule" ng-controller="arrayController">
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
