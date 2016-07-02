<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html ng-app="examlist">
<head>
<base href="<%=basePath%>">
<title>新闻列表</title>
<link rel="stylesheet" type="text/css" href="css/common.css" />
<link rel="stylesheet" type="text/css" href="css/bootstrap.min.css"/>
<script type="text/javascript" src="js/angular.min.js"></script>
<script type="text/javascript" src="js/base64.js"></script>
</head>

<body>
	<div class="container" ng-controller="examtable">
		<div class="text-right top-button-box">
			<a href="exam.jsp?type=exam&fun=1" class="btn btn-primary">录入</a>
			<button type="button" class="btn btn-danger" ng-click="deleteItem()">删除</button>
		</div>
		<table class="table table-hover paging-box listtable">
			<tr class="active">
				<th width="5%"></th>
				<th width="40%" class="text-center">题目</th>
				<th width="20%" class="text-center">答案</th>
				<th width="20%" class="text-center">录入时间</th>
				<th width="15%" class="text-center">操作</th>
			</tr>
			<tr ng-repeat="exam in exames">
				<td><input type="checkbox" id="checkid_{{exam.id}}" ng-click="listItemsId(exam.id)"></td>
				<td>{{exam.subject}}</td>
				<td>{{exam.answer}}</td>
				<td class="text-center">{{exam.time}}</td>
				<td class="text-center">
					<a href="exam.jsp?type=exam&fun=2&id={{exam.id}}" class="btn btn-success btn-xs">详情</a>
				</td>
			</tr>
		</table>
		<div class="paging-box text-center" ng-show="totalpage > 1 ? true : false">
			<nav>
				<ul class="pagination">
					<li>
						<a href="javascript: void(0);" aria-label="Previous">
							<span aria-hidden="true" ng-click="dopaging(1)">首页</span>
						</a>
					</li>
					<li><a href="javascript: void(0);" ng-click="dopaging(currentpage > 1 ? currentpage - 1 : 1)">上一页</a></li>
					<li><a href="javascript: void(0);" ng-repeat="page in pageArray" ng-click="dopaging(page)">{{page}}</a></li>
					<li><a href="javascript: void(0);" ng-click="dopaging(currentpage < totalpage ? currentpage + 1 : totalpage">下一页</a></li>
			    	<li>
						<a href="javascript: void(0)" aria-label="Next">
							<span aria-hidden="true" ng-click="dopaging(totalpage)">尾页</span>
						</a>
			    	</li>
			    	<li>
						<span aria-hidden="true">当前第{{currentpage}}/{{totalpage}}页</span>
			    	</li>
				</ul>
			</nav>
		</div>
	</div>
	<script type="text/javascript" src="js/list.js"></script>
</body>
</html>
