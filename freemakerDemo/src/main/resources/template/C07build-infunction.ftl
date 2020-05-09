<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Freemaker 入门小Demo</title>
</head>
<body>
    <#list goodsList as good>
        ${good_index} 商品名称：${good.name}，价格：${good.price}<br/>
    </#list>
    <#-- 内建函数-->
    共${goodsList?size}条记录
</body>
</html>