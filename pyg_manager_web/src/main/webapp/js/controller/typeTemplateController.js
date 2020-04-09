 //控制层 用到了多个service，注意页面也要引入相应的service js文件
app.controller('typeTemplateController' ,function($scope,$controller,typeTemplateService,brandService,specificationService){
	
	$controller('baseController',{$scope:$scope});//继承
	
    //读取列表数据绑定到表单中  
	$scope.findAll=function(){
		typeTemplateService.findAll().success(
			function(response){
				$scope.list=response;
			}			
		);
	}    
	
	//分页
	$scope.findPage=function(page,rows){			
		typeTemplateService.findPage(page,rows).success(
			function(response){
				$scope.list=response.rows;	
				$scope.paginationConf.totalItems=response.total;//更新总记录数
			}			
		);
	}
	
	//查询实体 
	$scope.findOne=function(id){				
		typeTemplateService.findOne(id).success(
			function(response){
				$scope.entity= response;
				// 从数据库中查询出来的是字符串，需要转换成json对象才能进行双向绑定，并显示。
				$scope.entity.brandIds = JSON.parse($scope.entity.brandIds);
				$scope.entity.specIds = JSON.parse($scope.entity.specIds);
				$scope.entity.customAttributeItems = JSON.parse($scope.entity.customAttributeItems);

			}
		);				
	}
	
	//保存 
	$scope.save=function(){				
		var serviceObject;//服务层对象  				
		if($scope.entity.id!=null){//如果有ID
			serviceObject=typeTemplateService.update( $scope.entity ); //修改  
		}else{
			serviceObject=typeTemplateService.add( $scope.entity  );//增加 
		}				
		serviceObject.success(
			function(response){
				if(response.success){
					//重新查询 
		        	$scope.reloadList();//重新加载
				}else{
					alert(response.message);
				}
			}		
		);				
	}
	
	 
	//批量删除 
	$scope.dele=function(){			
		//获取选中的复选框			
		typeTemplateService.dele( $scope.selectIds ).success(
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
		typeTemplateService.search(page,rows,$scope.searchEntity).success(
			function(response){
				$scope.list=response.rows;	
				$scope.paginationConf.totalItems=response.total;//更新总记录数
			}			
		);
	};
	
	//模版表存储数据格式:
	//品牌:
	//[{"id":16,"text":"TCL"},{"id":13,"text":"长虹"},{"id":14,"text":"海尔"},{"id":19,"text":"创维"},{"id":21,"text":"康佳"},{"id":18,"text":"夏普"},{"id":17,"text":"海信"},{"id":20,"text":"东芝"},{"id":15,"text":"飞利浦"},{"id":22,"text":"LG"}]
	//规格数据格式:
	//[{"id":33,"text":"电视屏幕尺寸"}]
	//扩展属性:
	//




	// 使用brandService方法实现查询
	// 使用下面这一句模拟后端数据调用
    //$scope.brandList = {data:[{id:1,text:"联想"},{id:2,text:"华为"},{id:3,text:"小米"}]};
	$scope.brandList = {data:[]};
	// 读取品牌列表
	$scope.findBrandList = function(){
		brandService.selectOptionList().success(function(response){
			$scope.brandList = {data:response};
		});
	};
	// 可以在这里调用，则加载的时候自动调用，也可以在页面元素上选择点击事件调用
    $scope.findBrandList();

    $scope.specList = {data:[]};
    $scope.findSpecList = function(){
        specificationService.selectOptionList().success(function(response){
        	$scope.specList = {data:response};
		});
    };
    $scope.findSpecList();

    // 新增扩展属性行
	$scope.addTableRow = function(){
		$scope.entity.customAttributeItems.push({});
	};

	// 删除扩展属性行
	$scope.deleTableRow = function(index){
		$scope.entity.customAttributeItems.splice(index,1);
	};

});	
