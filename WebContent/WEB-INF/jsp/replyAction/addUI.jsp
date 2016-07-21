<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html>
<head>
<title>帖子回复</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<%@include file="/WEB-INF/jsp/public/header.jspf"%>
<link type="text/css" rel="stylesheet"
	href="<%=basePath%>style/blue/forum.css" />
<script type="text/javascript" src="<%=basePath%>fckeditor/fckeditor.js"></script>
<script type="text/javascript">
	$(function() {
		var oFCKeditor = new FCKeditor('content'); // 提交表单时本字段使用的参数名
		oFCKeditor.BasePath = "${pageContext.request.contextPath}/fckeditor/"; // 必须要有，这是指定editor文件夹所在的路径，一定要以'/'结尾
		oFCKeditor.Height = "95%";
		oFCKeditor.Width = "95%";
		oFCKeditor.ToolbarSet = "bbs";
		oFCKeditor.ReplaceTextarea(); // 替换id或name为指定值的textarea
	});
</script>
</head>
<body>

	<!-- 标题显示 -->
	<div id="Title_bar">
		<div id="Title_bar_Head">
			<div id="Title_Head"></div>
			<div id="Title">
				<!--页面标题-->
				<img border="0" width="13" height="13"
					src="<%=basePath%>style/images/title_arrow.gif" /> 帖子回复
			</div>
			<div id="Title_End"></div>
		</div>
	</div>

	<!--显示表单内容-->
	<div id="MainArea">
		<s:form action="replyAction_add" cssStyle="margin: 0; padding: 0;">
			<s:hidden name="topicId"></s:hidden>
			<div id="PageHead"></div>
			<center>
				<div class="ItemBlock_Title1">
					<div width=85% style="float: left">
						<font class="MenuPoint"> &gt; </font>
						<s:a action="forumAction_list">论坛</s:a>
						<font class="MenuPoint"> &gt; </font>
						<s:a action="forumAction_show>id=%{#topic.forum.id}">${topic.forum.name }</s:a>
						<font class="MenuPoint"> &gt;&gt; </font> 帖子回复
					</div>
				</div>
				<div class="ItemBlockBorder">
					<table border="0" cellspacing="1" cellpadding="1" width="100%"
						id="InputArea">
						<tr>
							<td class="InputAreaBg" height="30"><div class="InputTitle">帖子主题</div></td>
							<td class="InputAreaBg"><div class="InputContent">${topic.title }</div></td>
						</tr>
						<tr height="240">
							<td class="InputAreaBg"><div class="InputTitle">内容</div></td>
							<td class="InputAreaBg"><div class="InputContent">
									<s:textarea name="content" cssStyle="width:650px;height:200px;"></s:textarea>
								</div></td>
						</tr>
						<tr height="30">
							<td class="InputAreaBg" colspan="2" align="center"><input
								type="image"
								src="<%=basePath%>style/blue/images/button/submit.PNG"
								style="margin-right: 15px;" /> <a
								href="javascript:history.go(-1);"><img
									src="<%=basePath%>style/blue/images/button/goBack.png" /></a></td>
						</tr>
					</table>
				</div>
			</center>
		</s:form>
	</div>

	<div class="Description">
		说明：<br />

	</div>

</body>
</html>
