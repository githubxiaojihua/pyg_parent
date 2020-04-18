 //控制层 
app.controller('goodsController' ,function($scope,$controller,$location,goodsService,itemCatService,uploadService,typeTemplateService){
	
	$controller('baseController',{$scope:$scope});//继承

    $scope.austatus = ['未审核','已审核','审核未通过','关闭'];
	
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
	$scope.findOne=function(){
		// 这种方式可以获取ng-click传递的参数，也可以获取请求中#后面获取的参数
		var id = $location.search()["id"];
		if(id == null){
			return;
		}
		goodsService.findOne(id).success(
			function(response){
				$scope.entity= response;
				// 设置富文本内容
				editor.html($scope.entity.goodsDesc.introduction);
				// 设置图片列表，将存储的json字符串转换成json对象
				$scope.entity.goodsDesc.itemImages = JSON.parse($scope.entity.goodsDesc.itemImages);
				// 设置扩展属性
				$scope.entity.goodsDesc.customAttributeItems = JSON.parse($scope.entity.goodsDesc.customAttributeItems);
                // 回显规格属性
                $scope.entity.goodsDesc.specificationItems = JSON.parse($scope.entity.goodsDesc.specificationItems);

                //回显规格属性选项组合sku
                for(var i = 0; i<$scope.entity.itemList.length;i++){
                    $scope.entity.itemList[i].spec = JSON.parse($scope.entity.itemList[i].spec);
                }

            }
		);				
	}
	
	//保存 
	$scope.save=function(){				
		var serviceObject;//服务层对象  				
		if($scope.entity.goods.id !=null){//如果有ID
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
	$scope.entity = {goods:{},goodsDesc:{itemImages:[],specificationItems:[]}};

	// 添加图片列表，通过数据双向绑定回显
	$scope.add_image_entity = function(){
		$scope.entity.goodsDesc.itemImages.push($scope.image_entity);
	};

	// 删除图片
	$scope.removeImageEntity = function(index){
        $scope.entity.goodsDesc.itemImages.splice(index,1);
	};

	// 监控模版的变化更新品牌、扩展属性以及规格和规格选项
	$scope.$watch("entity.goods.typeTemplateId",function(newValue,oldValue){
		typeTemplateService.findOne(newValue).success(function(response){
			$scope.typeTemplate=response;
			// 将获取的json字符串转换成json对象
			$scope.typeTemplate.brandIds=JSON.parse($scope.typeTemplate.brandIds);
			// 此行与修改页面加载的时候出现冲突，因为根据ID加载数据后，由于typeTemplateId更改了
			// 监控程序会自动执行此方法，下面这句会将已经加载的数据进行覆盖所以要进行判断
			// 如果存在id则不执行此行
			if($location.search()["id"] == null){
                $scope.entity.goodsDesc.customAttributeItems = JSON.parse($scope.typeTemplate.customAttributeItems);
			}

		});
		typeTemplateService.findSpecList(newValue).success(function(response){
			$scope.specList = response;
		});
	});

	// 更新规格选项
	$scope.updateSpecAttribute = function($event,name,value){
		var specItems = $scope.entity.goodsDesc.specificationItems;
		for(var i=0; i<specItems.length; i++){
			if(specItems[i].attributeName == name){
				if($event.target.checked){
					specItems[i].attributeValue.push(value);
				}else{
                    specItems[i].attributeValue.splice(specItems[i].attributeValue.indexOf(value),1);
				}
				return;
			}
		}
		specItems.push({"attributeName":name,"attributeValue":[value]});
	};

    // 定义函数,封装规格选项组合成商品最小销售单元
    $scope.createSkuItemList = function() {

        // 初始化规格数据组合
        $scope.entity.itemList = [ {
            spec : {},
            price : 999999,
            stockCount : 0,
            status : '0',
            isDefault : '0'
        } ];
        // 获取选中规格属性值
        // [{"attributeName":"网络","attributeValue":["电信2G","联通2G"]},{"attributeName":"机身内存","attributeValue":["16G","64G"]}]
        var specList = $scope.entity.goodsDesc.specificationItems;

        // 循环规格属性值,组合sku最小销售单元商品数据
        for (var i = 0; i < specList.length; i++) {
            // 第一次循环:$scope.entity.itemList =
            // [{spec:{"网络":"电信2G"},price:999999,stockCount:0,status:'0',idDefault:'0'},{spec:{"网络":"联通2G"},price:999999,stockCount:0,status:'0',idDefault:'0'}]
            // 添加一列
            $scope.entity.itemList = addColumn($scope.entity.itemList,
                specList[i].attributeName, specList[i].attributeValue);

        };


    };

    addColumn = function(list, name, columnValues) {

        var newList = [];

        // 第一次循环数据:[{spec:{},price:999999,stockCount:0,status:'0',idDefault:'0'}];
        // 第二次循环数据:[{spec:{"网络":"电信2G"},price:999999,stockCount:0,status:'0',idDefault:'0'},{spec:{"网络":"联通2G"},price:999999,stockCount:0,status:'0',idDefault:'0'}]
        // 循环list集合数据 2
        for (var i = 0; i < list.length; i++) {

            // 第一次循环第一个对象:{spec:{},price:999999,stockCount:0,status:'0',idDefault:'0'}
            // 第二次循环第一个对象:{spec:{"网络":"电信2G"},price:999999,stockCount:0,status:'0',idDefault:'0'}
            // 获取一个旧的对象
            var oldRow = list[i];
            // 第一次循环:columnValues:["电信2G","联通2G"]
            // 第二次循环:columnValues:["16G","64G"]
            // 第二个循环
            for (var j = 0; j < columnValues.length; j++) {
                // 第一次克隆:{spec:{},price:999999,stockCount:0,status:'0',idDefault:'0'}
                // 第二次克隆:
                // {spec:{"网络":"电信2G"},price:999999,stockCount:0,status:'0',idDefault:'0'}
                // 深克隆操作,新创建一行数据
                var newRow = JSON.parse(JSON.stringify(oldRow));
                // {spec:{"网络":"电信2G",:"机身内存":"4G"},price:999999,stockCount:0,status:'0',idDefault:'0'}
                newRow.spec[name] = columnValues[j];

                // j:循环第一次:{spec:{"网络":"电信2G"},price:999999,stockCount:0,status:'0',idDefault:'0'}
                // j:循环第二次:{spec:{"网络":"联通2G"},price:999999,stockCount:0,status:'0',idDefault:'0'}
                // 推送集合
                newList.push(newRow);
            }
        }
        // [{spec:{"网络":"电信2G"},price:999999,stockCount:0,status:'0',idDefault:'0'},{spec:{"网络":"联通2G"},price:999999,stockCount:0,status:'0',idDefault:'0'}]
        return newList;

    };

    $scope.itemCatList = [];
    //读取列表数据绑定到表单中
    $scope.findItemCatList=function(){
        itemCatService.findAll().success(
            function(response){
               for(var i=0; i<response.length; i++){
               		$scope.itemCatList[response[i].id] = response[i].name;
			   }
            }
        );
    };

    // 定义检测规格选项是否选中的函数
    // 选中:return true
    // 未选中: return false
    $scope.isAttributeChecked = function(specName, value) {
        // 获取商品规格属性值
        var specList = $scope.entity.goodsDesc.specificationItems;
        // 循环规格属性
        for (var i = 0; i < specList.length; i++) {
            // 判断选择的属性属于那个属性分类
            if (specList[i].attributeName == specName) {
                // 判断规格选项是否包含此时选中选项值
                if (specList[i].attributeValue.indexOf(value) >= 0) {

                    return true;

                }
            }

        }

        return false;

    };


});	
