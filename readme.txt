
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



合同号	客户姓名	性别	身份证	总欠款	客户手机	客户办公电话	客户配偶姓名	客户配偶联系电话	客户亲戚姓名	客户与亲戚关系	客户亲戚联系电话	其他联系人姓名	其他联系人关系	其他联系人电话	催收时间	催收记录
提线流水号	债务人姓名	催收拨打号码	催收日期	催收时间	催收方式	催收员	案件状态	催收详细情况


