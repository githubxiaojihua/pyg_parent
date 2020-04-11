// 服务层
app.service("sellerService",function($http){
    this.add = function(entity){
      // 说明此处的相对地址是./而brand对应的brandService.js中是../
      // 主要原因还是使用此js的页面路径，使用本js的页面路径是：http://localhost:8081/shopWeb/register.html
      // 所以如果使用../那么就跳到了http://localhot:8081/下面，没有了项目名，因此在调用后端controller的时候
      // 会出现路径错误。而brandService.js的页面是http://localhost:8082/managerWeb/admin/index.html
      // 因此../代表的是http://localhost:8082/managerWeb，所以是没有问题的
      return $http.post("./seller/add",entity);
    };
});