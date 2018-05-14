
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


类结构
实体类
	注解
	@Entity
	@Table(name = "t_user")
	继承
	extends BaseEntity
mapper接口
	注解
	@Mapper
	继承
	extends BaseMapper<实体类,实体ID类型>
provider类
	继承
	extends BaseProvider<实体类,实体ID类型>
service街口
	继承
	extends BaseService<实现类,实体类型ID>
service实现类
	注解
	@Service
	开启事务
	@Transactional
	继承
	extends BaseServiceImpl<实现类,实体类型ID>
	实现
	implements 实体的Service
