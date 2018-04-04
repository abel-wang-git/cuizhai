
<html lang="en"  xmlns:th="http://www.thymeleaf.org" xmlns:shiro="http://www.pollix.at/thymeleaf/shiro">
<head>
    <meta charset="UTF-8"/>
    <title>委案管理</title>
    <div th:include="fragment/head :: head"></div>
</head>
<body>
#[[$END$]]#
</body>
<script th:src="@{/static/layui/layui.js}"></script>
<script th:inline="javascript">

/*<![CDATA[*/

/*]]>*/
</script>
</html>

一级管理员
二级管理员
催收员

总公司{所有公司（按地区分）}
    分公司（子公司（列出属于自己的所有case））
        分公司