app.service("uploadService",function($http){
    this.uploadFile = function(){
      // 创建一个angularjs的内置对象FormData
      var formData = new FormData();
      //将页面中的上传控件内容追加到formData中
      //其中file作为参数应该与后台controller层中接收参数MultipartFile类的参数名一直
      //file1应该与页面上上传控件的id名称一直
      formData.append("file",file1.files[0]);
      // 以下是$http组件发送请求的另一种方式
      return $http({
          method:"post",
          url:"../upload",
          data:formData,
          headers:{"Content-Type":undefined},
          transformRequest:angular.identity
      });
    };
});