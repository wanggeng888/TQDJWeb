<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html ng-app="newslist">
<head>
<base href="<%=basePath%>">
<title>新闻列表</title>
<link rel="stylesheet" type="text/css" href="css/common.css" />
<link rel="stylesheet" type="text/css" href="css/bootstrap.min.css"/>
<script type="text/javascript" src="js/angular.min.js"></script>
<script type="text/javascript" src="js/base64.js"></script>
</head>

<body>
	<div class="container" ng-controller="newstable">
		<div class="text-right top-button-box">
			<a href="news.jsp?type=news&fun=1" class="btn btn-primary">录入</a>
			<button type="button" class="btn btn-danger" ng-click="deleteItem()">删除</button>
		</div>
		<table class="table table-hover paging-box listtable">
			<tr class="active">
				<th width="3%"></th>
				<th width="20%" class="text-center">标题</th>
				<th width="40%" class="text-center">导语</th>
				<th width="17%" class="text-center">来源</th>
				<th width="12%" class="text-center">录入时间</th>
				<th width="8%" class="text-center">操作</th>
			</tr>
			<tr ng-repeat="news in newses">
				<td><input type="checkbox" id="checkid_{{news.id}}" ng-click="listItemsId(news.id)"></td>
				<td>{{news.title}}</td>
				<td>{{news.lead}}</td>
				<td>{{news.source}}</td>
				<td class="text-center">{{news.time}}</td>
				<td class="text-center">
					<a href="news.jsp?type=news&fun=2&id={{news.id}}" class="btn btn-success btn-xs">详情</a>
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
