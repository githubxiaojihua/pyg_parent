<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Freemaker 入门小Demo</title>
</head>
<body>
    <#assign text="{'bank':'工商银行','account':'1234567890'}">
    <#assign data=text?eval>
    开户行：${data.bank}<br/>
    账 号: ${data.account}
</body>
</html>