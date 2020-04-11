// 控制层
app.controller("sellerController",function($scope,$controller,sellerService){
    $scope.add = function(){
        sellerService.add($scope.entity).success(function(response){
            if(response.success){
                location.href = "shoplogin.html";
            }else{
                alert(response.message);
            }
        });
    }
});