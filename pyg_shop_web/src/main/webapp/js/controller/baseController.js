//定义父控制器
app.controller("baseController",function($scope){

    //重新加载列表 数据
    $scope.reloadList = function() {
        //切换页码
        $scope.search($scope.paginationConf.currentPage,
            $scope.paginationConf.itemsPerPage);
    }

    //分页控件配置
    $scope.paginationConf = {
        currentPage : 1,
        totalItems : 10,
        itemsPerPage : 10,
        perPageOptions : [ 10, 20, 30, 40, 50 ],
        onChange : function() {
            $scope.reloadList();//重新加载
        }
    };

    // 定义用于存放选中ID的集合
    $scope.selectIds = [];
    // 定义更新ids的方法
    $scope.updateSelection = function ($event,id) {
        // 选中则是增加
        if($event.target.checked){
            $scope.selectIds.push(id);
        }else{
            // 取消选择是删除
            var idx = $scope.selectIds.indexOf(id);
            $scope.selectIds.splice(idx,1);// 删除
        }
    };

    // 提取json数组字符串中的某个属性，并用逗号拼接  [{"text":"内存大小"},{"text":"颜色"}]
    $scope.jsonToString = function(jsonString,key){
        var jsonObj = JSON.parse(jsonString);
        var value = "";
        for(var i=0; i<jsonObj.length;i++){
            if(i>0){
                value += ",";
            }
            value += jsonObj[i][key];
        }
        return value;
    }
});