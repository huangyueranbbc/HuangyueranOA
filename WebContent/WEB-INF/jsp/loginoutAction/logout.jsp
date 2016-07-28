<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>您已退出Itcast OA系统</title>
<%@ include file="/WEB-INF/jsp/public/header.jspf"%>
<link href="<%=basePath%>style/blue/logout.css" rel="stylesheet"
	type="text/css" />
</head>

<body>
	<table border=0 cellspacing=0 cellpadding=0 width=100% height=100%>
		<tr>
			<td align=center>
				<div id=Logout>
					<div id=AwokeMsg>
						<img id=LogoutImg
							src="<%=basePath%>style/blue/images/logout/logout.gif" border=0 /><img
							id=LogoutTitle
							src="<%=basePath%>style/blue/images/logout/logout1.gif" border=0 />
					</div>
					<div id=LogoutOperate>
						<img src="<%=basePath%>style/blue/images/logout/logout2.gif"
							border=0 />

						<%-- 这个超链接不适用<s:a>标签！！ --%>
						<a href="<%=basePath%>loginoutAction_loginUI">重新进入系统</a>

						<!--
			window.close(); 可以关闭IE6~9 
			window.open('', '_self'); window.close(); 可以半闭 chrome
			FireFox不可以关闭
						-->
						<img src="<%=basePath%>style/blue/images/logout/logout3.gif"
							border=0 /> <a
							href="javascript:window.open('', '_self'); window.close();">关闭当前窗口</a>
					</div>
				</div>
			</td>
		</tr>
	</table>
</body>
</html>
