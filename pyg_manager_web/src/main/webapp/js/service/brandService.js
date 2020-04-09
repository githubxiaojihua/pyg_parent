// 商品品牌服务
// 定义服务层
app.service("brandService",function($http){

    // 定义fandAll服务端
    this.findAll = function(){
        return $http.get("../brand/findAll");
    };

    // 定义findPage服务端
    this.findPage = function(page,rows){
        return $http.get('../brand/findPage?page=' + page + '&rows='+ rows);
    };

    // 定义add服务端
    this.add = function(methodName,entity){
        return $http.post("../brand/"+methodName,entity);
    };

    // 定义findOne服务端
    this.findOne = function(id){
        return $http.get("../brand/findOne?id=" + id);
    };

    // 定义search服务端
    this.search = function(page,rows,searchEntity){
        return $http.post("../brand/search?page=" + page + "&rows=" + rows,searchEntity);
    };

    // 定义dele服务端
    this.dele = function(selectIds){
        return $http.get("../brand/delete?ids=" + selectIds);
    };

    // 定义查询品牌多选下拉框数据服务端
    this.selectOptionList = function(){
        return $http.get("../brand/selectOptionList");
    }
});