
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


2.主页面的委案金额，户数字体放大
3.增加结案如果点错了，可以取消
5.催收员可以自行选择留案时间，做一个日历插件，（过去的日期不可选，之后的日期可以到任何一天）
6.催收员已跟进和未跟进的案件要体现出来，（这一条可以加在主页，如上图）
1.人员管理-岗位，把业务部取消，单独出来，业务部分为1-10，再加一个佰仟案件
4.撤案或者留案，选择提交的时候，把原因取消改成确定-同意，再次查看的时候可以看到撤案或者留案的案件时间和操作人员
6.添加催收员多一个类别，一组，二组，三组，(可手动添加更改)
7.分配案件有3种方式，属地分单，自动分单，金额分单，可以自行选择分配案件的方式
8.上传案件的格式要简单
9.所有的案件都显示出来案件时间
10.添加归属地查找，分公司查找，催收员查找的类别
11.管理员可以改催收员的密码，催收员不能自行更改密码，管理员可以删除催收员，删除的时候如果这个催收员账号上有案件，需要提示
12.案件上传以后会给催收员分单，分单了的案件要体现出来已分单和未分单的区别
13.佰仟案件会有同一个人多个案件的情况，身份证号相同的，必须分给同一个人

