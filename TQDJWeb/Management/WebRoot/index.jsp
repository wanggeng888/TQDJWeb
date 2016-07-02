<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html ng-app="index">
<head>
<base href="<%=basePath%>">
<title>首页</title>
<script type="text/javascript" src="js/angular.min.js"></script>
<link rel="stylesheet" type="text/css" href="css/bootstrap.min.css"/>
<script type="text/javascript" src="js/bootstrap.min.js"></script>
<style type="text/css">
	.login-box {
		margin-top: 200px;
		border:  1px solid silver;
	}
</style>
</head>

<body>
	<div class="container" >
		<div class="panel panel-default col-md-4 col-md-offset-4 login-box">
			<div class="panel-heading">
				<h3 class="panel-title"><b>管理员登录</b></h3>
			</div>
			<div class="panel-body" ng-controller="login-controller">
		   		<form method="post">
					<div class="form-group">
						<label for="username">用户名</label>
						<input type="text" class="form-control" id="username" ng-model="username" placeholder="username">
					</div>
					<div class="form-group">
						<label for="password">密码</label>
						<input type="password" class="form-control" id="password" ng-model="password" placeholder="password">
					</div>
					<button type="button" class="btn btn-primary col-md-12" ng-click="dologin()">登录</button>
				</form>
			</div>
		</div>
	</div>
	<script type="text/javascript">
	var index = angular.module("index", []);
	index.controller("login-controller", function($scope, $http){
		$scope.dologin = function(){
			$http({
				url : "acct/02",
				method : 'POST',
				data : {
					username : $scope.username,
					password : $scope.password
				}
			}).success(function(data) {
				if (data.code == 'success') {
					setCookies($scope.username, data.token);
					location.href = "main.jsp";
				} else {
					alert('请求错误，请刷新重试');
				}
			}).error(function(data, status) {
				alert(status);
			});
		};
	});
	
	function setCookies(username, token) {
		document.cookie = "c_username=" + username + "&c_token=" + token;
	}
	</script>
</body>
</html>
