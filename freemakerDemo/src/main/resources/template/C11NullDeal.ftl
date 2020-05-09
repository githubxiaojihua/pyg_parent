<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Freemaker 入门小Demo</title>
</head>
<body>
   <#-- 对于null值的处理，如果不处理freemaker会报错-->
   <#-- 第一种使用if else进行判断  变量?? 当变量不为空的时候为true-->
   <#if aaa??>
        aaa变量存在
   <#else>
        aaa变量不存在
   </#if>
   <br/>
   <#-- 第二种使用！进行判断可以设置默认值或者不设置默认值，不设置默认值的时候为空就不显示-->
   aaa的值(默认值)：${aaa!'-----'}<br/>
   aaa的值(无默认值，为空则不显示): ${aaa!}
</body>
</html>