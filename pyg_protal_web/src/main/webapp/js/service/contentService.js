app.service("contentService",function($http){

    this.findByCategoryId = function(categoryId){
        return $http.get("./content/findByCategoryId?id=" + categoryId);
    }
});
