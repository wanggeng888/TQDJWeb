<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html ng-app="exam">
<head>
<base href="<%=basePath%>">
<title>My JSP 'News.jsp' starting page</title>
<link rel="stylesheet" type="text/css" href="css/common.css" />
<link rel="stylesheet" type="text/css" href="css/bootstrap.min.css"/>
<script type="text/javascript" src="js/angular.min.js"></script>
<script type="text/javascript" src="js/base64.js"></script>
</head>

<body>
	<div class="container" ng-controller="examcontent">
		<div class="top-button-box text-right">
			<a href="javascript: void(0);" id="eibtn" class="btn btn-primary" ng-click="edit()">编辑</a>
			<a href="javascript:history.go(-1);" id="goback" class="btn btn-warning">返回</a>
		</div>
		<form class="form-horizontal">
			<div class="form-group">
				<label for="subject" class="col-md-2 control-label">题目</label>
				<div class="col-md-10">
					<input type="text" class="form-control" id="subject" placeholder="请输入考试题目，不能为空">
				</div>
			</div>
			<div class="form-group">
				<label for="option1" class="col-md-2 control-label">选项1</label>
				<div class="col-md-10">
					<input type="text" class="form-control" id="option1" placeholder="请输入选项1内容">
				</div>
			</div>
			<div class="form-group">
				<label for="option2" class="col-md-2 control-label">选项2</label>
				<div class="col-md-10">
					<input type="text" class="form-control" id="option2" placeholder="请输入选项2内容">
				</div>
			</div>
			<div class="form-group">
				<label for="option3" class="col-md-2 control-label">选项3</label>
				<div class="col-md-10">
					<input type="text" class="form-control" id="option3" placeholder="请输入选项3内容">
				</div>
			</div>
			<div class="form-group">
				<label for="option4" class="col-md-2 control-label">选项4</label>
				<div class="col-md-10">
					<input type="text" class="form-control" id="option4" placeholder="请输入选项4内容">
				</div>
			</div>
			<div class="form-group">
				<label for="answer" class="col-md-2 control-label">答案</label>
				<div class="col-md-10" id="option4">
					<label class="radio-inline">
						<input type="radio" name="inlineRadioOptions" id="answer1" checked> A
					</label>
					<label class="radio-inline">
						<input type="radio" name="inlineRadioOptions" id="answer2"> B
					</label>
					<label class="radio-inline">
						<input type="radio" name="inlineRadioOptions" id="answer3"> C
					</label>
					<label class="radio-inline">
						<input type="radio" name="inlineRadioOptions" id="answer4"> D
					</label>
				</div>
			</div>
			<button type="button" id="cbtn" class="btn btn-primary btn-lg col-md-8 col-md-offset-3" ng-click="create()">提交</button>
			<button type="button" id="ebtn" class="btn btn-primary btn-lg col-md-8 col-md-offset-3" ng-click="update()">更新</button>
		</form>
	</div>
	<script type="text/javascript" src="js/content.js"></script>
</body>
</html>
