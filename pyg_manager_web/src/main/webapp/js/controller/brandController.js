//定义控制器
app.controller(
    "brandController",
    function($scope,brandService,$controller) {
        // 继承basecontroller，把baseController的scope赋值给子域，实际上就是共享$scope
        // 注意也要在页面上引入baseController相关js才行
        $controller("baseController",{$scope:$scope});
        //查询所有函数
        $scope.findAll = function() {
            //使用内置服务发送请求
            // ../代表的是上级目录，由于现在是在admin目录下，因此回到根目录发请求
            brandService.findAll().success(function(data) {
                $scope.list = data;
            })

        };

        //分页
        $scope.findPage = function(page, rows) {
            brandService.findPage(page,rows).success(function(data) {
                $scope.list = data.rows;
                $scope.paginationConf.totalItems = data.total;//更新总记录数
            });
        };

        //添加函数
        $scope.add = function(){

            //定义方法名
            var methodName = "add";
            //判断如果id存在,是修改
            if($scope.entity.id!=null){
                methodName="update";
            }
            //内置服务发送请求
            brandService.add(methodName,$scope.entity).success(function(data){
                //判断
                if(data.success){
                    //刷新
                    $scope.reloadList();
                }else{
                    alert(data.message);
                }
            })

        };

        // 查询根据id查询
        $scope.findOne = function(id){
            brandService.findOne(id).success(function(response){
                $scope.entity = response;
            });
        };

        // 定义查询对象
        $scope.searchEntity = {};

        $scope.search = function(page, rows){
            brandService.search(page,rows,$scope.searchEntity).success(function(response){
                // 更新总记录数
                $scope.paginationConf.totalItems = response.total;
                // 更新列表
                $scope.list = response.rows;
            });
        };



        // 批量删除
        $scope.dele = function(){
            brandService.dele($scope.selectIds).success(function(response){
                if(response.success){
                    $scope.reloadList();
                }else{
                    alert(response.message);
                }
            });
        }

    });