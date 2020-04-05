
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>angularjs-控制器</title>
    <script type="text/javascript" src="plugins/angularjs/angular.min.js"></script>
    <script type="text/javascript">
        // 定义函数
        // $scope可以理解为后端的session或者request这种域对象
        // $scope中的值发生改变的时候会自动渲染视图，同样基于$scope中的值的视图在改变
        // 值的时候会理解更新$scope
        function helloController($scope){
            $scope.greeting={
                text:"hello"// MVC中的M，模型层
            };
        }
    </script>
</head>
<!-- np-app指令用于告诉angularjs应用当前这个元素是根元素，所有angularjs应用都必须一个根元素
     html文档中只允许有一个ng-app，如果有多个那么只有第一个有作用。
-->
<body ng-app>
    <!-- ng-controller用于指定所使用的控制器，控制器实际上就是函数，关于angularjs的mvc模式
         可以参考教程第二天开头部分
    -->
    <div ng-controller="helloController"><!-- mvc中的c，控制层-->
        <p>{{greeting.text}},angularjs!</p><!-- mvc中的v，试图层-->
    </div>
</body>
</html>
