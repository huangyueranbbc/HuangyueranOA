<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>

	<s:iterator value="#roleList">
		${id }
		${name }
		${description } 
		<s:property value="users" />
		<s:a action="roleAction_delete?id=%{id}"
			onclick="return confirm('确定要删除吗？')">删除</s:a>
		<s:a action="roleAction_editUI?id=%{id}">修改</s:a>
	</s:iterator>
	<s:a action="roleAction_addUI">添加</s:a>


	<s:debug></s:debug>
</body>
</html>