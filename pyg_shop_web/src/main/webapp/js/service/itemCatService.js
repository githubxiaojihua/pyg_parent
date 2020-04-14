//服务层
app.service('itemCatService',function($http){

	//查询实体
	this.findOne=function(id){
		return $http.get('../itemCat/findOne?id='+id);
	}

	//根据parentId查询下级类目
	this.findByParentId = function(parentId){
		return $http.get("../itemCat/findByParentId?id=" + parentId);
	}
});
