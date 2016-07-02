<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">
<title>管理页面</title>
<script type="text/javascript" src="js/jquery-2.1.3.min.js"></script>
<link rel="stylesheet" type="text/css" href="css/bootstrap.min.css"/>
<style type="text/css">
	.container {
		padding: 0px;
		margin: 0px;
	}
	.menu-box {
		padding: 0px;
		height: 600px;
	}
	.option-box {
		padding: 0px;
	}
</style>
</head>
<body style="border: 1px solid silver;">
	<jsp:include page="header.jsp"></jsp:include>
	<div class="container col-md-12">
		<div class="col-md-2 menu-box panel panel-default" style="margin: 0px;">
			<div class="list-group">
				<a href="javascript: void(0);" class="list-group-item active">选项</a>
				<!-- <a href="javascript: menuSelect(1);" class="list-group-item">账号信息</a> -->
				<a href="javascript: menuSelect(2);" class="list-group-item">新闻列表</a>
				<a href="javascript: menuSelect(6);" class="list-group-item">轮播新闻</a>
				<!-- <a href="javascript: menuSelect(3);" class="list-group-item">学习列表</a> -->
				<!-- <a href="javascript: menuSelect(4);" class="list-group-item">两学一做</a> -->
				<a href="javascript: menuSelect(5);" class="list-group-item">考试题目</a>
			</div>
		</div>
		<div class="col-md-10 panel panel-default option-box" style="margin: 0px;">
			<div class="embed-responsive embed-responsive-16by9">
				<iframe id="option-frame" class="embed-responsive-item" src="welcome.jsp"></iframe>
			</div>
		</div>
	</div>
	<jsp:include page="footer.jsp"></jsp:include>
	<script type="text/javascript" src="js/main.js"></script>
</body>
</html>
