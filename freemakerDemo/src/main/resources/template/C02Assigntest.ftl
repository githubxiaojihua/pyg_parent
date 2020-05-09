<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Freemaker 入门小Demo</title>
</head>
<body>
    <#assign linkman="周先生">

    联系人：${linkman}<br/>

    <#-- 定义对象类型-->
    <#assign info={"mobile":"13233222233",'address':'北京市昌平区'}>
    电话：${info.mobile}<br/>
    地址：${info.address}
</body>
</html>