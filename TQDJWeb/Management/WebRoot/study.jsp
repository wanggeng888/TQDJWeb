<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html ng-app="study">
<head>
<base href="<%=basePath%>">
<title>My JSP 'News.jsp' starting page</title>
<link rel="stylesheet" type="text/css" href="css/common.css" />
<link rel="stylesheet" type="text/css" href="css/bootstrap.min.css"/>
<script type="text/javascript" src="js/angular.min.js"></script>
<script type="text/javascript" src="js/base64.js"></script>
</head>

<body>
	<div class="container" ng-controller="studycontent">
		<div class="top-button-box text-right">
			<a href="javascript: void(0);" id="eibtn" class="btn btn-primary" ng-click="edit()">编辑</a>
			<a href="javascript:history.go(-1);" id="goback" class="btn btn-warning">返回</a>
		</div>
		<form class="form-horizontal">
			<div class="form-group">
				<label for="title" class="col-md-2 control-label">标题</label>
				<div class="col-md-10">
					<input type="text" class="form-control" id="title" placeholder="请输入学习文章标题，不能为空">
				</div>
			</div>
			<div class="form-group">
				<label for="summary" class="col-md-2 control-label">简介</label>
				<div class="col-md-10">
					<input type="text" class="form-control" id="summary" placeholder="请输入学习文章导语">
				</div>
			</div>
			<div class="form-group">
				<label for="source" class="col-md-2 control-label">来源</label>
				<div class="col-md-10">
					<input type="text" class="form-control" id="source" placeholder="请输入学习文章来源，不能为空">
				</div>
			</div>
			<div class="form-group">
				<label for="text" class="col-md-2 control-label">正文</label>
				<div class="col-md-10">
					<textarea class="form-control" id="text" rows="8" placeholder="请输入学习文章内容，不能为空"></textarea>
				</div>
			</div>
			<button type="button" id="cbtn" class="btn btn-primary btn-lg col-md-8 col-md-offset-3" ng-click="create()">提交</button>
			<button type="button" id="ebtn" class="btn btn-primary btn-lg col-md-8 col-md-offset-3" ng-click="update()">更新</button>
		</form>
	</div>
	<script type="text/javascript" src="js/content.js"></script>
</body>
</html>
