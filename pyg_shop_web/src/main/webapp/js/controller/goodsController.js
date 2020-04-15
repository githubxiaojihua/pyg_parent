 //控制层 
app.controller('goodsController' ,function($scope,$controller,goodsService,itemCatService,uploadService,typeTemplateService){
	
	$controller('baseController',{$scope:$scope});//继承
	
    //读取列表数据绑定到表单中  
	$scope.findAll=function(){
		goodsService.findAll().success(
			function(response){
				$scope.list=response;
			}			
		);
	}    
	
	//分页
	$scope.findPage=function(page,rows){			
		goodsService.findPage(page,rows).success(
			function(response){
				$scope.list=response.rows;	
				$scope.paginationConf.totalItems=response.total;//更新总记录数
			}			
		);
	}
	
	//查询实体 
	$scope.findOne=function(id){				
		goodsService.findOne(id).success(
			function(response){
				$scope.entity= response;					
			}
		);				
	}
	
	//保存 
	$scope.save=function(){				
		var serviceObject;//服务层对象  				
		if($scope.entity.id!=null){//如果有ID
			serviceObject=goodsService.update( $scope.entity ); //修改  
		}else{
			// 获取kindeditor 富文本编辑器的内容并赋值给goodsEesc的introduction属性
			$scope.entity.goodsDesc.introduction = editor.html();
			serviceObject=goodsService.add( $scope.entity  );//增加
		}				
		serviceObject.success(
			function(response){
				if(response.success){
					alert("保存成功");
					$scope.entity = {};
					// 清空富文本编辑器内容
                    editor.html("");
				}else{
					alert(response.message);
				}
			}		
		);				
	}
	
	 
	//批量删除 
	$scope.dele=function(){			
		//获取选中的复选框			
		goodsService.dele( $scope.selectIds ).success(
			function(response){
				if(response.success){
					$scope.reloadList();//刷新列表
					$scope.selectIds=[];
				}						
			}		
		);				
	}
	
	$scope.searchEntity={};//定义搜索对象 
	
	//搜索
	$scope.search=function(page,rows){			
		goodsService.search(page,rows,$scope.searchEntity).success(
			function(response){
				$scope.list=response.rows;	
				$scope.paginationConf.totalItems=response.total;//更新总记录数
			}			
		);
	};

	// 设置goods_edit.html第一级类别下拉框的值
	$scope.findByParentId = function(parentId){
        itemCatService.findByParentId(parentId).success(function(response){
			$scope.itemCat1List = response;
		});
	};

	$scope.$watch("entity.goods.category1Id",function(newValue,oldValue){
		// 根据选择的值，查询二级分类
		itemCatService.findByParentId(newValue).success(function(response){
			$scope.itemCat2List = response;
		});
	});

	$scope.$watch("entity.goods.category2Id",function(newValue,oldValue){
		// 根据选择的值，查询三级分类
		itemCatService.findByParentId(newValue).success(function(response){
			$scope.itemCat3List = response;
		});
	});

	$scope.$watch("entity.goods.category3Id",function(newValue,oldValue){
		itemCatService.findOne(newValue).success(function(response){
			$scope.entity.goods.typeTemplateId = response.typeId;
		});
	});

	// 上传图片
	$scope.uploadFile = function(){
		uploadService.uploadFile().success(function(response){
			if(response.success){
                $scope.image_entity.url = response.message;
			}else{
				alert(response.message);
			}

		});
	};

	// 初始化保存对象
	$scope.entity = {goods:{},goodsDesc:{itemImages:[]}};

	// 添加图片列表，通过数据双向绑定回显
	$scope.add_image_entity = function(){
		$scope.entity.goodsDesc.itemImages.push($scope.image_entity);
	};

	// 删除图片
	$scope.removeImageEntity = function(index){
        $scope.entity.goodsDesc.itemImages.splice(index,1);
	};

	$scope.$watch("entity.goods.typeTemplateId",function(newValue,oldValue){
		typeTemplateService.findOne(newValue).success(function(response){
			$scope.typeTemplate=response;
			// 将获取的json字符串转换成json对象
			$scope.typeTemplate.brandIds=JSON.parse($scope.typeTemplate.brandIds);
		});
	});
    
});	
