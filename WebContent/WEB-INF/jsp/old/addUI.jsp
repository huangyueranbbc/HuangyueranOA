<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>addUI</title>
</head>
<body>

	<s:form action="roleAction_add">
		<s:textfield name="name" label="名称"></s:textfield>
		<s:textarea name="description" label="说明"></s:textarea>
		<s:submit value="提交"></s:submit>
	</s:form>

</body>
</html>