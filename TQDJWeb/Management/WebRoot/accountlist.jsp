<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html ng-app="acctlist">
<head>
<base href="<%=basePath%>">
<title>账号列表</title>
<link rel="stylesheet" type="text/css" href="css/common.css" />
<link rel="stylesheet" type="text/css" href="css/bootstrap.min.css"/>
<script type="text/javascript" src="js/angular.min.js"></script>
<script type="text/javascript" src="js/base64.js"></script>
</head>

<body>
	<div class="container" ng-controller="accttable">
		<div class="text-right top-button-box">
		</div>
		<table class="table table-hover paging-box listtable">
			<tr class="active">
				<th width="40%" class="text-center">用户名</th>
				<th width="40%" class="text-center">昵称</th>
				<th width="20%" class="text-center">注册时间</th>
			</tr>
			<tr ng-repeat="account in accounts">
				<td class="text-center">{{account.username}}</td>
				<td class="text-center">{{account.nickname}}</td>
				<td class="text-center">{{account.regist}}</td>
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
