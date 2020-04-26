app.controller("contentController",function($scope,contentService){

    $scope.contentList = [];

    // 根据广告Id查询广告列表
    $scope.findByCategoryId = function(categoryId){
        contentService.findByCategoryId(categoryId).success(
            function (response) {
                $scope.contentList[categoryId] = response;
            }
        );
    };
});