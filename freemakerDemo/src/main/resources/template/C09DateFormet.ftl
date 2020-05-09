<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Freemaker 入门小Demo</title>
</head>
<body>
   当前日期：${today?date}<br/>
   当前时间：${today?time}<br/>
   当前日期和时间：${today?datetime}<br/>
   日期格式化：${today?string("yyyy年MM月dd日")}
</body>
</html>